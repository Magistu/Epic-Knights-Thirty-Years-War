package com.magistuarmory.thirtyyearswar.client;
import com.magistuarmory.api.client.render.model.ModModelsProvider;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
public final class EquipmentModels extends ModModelsProvider {
    public EquipmentModels() {
        super(ThirtyYearsWar.ID);
        addArmorModel("knights_baroque_skirt", EquipmentGeometry::knights_baroque_skirt);
        addArmorModel("knights_pikeman_armour", EquipmentGeometry::knights_pikeman_armour);
        addArmorModel("knights_baroque_grilled_closed_burgonet", EquipmentGeometry::knights_baroque_grilled_closed_burgonet);
        addArmorModel("knights_baroque_lobstertail_helmet", EquipmentGeometry::knights_baroque_lobstertail_helmet);
        addArmorModel("knights_baroque_doublet", EquipmentGeometry::knights_baroque_doublet);
        addArmorModel("knights_baroque_breeches", EquipmentGeometry::knights_baroque_breeches);
        addArmorModel("knights_baroque_cavalry_armour", EquipmentGeometry::knights_baroque_cavalry_armour);
        addArmorModel("knights_baroque_cabasset_helmet", EquipmentGeometry::knights_baroque_cabasset_helmet);
        addArmorModel("knights_baroque_cavalier_hat", EquipmentGeometry::knights_baroque_cavalier_hat);
        addArmorModel("knights_baroque_cloak", EquipmentGeometry::knights_baroque_cloak);
        addDecorationModel("baroque_cloak", EquipmentGeometry::knights_baroque_cloak);
        addArmorModel("knights_baroque_closed_helmet", EquipmentGeometry::knights_baroque_closed_helmet);
        addArmorModel("knights_baroque_cuirassier_armour", EquipmentGeometry::knights_baroque_cuirassier_armour);
        addArmorModel("knights_baroque_savoyard_helmet", EquipmentGeometry::knights_baroque_savoyard_helmet);
        addArmorModel("knights_baroque_open_burgonet", EquipmentGeometry::knights_baroque_open_burgonet);
        addArmorModel("knights_baroque_pikeman_pot_helmet", EquipmentGeometry::knights_baroque_pikeman_pot_helmet);
        addArmorModel("knights_baroque_dutch_cap", EquipmentGeometry::knights_baroque_dutch_cap);
        addArmorModel("knights_baroque_flat_cap", EquipmentGeometry::knights_baroque_flat_cap);
        addArmorModel("knights_baroque_monmouth_cap", EquipmentGeometry::knights_baroque_monmouth_cap);
        addDecorationModel("baroque_open_cloak", EquipmentGeometry::knights_baroque_cloak);
        addDecorationModel("baroque_panel_cloak", EquipmentGeometry::knights_baroque_cloak);
        addDecorationModel("baroque_shoulder_sash", EquipmentGeometry::knights_baroque_shoulder_sash);
        addArmorModel("knights_baroque_skull_cap", EquipmentGeometry::knights_baroque_skull_cap);
        addArmorModel("knights_baroque_slouch_hat", EquipmentGeometry::knights_baroque_slouch_hat);
        addDecorationModel("baroque_waist_sash", EquipmentGeometry::knights_baroque_waist_sash);
    }
}
