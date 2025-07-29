package net.svisvi.jigsawpp.gas;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoopGasClass extends EffectGasClass{
    public ParticleOptions particle = ModParticleTypes.POOP_CLOUD.get();
    public SoundEvent sound = SoundEvents.COW_MILK;

    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(ModEffects.POOP.get(), 60, 0)));

    public PoopGasClass(){
        this(ModParticleTypes.POOP_CLOUD.get(), SoundEvents.COW_MILK, new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(ModEffects.POOP.get(), 21, 0))));
    }

    public PoopGasClass(MobEffectInstance poopEffect){
        this(ModParticleTypes.POOP_CLOUD.get(), SoundEvents.COW_MILK, new ArrayList<MobEffectInstance>(Arrays.asList(poopEffect)));
    }

    public PoopGasClass(ParticleOptions partcl, SoundEvent soundd, List<MobEffectInstance> effectList){
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
