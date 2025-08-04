package net.svisvi.jigsawpp.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.config.ModServerConfigs;
import net.svisvi.jigsawpp.procedures.ut.RegistriesGoon;

import java.util.ArrayList;
import java.util.List;

public class EmpregnationEffect extends MobEffect {
    //reging
    public int color = -32487552;

    public EmpregnationEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.empregnation";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    public static EntityType<?> getRandomGonner(Level level){
        List<String> gonners_ids = new ArrayList<>(ModServerConfigs.EMPREGNATION_MOBS_LIST.get());
        EntityType<?> ret = EntityType.PIG;
        String conv = "minecraft:pig";
        if (gonners_ids == null || gonners_ids.isEmpty()) {
            return ret;
        }
        conv = gonners_ids.get(level.random.nextInt(gonners_ids.size()));
        ret = RegistriesGoon.getEntityFromRegistryName(conv);
        return ret;

    }

    public void born(Level level, BlockPos pos){
        if (!level.isClientSide()) {
            level.playSound(null, pos, SoundEvents.RAVAGER_DEATH, SoundSource.NEUTRAL, 1, 1);
            level.playSound(null, pos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1);
        } else {
            level.playLocalSound(pos, SoundEvents.RAVAGER_DEATH, SoundSource.NEUTRAL, 1, 1, false);
            level.playLocalSound(pos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1, false);
        }
        if (level instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.EXPLOSION, pos.getX(), pos.getY()+1, pos.getZ(), 12, 0.5, 0.5, 0.5, 0);
        EntityType<?> goonner = getRandomGonner(level);

//            EntityType<?> rabbit = (EntityType<?>) goonner.create(pLivingEntity.level());
        Entity rabbit = goonner.create(level);
        rabbit.moveTo(pos, 0.0F, 0.0F);
        level.addFreshEntity(rabbit);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        if (pLivingEntity.getEffect(this).getDuration() <= 1){
            pLivingEntity.removeEffect(this);
            Level level = pLivingEntity.level();

            BlockPos pos = pLivingEntity.getOnPos().above();

            for (int i = 0; i < pAmplifier + 1; i++){
                born(level, pos);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}