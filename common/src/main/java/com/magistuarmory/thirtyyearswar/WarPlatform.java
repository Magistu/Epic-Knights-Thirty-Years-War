package com.magistuarmory.thirtyyearswar;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
public final class WarPlatform {
    @ExpectPlatform
    public static Item createGun(String id, GunSettings config) { throw new AssertionError(); }
}
