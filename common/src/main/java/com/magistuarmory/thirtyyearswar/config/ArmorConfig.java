package com.magistuarmory.thirtyyearswar.config;

import com.magistuarmory.config.ArmorConfig.ArmorTypeConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Config(name = "armors")
public final class ArmorConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.RequiresRestart
    private Map<String, ArmorTypeConfig> armor = defaults();

    private static Map<String, ArmorTypeConfig> defaults() {
        Map<String, ArmorTypeConfig> values = new LinkedHashMap<>();
        values.put("baroque_apron_skirt", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true));
        values.put("baroque_armour", ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 315, 0 }, new Integer[] { 0, 0, 6, 0 }, 9, true));
        values.put("baroque_barred_closed_burgonet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_barred_lobstertail_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_bodice", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_breeches", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true));
        values.put("baroque_buff_coat", ArmorTypeConfig.of(0.20f, 0.0f, new Integer[] { 0, 0, 210, 0 }, new Integer[] { 0, 0, 4, 0 }, 9, true));
        values.put("baroque_buff_waistcoat", ArmorTypeConfig.of(0.20f, 0.0f, new Integer[] { 0, 0, 210, 0 }, new Integer[] { 0, 0, 4, 0 }, 9, true));
        values.put("baroque_cabasset_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_cavalier_hat", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true));
        values.put("baroque_cavalry_armour", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_cavalry_armour_gauntlet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_cloak", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_closed_helmet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_coat", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_cuirass", ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 315, 0 }, new Integer[] { 0, 0, 6, 0 }, 9, true));
        values.put("baroque_cuirassier_armour", ArmorTypeConfig.of(1.80f, 0.5f, new Integer[] { 0, 0, 440, 0 }, new Integer[] { 0, 0, 6, 0 }, 12, true));
        values.put("baroque_dark_barred_closed_burgonet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_barred_lobstertail_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_cabasset_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_dark_cavalry_armour", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_dark_cavalry_armour_gauntlet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_dark_closed_helmet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_cuirassier_armour", ArmorTypeConfig.of(1.80f, 0.5f, new Integer[] { 0, 0, 440, 0 }, new Integer[] { 0, 0, 6, 0 }, 12, true));
        values.put("baroque_dark_death_helmet", ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_grilled_closed_burgonet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_lobstertail_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_officer_armour", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_dark_open_burgonet", ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_dark_open_closed_burgonet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_dark_pikeman_armour", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_dark_pikeman_cuirass", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_dark_pikeman_pot_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_dark_savoyard_helmet", ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_death_helmet", ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_doublet", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_dutch_cap", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true));
        values.put("baroque_flat_cap", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true));
        values.put("baroque_grilled_closed_burgonet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_lobstertail_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_monmouth_cap", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true));
        values.put("baroque_officer_armour", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 335, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_officer_doublet", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 140, 0 }, new Integer[] { 0, 0, 2, 0 }, 9, true));
        values.put("baroque_open_burgonet", ArmorTypeConfig.of(0.50f, 0.0f, new Integer[] { 0, 0, 0, 200 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_open_cloak", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_open_closed_burgonet", ArmorTypeConfig.of(1.25f, 0.5f, new Integer[] { 0, 0, 0, 275 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_panel_cloak", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_pikeman_armour", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_pikeman_cuirass", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 365, 0 }, new Integer[] { 0, 0, 8, 0 }, 9, true));
        values.put("baroque_pikeman_pot_helmet", ArmorTypeConfig.of(0.60f, 0.0f, new Integer[] { 0, 0, 0, 180 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_riders_breeches", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true));
        values.put("baroque_savoyard_helmet", ArmorTypeConfig.of(1.25f, 0.0f, new Integer[] { 0, 0, 0, 225 }, new Integer[] { 0, 0, 0, 3 }, 9, true));
        values.put("baroque_shirt", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_skirt", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 100, 0, 0 }, new Integer[] { 0, 1, 0, 0 }, 9, true));
        values.put("baroque_skull_cap", ArmorTypeConfig.of(0.20f, 0.0f, new Integer[] { 0, 0, 0, 170 }, new Integer[] { 0, 0, 0, 2 }, 9, true));
        values.put("baroque_slouch_hat", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 0, 100 }, new Integer[] { 0, 0, 0, 1 }, 9, true));
        values.put("baroque_waistcoat", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 0, 0, 120, 0 }, new Integer[] { 0, 0, 1, 0 }, 9, true));
        values.put("baroque_apron_skirt_boots", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 100, 0, 0, 0 }, new Integer[] { 1, 0, 0, 0 }, 9, true));
        values.put("baroque_breeches_boots", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 100, 0, 0, 0 }, new Integer[] { 1, 0, 0, 0 }, 9, true));
        values.put("baroque_riders_breeches_boots", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 130, 0, 0, 0 }, new Integer[] { 2, 0, 0, 0 }, 9, true));
        values.put("baroque_skirt_boots", ArmorTypeConfig.of(0.00f, 0.0f, new Integer[] { 100, 0, 0, 0 }, new Integer[] { 1, 0, 0, 0 }, 9, true));
        return values;
    }

    public ArmorTypeConfig get(String name) {
        if (armor == null) armor = defaults();
        return armor.computeIfAbsent(name, key -> Objects.requireNonNull(defaults().get(key), "Unknown config key: " + key));
    }
}
