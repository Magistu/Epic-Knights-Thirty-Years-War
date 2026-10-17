# Epic Knights: Thirty Years War

Minecraft **1.20.1**, Fabric and Forge equipment addon for **Epic Knights 10.11**.
Mod ID: `thirtyyearswar`. Requires Architectury API 9.x and Cloth Config 11.x;
Fabric also requires Fabric API. The example Epic Knights: Addon is not required.

## Content and survival

- 54 armor/clothing items, with dyeable clothing and hats.
- Three dyeable wearable cloaks: cloak, open cloak and panel cloak. Each can be
  worn directly in the chest slot or attached to chest armor using Epic Knights'
  normal decoration crafting workflow.
- Two dyeable sash decorations for chest armor.
- Nine Epic Knights steel melee weapons and two functional firearms.
- Paper cartridges and all ten source crafting ingredients: steel ingot, steel
  nugget, steel plate, small steel plate, leather strip, hilt, pole, wool fibers,
  woollen cloth and felt fabric.

All **81 items are obtainable in survival** through crafting/smelting chains that
start with vanilla materials. Recipes retain the original ingredients rather than
replacing them with vanilla substitutes. Recipe-book unlocks are included.
The two source rapier recipes that required their own output now upgrade the base
rapier. Steel ingredients are also in the common Fabric/Forge steel tags.

There is no addon creative tab. Armor and wearable cloaks are in Epic Knights'
Armor tab, weapons and guns in its Particular Weapons tab, and cloaks/sashes in
its Armor Decorations tab. Ingredients use vanilla Ingredients, as Epic Knights
does. No mobs, eggs, coins, factions, trading or world generation are included.
The supplied packs contain no registered shields.

## Dyeing

There is one item per dyeable design; the 64 black/green/red/yellow item variants
have been removed. Dyeable base textures are neutral white/grayscale with shading
preserved. Undyed details remain in separate overlays. Use Minecraft/Epic Knights'
normal armor/decoration dye crafting; cloth can be any dye color.
Dark metal armor is retained as a separate blackened metal finish with its original
smelting recipes, rather than a cloth dye variant.

This revision removes the earlier prototype's colored item IDs. Existing stacks
with those removed IDs are not migrated automatically.

## Configuration

Explicit registrations live in `item/AddonItems.java`, with materials in
`item/armor/AddonArmorTypes.java`. There is no runtime equipment JSON catalog.
Like the example addon, AutoConfig uses partitioned JSON5 configuration under
`config/thirtyyearswar/`:

- `armors.json5`: per-slot durability and defense, toughness, knockback resistance,
  enchantability and enabled state for all armor and wearable cloaks.
- `weapons.json5`: Epic Knights weapon damage, speed, reach, size, weight, armor
  piercing, two-handed behavior, blocking and enabled state.
- `decorations.json5`: enabled state and default dye color for every dyeable design,
  including armor, wearable cloaks and sashes. The default is white (`16777215`).
- `guns.json5`: enabled state, durability, reload time, cooldown, damage, range and
  dispersion for each firearm.

Restart after editing. Missing entries in older/partial configs fall back to the
built-in defaults. Use matching configs on clients and servers. Disabling an item
prevents its registration, following the example addon's behavior; recipes for
such items cannot be used. Ingredients remain registered so recipe chains have
stable material IDs.

## Firearms

Hold use to load and aim; release after a full charge to fire. Early release
consumes neither ammunition nor durability. A survival shot consumes one paper
cartridge and one durability. Creative mode needs no ammunition. Guns repair with
this addon's steel ingots.

Defaults: musket 45-tick charge, 24 damage, 80-block range and 250 durability;
pistol 20-tick charge, 14 damage, 40-block range and 200 durability. Both use
server-authoritative hitscan with dispersion, stop at blocks and respect player
friendly-fire restrictions. Smoke and original gunshot audio are included. No
new entities are registered.

## Build and checks

Use **JDK 17**:

```sh
bash gradlew :common:verifyConfig :common:verifyGeometry :fabric:assemble :forge:assemble
python3 tools/validate_content.py
bash gradlew :fabric:runClient
bash gradlew :forge:runClient
```

Install the plain JAR from the appropriate loader's `build/libs/`, excluding the
`-sources`, `-javadoc` and `-dev-shadow` artifacts.

The checks cover config overrides and partial files, all 24 geometry layers,
equipment-slot visibility, explicit Java registrations, resource references and
survival recipe reachability (including cycles). Running the Python validator with
Pillow additionally checks all dye masks for neutral color and white normalization.
Multiplayer combat and a complete visual inspection of every item remain manual
checks.

## Source conversion and credits

`tools/import_bedrock.py /path/to/30-years-war` regenerates assets and explicit Java
registration/config defaults from the supplied packs (Pillow required). The Java
runtime never reads those source packs or the audit reports. Building requires
neither Pillow nor the Bedrock packs. Hand edits to generated registration/default
files should also be applied to `tools/generate_registrations.py`.

`docs/import-report.json` records included, merged and excluded definitions;
`docs/recipe-report.json` records recipe coverage and repairs. Bedrock comments,
malformed doublet definitions, mislabeled attachables, texture alpha masks and
recipe defects are normalized without modifying the original packs. Chest-armor
tassets follow the torso to remain visible under Java's chest-slot rendering rules.

Artwork, geometry and audio come from the supplied Thirty Years War / Baroque packs;
their manifests do not name the authors. The cross-loader structure and integration
follow the local Epic Knights: Addon example by Magistu and its contributors.
