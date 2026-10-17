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
        values.put("baroque_steel_cuphilt_rapier", WeaponType.of(4.0F, 1.6F, 0.5F, 0.0F, 1.5F, 0, true, 0, 5.0F, true, false, false));
        values.put("baroque_steel_halberd", WeaponType.of(8.0F, 1.0F, 1.5F, 0.0F, 3.0F, 0, true, 2, 8.0F, true, false, true));
        values.put("baroque_steel_partisan", WeaponType.of(6.0F, 1.0F, 1.5F, 0.0F, 3.0F, 0, true, 2, 8.0F, true, false, false));
        values.put("baroque_steel_pike", WeaponType.of(2.0F, 1.0F, 2.5F, 0.0F, 3.0F, 0, true, 2, 8.0F, true, false, false));
        values.put("baroque_steel_pollax", WeaponType.of(10.0F, 1.6F, 0.5F, 0.0F, 1.5F, 0, true, 0, 5.0F, true, false, false));
        values.put("baroque_steel_rapier", WeaponType.of(4.0F, 1.6F, 0.5F, 0.0F, 1.5F, 0, true, 0, 5.0F, true, false, false));
        values.put("baroque_steel_sidesword", WeaponType.of(2.0F, 1.6F, 0.5F, 0.0F, 1.5F, 0, true, 0, 5.0F, true, false, false));
        values.put("baroque_steel_swepthilt_rapier", WeaponType.of(4.0F, 1.6F, 0.5F, 0.0F, 1.5F, 0, true, 0, 5.0F, true, false, false));
        values.put("baroque_steel_walloon_sword", WeaponType.of(6.0F, 1.6F, 0.5F, 0.0F, 1.5F, 0, true, 0, 5.0F, true, false, false));
        return values;
    }

    public WeaponType get(String name) {
        if (meleeWeapons == null) meleeWeapons = defaults();
        return meleeWeapons.computeIfAbsent(name, key -> Objects.requireNonNull(defaults().get(key), "Unknown config key: " + key));
    }
}
