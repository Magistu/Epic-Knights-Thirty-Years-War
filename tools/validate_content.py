#!/usr/bin/env python3
"""Check registry/resource/recipe closure without Minecraft or external packages."""
import json,re,struct
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
check(len(ids)==len(items)==81,'Unexpected or duplicate item IDs')
check(Counter(e['kind'] for e in items)=={'armor':54,'decoration':5,'weapon':9,'gun':2,'ammo':1,'ingredient':10},'Unexpected scope')
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
outputs=set();dependencies=[]
for p in (RES/'data/thirtyyearswar/recipes').glob('*.json'):
    r=read(p);result=r['result'];result=result if isinstance(result,str) else result['item']
    check(result.removeprefix('thirtyyearswar:') in ids,'Unknown recipe output '+str(p));outputs.add(result.split(':')[1])
    ingredients=list(r.get('key',{}).values())+r.get('ingredients',[])+([r['ingredient']] if 'ingredient' in r else [])
    dependencies.append((result.split(':')[1],{v['item'].split(':')[1] for v in ingredients if v['item'].startswith('thirtyyearswar:')}))
    for v in ingredients:
        item=v['item'];check(not item.startswith('knights:'),'Unconverted ingredient '+str(p))
        if item.startswith('thirtyyearswar:'):check(item.split(':')[1] in ids,'Unknown recipe input '+str(p))
    if 'pattern' in r:
        check(len(set(map(len,r['pattern'])))==1,'Ragged recipe '+str(p))
        check(set(''.join(r['pattern']))-{' '}==set(r['key']),'Invalid recipe key '+str(p))
check(outputs==ids,'Uncraftable items: '+str(ids-outputs))
reachable=set()
while True:
    previous=reachable.copy()
    reachable.update(out for out,needs in dependencies if needs<=reachable)
    if previous==reachable:break
check(reachable==ids,'Unreachable/circular survival recipes: '+str(ids-reachable))
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
