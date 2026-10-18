#!/usr/bin/env python3
"""Deterministic equipment-only importer. Requires Pillow; never modifies the source packs."""
import argparse, copy, json, math, re, shutil
from pathlib import Path
from PIL import Image
from recipe_materials import REUSED, material_tags, FORGE_VANILLA
ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT/'common/src/main/resources/assets/thirtyyearswar'
DECORATION_ASSETS = ROOT/'common/src/main/resources/assets/magistuarmory'
JAVA = ROOT/'common/src/main/java/com/magistuarmory/thirtyyearswar'
REPAIRS=[]
def read(p):
    text=p.read_text(encoding='utf-8-sig')
    text=re.sub(r'//[^\n]*','',text)
    if '"menu_category": {l' in text:
        REPAIRS.append(str(p)); text=text.replace('"menu_category": {l','"menu_category": {')
    return json.loads(re.sub(r',\s*([}\]])',r'\1',text))
def write(p,v):
    p.parent.mkdir(parents=True,exist_ok=True)
    p.write_text(json.dumps(v,indent=2)+'\n' if not isinstance(v,str) else v)
def texture_path(p):
    if p.exists(): return p
    if p.with_suffix(".tga").exists(): return p.with_suffix(".tga")
    if "30YWRP" in p.parts: return texture_path(Path(str(p).replace("30YWRP", "BaroqueRP")))
    raise FileNotFoundError(p)

def cp(src,dst):
    assert src.is_file(),src
    dst.parent.mkdir(parents=True,exist_ok=True);shutil.copyfile(src,dst)
def f(n): return f'{float(n):.6f}F'
def vec(v):return ', '.join(map(f,v))
def j(s):return json.dumps(s)
def model(g):
    """Bedrock Y-up world pivots -> Java Y-down parent-relative poses."""
    roots={'head':[0,24,0],'hat':[0,24,0],'body':[0,24,0],'right_arm':[-5,22,0],'left_arm':[5,22,0],'right_leg':[-1.9,12,0],'left_leg':[1.9,12,0]}
    rename={'rightArm':'right_arm','leftArm':'left_arm','rightLeg':'right_leg','leftLeg':'left_leg'}
    lines=['MeshDefinition mesh = new MeshDefinition();','PartDefinition root = mesh.getRoot();']
    for n,p in roots.items():lines.append(f'PartDefinition {n} = root.addOrReplaceChild("{n}", CubeListBuilder.create(), PartPose.offset({vec([p[0],24-p[1],p[2]])}));')
    bones={b['name']:b for b in g['bones']}; done={}; count=0
    def bone(b):
        nonlocal count
        n=b['name']
        if n in done:return done[n]
        parent=b.get('parent'); piv=b.get('pivot',[0,0,0]); name=rename.get(n,n)
        if parent: par=bone(bones[parent]); pp=bones[parent].get('pivot',[0,0,0])
        else: par=name if name in roots else 'root';pp=roots.get(name,[0,24,0])
        v='b'+str(count);count+=1
        rot=b.get('rotation',[0,0,0]);delta=[piv[0]-pp[0],pp[1]-piv[1],piv[2]-pp[2]]
        lines.append(f'PartDefinition {v} = {par}.addOrReplaceChild({j("source_"+n)}, CubeListBuilder.create(), PartPose.offsetAndRotation({vec(delta)}, {vec([-math.radians(rot[0]),-math.radians(rot[1]),math.radians(rot[2])])}));')
        done[n]=v
        for i,c in enumerate(b.get('cubes',[])):
            o=c['origin']; sz=c['size']; pivot=c.get('pivot',piv);r=c.get('rotation',[0,0,0]);uv=c['uv'];assert isinstance(uv,list)
            xyz=[o[0]-pivot[0],pivot[1]-o[1]-sz[1],o[2]-pivot[2]]
            delta=[pivot[0]-piv[0],piv[1]-pivot[1],pivot[2]-piv[2]]
            lines.append(f'{v}.addOrReplaceChild("cube_{i}", CubeListBuilder.create().texOffs({int(uv[0])}, {int(uv[1])}).mirror({str(c.get("mirror",b.get("mirror",False))).lower()}).addBox({vec(xyz)}, {vec(sz)}, new CubeDeformation({f(c.get("inflate",0))})), PartPose.offsetAndRotation({vec(delta)}, {vec([-math.radians(r[0]),-math.radians(r[1]),math.radians(r[2])])}));')
        return v
    for b in g['bones']:bone(b)
    d=g['description'];lines.append(f'return LayerDefinition.create(mesh, {d["texture_width"]}, {d["texture_height"]});')
    return '\n        '.join(lines)
