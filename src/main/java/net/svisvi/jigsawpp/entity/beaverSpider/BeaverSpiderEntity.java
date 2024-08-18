package net.svisvi.jigsawpp.entity.beaverSpider;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraft.network.syncher.EntityDataAccessor;

import java.util.Random;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.eventbus.api.Event.Result;
import net.svisvi.jigsawpp.entity.projectile.BeaverBombProjectile;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.entity.projectile.SlonProjectile;
import net.svisvi.jigsawpp.init.ModSounds;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenGunItem;


/**
 * BeaverSpiderEntity
 */
public class BeaverSpiderEntity extends Monster implements RangedAttackMob{
   private static final EntityDataAccessor<Byte> DATA_FLAGS_ID;
   private static final float SPIDER_SPECIAL_EFFECT_CHANCE = 0.1F;
  
  public BeaverSpiderEntity(EntityType<? extends BeaverSpiderEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
  }

  protected void registerGoals() {
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 40.0F));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 60, 10.0F));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new BeaverSpiderAttackGoal(this)); 
      this.targetSelector.addGoal(2, new BeaverSpiderNearestAttackableTarget<>(this, Player.class));
  }

  public double getPassengersRidingOffset() {
    return (double)(this.getBbHeight() * 0.5F);
  }

  protected PathNavigation createNavigation(Level pLevel) {
     return new WallClimberNavigation(this, pLevel);
  }

  protected void defineSynchedData() {
    super.defineSynchedData();
    this.entityData.define(DATA_FLAGS_ID, (byte)0);
  }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         this.setClimbing(this.horizontalCollision);
      }

   }

  public static AttributeSupplier.Builder createAttributes() {
    return Monster.createMonsterAttributes()
      .add(Attributes.MAX_HEALTH, 40)
      .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896);
  }

  protected SoundEvent getAmbientSound() {
     return ModSounds.BEAVER_SPIDER_EMBIENT.get(); 
  }

   protected SoundEvent getHurtSound(DamageSource pDamageSource) {
      return ModSounds.BEAVER_SPIDER_EMBIENT.get();
   }

   protected SoundEvent getDeathSound() {
      return ModSounds.BEAVER_SPIDER_EMBIENT.get();
   }

   protected void playStepSound(BlockPos pPos, BlockState pBlock) {
      this.playSound(ModSounds.BEAVER_SPIDER_CHOP.get(), 0.15F, 1.0F);
   }

   public boolean onClimbable() {
      return this.isClimbing();
   }

   public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier) {
      if (!pState.is(Blocks.COBWEB)) {
         super.makeStuckInBlock(pState, pMotionMultiplier);
      }

   }

   public MobType getMobType() {
      return MobType.ARTHROPOD;
   }

   public boolean canBeAffected(MobEffectInstance pPotioneffect) {
      if (pPotioneffect.getEffect() == MobEffects.POISON) {
         MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, pPotioneffect);
         MinecraftForge.EVENT_BUS.post(event);
         return event.getResult() == Result.ALLOW;
      } else {
         return super.canBeAffected(pPotioneffect);
      }
   }

   public boolean isClimbing() {
      return ((Byte)this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
   }

   public void setClimbing(boolean pClimbing) {
      byte b0 = (Byte)this.entityData.get(DATA_FLAGS_ID);
      if (pClimbing) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 &= -2;
      }

      this.entityData.set(DATA_FLAGS_ID, b0);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
      //SpawnGroupData pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
      //RandomSource randomsource = pLevel.getRandom();
      //prigoditsa
      /*if (randomsource.nextInt(100) == 0) {
         Skeleton skeleton = (Skeleton)EntityType.SKELETON.create(this.level());
         if (skeleton != null) {
            skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
            skeleton.finalizeSpawn(pLevel, pDifficulty, pReason, (SpawnGroupData)null, (CompoundTag)null);
            skeleton.startRiding(this);
         }
      }*/

      /*if (pSpawnData == null) {
         pSpawnData = new SpiderEffectsGroupData();
         if (pLevel.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()) {
            ((SpiderEffectsGroupData)pSpawnData).setRandomEffect(randomsource);
         }
      }*/

      /*if (pSpawnData instanceof SpiderEffectsGroupData spider$spidereffectsgroupdata) {
         MobEffect mobeffect = spider$spidereffectsgroupdata.effect;
         if (mobeffect != null) {
            this.addEffect(new MobEffectInstance(mobeffect, -1));
         }
      }*/

      return (SpawnGroupData)pSpawnData;
   }

   protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
      return 0.65F;
   }

   static {
      DATA_FLAGS_ID = SynchedEntityData.defineId(BeaverSpiderEntity.class, EntityDataSerializers.BYTE);
   }


  @Override
  public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
    Random rand = new Random();
    float randf = rand.nextFloat(0.0f, 1.0f);
    if(!this.level().isClientSide()){
    if(randf <= 0.5f){
      BeaverBombProjectile bombr = new BeaverBombProjectile(this.level());
      bombr.shootEnt(this.level(), this, (float) (pVelocity * 1.5), 0.4f);
    }}
  }
 
}
