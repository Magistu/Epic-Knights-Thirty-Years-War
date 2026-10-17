#!/usr/bin/env python3
"""Check registry/resource/recipe closure without Minecraft or external packages."""
import json,re,struct,zipfile
from recipe_materials import REUSED, material_tags, FORGE_VANILLA
from pathlib import Path
from collections import Counter
ROOT=Path(__file__).resolve().parents[1]
RES=ROOT/'common/src/main/resources'
ASSETS=RES/'assets/thirtyyearswar'
def read(p):return json.loads(p.read_text())
def check(value,message):
    if not value:raise AssertionError(message)
items=read(ROOT/'docs/import-report.json')['items'];ids={e['id'] for e in items}
check(not (ASSETS/'equipment.json').exists(),'Runtime JSON catalog must not exist')
registry=(ROOT/'common/src/main/java/com/magistuarmory/thirtyyearswar/item/AddonItems.java').read_text()
registered=set(re.findall(r'public static final RegistrySupplier<[^>]+> ([A-Z_]+) =',registry))
check(registered=={id.upper() for id in ids},'Java declarations do not match audit inventory')
check(not any(re.match(r'baroque_(black|green|red|yellow)_',id) for id in ids),'Color variants remain')
check(len(ids)==len(items)==74,'Unexpected or duplicate item IDs')
check(Counter(e['kind'] for e in items)=={'armor':54,'decoration':5,'weapon':9,'gun':2,'ammo':1,'ingredient':3},'Unexpected scope')
lang=read(ASSETS/'lang/en_us.json');geo=(ROOT/'common/src/main/java/com/magistuarmory/thirtyyearswar/client/EquipmentGeometry.java').read_text()
for e in items:
    id=e['id'];check('item.thirtyyearswar.'+id in lang,'Missing translation '+id)
    m=read(ASSETS/f'models/item/{id}.json')
    for t in m['textures'].values():
        ns,path=t.split(':');check((RES/f'assets/{ns}/textures/{path}.png').is_file(),'Missing texture '+t)
    if 'model' in e:check('LayerDefinition '+e['model']+'()' in geo,'Missing geometry '+id)
    if e['kind']=='weapon':check(isinstance(e['damage'],int) and e['damage']>0,'Invalid damage '+id)
    if e['kind']=='gun':check(0<e['reload_ticks']<200,'Invalid charge time '+id)
    if e['kind'] in ['armor','decoration']:
        stems=([id,id+'_layer_1',id+'_layer_2'] if e.get('wearable') else [id]) if e['kind']=='decoration' else [id+'_layer_1',id+'_layer_2']
        for stem in stems:
            for suffix in ['', '_overlay'] if e['dyeable'] else ['']:
                p=ASSETS/f'textures/models/armor/{stem}{suffix}.png';check(p.is_file(),'Missing worn texture '+str(p))
                check(p.read_bytes()[:8]==b'\x89PNG\r\n\x1a\n','Invalid PNG '+str(p))
