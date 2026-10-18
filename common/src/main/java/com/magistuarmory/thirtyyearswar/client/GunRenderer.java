package com.magistuarmory.thirtyyearswar.client;

import com.magistuarmory.thirtyyearswar.item.GunItem;
import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.architectury.platform.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;

/** Same original 3D gun geometry on both loaders. No client references in GunItem. */
public final class GunRenderer extends BlockEntityWithoutLevelRenderer {
    private final ModelPart musket = createMusketModel();
    private final ModelPart pistol = createPistolModel();
    private ItemStack observedMusketStack = ItemStack.EMPTY;
    private int observedMusketDamage = -1;
    private ItemStack pendingChargedMusketStack = ItemStack.EMPTY;
    private long pendingChargedMusketSince = Long.MIN_VALUE;
    private ItemStack postShotStack = ItemStack.EMPTY;
    private long postShotStart = Long.MIN_VALUE;
    public GunRenderer() { super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()); }
    @Override public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose,
                                       MultiBufferSource buffer, int light, int overlay) {
        if (!(stack.getItem() instanceof GunItem gun)) return;
        pose.pushPose();
        Minecraft minecraft = Minecraft.getInstance();
        boolean reloading = minecraft.player != null && minecraft.player.isUsingItem() && minecraft.player.getUseItem() == stack;
        boolean firstPerson = minecraft.player != null && context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
        if (firstPerson) {
            applyFirstPersonTransform(stack, context, pose, minecraft, reloading);
        }
        pose.translate(0.5, 0.5, 0.5);
        float scale = 0.8F;
        pose.scale(scale, scale, scale);
        boolean isMusket = gun.id.contains("musket");
        ModelPart model = isMusket ? musket : pistol;
        ModelPart weapon = model.getChild("rightitem").getChild("weapon");
        resetWeaponMechanism(stack, gun, minecraft, weapon, isMusket, reloading);
        if (reloading) {
            animateReloading(weapon, minecraft, stack, gun);
        }
        model.render(pose, buffer.getBuffer(RenderType.entityCutoutNoCull(ThirtyYearsWar.id(
                "textures/item/" + gun.id + "_model.png"))), light, overlay);
        pose.popPose();
    }

    private void resetWeaponMechanism(ItemStack stack, GunItem gun, Minecraft minecraft, ModelPart weapon, boolean isMusket, boolean reloading) {
        if (isMusket) {
            long gameTime = minecraft.level == null ? Long.MIN_VALUE : minecraft.level.getGameTime();
            if (reloading && minecraft.player != null
                    && gun.getUseDuration(stack) - minecraft.player.getUseItemRemainingTicks() >= gun.getReloadTicks()) {
                pendingChargedMusketStack = stack;
                pendingChargedMusketSince = gameTime;
            }

            boolean charged = GunItem.isCharged(stack);
            if (charged) {
                clearPendingChargedMusket(stack);
            } else if (pendingChargedMusketStack == stack && gameTime != Long.MIN_VALUE) {
                if (gameTime - pendingChargedMusketSince <= 5L) {
                    charged = true;
                } else {
                    clearPendingChargedMusket(stack);
                }
            }
            weapon.getChild("cock").xRot = charged ? degrees(60.0F) : 0.0F;
            weapon.getChild("cock").getChild("wick").visible = charged;
        } else {
            weapon.getChild("hammer").xRot = 0.0F;
            ModelPart spanner = weapon.getChild("spanner");
            spanner.xRot = degrees(90.0F);
            spanner.visible = false;
        }
    }

    private void applyFirstPersonTransform(ItemStack stack, ItemDisplayContext context, PoseStack pose, Minecraft minecraft, boolean reloading) {
        HumanoidArm renderedArm = context == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        if (Platform.isForge()) {
            if (GunItem.isCharged(stack)) {
                if (renderedArm == minecraft.player.getMainArm() && minecraft.player.getAttackAnim(minecraft.getFrameTime()) < 0.001F) {
                    pose.mulPose(Axis.YN.rotationDegrees(renderedArm == HumanoidArm.RIGHT ? 10.0F : -10.0F));
                    pose.translate(renderedArm == HumanoidArm.RIGHT ? 0.34 : -0.34, 0.25, 0.0);
                }
            } else if (reloading) {
                pose.mulPose(Axis.YN.rotationDegrees(renderedArm == HumanoidArm.RIGHT ? 30.0F : -30.0F));
                pose.mulPose(Axis.XN.rotationDegrees(-15.0F));
                pose.translate(renderedArm == HumanoidArm.RIGHT ? 0.3 : -0.3, 0.25, -0.25);
            }
        } else {
            if (GunItem.isCharged(stack)) {
                if (renderedArm == minecraft.player.getMainArm() && minecraft.player.getAttackAnim(minecraft.getFrameTime()) < 0.001F) {
                    pose.translate(renderedArm == HumanoidArm.RIGHT ? -0.43 : 0.43, 0.25, 0.0);
                }
            } else if (reloading) {
                pose.mulPose(Axis.YN.rotationDegrees(renderedArm == HumanoidArm.RIGHT ? 15.0F : -15.0F));
                pose.translate(renderedArm == HumanoidArm.RIGHT ? 0.2 : -0.2, -0.5, -0.8);
            }
        }
    }

    private static ModelPart createMusketModel() {
        ModelPart model = EquipmentGeometry.knights_baroque_matchlock_musket().bakeRoot();
        model.getChild("rightitem").getChild("weapon").getChild("cock").getChild("wick").visible = false;
        return model;
    }

    private static ModelPart createPistolModel() {
        ModelPart model = EquipmentGeometry.knights_baroque_wheellock_pistol().bakeRoot();
        model.getChild("rightitem").getChild("weapon").getChild("spanner").visible = false;
        return model;
    }

    private void animateReloading(ModelPart model, Minecraft minecraft, ItemStack stack, GunItem gun) {
        boolean isMusket = gun.id.contains("musket");
        int usedTicks = gun.getUseDuration(stack) - minecraft.player.getUseItemRemainingTicks();
        if (isMusket) {
            animateMusketReloading(model, usedTicks);
        } else {
            animatePistolReloading(model, gun, usedTicks);
        }
    }

    private void animatePistolReloading(ModelPart model, GunItem gun, int usedTicks) {
        ModelPart hammer = model.getChild("hammer");
        ModelPart spanner = model.getChild("spanner");
        int reloadTicks = gun.getReloadTicks();
        float activeTicks = reloadTicks - 2.0F * GunItem.WHEELLOCK_PHASE_PAUSE_TICKS;
        float baseActiveTicks = GunItem.WHEELLOCK_HAMMER_COCK_TICKS
                + GunItem.WHEELLOCK_SPANNER_ROTATION_TICKS + GunItem.WHEELLOCK_HAMMER_RETURN_TICKS;
        float hammerCockEnd = activeTicks * GunItem.WHEELLOCK_HAMMER_COCK_TICKS / baseActiveTicks;
        float spannerStart = hammerCockEnd + GunItem.WHEELLOCK_PHASE_PAUSE_TICKS;
        float spannerRotationTicks = activeTicks * GunItem.WHEELLOCK_SPANNER_ROTATION_TICKS / baseActiveTicks;
        float spannerEnd = spannerStart + spannerRotationTicks;
        float hammerReturnStart = spannerEnd + GunItem.WHEELLOCK_PHASE_PAUSE_TICKS;
        float hammerProgress;
        if (usedTicks < hammerCockEnd) {
            hammerProgress = smoothstep(usedTicks / hammerCockEnd);
        } else if (usedTicks < hammerReturnStart) {
            hammerProgress = 1.0F;
        } else {
            hammerProgress = 1.0F - smoothstep((usedTicks - hammerReturnStart) / (reloadTicks - hammerReturnStart));
        }
        hammer.xRot = degrees(90.0F * hammerProgress);

        spanner.visible = usedTicks >= spannerStart && usedTicks < spannerEnd;
        float stepProgress = smoothstep((usedTicks - spannerStart) / spannerRotationTicks);
        spanner.xRot = degrees(90.0F + 315.0F * stepProgress);
    }

    private void animateMusketReloading(ModelPart model, int usedTicks) {
        float wickStart = GunItem.MATCHLOCK_FIRST_PHASE_DELAY_TICKS;
        float cockStart = wickStart + GunItem.MATCHLOCK_PHASE_DELAY_TICKS;
        float cockEnd = cockStart + GunItem.MATCHLOCK_COCK_ROTATION_TICKS;
        float coverStart = cockEnd + GunItem.MATCHLOCK_PHASE_DELAY_TICKS;

        ModelPart cock = model.getChild("cock");
        ModelPart cover = model.getChild("cover");
        cock.getChild("wick").visible = usedTicks >= wickStart;
        cock.xRot = degrees(60.0F * smoothstep((usedTicks - cockStart) / GunItem.MATCHLOCK_COCK_ROTATION_TICKS));
        cover.zRot = degrees(90.0F * smoothstep((usedTicks - coverStart) / GunItem.MATCHLOCK_COVER_ROTATION_TICKS));
    }

    private void animateMusketAfterShot(ModelPart weapon, float progress) {
        weapon.getChild("cock").xRot = degrees(60.0F);
        weapon.getChild("cock").getChild("wick").visible = false;
        weapon.getChild("cover").zRot = degrees(90.0F * (1.0F - smoothstep(progress)));
    }

    private float updatePostShot(ItemStack stack, Minecraft minecraft) {
        int damage = stack.getDamageValue();
        if (observedMusketStack != stack) {
            observedMusketStack = stack;
            observedMusketDamage = damage;
        } else if (damage > observedMusketDamage) {
            observedMusketDamage = damage;
            postShotStack = stack;
            postShotStart = minecraft.level == null ? Long.MIN_VALUE : minecraft.level.getGameTime();
            clearPendingChargedMusket(stack);
        } else {
            observedMusketDamage = damage;
        }

        if (postShotStack != stack || minecraft.level == null || postShotStart == Long.MIN_VALUE) return -1.0F;
        float progress = (minecraft.level.getGameTime() - postShotStart)
                / (float) GunItem.MATCHLOCK_POST_SHOT_TICKS;
        if (progress >= 1.0F) {
            postShotStack = ItemStack.EMPTY;
            postShotStart = Long.MIN_VALUE;
            return -1.0F;
        }
        return Math.max(0.0F, progress);
    }

    private void clearPendingChargedMusket(ItemStack stack) {
        if (pendingChargedMusketStack == stack) {
            pendingChargedMusketStack = ItemStack.EMPTY;
            pendingChargedMusketSince = Long.MIN_VALUE;
        }
    }

    private static float degrees(float angle) {
        return angle * ((float) Math.PI / 180.0F);
    }

    private static float smoothstep(float progress) {
        float clamped = Math.min(1.0F, Math.max(0.0F, progress));
        return clamped * clamped * (3.0F - 2.0F * clamped);
    }
}
