package com.magistuarmory.thirtyyearswar.client;

import com.magistuarmory.thirtyyearswar.GunItem;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** Same original 3D gun geometry on both loaders. No client references in GunItem. */
public final class GunRenderer extends BlockEntityWithoutLevelRenderer {
    private final ModelPart musket = EquipmentGeometry.knights_baroque_matchlock_musket().bakeRoot();
    private final ModelPart pistol = EquipmentGeometry.knights_baroque_wheellock_pistol().bakeRoot();
    public GunRenderer() { super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()); }
    @Override public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose,
                                       MultiBufferSource buffer, int light, int overlay) {
        if (!(stack.getItem() instanceof GunItem gun)) return;
        pose.pushPose();
        pose.translate(0.5, 0.5, 0.5);
        pose.mulPose(Axis.XP.rotationDegrees(90));
        pose.scale(0.7F, 0.7F, 0.7F);
        pose.translate(1.0/16, 0, 4.0/16);
        ModelPart model = gun.id.contains("musket") ? musket : pistol;
        model.render(pose, buffer.getBuffer(RenderType.entityCutoutNoCull(ThirtyYearsWar.id(
                "textures/item/" + gun.id + "_model.png"))), light, overlay);
        pose.popPose();
    }
}
