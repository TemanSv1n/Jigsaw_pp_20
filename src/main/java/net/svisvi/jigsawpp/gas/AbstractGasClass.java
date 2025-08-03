package net.svisvi.jigsawpp.gas;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.emitters.AbstractEmitterEntity;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class AbstractGasClass {
    public ParticleOptions particle;
    public SoundEvent sound;
    public AbstractEmitterEntity emitter;

    @Nullable
    private LivingEntity owner;
    @Nullable private UUID ownerUUID;


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
    public AbstractEmitterEntity getEmitter() {
        return emitter;
    }
    public void setEmitter(AbstractEmitterEntity emitter) {
        this.emitter = emitter;
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

    // ========== Owner Handling ==========
    @Nullable
    public LivingEntity getOwner(Level level) {
        if (this.owner == null && this.ownerUUID != null && level instanceof ServerLevel) {
            Entity entity = ((ServerLevel)level).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }
        return this.owner;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner != null ? owner.getUUID() : null;
    }

    
}
