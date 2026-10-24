package com.magistuarmory.thirtyyearswar.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Config(name = "decorations")
public final class DecorationsConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.RequiresRestart
    private Map<String, DyeSettings> decorations = defaults();

    private static Map<String, DyeSettings> defaults() {
        Map<String, DyeSettings> values = new LinkedHashMap<>();
        values.put("baroque_apron_skirt", new DyeSettings());
        values.put("baroque_bodice", new DyeSettings());
        values.put("baroque_breeches", new DyeSettings());
        values.put("baroque_buff_waistcoat", new DyeSettings());
        values.put("baroque_buff_coat", new DyeSettings(0x938467));
        values.put("baroque_cavalier_hat", new DyeSettings());
        values.put("baroque_cavalry_armour", new DyeSettings(0x938467));
        values.put("baroque_cavalry_armour_gauntlet", new DyeSettings(0x938467));
        values.put("baroque_cloak", new DyeSettings());
        values.put("baroque_coat", new DyeSettings());
        values.put("court_robe", new DyeSettings(0x1D1D21));
        values.put("baroque_dark_cavalry_armour", new DyeSettings(0x938467));
        values.put("baroque_dark_cavalry_armour_gauntlet", new DyeSettings(0x938467));
        values.put("baroque_dark_officer_armour", new DyeSettings());
        values.put("baroque_dark_pikeman_armour", new DyeSettings());
        values.put("baroque_dark_pikeman_cuirass", new DyeSettings());
        values.put("baroque_doublet", new DyeSettings());
        values.put("baroque_dutch_cap", new DyeSettings());
        values.put("baroque_flat_cap", new DyeSettings());
        values.put("baroque_monmouth_cap", new DyeSettings());
        values.put("baroque_officer_armour", new DyeSettings());
        values.put("baroque_officer_doublet", new DyeSettings());
        values.put("baroque_open_cloak", new DyeSettings());
        values.put("baroque_panel_cloak", new DyeSettings());
        values.put("baroque_pikeman_armour", new DyeSettings());
        values.put("baroque_pikeman_cuirass", new DyeSettings());
        values.put("renaissance_cap", new DyeSettings(0x1D1D21));
        values.put("baroque_riders_breeches", new DyeSettings());
        values.put("rich_breeches", new DyeSettings(0x1D1D21));
        values.put("rich_doublet", new DyeSettings(0x1D1D21));
        values.put("baroque_shirt", new DyeSettings());
        values.put("baroque_shoulder_sash", new DyeSettings());
        values.put("baroque_skirt", new DyeSettings());
        values.put("baroque_slouch_hat", new DyeSettings());
        values.put("baroque_waist_sash", new DyeSettings());
        values.put("baroque_waistcoat", new DyeSettings());
        return values;
    }

    public DyeSettings get(String name) {
        if (decorations == null) decorations = defaults();
        return decorations.computeIfAbsent(name, key -> Objects.requireNonNull(defaults().get(key), "Unknown config key: " + key));
    }

    public static class DyeSettings {
        public boolean enabled = true;
        public int defaultColor = 0xFFFFFF;

        public DyeSettings() {}

        public DyeSettings(int defaultColor) {
            this.defaultColor = defaultColor;
        }
    }
}
