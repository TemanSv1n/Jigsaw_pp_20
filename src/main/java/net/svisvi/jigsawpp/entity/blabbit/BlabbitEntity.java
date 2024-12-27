package net.svisvi.jigsawpp.entity.blabbit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.recipe.ElephantingRecipe;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BlabbitEntity extends Monster implements IForgeShearable, Shearable {
    private static final EntityDataAccessor<Boolean> JUMPING =
            SynchedEntityData.defineId(BlabbitEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FORCE_JUMP =
            SynchedEntityData.defineId(BlabbitEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FEAR =
            SynchedEntityData.defineId(BlabbitEntity.class, EntityDataSerializers.BOOLEAN);
    public BlabbitEntity(EntityType<? extends Monster> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
        this.moveControl = new BlabbitGoals.BlabbitMoveControl(this);

    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState jumpAnimationState = new AnimationState();
    private int jumpAnimationTimeout = 0;
    private int jumpAnimationSwitcher = 0;

    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    //private static final Set<ResourceLocation> SPAWN_BIOMES = Set.of(new ResourceLocation("beach"), new ResourceLocation("swamp"));

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(JUMPING, false);
        this.entityData.define(FORCE_JUMP, false);
        this.entityData.define(FEAR, false);
    }

    public void setJumping_1(boolean jumping){
        this.entityData.set(JUMPING, jumping);
    }
    public boolean isJumping_1(){
//        System.out.println("JUMP DATA");
//        System.out.println(this.entityData.get(JUMPING));
        return this.entityData.get(JUMPING);

    }
    public void setForceJump(boolean jumping){
        this.entityData.set(FORCE_JUMP, jumping);
    }
    public boolean isForceJumping(){
//        System.out.println("JUMP DATA");
//        System.out.println(this.entityData.get(JUMPING));
        return this.entityData.get(FORCE_JUMP);

    }

    public void setFear(boolean jumping){
        this.entityData.set(FEAR, jumping);
    }
    public boolean isFear(){
//        System.out.println("JUMP DATA");
//        System.out.println(this.entityData.get(JUMPING));
        return this.entityData.get(FEAR);

    }

    @Override
    public double getPerceivedTargetDistanceSquareForMeleeAttack(LivingEntity pEntity) {
        if (pEntity == null){
            return 1488;
        }
        return Math.max(this.distanceToSqr(pEntity.getMeleeAttackReferencePosition()), this.distanceToSqr(pEntity.position()));
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (this.getHealth()/this.getMaxHealth() < 0.4){
            this.setFear(true);
        } else {
            this.setFear(false);
        }

    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }

        if (this.isJumping_1() && this.jumpAnimationTimeout <= 0){
            this.jumpAnimationTimeout = 10; //length in ticks
//            System.out.println("time to start");
            //idleAnimationState.stop();
            this.jumpAnimationState.start(this.tickCount);
        } else {
            --this.jumpAnimationTimeout;
        }
        if(!this.isJumping_1()) {
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
        this.goalSelector.addGoal(2, new BlabbitGoals.BlabbitAttackGoal(this, 1.3D, true){
            @Override
            public boolean canUse() {
                Entity entity = BlabbitEntity.this;
                if (entity instanceof BlabbitEntity entity_) {
                    if (entity_.isFear()){
                        return false;
                    }
                }
                return super.canUse() && true;
            }

            @Override
            public boolean canContinueToUse() {
                Entity entity = BlabbitEntity.this;
                if (entity instanceof BlabbitEntity entity_) {
                    if (entity_.isFear()){
                        return false;
                    }
                }
                return super.canContinueToUse() && true;
            }
        });
        this.goalSelector.addGoal(4, new BlabbitGoals.BlabbitRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new BlabbitGoals.BlabbitKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, BeaverZombieEntity.class, 10, true, false, (p_289461_) -> {
            return Math.abs(p_289461_.getY() - this.getY()) <= 4.0D;
        })); //placeholder for beaver zomdbie
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this){
            @Override
            public boolean canUse() {
                Entity entity = BlabbitEntity.this;
                if (entity instanceof BlabbitEntity entity_) {
                    if (entity_.isFear()){
                        return false;
                    }
                }
                return super.canUse() && true;
            }

            @Override
            public boolean canContinueToUse() {
                Entity entity = BlabbitEntity.this;
                if (entity instanceof BlabbitEntity entity_) {
                    if (entity_.isFear()){
                        return false;
                    }
                }
                return super.canContinueToUse() && true;
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.2){
            @Override
            public boolean canUse() {
                Entity entity = BlabbitEntity.this;
                if (entity instanceof BlabbitEntity entity_) {
                    return super.canUse() && entity_.isFear();
                }
                return false;
            }

            @Override
            public boolean canContinueToUse() {
                Entity entity = BlabbitEntity.this;
                if (entity instanceof BlabbitEntity entity_) {
                    return super.canContinueToUse() && entity_.isFear();
                }
                return false;
            }
        });

    }
    //goaaaaaaaaaaaaaaaaals
    protected boolean isDealsDamage() {
        return true;
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
//        System.out.println("JUMPED");
        super.jumpFromGround();
        this.setJumping_1(true);

    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        setJumping_1(false);
        if (isForceJumping()){
            setForceJump(false);
            forceJumpDamage(this.level(), this.position());
        }
        Random random = new Random();
        if (random.nextInt() > 0.9 && (this.isAggressive() || this.lastHurtByPlayerTime > 0)){
            setForceJump(true);
        }
        return super.causeFallDamage(pFallDistance, pMultiplier, pSource);
    }

    @Override
    protected float getJumpPower() {
        float k = 0.42f;
        if (this.isLeashed()){k = 0.32f;}
        else if (this.isFear()){k = 1.02f;}
        else if (this.isForceJumping()){k = 0.72f;}
        else if (this.lastHurtByPlayerTime > 0){k = 0.62f;}
//        System.out.println(k);
//        System.out.println(this.lastHurtByPlayerTime);

        return k * this.getBlockJumpFactor() + this.getJumpBoostPower();
    }

    public void forceJumpDamage(LevelAccessor world, Vec3 vec3){
        BlockPos pPos = BlockPos.containing(vec3.x, vec3.y, vec3.z);
        if (world instanceof Level _level) {
            if (!_level.isClientSide()) {
                _level.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.husk.converted_to_zombie")), SoundSource.HOSTILE, 1, 1);
            } else {
                _level.playLocalSound(pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.husk.converted_to_zombie")), SoundSource.HOSTILE, 1, 1, false);
            }
        }
        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.BUBBLE_POP, pPos.getX(), pPos.getY(), pPos.getZ(), 40, 1.5, 0.2, 1.5, 0.2);
        {
            final Vec3 _center = vec3;
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
            for (Entity entityiterator : _entfound) {
                if (entityiterator != this)
                entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), this), 8);
            }
        }

    }
    @Override
    public boolean canAttack(LivingEntity pTarget) {
        return pTarget instanceof Player && this.level().getDifficulty() == Difficulty.PEACEFUL ? false : pTarget.canBeSeenAsEnemy();
    }

    public static boolean init(EntityType<? extends BlabbitEntity> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        int y = pPos.getY();
        return checkMonsterSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom) && y <= 40 && ((pLevel instanceof Level _lvl ? _lvl.dimension() : (pLevel instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD)) == Level.OVERWORLD);
    } 

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 20D)
                .add(Attributes.MAX_HEALTH, 200)
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

