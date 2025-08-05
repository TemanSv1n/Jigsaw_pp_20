package net.svisvi.jigsawpp.gas;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmokeGasClass extends EffectGasClass{
    public ParticleOptions particle = ParticleTypes.CAMPFIRE_COSY_SMOKE;
    public SoundEvent sound = SoundEvents.SMOKER_SMOKE;

    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(MobEffects.DARKNESS, 21, 0)));// = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(ModEffects.POOP.get(), 60, 0)));

    public SmokeGasClass(){
        this(ParticleTypes.CAMPFIRE_COSY_SMOKE, SoundEvents.SMOKER_SMOKE, new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(MobEffects.DARKNESS, 21, 0))));
    }

    public SmokeGasClass(MobEffectInstance poopEffect){
        this(ParticleTypes.CAMPFIRE_COSY_SMOKE, SoundEvents.SMOKER_SMOKE, new ArrayList<MobEffectInstance>(Arrays.asList(poopEffect)));
    }

    public SmokeGasClass(ParticleOptions partcl, SoundEvent soundd, List<MobEffectInstance> effectList){
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
        }
    }

    @Override
    public boolean gasApplyCondition(Entity entity){
        if (entity instanceof LivingEntity livingEntity){
            if (PoopEffect.poopAdditionConditionGasWay(entity, new MobEffectInstance(ModEffects.POOP.get()))){
                return true;
            }
        }
        return false;
    }
}
