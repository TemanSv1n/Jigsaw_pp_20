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
import net.svisvi.jigsawpp.entity.emitters.AbstractEmitterEntity;

import java.util.ArrayList;
import java.util.List;

public class EffectGasClass extends AbstractGasClass{
    public ParticleOptions particle = ParticleTypes.ENTITY_EFFECT;
    public SoundEvent sound = SoundEvents.FIRE_EXTINGUISH;


    public List<MobEffectInstance> effectList = new ArrayList<MobEffectInstance>();

    public EffectGasClass(ParticleOptions pparticle, SoundEvent psound, List<MobEffectInstance> peffectlist){
        setEffectList(peffectlist);
        setSound(psound);
        setParticle(pparticle);
    }

    public EffectGasClass(List<MobEffectInstance> peffectlist){
        this(ParticleTypes.ENTITY_EFFECT, SoundEvents.FIRE_EXTINGUISH, peffectlist);
    }

    @Override
    public void gasApplyEffect(Entity entity){
        super.gasApplyEffect(entity);
        if (entity instanceof LivingEntity livingEntity && this.gasApplyCondition(entity)){
            for (MobEffectInstance mef : effectList){
                livingEntity.addEffect(mef);
            }
        }
    }

    @Override
    public SoundEvent getSound() {
        return sound;
    }
    @Override
    public void setSound(SoundEvent sound) {
        this.sound = sound;
    }
    @Override
    public ParticleOptions getParticle() {
        return particle;
    }
    @Override
    public void setParticle(ParticleOptions particle) {
        this.particle = particle;
    }

    public List<MobEffectInstance> getEffectList() {
        return effectList;
    }
    public void setEffectList(List<MobEffectInstance> effectList) {
        this.effectList = effectList;
    }
}
