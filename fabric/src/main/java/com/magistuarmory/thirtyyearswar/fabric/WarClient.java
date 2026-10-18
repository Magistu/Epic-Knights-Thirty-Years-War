package com.magistuarmory.thirtyyearswar.fabric;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
import com.magistuarmory.thirtyyearswar.client.GunRenderer;
import com.magistuarmory.thirtyyearswar.item.GunItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
public final class WarClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        GunRenderer renderer = new GunRenderer();
        AddonItems.INSTANCE.rangedWeaponItems.forEach(entry -> {
            if (entry.get() instanceof GunItem) BuiltinItemRendererRegistry.INSTANCE.register(entry.get(), renderer::renderByItem);
        });
    }
}
