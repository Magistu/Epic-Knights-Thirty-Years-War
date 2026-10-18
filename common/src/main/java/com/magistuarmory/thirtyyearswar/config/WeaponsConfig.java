package com.magistuarmory.thirtyyearswar.config;

import com.magistuarmory.item.WeaponType;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Config(name = "weapons")
public final class WeaponsConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.RequiresRestart
    private Map<String, WeaponType> meleeWeapons = defaults();

    private static Map<String, WeaponType> defaults() {
        Map<String, WeaponType> values = new LinkedHashMap<>();
        values.put("baroque_steel_cuphilt_rapier", WeaponType.of(3.40f, 1.60f, 0.00f, 0.00f, 1.2f, 5, true, 0, 6.00f, true, false, false));
        values.put("baroque_steel_halberd", WeaponType.of(7.15f, 0.90f, 1.00f, 0.05f, 4.1f, 3, true, 2, 2.00f, true, false, true));
        values.put("baroque_steel_partisan", WeaponType.of(3.35f, 1.17f, 1.90f, 0.00f, 2.5f, 0, true, 1, 5.00f, true, false, false));
        values.put("baroque_steel_pike", WeaponType.of(3.35f, 1.17f, 2.50f, 0.00f, 2.5f, 0, true, 2, 5.00f, true, false, false));
        values.put("baroque_steel_pollax", WeaponType.of(6.60f, 0.90f, 1.00f, 0.04f, 3.8f, 20, true, 2, 4.00f, true, false, true));
        values.put("baroque_steel_rapier", WeaponType.of(3.40f, 1.60f, 0.00f, 0.00f, 1.2f, 5, true, 0, 6.00f, true, false, false));
        values.put("baroque_steel_sidesword", WeaponType.of(3.68f, 1.46f, 0.10f, 0.00f, 1.5f, 2, true, 0, 6.00f, true, false, false));
        values.put("baroque_steel_swepthilt_rapier", WeaponType.of(3.40f, 1.60f, 0.00f, 0.00f, 1.2f, 5, true, 0, 6.00f, true, false, false));
        values.put("baroque_steel_walloon_sword", WeaponType.of(3.68f, 1.46f, 0.10f, 0.00f, 1.5f, 2, true, 0, 6.00f, true, false, false));
        return values;
    }

    public WeaponType get(String name) {
        if (meleeWeapons == null) meleeWeapons = defaults();
        return meleeWeapons.computeIfAbsent(name, key -> Objects.requireNonNull(defaults().get(key), "Unknown config key: " + key));
    }
}
