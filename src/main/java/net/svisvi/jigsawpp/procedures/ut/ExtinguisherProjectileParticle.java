package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.LevelAccessor;

public class ExtinguisherProjectileParticle {
    public static void particleSpawn (LevelAccessor world, double x, double y, double z) {
        world.addParticle(ParticleTypes.POOF, x, y, z,0, 0, 0);
    }
}
