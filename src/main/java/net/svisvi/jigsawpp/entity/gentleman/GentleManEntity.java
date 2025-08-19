package net.svisvi.jigsawpp.entity.gentleman;


import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.emitters.BrownHoleGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.gamerules.ModGameRules;

public class GentleManEntity extends Monster{
    protected byte phase = 1;

    public static final Component spawnTntAtPlayerComponent = Component.translatable("entity.gentleman.chat.spawntnt");
    public static final Component shootPurgenPiluleComponent = Component.translatable("entity.gentleman.chat.pilule_shoot");
    public static final Component purgenManSummonComponent = Component.translatable("entity.gentleman.chat.phase1");
    public static final Component purgenmanphase2component = Component.translatable("entity.gentleman.chat.phase2");
    public static final Component purgenmanphase3component = Component.translatable("entity.gentleman.chat.phase3");
    public static final Component purgenmanDeathcomponent = Component.translatable("entity.gentleman.chat.death");

    private final ServerBossEvent bossEvent =
        new ServerBossEvent(this.getDisplayName(),
            BossBarColor.RED,
            BossBarOverlay.NOTCHED_10);

    public GentleManEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.GENTLEMAN.get(), world);
    }

    public GentleManEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        xpReward = 10;
        setNoAi(false);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.step")), 0.15f, 1);
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.death"));
    }

                
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        if (!this.level().isClientSide()) {
            MinecraftServer server = this.level().getServer();
            if (server != null) {

                server.getPlayerList().broadcastSystemMessage(
                    purgenManSummonComponent, false
                );
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return super.hurt(source, amount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
        builder = builder.add(Attributes.MAX_HEALTH, 300);
        builder = builder.add(Attributes.ARMOR, 25);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
        return builder;
    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        if(!this.level().isClientSide()) {
            this.level().getGameRules().getRule(ModGameRules.APOOCALYPSE).set(true, this.level().getServer());
            BrownHoleGasEmitterEntity bHGasEmitter = new BrownHoleGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ(), 2, 6000);
            this.level().addFreshEntity(bHGasEmitter);

            MinecraftServer server = this.level().getServer();
            if (server != null) { server.getPlayerList().broadcastSystemMessage(purgenmanDeathcomponent, false); }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag pCompoundTag = new CompoundTag();
        return pCompoundTag;
    }
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName()); // keep synced with entity name
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player); // add to boss bar
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player); // remove from boss bar
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompoundTag) {
        super.addAdditionalSaveData(pCompoundTag);
        // pCompoundTag.putByte("Phase", this.phase);
        
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }
    


    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return !this.isLeashed();
    }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.6F * this.getEyeHeight()-0.35D), (double)(this.getBbWidth() * 0.4F)-0.2D);
    }

    @Override
    protected double followLeashSpeed() {
        return 1.5D;
    }

    public byte getPhase() { return this.phase; }

    public void setPhase(byte pPhase) {
        this.phase = pPhase;
    }
    private void shoot(LivingEntity pEntity, Level pLevel, Projectile pProjectile, float pVelocity, float pInaccuracy) {
        if(!this.level().isClientSide()) {
            pProjectile.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
            this.level().addFreshEntity(pProjectile);
        }
    }


    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse();
            }
        });
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, (float) 100));

        this.goalSelector.addGoal(5, new GentleManGoals.FirstGentlemanPhase(this, 0.7, true, (byte) 1));
        this.goalSelector.addGoal(5, new GentleManGoals.SecondGentlemanPhase(this, 0.999999999, true, (byte) 2));
        this.goalSelector.addGoal(5, new GentleManGoals.ThirdGentlemanPhase(this, 0.7, true, (byte)3));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getHealth() < this.getMaxHealth() / 1.5f && phase < 2) {
            phase = 2;
            MinecraftServer server = this.level().getServer();
            if (server != null) { server.getPlayerList().broadcastSystemMessage(purgenmanphase2component, false); }
        }
        else if (this.getHealth() < this.getMaxHealth() / 3f && phase < 3) { 
            phase = 3;
            MinecraftServer server = this.level().getServer();
            if (server != null) { server.getPlayerList().broadcastSystemMessage(purgenmanphase3component, false); }
        }
        if (this.getTarget() != null && this.isInFluidType()) {
            if(!this.level().isClientSide())
            this.level().addFreshEntity(new PoopGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ(), 2, 60));
            this.teleportTo(this.getTarget().getX(), this.getTarget().getY() + 0.5, this.getTarget().getZ());
        }
    }
}

