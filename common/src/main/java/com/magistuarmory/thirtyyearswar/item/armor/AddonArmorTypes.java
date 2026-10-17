package com.magistuarmory.thirtyyearswar.item.armor;

import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
import com.magistuarmory.item.armor.ArmorType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;

public final class AddonArmorTypes {
    public static final ArmorType BAROQUE_APRON_SKIRT = create("baroque_apron_skirt", "knights_baroque_skirt", true);
    public static final ArmorType BAROQUE_ARMOUR = create("baroque_armour", "knights_pikeman_armour", false);
    public static final ArmorType BAROQUE_BARRED_CLOSED_BURGONET = create("baroque_barred_closed_burgonet", "knights_baroque_grilled_closed_burgonet", false);
    public static final ArmorType BAROQUE_BARRED_LOBSTERTAIL_HELMET = create("baroque_barred_lobstertail_helmet", "knights_baroque_lobstertail_helmet", false);
    public static final ArmorType BAROQUE_BODICE = create("baroque_bodice", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_BREECHES = create("baroque_breeches", "knights_baroque_breeches", true);
    public static final ArmorType BAROQUE_BUFF_COAT = create("baroque_buff_coat", "knights_baroque_cavalry_armour", false);
    public static final ArmorType BAROQUE_BUFF_WAISTCOAT = create("baroque_buff_waistcoat", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_CABASSET_HELMET = create("baroque_cabasset_helmet", "knights_baroque_cabasset_helmet", false);
    public static final ArmorType BAROQUE_CAVALIER_HAT = create("baroque_cavalier_hat", "knights_baroque_cavalier_hat", true);
    public static final ArmorType BAROQUE_CAVALRY_ARMOUR = create("baroque_cavalry_armour", "knights_baroque_cavalry_armour", false);
    public static final ArmorType BAROQUE_CAVALRY_ARMOUR_GAUNTLET = create("baroque_cavalry_armour_gauntlet", "knights_baroque_cavalry_armour", false);
    public static final ArmorType BAROQUE_CLOAK = create("baroque_cloak", "knights_baroque_cloak", true);
    public static final ArmorType BAROQUE_CLOSED_HELMET = create("baroque_closed_helmet", "knights_baroque_closed_helmet", false);
    public static final ArmorType BAROQUE_COAT = create("baroque_coat", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_CUIRASS = create("baroque_cuirass", "knights_pikeman_armour", false);
    public static final ArmorType BAROQUE_CUIRASSIER_ARMOUR = create("baroque_cuirassier_armour", "knights_baroque_cuirassier_armour", false);
    public static final ArmorType BAROQUE_DARK_BARRED_CLOSED_BURGONET = create("baroque_dark_barred_closed_burgonet", "knights_baroque_grilled_closed_burgonet", false);
    public static final ArmorType BAROQUE_DARK_BARRED_LOBSTERTAIL_HELMET = create("baroque_dark_barred_lobstertail_helmet", "knights_baroque_lobstertail_helmet", false);
    public static final ArmorType BAROQUE_DARK_CABASSET_HELMET = create("baroque_dark_cabasset_helmet", "knights_baroque_cabasset_helmet", false);
    public static final ArmorType BAROQUE_DARK_CAVALRY_ARMOUR = create("baroque_dark_cavalry_armour", "knights_baroque_cavalry_armour", false);
    public static final ArmorType BAROQUE_DARK_CAVALRY_ARMOUR_GAUNTLET = create("baroque_dark_cavalry_armour_gauntlet", "knights_baroque_cavalry_armour", false);
    public static final ArmorType BAROQUE_DARK_CLOSED_HELMET = create("baroque_dark_closed_helmet", "knights_baroque_closed_helmet", false);
    public static final ArmorType BAROQUE_DARK_CUIRASSIER_ARMOUR = create("baroque_dark_cuirassier_armour", "knights_baroque_cuirassier_armour", false);
    public static final ArmorType BAROQUE_DARK_DEATH_HELMET = create("baroque_dark_death_helmet", "knights_baroque_savoyard_helmet", false);
    public static final ArmorType BAROQUE_DARK_GRILLED_CLOSED_BURGONET = create("baroque_dark_grilled_closed_burgonet", "knights_baroque_grilled_closed_burgonet", false);
    public static final ArmorType BAROQUE_DARK_LOBSTERTAIL_HELMET = create("baroque_dark_lobstertail_helmet", "knights_baroque_lobstertail_helmet", false);
    public static final ArmorType BAROQUE_DARK_OFFICER_ARMOUR = create("baroque_dark_officer_armour", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_DARK_OPEN_BURGONET = create("baroque_dark_open_burgonet", "knights_baroque_open_burgonet", false);
    public static final ArmorType BAROQUE_DARK_OPEN_CLOSED_BURGONET = create("baroque_dark_open_closed_burgonet", "knights_baroque_grilled_closed_burgonet", false);
    public static final ArmorType BAROQUE_DARK_PIKEMAN_ARMOUR = create("baroque_dark_pikeman_armour", "knights_pikeman_armour", true);
    public static final ArmorType BAROQUE_DARK_PIKEMAN_CUIRASS = create("baroque_dark_pikeman_cuirass", "knights_pikeman_armour", true);
    public static final ArmorType BAROQUE_DARK_PIKEMAN_POT_HELMET = create("baroque_dark_pikeman_pot_helmet", "knights_baroque_pikeman_pot_helmet", false);
    public static final ArmorType BAROQUE_DARK_SAVOYARD_HELMET = create("baroque_dark_savoyard_helmet", "knights_baroque_savoyard_helmet", false);
    public static final ArmorType BAROQUE_DEATH_HELMET = create("baroque_death_helmet", "knights_baroque_savoyard_helmet", false);
    public static final ArmorType BAROQUE_DOUBLET = create("baroque_doublet", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_DUTCH_CAP = create("baroque_dutch_cap", "knights_baroque_dutch_cap", true);
    public static final ArmorType BAROQUE_FLAT_CAP = create("baroque_flat_cap", "knights_baroque_flat_cap", true);
    public static final ArmorType BAROQUE_GRILLED_CLOSED_BURGONET = create("baroque_grilled_closed_burgonet", "knights_baroque_grilled_closed_burgonet", false);
    public static final ArmorType BAROQUE_LOBSTERTAIL_HELMET = create("baroque_lobstertail_helmet", "knights_baroque_lobstertail_helmet", false);
    public static final ArmorType BAROQUE_MONMOUTH_CAP = create("baroque_monmouth_cap", "knights_baroque_monmouth_cap", true);
    public static final ArmorType BAROQUE_OFFICER_ARMOUR = create("baroque_officer_armour", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_OFFICER_DOUBLET = create("baroque_officer_doublet", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_OPEN_BURGONET = create("baroque_open_burgonet", "knights_baroque_open_burgonet", false);
    public static final ArmorType BAROQUE_OPEN_CLOAK = create("baroque_open_cloak", "knights_baroque_cloak", true);
    public static final ArmorType BAROQUE_OPEN_CLOSED_BURGONET = create("baroque_open_closed_burgonet", "knights_baroque_grilled_closed_burgonet", false);
    public static final ArmorType BAROQUE_PANEL_CLOAK = create("baroque_panel_cloak", "knights_baroque_cloak", true);
    public static final ArmorType BAROQUE_PIKEMAN_ARMOUR = create("baroque_pikeman_armour", "knights_pikeman_armour", true);
    public static final ArmorType BAROQUE_PIKEMAN_CUIRASS = create("baroque_pikeman_cuirass", "knights_pikeman_armour", true);
    public static final ArmorType BAROQUE_PIKEMAN_POT_HELMET = create("baroque_pikeman_pot_helmet", "knights_baroque_pikeman_pot_helmet", false);
    public static final ArmorType BAROQUE_RIDERS_BREECHES = create("baroque_riders_breeches", "knights_baroque_breeches", true);
    public static final ArmorType BAROQUE_SAVOYARD_HELMET = create("baroque_savoyard_helmet", "knights_baroque_savoyard_helmet", false);
    public static final ArmorType BAROQUE_SHIRT = create("baroque_shirt", "knights_baroque_doublet", true);
    public static final ArmorType BAROQUE_SKIRT = create("baroque_skirt", "knights_baroque_skirt", true);
    public static final ArmorType BAROQUE_SKULL_CAP = create("baroque_skull_cap", "knights_baroque_skull_cap", false);
    public static final ArmorType BAROQUE_SLOUCH_HAT = create("baroque_slouch_hat", "knights_baroque_slouch_hat", true);
    public static final ArmorType BAROQUE_WAISTCOAT = create("baroque_waistcoat", "knights_baroque_doublet", true);
    private static ArmorType create(String name, String model, boolean dyeable) {
        var c = ThirtyYearsWar.CONFIG.armor.get(name);
        boolean enabled = c.enabled && (!dyeable || ThirtyYearsWar.CONFIG.decorations.get(name).enabled);
        return new ArmorType(ThirtyYearsWar.id(name), ThirtyYearsWar.id(model), c.toughness, c.knockbackResistance,
                new Integer[] {c.bootsDurability, c.leggingsDurability, c.chestplateDurability, c.helmetDurability},
                new Integer[] {c.bootsDefense, c.leggingsDefense, c.chestplateDefense, c.helmetDefense},
                c.enchantmentValue, dyeable ? SoundEvents.ARMOR_EQUIP_LEATHER : SoundEvents.ARMOR_EQUIP_IRON, enabled,
                () -> Ingredient.of(dyeable ? AddonItems.WOOLLEN_CLOTH.get() : AddonItems.STEEL_INGOT.get()));
    }
}