# Validate each loader independently, including tags supplied by the pinned Epic Knights dependency.
for loader in ['fabric', 'forge']:
    outputs=set();dependencies=[]
    tags=material_tags(loader)
    jar_root=Path.home()/'.gradle/caches/modules-2/files-2.1/maven.modrinth/epic-knights-shields-armor-and-weapons'/('10.11-'+loader)
    jars=list(jar_root.rglob('*.jar'))
    check(bool(jars),'Build/download Epic Knights 10.11 before checking dependency tags')
    with zipfile.ZipFile(jars[0]) as jar:
        for item,tag in tags.items():
            ns,name=tag.split(':')
            values=json.loads(jar.read(f'data/{ns}/tags/items/{name}.json'))['values']
            check('magistuarmory:'+item in values,'Tag does not include requested Epic Knights ingredient: '+tag)
    folder=ROOT/f'{loader}/src/main/resources/data/thirtyyearswar'
    for p in (folder/'recipes').glob('*.json'):
        r=read(p);result=r['result'];result=result if isinstance(result,str) else result['item']
        check(result.removeprefix('thirtyyearswar:') in ids,'Unknown recipe output '+str(p));outputs.add(result.split(':')[1])
        ingredients=list(r.get('key',{}).values())+r.get('ingredients',[])+([r['ingredient']] if 'ingredient' in r else [])
        dependencies.append((result.split(':')[1],{v['item'].split(':')[1] for v in ingredients if v.get('item','').startswith('thirtyyearswar:')}))
        for v in ingredients:
            if 'tag' in v:
                check(v['tag'] in set(tags.values()) | (set(FORGE_VANILLA.values()) if loader=='forge' else set()) | {'minecraft:wool','minecraft:planks', 'c:iron_nuggets' if loader=='fabric' else 'forge:nuggets/iron'},'Unexpected ingredient tag '+str(v))
                continue
            item=v['item'];check(not item.startswith('knights:'),'Unconverted ingredient '+str(p))
            if item.startswith('thirtyyearswar:'):check(item.split(':')[1] in ids,'Unknown recipe input '+str(p))
        if 'pattern' in r:
            check(len(set(map(len,r['pattern'])))==1,'Ragged recipe '+str(p))
            check(set(''.join(r['pattern']))-{' '}==set(r['key']),'Invalid recipe key '+str(p))
        unlock=read(folder/f'advancements/recipes/{p.stem}.json')
        check(unlock['rewards']['recipes']==['thirtyyearswar:'+p.stem],'Incorrect recipe unlock')
        predicates=[c['conditions']['items'][0] for c in unlock['criteria'].values()]
        check(predicates==[{'tag':v['tag']} if 'tag' in v else {'items':[v['item']]} for v in ingredients],'Unlock predicates do not match recipe ingredients')
    check(outputs==ids,'Uncraftable items: '+str(ids-outputs))
    reachable=set()
    while True:
        previous=reachable.copy()
        reachable.update(out for out,needs in dependencies if needs<=reachable)
        if previous==reachable:break
    check(reachable==ids,'Unreachable/circular survival recipes: '+str(ids-reachable))
    for p in (ROOT/f'{loader}/src/main/resources').rglob('*.json'):read(p)
    print('PASS:',loader,len(reachable),'survival-reachable outputs and dependency material tags.')
check(not list((RES/'data/thirtyyearswar/recipes').glob('*.json')),'Obsolete common recipes remain')
for loader in ['common','fabric','forge']:
    for p in (ROOT/f'{loader}/src/main/resources').rglob('*.json'):
        text=p.read_text()
        for item in REUSED:check('"thirtyyearswar:'+item+'"' not in text,'Obsolete duplicate material reference '+str(p))
# Optional image checks use Pillow when available, keeping the basic validator stdlib-only.
try:
    from PIL import Image
except ImportError:
    Image=None
if Image:
    masks=0
    for e in items:
        if not e.get('dyeable'):continue
        id=e['id'];paths=[ASSETS/f'textures/item/{id}.png']
        stems=([id,id+'_layer_1',id+'_layer_2'] if e.get('wearable') else [id]) if e['kind']=='decoration' else [id+'_layer_1',id+'_layer_2']
        paths.extend(ASSETS/f'textures/models/armor/{stem}.png' for stem in stems)
        for p in paths:
            pixels=[px for px in Image.open(p).convert('RGBA').getdata() if px[3]]
            check(all(r==g==b for r,g,b,a in pixels),'Tint mask is not neutral white/grayscale: '+str(p))
            check(not pixels or max(px[0] for px in pixels)==255,'Tint mask white level is not normalized: '+str(p))
            check(all(a==255 for r,g,b,a in pixels),'Bedrock low-alpha mask left in Java base: '+str(p));masks+=1
    print('PASS:',masks,'neutral white dye masks with shading preserved.')
for p in RES.rglob('*.json'):read(p)
for k,v in read(ASSETS/'sounds.json').items():
    for s in v['sounds']:
        if s.get('type')!='event':check((ASSETS/'sounds'/ (s['name'].split(':')[1]+'.ogg')).is_file(),'Missing sound '+k)
check(not list((RES/'data').rglob('worldgen')),'World generation is out of scope')
check(not list((ASSETS).rglob('entity/*.json')),'Entity assets are out of scope')
print('PASS:',len(ids),'items,',len(reachable),'survival-reachable outputs; models, textures, translations, sounds and recipes resolve.')
