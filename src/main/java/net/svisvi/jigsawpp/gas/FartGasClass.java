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

public class FartGasClass extends EffectGasClass{
    public ParticleOptions particle = ParticleTypes.SNEEZE;
    public SoundEvent sound = SoundEvents.COW_MILK;

    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(MobEffects.POISON, 120, 2, false, false)));

    public FartGasClass(){
        this(ParticleTypes.SNEEZE, SoundEvents.COW_MILK, new ArrayList<MobEffectInstance>(Arrays.asList(new MobEffectInstance(MobEffects.POISON, 120, 2, false, false))));
    }

    public FartGasClass(MobEffectInstance poopEffect){
        this(ParticleTypes.SNEEZE, SoundEvents.COW_MILK, new ArrayList<MobEffectInstance>(Arrays.asList(poopEffect)));
    }

    public FartGasClass(ParticleOptions partcl, SoundEvent soundd, List<MobEffectInstance> effectList){
        super(partcl, soundd, effectList);
    }

    @Override
    public void gasApplyEffect(Entity entity){
        super.gasApplyEffect(entity);
        if (entity instanceof LivingEntity livingEntity && this.gasApplyCondition(entity)){
            for (MobEffectInstance mef : effectList){
                livingEntity.addEffect(mef);
                //PoopEffect.addEffectGasWay(livingEntity, mef);
            }

        }
    }

}
