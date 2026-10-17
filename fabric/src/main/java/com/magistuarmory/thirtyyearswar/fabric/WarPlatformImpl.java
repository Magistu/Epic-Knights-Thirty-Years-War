package com.magistuarmory.thirtyyearswar.fabric;
import com.magistuarmory.thirtyyearswar.*;
import net.minecraft.world.item.Item;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
public final class WarPlatformImpl {
    public static Item createGun(String id, GunSettings config) { return new GunItem(id, config); }
}
