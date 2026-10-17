package com.magistuarmory.thirtyyearswar.forge;
import com.magistuarmory.thirtyyearswar.*;
import com.magistuarmory.thirtyyearswar.client.GunRenderer;
import net.minecraft.world.item.Item;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import java.util.function.Consumer;
public final class WarPlatformImpl {
    public static Item createGun(String id, GunSettings config) { return new ForgeGun(id, config); }
    private static final class ForgeGun extends GunItem {
        ForgeGun(String id, GunSettings config) { super(id, config); }
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
