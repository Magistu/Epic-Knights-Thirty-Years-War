"""Ingredient tags present in Epic Knights 10.11 for the respective loaders."""
SHARED = {
    'hilt': 'magistuarmory:hilts',
    'leather_strip': 'magistuarmory:leather_strips',
    'pole': 'magistuarmory:poles',
    'small_steel_plate': 'magistuarmory:small_plates/steel',
}
REUSED = set(SHARED) | {'steel_ingot', 'steel_nugget', 'steel_plate'}
def material_tags(loader):
    steel = ({'steel_ingot':'c:steel_ingots', 'steel_nugget':'c:steel_nuggets', 'steel_plate':'c:steel_plates'}
             if loader == 'fabric' else
             {'steel_ingot':'forge:ingots/steel', 'steel_nugget':'forge:nuggets/steel', 'steel_plate':'forge:plates/steel'})
    return dict(SHARED, **steel)

# Fabric 1.20.1 lacks matching built-in tags for these materials; retain items there.
FORGE_VANILLA = {'minecraft:leather':'forge:leather', 'minecraft:string':'forge:string',
                 'minecraft:feather':'forge:feathers', 'minecraft:gunpowder':'forge:gunpowder',
                 'minecraft:black_dye':'forge:dyes/black', 'minecraft:red_dye':'forge:dyes/red'}
