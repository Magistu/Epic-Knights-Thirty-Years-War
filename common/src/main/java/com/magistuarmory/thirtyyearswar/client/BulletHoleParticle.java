package com.magistuarmory.thirtyyearswar.client;

import com.magistuarmory.thirtyyearswar.ThirtyYearsWar;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.util.Mth;

public final class BulletHoleParticle extends TextureSheetParticle {
    private static final int LIFETIME_TICKS = 20 * 20;
    private static final int FADE_TICKS = 40;
    // Map the 16x16 sprite across one block face so its texels match block texture pixels.
    private static final float HALF_SIZE = 0.5F;
    private final Vec3 normal;

    private BulletHoleParticle(ClientLevel level, double x, double y, double z,
                               double normalX, double normalY, double normalZ, SpriteSet sprites) {
        super(level, x, y, z);
        Vec3 hitNormal = new Vec3(normalX, normalY, normalZ);
        this.normal = hitNormal.lengthSqr() < 1.0E-6 ? new Vec3(0.0D, 1.0D, 0.0D) : hitNormal.normalize();
        this.setPos(x + this.normal.x * 0.002D, y + this.normal.y * 0.002D, z + this.normal.z * 0.002D);
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.lifetime = LIFETIME_TICKS;
        this.setSprite(sprites.get(0, 1));
    }

    public static void register() {
        ParticleProviderRegistry.register(ThirtyYearsWar.BULLET_HOLE, BulletHoleParticle.Provider::new);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (++this.age >= this.lifetime) {
            this.remove();
            return;
        }
        this.alpha = this.age > this.lifetime - FADE_TICKS
                ? (this.lifetime - this.age) / (float) FADE_TICKS : 1.0F;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTick) {
        Vec3 cameraPosition = camera.getPosition();
        Vec3 center = new Vec3(
                Mth.lerp(partialTick, this.xo, this.x) - cameraPosition.x,
                Mth.lerp(partialTick, this.yo, this.y) - cameraPosition.y,
                Mth.lerp(partialTick, this.zo, this.z) - cameraPosition.z);
        Vec3 tangent = Math.abs(this.normal.y) > 0.99D
                ? new Vec3(1.0D, 0.0D, 0.0D)
                : this.normal.cross(new Vec3(0.0D, 1.0D, 0.0D)).normalize();
        Vec3 bitangent = this.normal.cross(tangent).normalize();
        Vec3 halfTangent = tangent.scale(HALF_SIZE);
        Vec3 halfBitangent = bitangent.scale(HALF_SIZE);

        this.vertex(buffer, center.subtract(halfTangent).add(halfBitangent), this.getU0(), this.getV0());
        this.vertex(buffer, center.subtract(halfTangent).subtract(halfBitangent), this.getU0(), this.getV1());
        this.vertex(buffer, center.add(halfTangent).subtract(halfBitangent), this.getU1(), this.getV1());
        this.vertex(buffer, center.add(halfTangent).add(halfBitangent), this.getU1(), this.getV0());
    }

    private void vertex(VertexConsumer buffer, Vec3 position, float u, float v) {
        buffer.vertex(position.x, position.y, position.z)
                .uv(u, v)
                .color(255, 255, 255, (int) (this.alpha * 255.0F))
                .uv2(this.getLightColor(0.0F))
                .endVertex();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    private static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        private Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                       double normalX, double normalY, double normalZ) {
            return new BulletHoleParticle(level, x, y, z, normalX, normalY, normalZ, this.sprites);
        }
    }
}
