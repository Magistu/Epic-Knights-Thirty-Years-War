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
    for e in armor:
        material = 'Items.LEATHER' if 'buff_' in e['id'] else 'AddonItems.WOOLLEN_CLOTH.get()' if e.get('toughness',0) == 0 else 'com.magistuarmory.item.ModItems.STEEL_INGOT.get()'
        mats.append(f'    public static final ArmorType {e["id"].upper()} = create("{e["id"]}", "{e["model"]}", {str(e["dyeable"]).lower()}, () -> Ingredient.of({material}));')
    write(Path('item/armor/AddonArmorTypes.java'),'''package com.magistuarmory.thirtyyearswar.item.armor;

import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
import com.magistuarmory.item.armor.ArmorType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;

public final class AddonArmorTypes {
'''+ '\n'.join(mats)+'''
    private static ArmorType create(String name, String model, boolean dyeable, java.util.function.Supplier<Ingredient> repairIngredient) {
        var c = ThirtyYearsWar.CONFIG.armor.get(name);
        boolean enabled = c.enabled && (!dyeable || ThirtyYearsWar.CONFIG.decorations.get(name).enabled);
        return new ArmorType(ThirtyYearsWar.id(name), ThirtyYearsWar.id(model), c.toughness, c.knockbackResistance,
                new Integer[] {c.bootsDurability, c.leggingsDurability, c.chestplateDurability, c.helmetDurability},
                new Integer[] {c.bootsDefense, c.leggingsDefense, c.chestplateDefense, c.helmetDefense},
                c.enchantmentValue, dyeable ? SoundEvents.ARMOR_EQUIP_LEATHER : SoundEvents.ARMOR_EQUIP_IRON, enabled,
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
                typ='DyeableWearableArmorDecorationItem';call=f'AddonArmorTypes.{const}.isDisabled() ? null : INSTANCE.addDyeableWearableArmorDecorationItem({arg}, AddonArmorTypes.{const}, ArmorItem.Type.CHESTPLATE, {props}, CONFIG.decorations.get({arg}).defaultColor)'
            else:
                typ='DyeableArmorDecorationItem';call=f'!CONFIG.decorations.get({arg}).enabled ? null : INSTANCE.addDyeableArmorDecorationItem({arg}, () -> new DyeableArmorDecorationItem(ThirtyYearsWar.id({arg}), {props}, ArmorItem.Type.CHESTPLATE, CONFIG.decorations.get({arg}).defaultColor))'
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
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;

/** Explicit registrations following Epic Knights: Addon. Disabled equipment fields may be null. */
public final class AddonItems extends ModItemsProvider {
    public static final AddonConfig CONFIG = ThirtyYearsWar.CONFIG;
    public static final AddonItems INSTANCE = new AddonItems();

'''+ '\n'.join(lines)+'''

    private AddonItems() { super(ThirtyYearsWar.ID); }
    private RegistrySupplier<Item> addGun(String id, GunSettings config) {
        if (!config.enabled) return null;
        RegistrySupplier<Item> item = items.register(id, () -> WarPlatform.createGun(id, config));
        rangedWeaponItems.add(item);
        return item;
    }
}
''')
