package com.magistuarmory.thirtyyearswar.item;

import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.ParticleTypes;
import com.magistuarmory.thirtyyearswar.config.GunsConfig.GunSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Hold to load and aim; release after a full charge to fire. All hits and costs are server-authoritative. */
public class GunItem extends CrossbowItem {
    private static final long WARNING_COOLDOWN_TICKS = 100L;
    private static final Map<UUID, Long> LAST_GUN_WARNING_TICK = new HashMap<>();

    public enum MechanismType {
        MATCHLOCK,
        WHEELLOCK
    }

    public static final ResourceKey<DamageType> BULLET = ResourceKey.create(Registries.DAMAGE_TYPE, ThirtyYearsWar.id("bullet"));
    private static final UUID ARMOR_PIERCING_MODIFIER_ID = UUID.fromString("5e05e30f-6f96-4b74-b805-98e07790ab61");
    public static final int WHEELLOCK_PHASE_PAUSE_TICKS = 10;
    public static final int WHEELLOCK_HAMMER_COCK_TICKS = 4;
    public static final int WHEELLOCK_SPANNER_ROTATION_TICKS = 30;
    public static final int WHEELLOCK_HAMMER_RETURN_TICKS = 5;
    public static final int MATCHLOCK_FIRST_PHASE_DELAY_TICKS = 24;
    public static final int MATCHLOCK_PHASE_DELAY_TICKS = 8;
    public static final int MATCHLOCK_COCK_ROTATION_TICKS = 6;
    public static final int MATCHLOCK_COVER_ROTATION_TICKS = 6;
    public static final int MATCHLOCK_POST_SHOT_TICKS = 4;
    private static final int MATCHLOCK_READY_SOUND_TICK = MATCHLOCK_FIRST_PHASE_DELAY_TICKS
            + 2 * MATCHLOCK_PHASE_DELAY_TICKS
            + MATCHLOCK_COCK_ROTATION_TICKS + MATCHLOCK_COVER_ROTATION_TICKS;
    private static final int MIN_MATCHLOCK_RELOAD_TICKS = MATCHLOCK_READY_SOUND_TICK;
    private static final int WHEELLOCK_ACTIVE_TICKS = WHEELLOCK_HAMMER_COCK_TICKS
            + WHEELLOCK_SPANNER_ROTATION_TICKS + WHEELLOCK_HAMMER_RETURN_TICKS;
    private static final int MIN_WHEELLOCK_RELOAD_TICKS = WHEELLOCK_ACTIVE_TICKS
            + 2 * WHEELLOCK_PHASE_PAUSE_TICKS;
    public final String id;
    public final GunSettings config;
    public final MechanismType mechanismType;
    public GunItem(String id, GunSettings config, MechanismType mechanismType) {
        super(new Item.Properties().durability(config.durability));
        this.id = id;
        this.config = config;
        this.mechanismType = mechanismType;
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
        if (isCharged(stack)) {
            performShooting(stack, level, player);
            setCharged(stack, false);
            return InteractionResultHolder.consume(stack);
        }
        if (mechanismType == MechanismType.MATCHLOCK && !player.getOffhandItem().is(Items.FLINT_AND_STEEL)) {
            warnPlayer(level, player,
                    Component.translatable("message.thirtyyearswar.matchlock_requires_flint_and_steel"));
            return InteractionResultHolder.fail(stack);
        }
        if (!player.getAbilities().instabuild && ammunition(player).isEmpty()) {
            warnPlayer(level, player, Component.translatable(
                    "message.thirtyyearswar.gun_requires_ammunition",
                    Component.translatable(AddonItems.PAPER_CARTRIDGE.get().getDescriptionId())));
            return InteractionResultHolder.fail(stack);
        }
        if (!level.isClientSide && mechanismType == MechanismType.MATCHLOCK
                && !player.getAbilities().instabuild) {
            player.getOffhandItem().hurtAndBreak(1, player,
                    p -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    private static void warnPlayer(Level level, Player player, Component message) {
        if (level.isClientSide) return;
        long gameTime = level.getGameTime();
        UUID playerId = player.getUUID();
        Long lastWarning = LAST_GUN_WARNING_TICK.get(playerId);
        if (lastWarning == null || gameTime - lastWarning >= WARNING_COOLDOWN_TICKS) {
            player.displayClientMessage(message, false);
            LAST_GUN_WARNING_TICK.put(playerId, gameTime);
        }
    }

    @Override public int getUseDuration(ItemStack stack) { return 72000; }
    @Override public UseAnim getUseAnimation(ItemStack stack) { return UseAnim.CROSSBOW; }
    @Override public boolean useOnRelease(ItemStack stack) { return true; }
    @Override public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("attribute.modifier.equals.0",
                new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ROOT)).format(config.damage),
                Component.translatable(Attributes.ATTACK_DAMAGE.getDescriptionId()))
                .withStyle(ChatFormatting.DARK_GREEN));
        tooltip.add(Component.translatable("tooltip.thirtyyearswar.gun_ammunition",
                Component.translatable(AddonItems.PAPER_CARTRIDGE.get().getDescriptionId()))
                .withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("tooltip.thirtyyearswar.gun_armor_penetration", config.armorPiercing)
                .withStyle(ChatFormatting.BLUE));
    }
    public int getReloadTicks() {
        if (id.contains("wheellock_pistol")) return Math.max(config.reloadTicks, MIN_WHEELLOCK_RELOAD_TICKS);
        if (id.contains("musket")) return Math.max(config.reloadTicks, MIN_MATCHLOCK_RELOAD_TICKS);
        return config.reloadTicks;
    }
    @Override public void onUseTick(Level level, LivingEntity user, ItemStack stack, int remaining) {
        int usedTicks = getUseDuration(stack) - remaining;
        if (level.isClientSide) return;
        if (id.contains("musket") && usedTicks == 1) {
            level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CANDLE_EXTINGUISH,
                    SoundSource.PLAYERS, 0.6F, 1.0F);
        }
        int readySoundTick = id.contains("musket") ? MATCHLOCK_READY_SOUND_TICK : getReloadTicks();
        if (usedTicks == readySoundTick) level.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 0.6F, 1.35F);
    }
    @Override public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(com.magistuarmory.item.ModItems.STEEL_INGOT.get());
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity user, int remaining) {
        if (!(level instanceof ServerLevel) || !(user instanceof Player player)
                || getUseDuration(itemStack) - remaining < getReloadTicks()
                || player.getCooldowns().isOnCooldown(this)
                || isCharged(itemStack)) {
            return;
        }
        ItemStack ammo = ammunition(player);

        if (!player.getAbilities().instabuild && ammo.isEmpty()) {
            return;
        }
        if (!player.getAbilities().instabuild) ammo.shrink(1);

        setCharged(itemStack, true);
        SoundSource soundSource = user instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
        level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundSource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
    }

    public static boolean isCharged(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getTag();
        return compoundTag != null && compoundTag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean bl) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.putBoolean("Charged", bl);
    }

    public void performShooting(ItemStack stack, Level level, LivingEntity user) {
        if (!(level instanceof ServerLevel server)) {
            return;
        }
        boolean musket = id.contains("musket");
        double range = config.range;
        Vec3 start = user.getEyePosition();
        Vec3 direction = user.getLookAngle();
        // Small random dispersion; blocks cap the entity ray so bullets cannot pass through walls.
        double spread = config.spread;
        direction = direction.add(level.random.nextGaussian()*spread, level.random.nextGaussian()*spread,
                server.random.nextGaussian()*spread).normalize();
        Vec3 end = start.add(direction.scale(range));
        var block = level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user));
        if (block.getType() == HitResult.Type.BLOCK) {
            BlockState hitState = level.getBlockState(block.getBlockPos());
            var blockSound = hitState.getSoundType();
            level.playSound(null, block.getBlockPos(), blockSound.getBreakSound(), SoundSource.BLOCKS,
                    (blockSound.getVolume() + 1.0F) / 2.0F, blockSound.getPitch() * 0.8F);
            if (!isGlass(hitState) && hitState.isSolidRender(level, block.getBlockPos())) {
                var face = block.getDirection();
                server.sendParticles(ThirtyYearsWar.BULLET_HOLE.get(), block.getLocation().x, block.getLocation().y,
                        block.getLocation().z, 0, face.getStepX(), face.getStepY(), face.getStepZ(), 1.0D);
            }
            if (hitState.is(Blocks.TARGET) || hitState.is(Blocks.BELL)) {
                Arrow bullet = new Arrow(level, user);
                bullet.setDeltaMovement(direction);
                hitState.onProjectileHit(level, hitState, block, bullet);
            }
            if (isGlass(hitState)) level.destroyBlock(block.getBlockPos(), false);
        }
        end = block.getLocation();
        var hit = ProjectileUtil.getEntityHitResult(user, start, end,
                user.getBoundingBox().expandTowards(direction.scale(range)).inflate(1),
                entity -> !entity.isSpectator() && entity.isPickable() && entity != user,
                start.distanceToSqr(end));
        if (hit != null) {
            end = hit.getLocation();
            if (!(hit.getEntity() instanceof Player target) || (!(user instanceof Player player) || player.canHarmPlayer(target))) {
                DamageSource damage = new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolderOrThrow(BULLET), user);
                hurtWithArmorPiercing(hit.getEntity(), damage, config.damage, config.armorPiercing);
            }
        }
        if (user instanceof Player player) {
            applyCooldownToInventory(player);
            InteractionHand hand = user.getUsedItemHand();
            stack.hurtAndBreak(1, user, p -> p.broadcastBreakEvent(hand));
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        Vec3 muzzle = start.add(direction.scale(0.9));
        server.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, muzzle.x, muzzle.y, muzzle.z, 12, .12, .12, .12, .025);
        server.sendParticles(ParticleTypes.SMOKE, end.x, end.y, end.z, 5, .04, .04, .04, .01);
        level.playSound(null, user.getX(), user.getY(), user.getZ(), ThirtyYearsWar.SHOT.get(),
                SoundSource.PLAYERS, 4, musket ? 0.9F : 1.15F);
    }

    private void applyCooldownToInventory(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack inventoryStack = player.getInventory().getItem(slot);
            Item item = inventoryStack.getItem();
            if (!inventoryStack.isEmpty() && !player.getCooldowns().isOnCooldown(item)) {
                int cooldown = config.cooldownTicks;
                if (item instanceof GunItem gun && gun.config.cooldownTicks > cooldown) {
                    cooldown = gun.config.cooldownTicks;
                }
                player.getCooldowns().addCooldown(item, cooldown);
            }
        }
    }

    private static void hurtWithArmorPiercing(net.minecraft.world.entity.Entity target, DamageSource source,
                                               float damage, int armorPiercing) {
        if (armorPiercing <= 0 || !(target instanceof LivingEntity living)) {
            target.hurt(source, damage);
            return;
        }

        AttributeInstance armor = living.getAttribute(Attributes.ARMOR);
        if (armor == null) {
            target.hurt(source, damage);
            return;
        }

        // Reduce the armor attribute only for this hit so Minecraft still runs its usual
        // damage, absorption, enchantment, and death handling in one hurt call.
        armor.removeModifier(ARMOR_PIERCING_MODIFIER_ID);
        armor.addTransientModifier(new AttributeModifier(ARMOR_PIERCING_MODIFIER_ID,
                "Gun armor piercing", -armorPiercing / 100.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        try {
            target.hurt(source, damage);
        } finally {
            armor.removeModifier(ARMOR_PIERCING_MODIFIER_ID);
        }
    }

    private static boolean isGlass(BlockState state) {
        String blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        if (!blockId.startsWith("minecraft:")) return false;
        String path = blockId.substring("minecraft:".length());
        return path.equals("glass") || path.equals("glass_pane") || path.equals("tinted_glass")
                || path.endsWith("_stained_glass") || path.endsWith("_stained_glass_pane");
    }
}