def split_dye(im):
    # Bedrock change-color textures reserve low, nonzero alpha for undyed detail.
    base=Image.new('RGBA',im.size); overlay=Image.new('RGBA',im.size)
    pixels=list(im.getdata())
    # Neutral white/grayscale tint masks preserve shading without baking in a hue.
    peak=max((max(r,g,b) for r,g,b,a in pixels if a>=128),default=255) or 255
    base.putdata([(round(max(r,g,b)*255/peak),)*3+(255 if a>=128 else 0,) if a>=128 else (0,0,0,0) for r,g,b,a in pixels])
    overlay.putdata([(r,g,b,255 if 0<a<128 else 0) for r,g,b,a in pixels])
    return base,overlay

def atlas(g,texture):
    """Repack Bedrock per-face UVs into Java box UVs, including reversed/rotated faces."""
    g=copy.deepcopy(g)
    # Java only displays torso/arms for a chest slot. Keep the full suit's tassets
    # under the torso so they do not disappear with the vanilla leg visibility mask.
    if g['description']['identifier'] in ['geometry.knights_pikeman_armour','geometry.knights_baroque_cavalry_armour','geometry.knights_baroque_cuirassier_armour']:
        for b in g['bones']:
            if b['name'] in ['rightLeg','leftLeg']:b['parent']='body'
    im=Image.open(texture).convert('RGBA');w,h=im.size; patches=[];y=h
    for b in g['bones']:
        for c in b.get('cubes',[]):
            if isinstance(c['uv'],list):continue
            x,cy,z=[math.ceil(v) for v in c['size']]; cy=max(cy,1)
            # A tiny thickness retains both sides of single-plane Bedrock elements.
            c['size']=[max(v,0.01) for v in c['size']]
            layout={'west':(0,z,z,cy),'north':(z,z,x,cy),'east':(z+x,z,z,cy),'south':(2*z+x,z,x,cy),'up':(z,0,x,z),'down':(z+x,0,x,z)}
            for face,(dx,dy,dw,dh) in layout.items():
                uv=c['uv'].get(face)
                if not uv or not dw or not dh:continue
                u,v=uv['uv'];uw,vh=uv.get('uv_size',[dw,dh])
                if not uw or not vh:continue
                tile=im.crop((min(u,u+uw),min(v,v+vh),max(u,u+uw),max(v,v+vh)))
                if uw<0:tile=tile.transpose(Image.Transpose.FLIP_LEFT_RIGHT)
                if vh<0:tile=tile.transpose(Image.Transpose.FLIP_TOP_BOTTOM)
                if uv.get('uv_rotation'):tile=tile.rotate(-uv['uv_rotation'],expand=True)
                tile=tile.resize((dw,dh),Image.Resampling.NEAREST)
                patches.append((tile,(dx,y+dy)))
            c['uv']=[0,y];y+=z+cy+2;w=max(w,2*x+2*z)
    if patches:
        w=2**math.ceil(math.log2(w));h=2**math.ceil(math.log2(y));out=Image.new('RGBA',(w,h));out.paste(im,(0,0))
        for tile,pos in patches:out.paste(tile,pos)
        im=out
    g['description']['texture_width']=im.width;g['description']['texture_height']=im.height
    return g,im


