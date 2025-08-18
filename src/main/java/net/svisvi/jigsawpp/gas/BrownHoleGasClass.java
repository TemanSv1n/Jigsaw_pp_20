package net.svisvi.jigsawpp.gas;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BrownHoleGasClass extends EffectGasClass{
    public ParticleOptions particle = ModParticleTypes.POOP.get();
    public SoundEvent sound = SoundEvents.END_GATEWAY_SPAWN;

    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(MobEffects.DARKNESS, 21, 0)));// = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(ModEffects.POOP.get(), 60, 0)));

    public BrownHoleGasClass(){
        this(ModParticleTypes.POOP.get(), SoundEvents.END_GATEWAY_SPAWN, new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(MobEffects.DARKNESS, 21, 0))));
    }

    public BrownHoleGasClass(MobEffectInstance poopEffect){
        this(ModParticleTypes.POOP.get(), SoundEvents.END_GATEWAY_SPAWN, new ArrayList<MobEffectInstance>(Arrays.asList(poopEffect)));
    }

    public BrownHoleGasClass(ParticleOptions partcl, SoundEvent soundd, List<MobEffectInstance> effectList){
        super(partcl, soundd, effectList);
    }

    @Override
    public void gasApplyEffect(Entity entity){
        super.gasApplyEffect(entity);
        if (entity instanceof LivingEntity livingEntity && this.gasApplyCondition(entity)){
            for (MobEffectInstance mef : effectList){
                //livingEntity.addEffect(mef);
                PoopEffect.addEffectGasWay(livingEntity, mef);
            }
            teleportToOverworldSpawn(livingEntity);
        }
    }

    @Override
    public boolean gasApplyCondition(Entity entity){
        return true;
    }

    public static void teleportToOverworldSpawn(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            // Get the Overworld dimension
            ServerLevel overworld = serverLevel.getServer().getLevel(Level.OVERWORLD);

            if (overworld != null) {
                // Calculate surface Y at X=0, Z=0 (using MOTION_BLOCKING heightmap)
                int surfaceY = overworld.getHeight(Heightmap.Types.MOTION_BLOCKING, 0, 0);
                BlockPos spawnPos = new BlockPos(0, surfaceY, 0);

                Set<RelativeMovement> movementFlags = Set.of(
                        RelativeMovement.X,
                        RelativeMovement.Y,
                        RelativeMovement.Z,
                        RelativeMovement.Y_ROT,
                        RelativeMovement.X_ROT
                );


                // Teleport the entity
                entity.teleportTo(overworld, spawnPos.getX()+0.5, spawnPos.getY()+1, spawnPos.getZ()+0.5, movementFlags, entity.getYRot(), entity.getXRot());
            }
        }
    }
}
