package com.magistuarmory.thirtyyearswar.config;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Config(name = "guns")
public final class GunsConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.RequiresRestart
    private Map<String, GunSettings> guns = defaults();
    private static Map<String, GunSettings> defaults() {
        Map<String, GunSettings> values = new LinkedHashMap<>();
        values.put("baroque_matchlock_musket", new GunSettings(250, 45, 80, 18, 80, 0.006, 50));
        values.put("baroque_wheellock_pistol", new GunSettings(200, 59, 40, 12, 40, 0.018, 25));
        return values;
    }
    public GunSettings get(String name) {
        if (guns == null) guns = defaults();
        return guns.computeIfAbsent(name, key -> Objects.requireNonNull(defaults().get(key), "Unknown gun: " + key));
    }
    @Override public void validatePostLoad() throws ValidationException {
        for (String name : defaults().keySet()) {
            GunSettings c = get(name);
            if (c.durability < 1 || c.reloadTicks < 1 || c.reloadTicks > 72000 || c.cooldownTicks < 0
                    || !Float.isFinite(c.damage) || c.damage < 0 || !Double.isFinite(c.range) || c.range <= 0
                    || !Double.isFinite(c.spread) || c.spread < 0 || c.armorPiercing < 0 || c.armorPiercing > 100)
                throw new ValidationException("Invalid firearm settings: " + name);
        }
    }
    public static class GunSettings {
        public boolean enabled = true;
        public int durability;
        public int reloadTicks;
        public int cooldownTicks;
        public float damage;
        public double range;
        public double spread;
        /** Percentage of the target's armor ignored by bullets fired from this gun. */
        public int armorPiercing;
        public GunSettings() { this(250, 45, 45, 24, 80, 0.006, 20); }
        public GunSettings(int durability, int reloadTicks, int cooldownTicks, float damage, double range, double spread) {
            this.durability=durability; this.reloadTicks=reloadTicks; this.cooldownTicks = cooldownTicks; this.damage=damage; this.range=range; this.spread=spread;
            this.armorPiercing=20;
        }
        public GunSettings(int durability, int reloadTicks, int cooldownTicks, float damage, double range, double spread, int armorPiercing) {
            this(durability, reloadTicks, cooldownTicks, damage, range, spread);
            this.armorPiercing=armorPiercing;
        }
    }
}