def recipes(source,catalog,loader):
    available={e['id'] for e in catalog}; written=set()
    decorations={e['id'] for e in catalog if e['kind']=='decoration'}
    def item_id(id):return f'magistuarmory:{id}_decoration' if id in decorations else 'thirtyyearswar:'+id
    replacements=material_tags(loader)
    def ingredient(v):
        item=v['item'].lower();id=item.split(':')[-1]
        if item.startswith('knights:'):
            if id in replacements:return {'tag':replacements[id]}
            if id not in available:raise ValueError('Unknown ingredient '+item)
            item=item_id(id)
        elif id in available and item.startswith('thirtyyearswar:'):
            item=item_id(id)
        elif item=='minecraft:wool':return {'tag':'minecraft:wool'}
        elif item=='minecraft:oak_planks':return {'tag':'minecraft:planks'}
        elif item=='minecraft:iron_nugget':return {'tag':'c:iron_nuggets' if loader=='fabric' else 'forge:nuggets/iron'}
        elif item=='minecraft:dye':item='minecraft:black_dye'
        if loader=='forge' and item in FORGE_VANILLA:return {'tag':FORGE_VANILLA[item]}
        return {'item':item}
    folder=ROOT/f'{loader}/src/main/resources/data/thirtyyearswar/recipes'
    for p in sorted((source/'BaroqueBP/recipes').rglob('*.json')):
        raw=read(p)
        for key,d in raw.items():
            if not key.startswith('minecraft:recipe_') or not isinstance(d,dict):continue
            if key=='minecraft:recipe_furnace':
                out=d['output']['item'].split(':')[-1]
                if out in available:
                    write(folder/(p.stem+'.json'),{'type':'minecraft:smelting','ingredient':ingredient(d['input']),'result':item_id(out),'experience':0.1,'cookingtime':200});written.add(out)
                continue
            result=d.get('result',{})
            if not isinstance(result,dict):continue
            out=result.get('item','').split(':')[-1]
            if out not in available:continue
            r={'type':key.replace('recipe_','crafting_'),'category':'equipment','result':{'item':item_id(out),'count':result.get('count',1)}}
            if 'pattern' in d:
                width=max(map(len,d['pattern']));r['pattern']=[row.ljust(width) for row in d['pattern']]
                r['key']={k:ingredient(v) for k,v in d['key'].items() if k in ''.join(r['pattern'])}
            elif 'ingredients' in d:
                r['ingredients']=[ingredient(v) for v in d['ingredients'] for _ in range(v.get('count',1))]
            else:continue
            # Two source rapier upgrades accidentally require their own output.
            if out in ['baroque_steel_cuphilt_rapier','baroque_steel_swepthilt_rapier']:
                for value in r.get('key',{}).values():
                    if value.get('item')=='thirtyyearswar:'+out:value['item']='thirtyyearswar:baroque_steel_rapier'
            write(folder/(p.stem+'.json'),r);written.add(out)
    # Unlock source recipes as soon as any one of their ingredients is discovered.
    for path in folder.glob('*.json'):
        recipe=read(path)
        ingredients=list(recipe.get('key',{}).values())+recipe.get('ingredients',[])+([recipe['ingredient']] if 'ingredient' in recipe else [])
        criteria={f'ingredient_{i}':{'trigger':'minecraft:inventory_changed','conditions':{'items':[({'tag':v['tag']} if 'tag' in v else {'items':[v['item']]})]}} for i,v in enumerate(ingredients)}
        write(ROOT/f'{loader}/src/main/resources/data/thirtyyearswar/advancements/recipes/{path.stem}.json',{'parent':'minecraft:recipes/root','criteria':criteria,'requirements':[list(criteria)],'rewards':{'recipes':['thirtyyearswar:'+path.stem]}})
    write(ROOT/f'docs/recipe-report-{loader}.json',{'crafted':sorted(written),'without_recipe':sorted(available-written),'material_substitutions':replacements,'recipe_repairs':['Cup-hilt and swept-hilt rapier upgrades use the base rapier instead of requiring their own output.']})

