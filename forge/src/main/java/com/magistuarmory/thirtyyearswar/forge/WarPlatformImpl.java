package com.magistuarmory.thirtyyearswar.forge;
import com.magistuarmory.thirtyyearswar.*;
import com.magistuarmory.thirtyyearswar.client.GunRenderer;
import com.magistuarmory.thirtyyearswar.item.GunItem;
import net.minecraft.world.item.Item;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import java.util.function.Consumer;
public final class WarPlatformImpl {
    public static Item createGun(String id, GunSettings config, GunItem.MechanismType mechanismType) {
        return new ForgeGun(id, config, mechanismType);
    }
    private static final class ForgeGun extends GunItem {
        ForgeGun(String id, GunSettings config, GunItem.MechanismType mechanismType) {
            super(id, config, mechanismType);
        }
        @Override public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                private GunRenderer renderer;
                @Override public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    if (renderer == null) renderer = new GunRenderer();
                    return renderer;
                }
            });
        }
    }
}
