package com.magistuarmory.thirtyyearswar;

import dev.architectury.platform.Platform;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
import com.magistuarmory.thirtyyearswar.misc.AddonCreativeTabs;
import com.magistuarmory.thirtyyearswar.config.AddonConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import dev.architectury.registry.registries.DeferredRegister;
import net.fabricmc.api.EnvType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class ThirtyYearsWar {
    public static final String ID = "thirtyyearswar";
    public static final AddonConfig CONFIG;
    static {
        AutoConfig.register(AddonConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        CONFIG = AutoConfig.getConfigHolder(AddonConfig.class).getConfig();
    }
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ID, Registries.SOUND_EVENT);
    public static final dev.architectury.registry.registries.RegistrySupplier<SoundEvent> SHOT =
            SOUNDS.register("gunshot", () -> SoundEvent.createVariableRangeEvent(id("gunshot")));
    public static ResourceLocation id(String path) { return new ResourceLocation(ID, path); }
    public static void init() {
        SOUNDS.register();
        AddonItems.INSTANCE.init();
        AddonCreativeTabs.init();
        if (Platform.getEnv() == EnvType.CLIENT) ClientBootstrap.init();
    }
}
