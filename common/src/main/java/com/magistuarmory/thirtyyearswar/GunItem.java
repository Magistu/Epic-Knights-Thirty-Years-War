package com.magistuarmory.thirtyyearswar;

import net.minecraft.core.particles.ParticleTypes;
import com.magistuarmory.thirtyyearswar.item.AddonItems;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Hold to load and aim; release after a full charge to fire. All hits and costs are server-authoritative. */
public class GunItem extends Item {
    public static final ResourceKey<DamageType> BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, ThirtyYearsWar.id("bullet"));
    public final String id;
    public final GunSettings config;
    public GunItem(String id, GunSettings config) {
        super(new Item.Properties().durability(config.durability));
        this.id = id;
        this.config = config;
    }
    private ItemStack ammunition(Player player) {
        Item cartridge = AddonItems.PAPER_CARTRIDGE.get();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.is(cartridge)) return stack;
        }
        return ItemStack.EMPTY;
    }
    @Override public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.getAbilities().instabuild && ammunition(player).isEmpty()) return InteractionResultHolder.fail(stack);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }
    @Override public int getUseDuration(ItemStack stack) { return 72000; }
    @Override public UseAnim getUseAnimation(ItemStack stack) { return UseAnim.BOW; }
    @Override public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(com.magistuarmory.item.ModItems.STEEL_INGOT.get()) || super.isValidRepairItem(stack, ingredient);
    }
    @Override public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remaining) {
        if (!(level instanceof ServerLevel server) || !(user instanceof Player player)
                || getUseDuration(stack) - remaining < config.reloadTicks
                || player.getCooldowns().isOnCooldown(this)) return;
        ItemStack ammo = ammunition(player);
        if (!player.getAbilities().instabuild && ammo.isEmpty()) return;
        boolean musket = id.contains("musket");
        double range = config.range;
        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getLookAngle();
        // Small random dispersion; blocks cap the entity ray so bullets cannot pass through walls.
        double spread = config.spread;
        direction = direction.add(server.random.nextGaussian()*spread, server.random.nextGaussian()*spread,
                server.random.nextGaussian()*spread).normalize();
        Vec3 end = start.add(direction.scale(range));
        var block = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        end = block.getLocation();
        var hit = ProjectileUtil.getEntityHitResult(player, start, end,
                player.getBoundingBox().expandTowards(direction.scale(range)).inflate(1),
                entity -> !entity.isSpectator() && entity.isPickable() && entity != player,
                start.distanceToSqr(end));
        if (hit != null) {
            end = hit.getLocation();
            if (!(hit.getEntity() instanceof Player target) || player.canHarmPlayer(target)) {
                DamageSource damage = new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(BULLET), player);
                hit.getEntity().hurt(damage, config.damage);
            }
        }
        if (!player.getAbilities().instabuild) ammo.shrink(1);
        InteractionHand hand = player.getUsedItemHand();
        stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        player.getCooldowns().addCooldown(this, config.cooldownTicks);
        player.awardStat(Stats.ITEM_USED.get(this));
        Vec3 muzzle = start.add(direction.scale(0.9));
        server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, muzzle.x, muzzle.y, muzzle.z, 12, .12, .12, .12, .025);
        server.sendParticles(ParticleTypes.SMOKE, end.x, end.y, end.z, 5, .04, .04, .04, .01);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), ThirtyYearsWar.SHOT.get(),
                SoundSource.PLAYERS, 4, musket ? 0.9F : 1.15F);
    }
}
