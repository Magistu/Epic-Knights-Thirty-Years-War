package com.magistuarmory.thirtyyearswar;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
import com.magistuarmory.thirtyyearswar.item.GunItem;

public final class WarPlatform {
    @ExpectPlatform
    public static Item createGun(String id, GunSettings config, GunItem.MechanismType mechanismType) { throw new AssertionError(); }
}
