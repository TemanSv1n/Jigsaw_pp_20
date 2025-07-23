package net.svisvi.jigsawpp.entity.emitters;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.NuclearShroom;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class AbstractEmitterEntity extends Entity implements TraceableEntity {
    private static final Logger LOGGER = LogUtils.getLogger();

    // Synced Data Definitions
    private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_DURATION = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ParticleOptions> DATA_PARTICLE = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.PARTICLE);
    private static final EntityDataAccessor<Boolean> DEBUG_MODE = SynchedEntityData.defineId(AbstractEmitterEntity.class, EntityDataSerializers.BOOLEAN);

    // Configuration
    private static final float DEFAULT_RADIUS = 2.5F;
    private static final int DEFAULT_DURATION = 20 * 60 * 5; // 5 minutes in ticks
    private static final ParticleOptions DEFAULT_PARTICLE = ModParticleTypes.POOP.get();
    private static final float MIN_RADIUS = 0.5F;
    private static final float MAX_RADIUS = 32.0F;
    private static final float DEFAULT_DENSITY = 35;
    //private static final int DEFAULT_EFFECT_APPLYING_COOLDOWN = 5;

    // Runtime properties
    private int waitTime = 0;
    private float radiusOnUse;
    private float radiusPerTick;
    public float particleSpeed = 0.01f;
    public float density = DEFAULT_DENSITY;
    //public int particleCount = (int) (DEFAULT_DENSITY * getRadius());
    //public int effect_applying_cooldown

    @Nullable private LivingEntity owner;
    @Nullable private UUID ownerUUID;

    // ========== Constructors ==========
    public AbstractEmitterEntity(EntityType<? extends AbstractEmitterEntity> type, Level level, ParticleOptions particleOptions, float radiuss, int durra) {
        super(type, level);
        this.noPhysics = true;
        this.setParticle(particleOptions);
        this.setRadius(radiuss);
        this.setDuration(durra);
        this.setDebugMode(false); // Default to normal mode
    }

    public AbstractEmitterEntity(EntityType<? extends AbstractEmitterEntity> type, Level level) {
        this(type, level, DEFAULT_PARTICLE, DEFAULT_RADIUS, DEFAULT_DURATION);
    }

    public AbstractEmitterEntity(Level level, double x, double y, double z) {
        this(level, x, y, z, DEFAULT_PARTICLE, DEFAULT_RADIUS, DEFAULT_DURATION);

    }

    public AbstractEmitterEntity(Level level, double x, double y, double z, ParticleOptions particleOptions, float radiuss, int durra){
        this(ModEntities.ABSTRACT_EMITTER.get(), level, particleOptions, radiuss, durra);
        this.setPos(x, y, z);
    }

    // ========== Synced Data Setup ==========
    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_RADIUS, DEFAULT_RADIUS);
        this.entityData.define(DATA_DURATION, DEFAULT_DURATION);
        this.entityData.define(DATA_WAITING, false);
        this.entityData.define(DATA_PARTICLE, DEFAULT_PARTICLE);
        this.entityData.define(DEBUG_MODE, false);
    }

    // ========== Debug Mode ==========
    public boolean isDebugMode() {
        return this.entityData.get(DEBUG_MODE);
    }

    public void setDebugMode(boolean debug) {
        this.entityData.set(DEBUG_MODE, debug);
        if (debug) {
            this.setDuration(Integer.MAX_VALUE); // Never expire in debug mode
            LOGGER.info("Emitter debug mode enabled - will not despawn");
        }
    }

    // ========== Lifetime Management ==========
    public int getDuration() {
        return this.entityData.get(DATA_DURATION);
    }

    public void setDuration(int duration) {
        if (!this.level().isClientSide) {
            this.entityData.set(DATA_DURATION, Math.max(0, duration));
        }
    }

    public int getRemainingTime() {
        return isDebugMode() ? Integer.MAX_VALUE : (this.getDuration() - this.tickCount);
    }

    // ========== Core Logic ==========
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            //spawnClientParticles();
            return;
        }

        spawnParticles(getParticleCount(), particleSpeed);

        // Skip lifetime checks in debug mode
        if (!isDebugMode() && this.getRemainingTime() <= 0) {
            this.discard();
            return;
        }

        if (this.radiusPerTick != 0.0F) {
            float newRadius = this.getRadius() + this.radiusPerTick;
            if (newRadius < MIN_RADIUS) {
                this.discard();
                return;
            }
            this.setRadius(newRadius);
        }

        if (this.tickCount % 1 == 0) {
            this.applyEffects();
        }
    }


    public void spawnParticles(int count, float speed){
        if (this.level() instanceof ServerLevel serverLevel) {
            NuclearShroom.sendFarParticles(serverLevel,
                    this.getParticle(),
                    this.getX(), this.getY(), this.getZ(),
                    getRadius(),
                    getRadius(),
                    getRadius(),
                    count, speed);
        }
    }


    protected void applyEffects() {
        Level level = this.level();

        final Vec3 _center = this.position();
        List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(this.getRadius() * 2), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
        for (Entity entityiterator : _entfound) {
            if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                effectForEach(_entity);
            }
        }
    }

    protected void effectForEach(LivingEntity entity){

    }

    // ========== Data Accessors ==========
    public float getRadius() {
        return this.entityData.get(DATA_RADIUS);
    }

    public void setRadius(float radius) {
        if (!this.level().isClientSide) {
            this.entityData.set(DATA_RADIUS, Mth.clamp(radius, MIN_RADIUS, MAX_RADIUS));
        }
    }

    public ParticleOptions getParticle() {
        return this.entityData.get(DATA_PARTICLE);
    }

    public void setParticle(ParticleOptions particle) {
        if (!this.level().isClientSide) {
            this.entityData.set(DATA_PARTICLE, particle);
        }
    }

    public boolean isWaiting() {
        return this.entityData.get(DATA_WAITING);
    }

    public void setWaiting(boolean waiting) {
        if (!this.level().isClientSide) {
            this.entityData.set(DATA_WAITING, waiting);
        }
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getParticleCount(){
        return (int) (getDensity() * getRadius());
    }



    // ========== Owner Handling ==========
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

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner != null ? owner.getUUID() : null;
    }

    // ========== NBT Handling ==========
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        // Read basic properties
        this.setRadius(tag.getFloat("Radius"));
        this.setDuration(tag.getInt("Duration"));
        this.setDebugMode(tag.getBoolean("DebugMode"));

        // Handle particle reading with proper error handling
        if (tag.contains("Particle", 8)) { // 8 = String tag ID
            try {
                ParticleOptions particle = ParticleArgument.readParticle(
                        new StringReader(tag.getString("Particle")),
                        BuiltInRegistries.PARTICLE_TYPE.asLookup()
                );
                this.setParticle(particle);
            } catch (CommandSyntaxException e) {
                LOGGER.error("Failed to parse particle type: {}", tag.getString("Particle"), e);
                // Fall back to default particle
                this.setParticle(DEFAULT_PARTICLE);
            }
        }

        // Handle owner UUID
        if (tag.hasUUID("Owner")) {
            this.ownerUUID = tag.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("Radius", this.getRadius());
        tag.putInt("Duration", this.getDuration());
        tag.putString("Particle", this.getParticle().writeToString());
        tag.putBoolean("DebugMode", this.isDebugMode());

        if (this.ownerUUID != null) {
            tag.putUUID("Owner", this.ownerUUID);
        }
    }

    // ========== Entity Properties ==========
    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, 0.5F);
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_RADIUS.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    // ========== Utility Methods ==========
    public void setLifetimeSeconds(float seconds) {
        this.setDuration((int)(seconds * 20));
    }

    public void setLifetimeMinutes(float minutes) {
        this.setDuration((int)(minutes * 1200)); // 20 ticks * 60 seconds
    }
}