//    @Nullable
//    @Override
//    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherPartner){
//        return null;
//    }


    //decorative stuff
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return getRandomSound();
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:blabbit_live_2"));
    }
    @Override
    public SoundEvent getAmbientSound() {
        return getRandomSound();
    }


    public SoundEvent getRandomSound(){
        Random random = new Random();
        int rand = random.nextInt(4);
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:blabbit_live_" + Integer.toString(rand)));
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

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return !this.isLeashed() && this.isFear();
    }
    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.6F * this.getEyeHeight()-0.35D), (double)(this.getBbWidth() * 0.4F)-0.2D);
    }
    @Override
    protected double followLeashSpeed() {
        return 1.5D;
    }

    public static final Set<Item> FOODS = new HashSet<Item>();
    static {
        FOODS.add(Items.CARROT);
        FOODS.add(Items.GOLDEN_CARROT);
    }
    public boolean isFood(ItemStack pStack) {
        return FOODS.contains(pStack.getItem());
    }



    //NO BALLS ?
    public boolean isShearable(@NotNull ItemStack item, Level level, BlockPos pos) {
        return this.isLeashed();
    }

    public @NotNull List<ItemStack> onSheared(@Nullable Player player, @NotNull ItemStack item, Level level, BlockPos pos, int fortune) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        list.add(new ItemStack(ModItems.BLABBALL.get(), 2));
        if (this.isLeashed()){
            list.add(new ItemStack(Items.LEAD, 1));
        }

        if (!level.isClientSide()) {
            level.playSound(null, pos, SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1, 1);
            level.playSound(null, pos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1);
        } else {
            level.playLocalSound(pos, SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1, 1, false);
            level.playLocalSound(pos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1, false);
        }
        if (level instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.EXPLOSION, pos.getX(), pos.getY()+1, pos.getZ(), 12, 0.5, 0.5, 0.5, 0);

        Rabbit rabbit = (Rabbit) EntityType.RABBIT.create(level);
        rabbit.moveTo(pos, 0.0F, 0.0F);
        rabbit.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(this.getOnPos()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
        level.addFreshEntity(rabbit);
        this.discard();
        return list;
    }
    
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        pPlayer.getItemInHand(pHand);
        return super.mobInteract(pPlayer, pHand);
    }
    @Override //spizzeno s Mob.java, but edited
    public final InteractionResult interact(Player pPlayer, InteractionHand pHand){
        if (!this.isAlive()) {
            return InteractionResult.PASS;
        } else if (this.getLeashHolder() == pPlayer && pPlayer.getItemInHand(pHand).isEmpty()) {
            this.dropLeash(true, !pPlayer.getAbilities().instabuild);
            this.gameEvent(GameEvent.ENTITY_INTERACT, pPlayer);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            InteractionResult interactionresult = this.checkAndHandleImportantInteractions(pPlayer, pHand);
            if (interactionresult.consumesAction()) {
                this.gameEvent(GameEvent.ENTITY_INTERACT, pPlayer);
                return interactionresult;
            } else {
                interactionresult = this.mobInteract(pPlayer, pHand);
                if (interactionresult.consumesAction()) {
                    this.gameEvent(GameEvent.ENTITY_INTERACT, pPlayer);
                    return interactionresult;
                } else {
                    return InteractionResult.PASS;
                }
            }
        }
    }
    @Override
    public void shear(SoundSource pCategory) {
        this.level().playSound((Player)null, this, SoundEvents.SHEEP_SHEAR, pCategory, 1.0F, 1.0F);
        int i = 2;

        for(int j = 0; j < i; ++j) {
            ItemEntity itementity = this.spawnAtLocation((ItemLike)ModItems.BLABBALL.get(), 1);
            if (itementity != null) {
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
            }
        }
        Rabbit rabbit = (Rabbit) EntityType.RABBIT.create(this.level());
        rabbit.moveTo(rabbit.getOnPos(), 0.0F, 0.0F);
        rabbit.finalizeSpawn((ServerLevelAccessor) this.level(), this.level().getCurrentDifficultyAt(this.getOnPos()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
        this.level().addFreshEntity(rabbit);
        this.discard();

    }

    @Override
    public boolean readyForShearing() {
        return true;
    }
}


