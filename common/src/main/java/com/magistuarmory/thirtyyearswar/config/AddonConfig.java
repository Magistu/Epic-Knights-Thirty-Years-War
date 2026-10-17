package com.magistuarmory.thirtyyearswar.config;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "thirtyyearswar")
public final class AddonConfig extends PartitioningSerializer.GlobalData {
    @ConfigEntry.Gui.CollapsibleObject public ArmorConfig armor = new ArmorConfig();
    @ConfigEntry.Gui.CollapsibleObject public WeaponsConfig weapons = new WeaponsConfig();
    @ConfigEntry.Gui.CollapsibleObject public DecorationsConfig decorations = new DecorationsConfig();
    @ConfigEntry.Gui.CollapsibleObject public GunsConfig guns = new GunsConfig();
}
