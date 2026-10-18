package com.magistuarmory.thirtyyearswar.client;

import com.magistuarmory.api.client.render.model.ModModelsProvider;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class AddonModels extends ModModelsProvider {
    public static AddonModels INSTANCE = new AddonModels();

    // Armor
    public static final ModelLayerLocation KNIGHTS_BAROQUE_SKIRT_LOCATION = INSTANCE.addArmorModel("knights_baroque_skirt", EquipmentGeometry::knights_baroque_skirt);
    public static final ModelLayerLocation KNIGHTS_PIKEMAN_ARMOUR_LOCATION = INSTANCE.addArmorModel("knights_pikeman_armour", EquipmentGeometry::knights_pikeman_armour);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_GRILLED_CLOSED_BURGONET_LOCATION = INSTANCE.addArmorModel("knights_baroque_grilled_closed_burgonet", EquipmentGeometry::knights_baroque_grilled_closed_burgonet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_LOBSTERTAIL_HELMET_LOCATION = INSTANCE.addArmorModel("knights_baroque_lobstertail_helmet", EquipmentGeometry::knights_baroque_lobstertail_helmet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_DOUBLET_LOCATION = INSTANCE.addArmorModel("knights_baroque_doublet", EquipmentGeometry::knights_baroque_doublet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_BREECHES_LOCATION = INSTANCE.addArmorModel("knights_baroque_breeches", EquipmentGeometry::knights_baroque_breeches);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_CAVALRY_ARMOUR_LOCATION = INSTANCE.addArmorModel("knights_baroque_cavalry_armour", EquipmentGeometry::knights_baroque_cavalry_armour);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_CABASSET_HELMET_LOCATION = INSTANCE.addArmorModel("knights_baroque_cabasset_helmet", EquipmentGeometry::knights_baroque_cabasset_helmet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_CAVALIER_HAT_LOCATION = INSTANCE.addArmorModel("knights_baroque_cavalier_hat", EquipmentGeometry::knights_baroque_cavalier_hat);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_CLOAK_LOCATION = INSTANCE.addArmorModel("knights_baroque_cloak", EquipmentGeometry::knights_baroque_cloak);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_CLOSED_HELMET_LOCATION = INSTANCE.addArmorModel("knights_baroque_closed_helmet", EquipmentGeometry::knights_baroque_closed_helmet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_CUIRASSIER_ARMOUR_LOCATION = INSTANCE.addArmorModel("knights_baroque_cuirassier_armour", EquipmentGeometry::knights_baroque_cuirassier_armour);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_SAVOYARD_HELMET_LOCATION = INSTANCE.addArmorModel("knights_baroque_savoyard_helmet", EquipmentGeometry::knights_baroque_savoyard_helmet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_OPEN_BURGONET_LOCATION = INSTANCE.addArmorModel("knights_baroque_open_burgonet", EquipmentGeometry::knights_baroque_open_burgonet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_PIKEMAN_POT_HELMET_LOCATION = INSTANCE.addArmorModel("knights_baroque_pikeman_pot_helmet", EquipmentGeometry::knights_baroque_pikeman_pot_helmet);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_DUTCH_CAP_LOCATION = INSTANCE.addArmorModel("knights_baroque_dutch_cap", EquipmentGeometry::knights_baroque_dutch_cap);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_FLAT_CAP_LOCATION = INSTANCE.addArmorModel("knights_baroque_flat_cap", EquipmentGeometry::knights_baroque_flat_cap);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_MONMOUTH_CAP_LOCATION = INSTANCE.addArmorModel("knights_baroque_monmouth_cap", EquipmentGeometry::knights_baroque_monmouth_cap);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_SLOUCH_HAT_LOCATION = INSTANCE.addArmorModel("knights_baroque_slouch_hat", EquipmentGeometry::knights_baroque_slouch_hat);
    public static final ModelLayerLocation KNIGHTS_BAROQUE_SKULL_CAP_LOCATION = INSTANCE.addArmorModel("knights_baroque_skull_cap", EquipmentGeometry::knights_baroque_skull_cap);

    public static final ModelLayerLocation BAROQUE_APRON_SKIRT_LEGGINGS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_apron_skirt_leggings_model", EquipmentGeometry::baroque_apron_skirt_leggings_model);

    public static final ModelLayerLocation BAROQUE_APRON_SKIRT_BOOTS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_apron_skirt_boots_model", EquipmentGeometry::baroque_apron_skirt_boots_model);

    public static final ModelLayerLocation BAROQUE_BREECHES_LEGGINGS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_breeches_leggings_model", EquipmentGeometry::baroque_breeches_leggings_model);

    public static final ModelLayerLocation BAROQUE_BREECHES_BOOTS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_breeches_boots_model", EquipmentGeometry::baroque_breeches_boots_model);

    public static final ModelLayerLocation BAROQUE_RIDERS_BREECHES_LEGGINGS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_riders_breeches_leggings_model", EquipmentGeometry::baroque_riders_breeches_leggings_model);

    public static final ModelLayerLocation BAROQUE_RIDERS_BREECHES_BOOTS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_riders_breeches_boots_model", EquipmentGeometry::baroque_riders_breeches_boots_model);

    public static final ModelLayerLocation BAROQUE_SKIRT_LEGGINGS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_skirt_leggings_model", EquipmentGeometry::baroque_skirt_leggings_model);

    public static final ModelLayerLocation BAROQUE_SKIRT_BOOTS_MODEL_LOCATION = INSTANCE.addArmorModel("baroque_skirt_boots_model", EquipmentGeometry::baroque_skirt_boots_model);

    // Decorations
    public static final ModelLayerLocation BAROQUE_OPEN_CLOAK_DECORATION_LOCATION = INSTANCE.addDecorationModel("baroque_open_cloak", EquipmentGeometry::knights_baroque_cloak);
    public static final ModelLayerLocation BAROQUE_CLOAK_DECORATION_LOCATION = INSTANCE.addDecorationModel("baroque_cloak", EquipmentGeometry::knights_baroque_cloak);
    public static final ModelLayerLocation BAROQUE_PANEL_CLOAK_DECORATION_LOCATION = INSTANCE.addDecorationModel("baroque_panel_cloak", EquipmentGeometry::knights_baroque_cloak);
    public static final ModelLayerLocation BAROQUE_SHOULDER_SASH_DECORATION_LOCATION = INSTANCE.addDecorationModel("baroque_shoulder_sash", EquipmentGeometry::knights_baroque_shoulder_sash);
    public static final ModelLayerLocation BAROQUE_WAIST_SASH_DECORATION_LOCATION = INSTANCE.addDecorationModel("baroque_waist_sash", EquipmentGeometry::knights_baroque_waist_sash);

    public AddonModels() {
        super(ThirtyYearsWar.ID);
    }
}
