package net.svisvi.jigsawpp.gas;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

public abstract class AbstractGasClass {
    public ParticleOptions particle;
    public SoundEvent sound;

    public SoundEvent getSound() {
        return sound;
    }
    public void setSound(SoundEvent sound) {
        this.sound = sound;
    }
    public ParticleOptions getParticle() {
        return particle;
    }
    public void setParticle(ParticleOptions particle) {
        this.particle = particle;
    }

    public float gasCloudSizeMultiplier = 1f;
    public int effectApplyingTiming = 0;

    public boolean gasApplyCondition(Entity entity){
        if (entity instanceof LivingEntity livingEntity){
            if (!PoopProtectionArmorConditions.isProtectedFromGas(entity)){
                return true;
            }
        }
        return false;
    }

    public void gasSpawnCloud(){

    }

    public void gasApplyEffect(Entity entity){
        if (entity instanceof LivingEntity livingEntity && this.gasApplyCondition(entity)){
            if (livingEntity instanceof Player player){
                Level level = player.level();
                if (!level.isClientSide()) {
                    level.playSound(null, player.getOnPos(), this.getSound(), SoundSource.HOSTILE, 0.5f, 1);
                } else {
                    level.playLocalSound(player.getOnPos(), this.getSound(), SoundSource.HOSTILE, 0.5f, 1, false);
                }
            }
        } else {
            return;
        }
    }

    
}
