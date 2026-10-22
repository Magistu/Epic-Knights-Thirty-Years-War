package com.magistuarmory.thirtyyearswar.item;

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

    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_APRON_SKIRT = INSTANCE.addDyeableMedievalArmorItem("baroque_apron_skirt", AddonArmorTypes.BAROQUE_APRON_SKIRT, ArmorItem.Type.LEGGINGS, new Item.Properties(), CONFIG.decorations.get("baroque_apron_skirt").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_ARMOUR = INSTANCE.addMedievalArmorItem("baroque_armour", AddonArmorTypes.BAROQUE_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BARRED_CLOSED_BURGONET = INSTANCE.addMedievalArmorItem("baroque_barred_closed_burgonet", AddonArmorTypes.BAROQUE_BARRED_CLOSED_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BARRED_LOBSTERTAIL_HELMET = INSTANCE.addMedievalArmorItem("baroque_barred_lobstertail_helmet", AddonArmorTypes.BAROQUE_BARRED_LOBSTERTAIL_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BODICE = INSTANCE.addDyeableMedievalArmorItem("baroque_bodice", AddonArmorTypes.BAROQUE_BODICE, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_bodice").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BREECHES = INSTANCE.addDyeableMedievalArmorItem("baroque_breeches", AddonArmorTypes.BAROQUE_BREECHES, ArmorItem.Type.LEGGINGS, new Item.Properties(), CONFIG.decorations.get("baroque_breeches").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BUFF_COAT = INSTANCE.addMedievalArmorItem("baroque_buff_coat", AddonArmorTypes.BAROQUE_BUFF_COAT, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BUFF_WAISTCOAT = INSTANCE.addDyeableMedievalArmorItem("baroque_buff_waistcoat", AddonArmorTypes.BAROQUE_BUFF_WAISTCOAT, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_buff_waistcoat").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CABASSET_HELMET = INSTANCE.addMedievalArmorItem("baroque_cabasset_helmet", AddonArmorTypes.BAROQUE_CABASSET_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CAVALIER_HAT = INSTANCE.addDyeableMedievalArmorItem("baroque_cavalier_hat", AddonArmorTypes.BAROQUE_CAVALIER_HAT, ArmorItem.Type.HELMET, new Item.Properties(), CONFIG.decorations.get("baroque_cavalier_hat").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CAVALRY_ARMOUR = INSTANCE.addMedievalArmorItem("baroque_cavalry_armour", AddonArmorTypes.BAROQUE_CAVALRY_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CAVALRY_ARMOUR_GAUNTLET = INSTANCE.addMedievalArmorItem("baroque_cavalry_armour_gauntlet", AddonArmorTypes.BAROQUE_CAVALRY_ARMOUR_GAUNTLET, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<DyeableWearableArmorDecorationItem> BAROQUE_CLOAK = AddonArmorTypes.BAROQUE_CLOAK.isDisabled() ? null : INSTANCE.addDyeableWearableArmorDecorationItem("baroque_cloak_decoration", AddonArmorTypes.BAROQUE_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_cloak").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CLOSED_HELMET = INSTANCE.addMedievalArmorItem("baroque_closed_helmet", AddonArmorTypes.BAROQUE_CLOSED_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_COAT = INSTANCE.addDyeableMedievalArmorItem("baroque_coat", AddonArmorTypes.BAROQUE_COAT, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_coat").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CUIRASS = INSTANCE.addMedievalArmorItem("baroque_cuirass", AddonArmorTypes.BAROQUE_CUIRASS, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_CUIRASSIER_ARMOUR = INSTANCE.addMedievalArmorItem("baroque_cuirassier_armour", AddonArmorTypes.BAROQUE_CUIRASSIER_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_BARRED_CLOSED_BURGONET = INSTANCE.addMedievalArmorItem("baroque_dark_barred_closed_burgonet", AddonArmorTypes.BAROQUE_DARK_BARRED_CLOSED_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_BARRED_LOBSTERTAIL_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_barred_lobstertail_helmet", AddonArmorTypes.BAROQUE_DARK_BARRED_LOBSTERTAIL_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_CABASSET_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_cabasset_helmet", AddonArmorTypes.BAROQUE_DARK_CABASSET_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_CAVALRY_ARMOUR = INSTANCE.addMedievalArmorItem("baroque_dark_cavalry_armour", AddonArmorTypes.BAROQUE_DARK_CAVALRY_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_CAVALRY_ARMOUR_GAUNTLET = INSTANCE.addMedievalArmorItem("baroque_dark_cavalry_armour_gauntlet", AddonArmorTypes.BAROQUE_DARK_CAVALRY_ARMOUR_GAUNTLET, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_CLOSED_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_closed_helmet", AddonArmorTypes.BAROQUE_DARK_CLOSED_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_CUIRASSIER_ARMOUR = INSTANCE.addMedievalArmorItem("baroque_dark_cuirassier_armour", AddonArmorTypes.BAROQUE_DARK_CUIRASSIER_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_DEATH_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_death_helmet", AddonArmorTypes.BAROQUE_DARK_DEATH_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_GRILLED_CLOSED_BURGONET = INSTANCE.addMedievalArmorItem("baroque_dark_grilled_closed_burgonet", AddonArmorTypes.BAROQUE_DARK_GRILLED_CLOSED_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_LOBSTERTAIL_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_lobstertail_helmet", AddonArmorTypes.BAROQUE_DARK_LOBSTERTAIL_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_OFFICER_ARMOUR = INSTANCE.addDyeableMedievalArmorItem("baroque_dark_officer_armour", AddonArmorTypes.BAROQUE_DARK_OFFICER_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_dark_officer_armour").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_OPEN_BURGONET = INSTANCE.addMedievalArmorItem("baroque_dark_open_burgonet", AddonArmorTypes.BAROQUE_DARK_OPEN_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_OPEN_CLOSED_BURGONET = INSTANCE.addMedievalArmorItem("baroque_dark_open_closed_burgonet", AddonArmorTypes.BAROQUE_DARK_OPEN_CLOSED_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_PIKEMAN_ARMOUR = INSTANCE.addDyeableMedievalArmorItem("baroque_dark_pikeman_armour", AddonArmorTypes.BAROQUE_DARK_PIKEMAN_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_dark_pikeman_armour").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_PIKEMAN_CUIRASS = INSTANCE.addDyeableMedievalArmorItem("baroque_dark_pikeman_cuirass", AddonArmorTypes.BAROQUE_DARK_PIKEMAN_CUIRASS, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_dark_pikeman_cuirass").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_PIKEMAN_POT_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_pikeman_pot_helmet", AddonArmorTypes.BAROQUE_DARK_PIKEMAN_POT_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DARK_SAVOYARD_HELMET = INSTANCE.addMedievalArmorItem("baroque_dark_savoyard_helmet", AddonArmorTypes.BAROQUE_DARK_SAVOYARD_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DEATH_HELMET = INSTANCE.addMedievalArmorItem("baroque_death_helmet", AddonArmorTypes.BAROQUE_DEATH_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DOUBLET = INSTANCE.addDyeableMedievalArmorItem("baroque_doublet", AddonArmorTypes.BAROQUE_DOUBLET, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_doublet").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_DUTCH_CAP = INSTANCE.addDyeableMedievalArmorItem("baroque_dutch_cap", AddonArmorTypes.BAROQUE_DUTCH_CAP, ArmorItem.Type.HELMET, new Item.Properties(), CONFIG.decorations.get("baroque_dutch_cap").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_FLAT_CAP = INSTANCE.addDyeableMedievalArmorItem("baroque_flat_cap", AddonArmorTypes.BAROQUE_FLAT_CAP, ArmorItem.Type.HELMET, new Item.Properties(), CONFIG.decorations.get("baroque_flat_cap").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_GRILLED_CLOSED_BURGONET = INSTANCE.addMedievalArmorItem("baroque_grilled_closed_burgonet", AddonArmorTypes.BAROQUE_GRILLED_CLOSED_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_LOBSTERTAIL_HELMET = INSTANCE.addMedievalArmorItem("baroque_lobstertail_helmet", AddonArmorTypes.BAROQUE_LOBSTERTAIL_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<Item> BAROQUE_MATCHLOCK_MUSKET = INSTANCE.addGun("baroque_matchlock_musket", CONFIG.guns.get("baroque_matchlock_musket"), GunItem.MechanismType.MATCHLOCK);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_MONMOUTH_CAP = INSTANCE.addDyeableMedievalArmorItem("baroque_monmouth_cap", AddonArmorTypes.BAROQUE_MONMOUTH_CAP, ArmorItem.Type.HELMET, new Item.Properties(), CONFIG.decorations.get("baroque_monmouth_cap").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_OFFICER_ARMOUR = INSTANCE.addDyeableMedievalArmorItem("baroque_officer_armour", AddonArmorTypes.BAROQUE_OFFICER_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_officer_armour").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_OFFICER_DOUBLET = INSTANCE.addDyeableMedievalArmorItem("baroque_officer_doublet", AddonArmorTypes.BAROQUE_OFFICER_DOUBLET, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_officer_doublet").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_OPEN_BURGONET = INSTANCE.addMedievalArmorItem("baroque_open_burgonet", AddonArmorTypes.BAROQUE_OPEN_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<DyeableWearableArmorDecorationItem> BAROQUE_OPEN_CLOAK = AddonArmorTypes.BAROQUE_OPEN_CLOAK.isDisabled() ? null : INSTANCE.addDyeableWearableArmorDecorationItem("baroque_open_cloak_decoration", AddonArmorTypes.BAROQUE_OPEN_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_open_cloak").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_OPEN_CLOSED_BURGONET = INSTANCE.addMedievalArmorItem("baroque_open_closed_burgonet", AddonArmorTypes.BAROQUE_OPEN_CLOSED_BURGONET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<DyeableWearableArmorDecorationItem> BAROQUE_PANEL_CLOAK = AddonArmorTypes.BAROQUE_PANEL_CLOAK.isDisabled() ? null : INSTANCE.addDyeableWearableArmorDecorationItem("baroque_panel_cloak_decoration", AddonArmorTypes.BAROQUE_PANEL_CLOAK, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_panel_cloak").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_PIKEMAN_ARMOUR = INSTANCE.addDyeableMedievalArmorItem("baroque_pikeman_armour", AddonArmorTypes.BAROQUE_PIKEMAN_ARMOUR, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_pikeman_armour").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_PIKEMAN_CUIRASS = INSTANCE.addDyeableMedievalArmorItem("baroque_pikeman_cuirass", AddonArmorTypes.BAROQUE_PIKEMAN_CUIRASS, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_pikeman_cuirass").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_PIKEMAN_POT_HELMET = INSTANCE.addMedievalArmorItem("baroque_pikeman_pot_helmet", AddonArmorTypes.BAROQUE_PIKEMAN_POT_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_RIDERS_BREECHES = INSTANCE.addDyeableMedievalArmorItem("baroque_riders_breeches", AddonArmorTypes.BAROQUE_RIDERS_BREECHES, ArmorItem.Type.LEGGINGS, new Item.Properties(), CONFIG.decorations.get("baroque_riders_breeches").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_SAVOYARD_HELMET = INSTANCE.addMedievalArmorItem("baroque_savoyard_helmet", AddonArmorTypes.BAROQUE_SAVOYARD_HELMET, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_SHIRT = INSTANCE.addDyeableMedievalArmorItem("baroque_shirt", AddonArmorTypes.BAROQUE_SHIRT, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_shirt").defaultColor);
    public static final RegistrySupplier<DyeableArmorDecorationItem> BAROQUE_SHOULDER_SASH = !CONFIG.decorations.get("baroque_shoulder_sash").enabled ? null : INSTANCE.addDyeableArmorDecorationItem("baroque_shoulder_sash_decoration", () -> new DyeableArmorDecorationItem(ThirtyYearsWar.id("baroque_shoulder_sash"), new Item.Properties(), ArmorItem.Type.CHESTPLATE, CONFIG.decorations.get("baroque_shoulder_sash").defaultColor));
    public static final RegistrySupplier<WearableArmorDecorationItem> BAROQUE_RUFF = INSTANCE.addWearableArmorDecorationItem("baroque_ruff_decoration", AddonArmorTypes.BAROQUE_RUFF, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<WearableArmorDecorationItem> BAROQUE_SHORT_RUFF = INSTANCE.addWearableArmorDecorationItem("baroque_short_ruff_decoration", AddonArmorTypes.BAROQUE_SHORT_RUFF, ArmorItem.Type.CHESTPLATE, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_SKIRT = INSTANCE.addDyeableMedievalArmorItem("baroque_skirt", AddonArmorTypes.BAROQUE_SKIRT, ArmorItem.Type.LEGGINGS, new Item.Properties(), CONFIG.decorations.get("baroque_skirt").defaultColor);
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_SKULL_CAP = INSTANCE.addMedievalArmorItem("baroque_skull_cap", AddonArmorTypes.BAROQUE_SKULL_CAP, ArmorItem.Type.HELMET, new Item.Properties());
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_SLOUCH_HAT = INSTANCE.addDyeableMedievalArmorItem("baroque_slouch_hat", AddonArmorTypes.BAROQUE_SLOUCH_HAT, ArmorItem.Type.HELMET, new Item.Properties(), CONFIG.decorations.get("baroque_slouch_hat").defaultColor);
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_CUPHILT_RAPIER = INSTANCE.addMedievalWeaponItem("baroque_steel_cuphilt_rapier", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_cuphilt_rapier"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_HALBERD = INSTANCE.addMedievalWeaponItem("baroque_steel_halberd", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_halberd"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_PARTISAN = INSTANCE.addMedievalWeaponItem("baroque_steel_partisan", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_partisan"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_PIKE = INSTANCE.addMedievalWeaponItem("baroque_steel_pike", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_pike"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_POLLAX = INSTANCE.addMedievalWeaponItem("baroque_steel_pollax", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_pollax"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_RAPIER = INSTANCE.addMedievalWeaponItem("baroque_steel_rapier", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_rapier"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_SIDESWORD = INSTANCE.addMedievalWeaponItem("baroque_steel_sidesword", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_sidesword"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_SWEPTHILT_RAPIER = INSTANCE.addMedievalWeaponItem("baroque_steel_swepthilt_rapier", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_swepthilt_rapier"));
    public static final RegistrySupplier<MedievalWeaponItem> BAROQUE_STEEL_WALLOON_SWORD = INSTANCE.addMedievalWeaponItem("baroque_steel_walloon_sword", new Item.Properties(), ModItemTier.STEEL, CONFIG.weapons.get("baroque_steel_walloon_sword"));
    public static final RegistrySupplier<DyeableArmorDecorationItem> BAROQUE_WAIST_SASH = !CONFIG.decorations.get("baroque_waist_sash").enabled ? null : INSTANCE.addDyeableArmorDecorationItem("baroque_waist_sash_decoration", () -> new DyeableArmorDecorationItem(ThirtyYearsWar.id("baroque_waist_sash"), new Item.Properties(), ArmorItem.Type.CHESTPLATE, CONFIG.decorations.get("baroque_waist_sash").defaultColor));
    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_WAISTCOAT = INSTANCE.addDyeableMedievalArmorItem("baroque_waistcoat", AddonArmorTypes.BAROQUE_WAISTCOAT, ArmorItem.Type.CHESTPLATE, new Item.Properties(), CONFIG.decorations.get("baroque_waistcoat").defaultColor);
    public static final RegistrySupplier<Item> BAROQUE_WHEELLOCK_PISTOL = INSTANCE.addGun("baroque_wheellock_pistol", CONFIG.guns.get("baroque_wheellock_pistol"), GunItem.MechanismType.WHEELLOCK);
    public static final RegistrySupplier<Item> FELT_FABRIC = INSTANCE.addIngredientItem("felt_fabric", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> PAPER_CARTRIDGE = INSTANCE.addIngredientItem("paper_cartridge", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> WOOL_FIBERS = INSTANCE.addIngredientItem("wool_fibers", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> WOOLLEN_CLOTH = INSTANCE.addIngredientItem("woollen_cloth", () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_APRON_SKIRT_BOOTS = INSTANCE.addMedievalArmorItem("baroque_apron_skirt_boots", AddonArmorTypes.BAROQUE_APRON_SKIRT_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties());

    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_BREECHES_BOOTS = INSTANCE.addMedievalArmorItem("baroque_breeches_boots", AddonArmorTypes.BAROQUE_BREECHES_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties());

    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_RIDERS_BREECHES_BOOTS = INSTANCE.addMedievalArmorItem("baroque_riders_breeches_boots", AddonArmorTypes.BAROQUE_RIDERS_BREECHES_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties());

    public static final RegistrySupplier<MedievalArmorItem> BAROQUE_SKIRT_BOOTS = INSTANCE.addMedievalArmorItem("baroque_skirt_boots", AddonArmorTypes.BAROQUE_SKIRT_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties());

    private AddonItems() { super(ThirtyYearsWar.ID); }

    private RegistrySupplier<Item> addGun(String id, GunSettings config, GunItem.MechanismType mechanismType) {
        if (!config.enabled) return null;
        RegistrySupplier<Item> item = items.register(id, () -> WarPlatform.createGun(id, config, mechanismType));
        rangedWeaponItems.add(item);
        return item;
    }
}
