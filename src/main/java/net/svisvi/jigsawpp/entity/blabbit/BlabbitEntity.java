package net.svisvi.jigsawpp.entity.blabbit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.recipe.ElephantingRecipe;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

public class BlabbitEntity extends Animal {
    private static final EntityDataAccessor<Boolean> JUMPING =
            SynchedEntityData.defineId(BlabbitEntity.class, EntityDataSerializers.BOOLEAN);
    public BlabbitEntity(EntityType<? extends Animal> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.moveControl = new BlabbitGoals.BlabbitMoveControl(this);

    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState jumpAnimationState = new AnimationState();
    private int jumpAnimationTimeout = 0;
    private int jumpAnimationSwitcher = 0;

    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final Set<ResourceLocation> SPAWN_BIOMES = Set.of(new ResourceLocation("beach"), new ResourceLocation("swamp"));

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(JUMPING, false);
    }

    public void setJumping_1(boolean jumping){
        this.entityData.set(JUMPING, jumping);
    }
    public boolean isJumping(){
        return this.entityData.get(JUMPING);
    }
    public static void init() {
        SpawnPlacements.register(ModEntities.BLABBIT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }




    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }

    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0 && !this.isJumping()) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }

        if (this.isJumping() && jumpAnimationTimeout <= 0){
            jumpAnimationTimeout = 10; //length in ticks
            System.out.println("time to start");
            idleAnimationState.stop();
            jumpAnimationState.start(this.tickCount);
        } else {
            --this.jumpAnimationTimeout;
        }

        if (this.isJumping() && jumpAnimationSwitcher >= 10){
            this.setJumping_1(false);
            jumpAnimationSwitcher = 0;
        } else {
            ++this.jumpAnimationSwitcher;
        }

        if (!this.isJumping()){
            jumpAnimationState.stop();
        }
    }

//    @Override
//    protected void updateWalkAnimation(float pPartialTick) {
//        super.updateWalkAnimation(pPartialTick);
//        float f;
//        if(this.getPose() == Pose.LONG_JUMPING) {
//            f = Math.min(pPartialTick * 6f, 1f);
//        }else {
//            f = 0f;
//        }
//        this.walkAnimation.update(f, 0.2f);
//    }

    @Override
    protected void registerGoals(){

        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BlabbitGoals.BlabbitAttackGoal(this));
        this.goalSelector.addGoal(3, new BlabbitGoals.BlabbitRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new BlabbitGoals.BlabbitKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_289461_) -> {
            return Math.abs(p_289461_.getY() - this.getY()) <= 4.0D;
        })); //placeholder for beaver zomdbie
    }
    //goaaaaaaaaaaaaaaaaals
    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }
    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }
    protected SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP;
    }
    protected float getSoundVolume() {
        return 0.4F * 1;
    }
    float getSoundPitch() {
        float f = 0.8F;
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * f;
    }
    @Override
    protected void jumpFromGround() {
        System.out.println("JUMPED");
        super.jumpFromGround();
        this.setJumping_1(true);

    }

//    @Override
//    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
//        setJumping_1(false);
//        return super.causeFallDamage(pFallDistance, pMultiplier, pSource);
//    }

    @Override
    protected float getJumpPower() {
        return 0.62F * this.getBlockJumpFactor() + this.getJumpBoostPower();
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 20D)
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK,2f)
                .add(Attributes.ATTACK_DAMAGE, 8)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1f)
                ;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherPartner){
        return null;
    }


    //decorative stuff
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.moss.place"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.moss.break"));
    }

    @Override
    public boolean hurt(DamageSource damagesource, float amount) {
        if (damagesource.is(DamageTypes.FALL))
            return false;
        if (damagesource.is(DamageTypes.CACTUS))
            return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH))
            return false;
        return super.hurt(damagesource, amount);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }
    @Override
    protected void doPush(Entity entityIn) {
    }

    @Override
    protected void pushEntities() {
    }
    // SCRIPTING AND EDGING
    @Override
    public void baseTick() {
        super.baseTick();
        //fear

    }





}
