package net.svisvi.jigsawpp.gas;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class EffectGasClass extends AbstractGasClass{
    public ParticleOptions particle = ParticleTypes.ENTITY_EFFECT;
    public SoundEvent sound = SoundEvents.FIRE_EXTINGUISH;

    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>();

    public EffectGasClass(ParticleOptions pparticle, SoundEvent psound, List<MobEffectInstance> peffectlist){
        effectList = peffectlist;
        sound = psound;
        particle = pparticle;
    }

    public EffectGasClass(List<MobEffectInstance> peffectlist){
        this(ParticleTypes.ENTITY_EFFECT, SoundEvents.FIRE_EXTINGUISH, peffectlist);
    }

    @Override
    public void gasApplyEffect(Entity entity){
        super.gasApplyEffect(entity);
        if (entity instanceof LivingEntity livingEntity && gasApplyCondition(entity)){
            for (MobEffectInstance mef : effectList){
                livingEntity.addEffect(mef);
            }
        }
    }
}
