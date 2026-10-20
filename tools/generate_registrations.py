"""Generate ordinary, explicit addon Java source from import audit records (never read at runtime)."""
from pathlib import Path
from balance_defaults import ARMOR_DEFAULTS, WEAPON_DEFAULTS
ROOT=Path(__file__).resolve().parents[1]
JAVA=ROOT/'common/src/main/java/com/magistuarmory/thirtyyearswar'
def write(path,text):
    path=JAVA/path;path.parent.mkdir(parents=True,exist_ok=True);path.write_text(text)
def f(x):return str(float(x))+'F'
def generate(items):
    armor=[e for e in items if e['kind']=='armor' or e.get('wearable')]
    weapons=[e for e in items if e['kind']=='weapon']
    decorations=[e for e in items if e['kind']=='decoration' or e.get('dyeable')]
    configs=[('ArmorConfig','armors','ArmorTypeConfig','armor',armor),('WeaponsConfig','weapons','WeaponType','meleeWeapons',weapons),('DecorationsConfig','decorations','DyeSettings','decorations',decorations)]
    for cls,section,typ,field,entries in configs:
        lines=[]
        for e in entries:
            id=e['id']
            if typ=='ArmorTypeConfig':
                value=ARMOR_DEFAULTS[id]
            elif typ=='WeaponType':
                value=WEAPON_DEFAULTS[id]
            else:value='new DyeSettings()'
            lines.append(f'        values.put("{id}", {value});')
        imports={'ArmorTypeConfig':'import com.magistuarmory.config.ArmorConfig.ArmorTypeConfig;','WeaponType':'import com.magistuarmory.item.WeaponType;','DyeSettings':''}[typ]
        extra='''
    public static class DyeSettings {
        public boolean enabled = true;
        public int defaultColor = 0xFFFFFF;
    }
''' if typ=='DyeSettings' else ''
        write(Path('config')/(cls+'.java'),f'''package com.magistuarmory.thirtyyearswar.config;

{imports}
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Config(name = "{section}")
public final class {cls} implements ConfigData {{
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.RequiresRestart
    private Map<String, {typ}> {field} = defaults();

    private static Map<String, {typ}> defaults() {{
        Map<String, {typ}> values = new LinkedHashMap<>();
'''+ '\n'.join(lines)+f'''
        return values;
    }}

    public {typ} get(String name) {{
        if ({field} == null) {field} = defaults();
        return {field}.computeIfAbsent(name, key -> Objects.requireNonNull(defaults().get(key), "Unknown config key: " + key));
    }}
{extra}}}
''')
    mats=[]
    leather_sound_armor = {
        'baroque_apron_skirt', 'baroque_bodice', 'baroque_breeches', 'baroque_buff_coat',
        'baroque_buff_waistcoat', 'baroque_cavalier_hat', 'baroque_cloak', 'baroque_coat',
        'baroque_doublet', 'baroque_dutch_cap', 'baroque_flat_cap', 'baroque_monmouth_cap',
        'baroque_officer_doublet', 'baroque_open_cloak', 'baroque_panel_cloak',
        'baroque_riders_breeches', 'baroque_shirt', 'baroque_skirt', 'baroque_slouch_hat',
        'baroque_waistcoat',
    }
    for e in armor:
        material = 'Items.LEATHER' if e.get('repair_material')=='leather' or 'buff_' in e['id'] else 'AddonItems.WOOLLEN_CLOTH.get()' if e.get('toughness',0) == 0 else 'com.magistuarmory.item.ModItems.STEEL_INGOT.get()'
        leather_sound = e['id'] in leather_sound_armor or e['id'].endswith('_boots')
        equip_sound = 'SoundEvents.ARMOR_EQUIP_LEATHER' if leather_sound else 'SoundEvents.ARMOR_EQUIP_IRON'
        mats.append(f'    public static final ArmorType {e["id"].upper()} = create("{e["id"]}", "{e["model"]}", {str(e["dyeable"]).lower()}, {equip_sound}, () -> Ingredient.of({material}));')
    write(Path('item/armor/AddonArmorTypes.java'),'''package com.magistuarmory.thirtyyearswar.item.armor;

import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
import com.magistuarmory.item.armor.ArmorType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;

public final class AddonArmorTypes {
'''+ '\n'.join(mats)+'''
    private static ArmorType create(String name, String model, boolean dyeable, net.minecraft.sounds.SoundEvent equipSound, java.util.function.Supplier<Ingredient> repairIngredient) {
        var c = ThirtyYearsWar.CONFIG.armor.get(name);
        boolean enabled = c.enabled && (!dyeable || ThirtyYearsWar.CONFIG.decorations.get(name).enabled);
        ResourceLocation materialId = switch (name) {
            case "baroque_cloak", "baroque_open_cloak", "baroque_panel_cloak" -> new ResourceLocation("magistuarmory", name);
            default -> ThirtyYearsWar.id(name);
        };
        return new ArmorType(materialId, ThirtyYearsWar.id(model), c.toughness, c.knockbackResistance,
                new Integer[] {c.bootsDurability, c.leggingsDurability, c.chestplateDurability, c.helmetDurability},
                new Integer[] {c.bootsDefense, c.leggingsDefense, c.chestplateDefense, c.helmetDefense},
                c.enchantmentValue, equipSound, enabled,
                repairIngredient);
    }
}
''')
    lines=[]
    for e in items:
        id=e['id'];const=id.upper();kind=e['kind'];arg=f'"{id}"';props='new Item.Properties()'
        if kind=='armor':
            typ='MedievalArmorItem';call=f'INSTANCE.addDyeableMedievalArmorItem({arg}, AddonArmorTypes.{const}, ArmorItem.Type.{e["slot"]}, {props}, CONFIG.decorations.get({arg}).defaultColor)' if e['dyeable'] else f'INSTANCE.addMedievalArmorItem({arg}, AddonArmorTypes.{const}, ArmorItem.Type.{e["slot"]}, {props})'
        elif kind=='decoration':
            if e.get('wearable'):
                typ='DyeableWearableArmorDecorationItem';call=f'AddonArmorTypes.{const}.isDisabled() ? null : addDyeableWearableDecoration({arg}, AddonArmorTypes.{const}, ArmorItem.Type.CHESTPLATE, CONFIG.decorations.get({arg}).defaultColor)'
            else:
                typ='DyeableArmorDecorationItem';call=f'!CONFIG.decorations.get({arg}).enabled ? null : addDyeableDecoration({arg}, ArmorItem.Type.CHESTPLATE, CONFIG.decorations.get({arg}).defaultColor)'
        elif kind=='weapon':typ='MedievalWeaponItem';call=f'INSTANCE.addMedievalWeaponItem({arg}, {props}, ModItemTier.STEEL, CONFIG.weapons.get({arg}))'
        elif kind=='gun':typ='Item';call=f'INSTANCE.addGun({arg}, CONFIG.guns.get({arg}))'
        else:typ='Item';call=f'INSTANCE.addIngredientItem({arg}, () -> new Item({props}))'
        lines.append(f'    public static final RegistrySupplier<{typ}> {const} = {call};')
    write(Path('item/AddonItems.java'),'''package com.magistuarmory.thirtyyearswar.item;

import com.magistuarmory.api.item.ModItemsProvider;
import com.magistuarmory.item.*;
import com.magistuarmory.item.armor.*;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import com.magistuarmory.thirtyyearswar.WarPlatform;
import com.magistuarmory.thirtyyearswar.config.AddonConfig;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
import com.magistuarmory.thirtyyearswar.item.armor.AddonArmorTypes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

/** Explicit registrations following Epic Knights: Addon. Disabled equipment fields may be null. */
public final class AddonItems extends ModItemsProvider {
    public static final AddonConfig CONFIG = ThirtyYearsWar.CONFIG;
    public static final AddonItems INSTANCE = new AddonItems();
    private static final DeferredRegister<Item> DECORATION_ITEMS = DeferredRegister.create("magistuarmory", Registries.ITEM);

'''+ '\n'.join(lines)+'''

    private AddonItems() { super(ThirtyYearsWar.ID); }
    private static RegistrySupplier<DyeableWearableArmorDecorationItem> addDyeableWearableDecoration(String id, com.magistuarmory.item.armor.ArmorType type, ArmorItem.Type slot, int color) {
        RegistrySupplier<DyeableWearableArmorDecorationItem> item = ItemRegistryHelper.registerDyeableWearableArmorDecorationItem(DECORATION_ITEMS, id + "_decoration", type, slot, new Item.Properties(), color);
        INSTANCE.armorDecorationItems.add(item);
        INSTANCE.dyeableItems.add(item);
        INSTANCE.armorItems.add(item);
        return item;
    }
    private static RegistrySupplier<DyeableArmorDecorationItem> addDyeableDecoration(String id, ArmorItem.Type slot, int color) {
        RegistrySupplier<DyeableArmorDecorationItem> item = DECORATION_ITEMS.register(id + "_decoration", () -> new DyeableArmorDecorationItem(new ResourceLocation("magistuarmory", id), new Item.Properties(), slot, color));
        INSTANCE.armorDecorationItems.add(item);
        INSTANCE.dyeableItems.add(item);
        return item;
    }
    @Override public void init() {
        super.init();
        DECORATION_ITEMS.register();
    }
    private RegistrySupplier<Item> addGun(String id, GunSettings config) {
        if (!config.enabled) return null;
        RegistrySupplier<Item> item = items.register(id, () -> WarPlatform.createGun(id, config));
        rangedWeaponItems.add(item);
        return item;
    }
}
''')
