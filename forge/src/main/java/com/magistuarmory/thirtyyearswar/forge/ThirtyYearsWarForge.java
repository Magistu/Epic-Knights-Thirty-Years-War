package com.magistuarmory.thirtyyearswar.forge;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
@Mod(ThirtyYearsWar.ID)
public final class ThirtyYearsWarForge {
    public ThirtyYearsWarForge() {
        EventBuses.registerModEventBus(ThirtyYearsWar.ID, FMLJavaModLoadingContext.get().getModEventBus());
        ThirtyYearsWar.init();
    }
}