def main(source):
    old=ROOT/'docs/import-report.json'
    if old.exists():
        for e in read(old)['items']:
            id=e['id']
            for folder in ['models/item','textures/item','textures/models/armor']:
                for path in (ASSETS/folder).glob(id+'.*'):path.unlink()
                for path in (ASSETS/folder).glob(id+'_*.png'):path.unlink()
            if e['kind']=='decoration':
                (DECORATION_ASSETS/f'models/item/{id}_decoration.json').unlink(missing_ok=True)
    # These directories contain only importer-owned recipes and unlocks.
    for loader in ['common','fabric','forge']:
        for sub in ['recipes','advancements/recipes']:
            for path in (ROOT/f'{loader}/src/main/resources/data/thirtyyearswar/{sub}').glob('*.json'):path.unlink()
    # Remove obsolete duplicate material tags from earlier imports.
    for namespace, tags in [('c',['ingots/steel','steel_ingots','nuggets/steel','steel_nuggets']), ('forge',['ingots/steel','nuggets/steel'])]:
        for tag in tags:
            (ROOT/f'common/src/main/resources/data/{namespace}/tags/items/{tag}.json').unlink(missing_ok=True)
    (ROOT/'docs/recipe-report.json').unlink(missing_ok=True)
    (ASSETS/'equipment.json').unlink(missing_ok=True)
    attaches={};geos={};icons={};items={};excluded=[];catalog=[];methods={};lang={};decoration_lang={}
    for pack in ['Baroque','30YW']:
        rp=source/(pack+'RP')
        for p in sorted((rp/'attachables').rglob('*.json')):
            a=read(p)['minecraft:attachable']['description']
            if p.parent.name in ['black','red','green','yellow']:
                a['identifier']='knights:baroque_'+p.parent.name+'_'+p.stem.removeprefix('knights_')
            attaches[a['identifier']]=(a,rp)
        for p in sorted((rp/'models').rglob('*.json')):
            for g in read(p).get('minecraft:geometry',[]):geos[g['description']['identifier']]=g
        for key,v in read(rp/'textures/item_texture.json')['texture_data'].items():icons[key]=(v['textures'],rp)
        for p in sorted((source/(pack+'BP')/'items').rglob('*.json')):
            d=read(p)['minecraft:item'];items[d['description']['identifier']]=(d,p)
    for source_id,(d,p) in sorted(items.items()):
        c=d['components'];id=source_id.split(':')[1];wear=c.get('minecraft:wearable');kind=''
        if id in REUSED:
            excluded.append({'id':source_id,'reason':'Uses Epic Knights ingredient and loader-specific tags'});continue
        if any(id.startswith('baroque_'+color+'_') for color in ['black','red','green','yellow']):
            excluded.append({'id':source_id,'reason':'Merged into dyeable base item'});continue
        if wear:kind='decoration' if any(x in id for x in ['cloak','sash']) else 'armor'
        elif 'minecraft:shooter' in c:kind='gun'
        elif 'minecraft:damage' in c:kind='weapon'
        elif id=='paper_cartridge':kind='ammo'
        elif p.parent.name=='recipe_items':kind='ingredient'
        if not kind:excluded.append({'id':source_id,'reason':'Outside equipment and crafting ingredients scope'});continue
        e={'id':id,'source':str(p.relative_to(source)),'kind':kind,'durability':c.get('minecraft:durability',{}).get('max_durability',0)}
        name=c.get('minecraft:display_name',{}).get('value',id.replace('_',' ').title()).split('\n')[0].strip();name=re.sub('§.','',name)
        for col in ['black','red','green','yellow']:
            if id.startswith('baroque_'+col+'_'):name=col.title()+' '+name
        if kind=='decoration':decoration_lang[f'item.magistuarmory.{id}_decoration']=name
        else:lang['item.thirtyyearswar.'+id]=name
        icon=c['minecraft:icon']
        if isinstance(icon,dict):icon=icon.get('texture',icon.get('textures',{}).get('default'))
        path,rp=icons[icon]
        if isinstance(path,list):path=path[0]
        if isinstance(path,dict):path=path['path']
        icon_image=Image.open(texture_path(rp/(path+'.png'))).convert('RGBA')
        icon_target=ASSETS/f'textures/item/{id}.png';icon_target.parent.mkdir(parents=True,exist_ok=True);icon_image.save(icon_target)
        itemmodel={'parent':'minecraft:item/handheld' if kind in ['gun','weapon'] else 'minecraft:item/generated','textures':{'layer0':f'thirtyyearswar:item/{id}'}}
        blocking_model=None
        if kind=='weapon':
            e['damage']=c['minecraft:damage'].get('value',0) if isinstance(c['minecraft:damage'],dict) else c['minecraft:damage'];e['polearm']=any(s in id for s in ['pike','halberd','partisan','pollax'])
            itemmodel['overrides']=[{'predicate':{'blocking':1},'model':f'thirtyyearswar:item/{id}_blocking'}]
            if e['polearm']:
                itemmodel['display']={
                    'thirdperson_righthand':{'rotation':[-55,-90,0],'translation':[0,13,-1],'scale':[2.8125,2.8125,1]},
                    'thirdperson_lefthand':{'rotation':[-55,90,0],'translation':[0,13,-1],'scale':[2.8125,2.8125,1]},
                    'firstperson_righthand':{'rotation':[-60,-90,30],'translation':[1.13,7.5,-1.62],'scale':[2.25,2.25,1]},
                    'firstperson_lefthand':{'rotation':[-60,90,-30],'translation':[-1.13,7.5,-1.62],'scale':[2.25,2.25,1]},
                    'gui':{'scale':[1.25,1.25,1]}}
                blocking_pose={
                    'firstperson_righthand':{'rotation':[30,-23,122],'translation':[-9.8,6.25,-1.55],'scale':[2.25,2.25,1]},
                    'firstperson_lefthand':{'rotation':[-150,23,58],'translation':[-9.8,6.25,-1.55],'scale':[2.25,2.25,1]}}
            else:
                itemmodel['display']={
                    'thirdperson_righthand':{'rotation':[-50,-90,0],'translation':[0,14,0.2],'scale':[1.875,1.875,1]},
                    'thirdperson_lefthand':{'rotation':[-50,90,0],'translation':[0,14,0.2],'scale':[1.875,1.875,1]},
                    'firstperson_righthand':{'rotation':[0,-90,30],'translation':[1.13,11,1.13],'scale':[1.5,1.5,1]},
                    'firstperson_lefthand':{'rotation':[0,90,-30],'translation':[1.13,11,1.13],'scale':[1.5,1.5,1]},
                    'gui':{'scale':[1.35,1.35,1]}}
                blocking_pose={
                    'firstperson_righthand':{'rotation':[129,-35,180],'translation':[-8,9.25,-4.75],'scale':[1.5,1.5,1]},
                    'firstperson_lefthand':{'rotation':[-51,35,0],'translation':[-8,9.25,-4.75],'scale':[1.5,1.5,1]}}
            blocking_model=copy.deepcopy(itemmodel)
            blocking_model.pop('overrides',None)
            blocking_model['display'].update(blocking_pose)
        if wear or kind=='gun':
            a,rp=attaches[source_id];gid=a['geometry']['default'];g=geos[gid];e['geometry']=gid
            tex=texture_path(rp/(a['textures']['default']+'.png'));g,im=atlas(g,tex)
            modelid=re.sub('[^a-z0-9_]','_',gid.removeprefix('geometry.'))
            # Atlas layout depends on geometry, not the color variant.
            methods[modelid]=model(g);e['model']=modelid
            e['dyeable']='minecraft:dyeable' in c
            e['color']=0xffffff
            e['wearable']=kind=='decoration' and 'cloak' in id
            if e['dyeable']:
                icon_base,icon_overlay=split_dye(icon_image)
                icon_base.save(icon_target)
                icon_overlay.save(icon_target.with_name(id+'_overlay.png'))
                itemmodel['textures']['layer1']=f'thirtyyearswar:item/{id}_overlay'
            if wear:
                e['slot']='CHESTPLATE' if kind=='decoration' else {'slot.armor.head':'HELMET','slot.armor.chest':'CHESTPLATE','slot.armor.legs':'LEGGINGS','slot.armor.feet':'BOOTS'}[wear['slot']]
                e['defense']=wear.get('protection',0);e['enchantability']=c.get('minecraft:enchantable',{}).get('value',9)
                tags=c.get('minecraft:tags',{}).get('tags',[])
                e['toughness']=next((float(x.split('-')[-1]) for x in tags if 'toughness-' in x),0)
                e['knockback']=next((float(x.split('-')[-1])/10 for x in tags if 'knockback-' in x),0)
                folder='textures/models/armor'
                if kind=='decoration':
                    names=[id,id+'_layer_1'] if e['wearable'] else [id]
                else:
                    names=[id+'_layer_1'] + ([id+'_layer_2'] if e['slot']=='LEGGINGS' else [])
            else:
                folder='textures/item';names=[id+'_model'];e['reload_ticks']=round(c.get('minecraft:use_modifiers',{}).get('use_duration',2.25)*20)
                itemmodel={'parent':'builtin/entity','textures':{'particle':f'thirtyyearswar:item/{id}'},'display':{'gui':{'rotation':[0,0,45],'scale':[0.6,0.6,0.6]},'ground':{'scale':[0.4,0.4,0.4]},'fixed':{'scale':[0.6,0.6,0.6]}}} 
            for name in names:
                target=ASSETS/f'{folder}/{name}.png';target.parent.mkdir(parents=True,exist_ok=True)
                if e['dyeable']:
                    base,overlay=split_dye(im);base.save(target);overlay.save(target.with_name(name+'_overlay.png'))
                else:im.save(target)
        model_root=DECORATION_ASSETS/'models/item' if kind=='decoration' else ASSETS/'models/item'
        model_name=id+'_decoration' if kind=='decoration' else id
        write(model_root/f'{model_name}.json',itemmodel)
        if blocking_model:
            write(model_root/f'{id}_blocking.json',blocking_model)
        catalog.append(e)
    armor_textures=ASSETS/'textures/models/armor'
    valid_layer_2={f"{e['id']}_layer_2.png" for e in catalog if e['kind']=='armor' and e.get('slot')=='LEGGINGS'}
    valid_layer_2.update(f"{e['id']}_layer_2_overlay.png" for e in catalog
                         if e['kind']=='armor' and e.get('slot')=='LEGGINGS' and e.get('dyeable'))
    for path in armor_textures.glob('*_layer_2*.png'):
        if path.name not in valid_layer_2:path.unlink()
    # The catalog is audit metadata only. Runtime items/configs are explicit Java declarations.
    lang.update({'death.attack.thirtyyearswar.bullet':'%1$s was shot by %2$s','death.attack.thirtyyearswar.bullet.item':'%1$s was shot by %2$s using %3$s'})
    write(ASSETS/'lang/en_us.json',lang)
    write(DECORATION_ASSETS/'lang/en_us.json',decoration_lang)
    for loader in ['fabric','forge']:recipes(source,catalog,loader)
    write(ROOT/'docs/import-report.json',{'source_packs':['Baroque','30YW'],'items':catalog,'excluded':excluded,'source_repairs':[str(Path(x).relative_to(source)) for x in REPAIRS],'notes':['No shield item definitions exist in these packs.','Unregistered resource-only shell rapier and flags are not included.']})
    methods_text='\n\n'.join(f'    public static LayerDefinition {n}() {{\n        {body}\n    }}' for n,body in sorted(methods.items()))
    write(JAVA/'client/EquipmentGeometry.java','''package com.magistuarmory.thirtyyearswar.client;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

/** Generated by tools/import_bedrock.py; edit the converter, not this file. */
public final class EquipmentGeometry {
'''+methods_text+'\n}\n')
    registrations=[]
    for e in catalog:
        if e['kind']=='armor' or e.get('wearable'):registrations.append(f'addArmorModel("{e["model"]}", EquipmentGeometry::{e["model"]});')
        if e['kind']=='decoration':registrations.append(f'addDecorationModel("{e["id"]}", EquipmentGeometry::{e["model"]});')
    write(JAVA/'client/EquipmentModels.java','''package com.magistuarmory.thirtyyearswar.client;
import com.magistuarmory.api.client.render.model.ModModelsProvider;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
public final class EquipmentModels extends ModModelsProvider {
    public EquipmentModels() {
        super(ThirtyYearsWar.ID);
        '''+'\n        '.join(dict.fromkeys(registrations))+'\n    }\n}\n')
    # Only firearm sounds are in scope.
    sounds={}
    defs=read(source/'BaroqueRP/sounds/sound_definitions.json')['sound_definitions']
    for key,v in defs.items():
        if any(s in key for s in ['musket','pistol','reload']):
            sound=[]
            for s in v['sounds']:
                path=s if isinstance(s,str) else s['name'];src=source/'BaroqueRP'/(path+'.ogg')
                if src.exists():cp(src,ASSETS/(path+'.ogg'));sound.append({'name':'thirtyyearswar:'+path.removeprefix('sounds/')})
            if sound:sounds[key]={'sounds':sound}
    sounds['gunshot']=next(v for k,v in sounds.items() if 'shoot' in k)
    write(ASSETS/'sounds.json',sounds)
    cp(source/'BaroqueRP/pack_icon.png',ASSETS/'icon.png')
    from generate_registrations import generate
    generate(catalog)
    print('Imported',len(catalog),'items;',len(methods),'geometries;',len(excluded),'excluded')
if __name__=='__main__':
    parser=argparse.ArgumentParser();parser.add_argument('source',type=Path);args=parser.parse_args();main(args.source)
