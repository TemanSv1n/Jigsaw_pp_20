package net.svisvi.jigsawpp.entity.emitters;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AbstractEmitterEntity extends Entity implements TraceableEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int TIME_BETWEEN_APPLICATIONS = 5;
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> REMAINING_TIME = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.PARTICLE);
    private static final float MAX_RADIUS = 32.0F;
    private static final float MINIMAL_RADIUS = 0.5F;
    private static final float DEFAULT_RADIUS = 3.0F;
    public static final float DEFAULT_WIDTH = 6.0F;
    public static final float HEIGHT = 0.5F;
    private int duration = 600;
    public static int defaultLiveTime = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private boolean fixedColor;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusPerTick;
    @Nullable
    private LivingEntity owner;
    @Nullable
    private UUID ownerUUID;

    public AbstractEmitterEntity(EntityType<? extends AbstractEmitterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    public AbstractEmitterEntity(Level pLevel, double pX, double pY, double pZ) {
        this(ModEntities.ABSTRACT_EMITTER.get(), pLevel);
        this.setPos(pX, pY, pZ);
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, 3.0F);
        this.getEntityData().define(DATA_WAITING, false);
        this.getEntityData().define(REMAINING_TIME, defaultLiveTime);
        this.getEntityData().define(DATA_PARTICLE, ParticleTypes.ENTITY_EFFECT);
    }

    public void setRadius(float pRadius) {
        if (!this.level().isClientSide) {
            this.getEntityData().set(DATA_RADIUS, Mth.clamp(pRadius, 0.0F, 32.0F));
        }

    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public ParticleOptions getParticle() {
        return this.getEntityData().get(DATA_PARTICLE);
    }

    public void setParticle(ParticleOptions pParticleOption) {
        this.getEntityData().set(DATA_PARTICLE, pParticleOption);
    }

    public int getRemainingTime() {
        return this.getEntityData().get(REMAINING_TIME);
    }

    public void setRemainingTime(int time) {
        this.getEntityData().set(REMAINING_TIME, time);
    }

    /**
     * Sets if the cloud is waiting. While waiting, the radius is ignored and the cloud shows fewer particles in its
     * area.
     */
    protected void setWaiting(boolean pWaiting) {
        this.getEntityData().set(DATA_WAITING, pWaiting);
    }

    /**
     * Returns {@code true} if the cloud is waiting. While waiting, the radius is ignored and the cloud shows fewer
     * particles in its area.
     */
    public boolean isWaiting() {
        return this.getEntityData().get(DATA_WAITING);
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int pDuration) {
        this.duration = pDuration;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();

    }

    public float getRadiusOnUse() {
        return this.radiusOnUse;
    }

    public void setRadiusOnUse(float pRadiusOnUse) {
        this.radiusOnUse = pRadiusOnUse;
    }

    public float getRadiusPerTick() {
        return this.radiusPerTick;
    }

    public void setRadiusPerTick(float pRadiusPerTick) {
        this.radiusPerTick = pRadiusPerTick;
    }

    public int getDurationOnUse() {
        return this.durationOnUse;
    }

    public void setDurationOnUse(int pDurationOnUse) {
        this.durationOnUse = pDurationOnUse;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public void setWaitTime(int pWaitTime) {
        this.waitTime = pWaitTime;
    }

    public void setOwner(@Nullable LivingEntity pOwner) {
        this.owner = pOwner;
        this.ownerUUID = pOwner == null ? null : pOwner.getUUID();
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.tickCount = pCompound.getInt("Age");
        this.duration = pCompound.getInt("Duration");
        this.waitTime = pCompound.getInt("WaitTime");
        this.reapplicationDelay = pCompound.getInt("ReapplicationDelay");
        this.durationOnUse = pCompound.getInt("DurationOnUse");
        this.radiusOnUse = pCompound.getFloat("RadiusOnUse");
        this.radiusPerTick = pCompound.getFloat("RadiusPerTick");
        this.setRadius(pCompound.getFloat("Radius"));
        this.setRemainingTime(pCompound.getInt("RemainingTime"));
        if (pCompound.hasUUID("Owner")) {
            this.ownerUUID = pCompound.getUUID("Owner");
        }

        if (pCompound.contains("Particle", 8)) {
            try {
                this.setParticle(ParticleArgument.readParticle(new StringReader(pCompound.getString("Particle")), BuiltInRegistries.PARTICLE_TYPE.asLookup()));
            } catch (CommandSyntaxException commandsyntaxexception) {
                LOGGER.warn("Couldn't load custom particle {}", pCompound.getString("Particle"), commandsyntaxexception);
            }
        }

    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Age", this.tickCount);
        pCompound.putInt("Duration", this.duration);
        pCompound.putInt("WaitTime", this.waitTime);
        pCompound.putInt("ReapplicationDelay", this.reapplicationDelay);
        pCompound.putInt("DurationOnUse", this.durationOnUse);
        pCompound.putFloat("RadiusOnUse", this.radiusOnUse);
        pCompound.putFloat("RadiusPerTick", this.radiusPerTick);
        pCompound.putFloat("Radius", this.getRadius());
        pCompound.putString("Particle", this.getParticle().writeToString());
        pCompound.putInt("RemainingTime", this.getRemainingTime());
        if (this.ownerUUID != null) {
            pCompound.putUUID("Owner", this.ownerUUID);
        }

    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_RADIUS.equals(pKey)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(pKey);
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 0.5F);
    }
}
