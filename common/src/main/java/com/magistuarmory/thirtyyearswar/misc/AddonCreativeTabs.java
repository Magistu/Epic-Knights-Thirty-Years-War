package com.magistuarmory.thirtyyearswar.misc;

import com.magistuarmory.thirtyyearswar.item.AddonItems;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

/** Append to Epic Knights' tabs; ingredients follow Epic Knights into vanilla Ingredients. */
public final class AddonCreativeTabs {
    private static ResourceKey<CreativeModeTab> tab(String path) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("magistuarmory", path));
    }
    public static void init() {
        var items = AddonItems.INSTANCE;
        items.weaponItems.stream().filter(java.util.Objects::nonNull).forEach(i -> CreativeTabRegistry.append(tab("particular_weapons"), i));
        items.rangedWeaponItems.stream().filter(java.util.Objects::nonNull).forEach(i -> CreativeTabRegistry.append(tab("particular_weapons"), i));
        items.armorItems.stream().filter(java.util.Objects::nonNull).forEach(i -> CreativeTabRegistry.append(tab("armor"), i));
        items.armorDecorationItems.stream().filter(java.util.Objects::nonNull).forEach(i -> CreativeTabRegistry.append(tab("armor_decorations"), i));
        items.ingredientItems.forEach(i -> CreativeTabRegistry.append(CreativeModeTabs.INGREDIENTS, i));
    }
}
