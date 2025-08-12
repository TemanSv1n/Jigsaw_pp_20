package net.svisvi.jigsawpp.gas;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PurgativeGasClass extends EffectGasClass{
    public ParticleOptions particle = ModParticleTypes.PURGATIVE_CLOUD.get();
    public SoundEvent sound = SoundEvents.COW_MILK;


    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(ModEffects.PURGATIVE.get(), 60, 0)));

    public PurgativeGasClass(){
        this(ModParticleTypes.PURGATIVE_CLOUD.get(), SoundEvents.COW_MILK, new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(ModEffects.PURGATIVE.get(), 60, 0))));
    }

    public PurgativeGasClass(MobEffectInstance purgativeEffect){
        this(ModParticleTypes.PURGATIVE_CLOUD.get(), SoundEvents.COW_MILK, new ArrayList<MobEffectInstance>(Arrays.asList(purgativeEffect)));
    }

    public PurgativeGasClass(List<MobEffectInstance> purgativeEffectList){
        this(ModParticleTypes.PURGATIVE_CLOUD.get(), SoundEvents.COW_MILK, purgativeEffectList);
    }

    public PurgativeGasClass(ParticleOptions partcl, SoundEvent soundd, List<MobEffectInstance> effectList){
        super(partcl, soundd, effectList);
    }

    @Override
    public void gasApplyEffect(Entity entity){
        super.gasApplyEffect(entity);
        if (entity instanceof LivingEntity livingEntity && this.gasApplyCondition(entity)){
            for (MobEffectInstance mef : effectList){
                //livingEntity.addEffect(mef);
                PurgativeEffect.addEffectGasWay(livingEntity, mef);
            }
        }
    }

    @Override
    public boolean gasApplyCondition(Entity entity){
        if (entity instanceof LivingEntity livingEntity){
            if (PoopEffect.poopAdditionConditionGasWay(entity, new MobEffectInstance(ModEffects.PURGATIVE.get()))){
                return true;
            }
        }
        return false;
    }
}
