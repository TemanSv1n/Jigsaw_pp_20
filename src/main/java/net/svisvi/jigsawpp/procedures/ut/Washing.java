package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.init.ModEffects;

import java.util.logging.Level;

public class Washing {

    public static void wash(LivingEntity entity){
        if (!PoopProtectionArmorConditions.isProtectedFromLiquid(entity)){
            entity.removeEffect(ModEffects.POOP.get());
        }
    }
    public static void washEffect(BlockPos pPos, net.minecraft.world.level.Level world) {
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.DRIPPING_WATER, x, y, z, 180, 1, 1, 1, 0);
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.BUBBLE, x, y, z, 180, 1, 1, 1, 0);
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.POOF, x, y, z, 30, 0.5, 0.5, 0.5, 0);


            if (!world.isClientSide()) {
                world.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty")), SoundSource.NEUTRAL, 2, 1);
            } else {
                world.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty")), SoundSource.NEUTRAL, 2, 1, false);
            }

    }
}
