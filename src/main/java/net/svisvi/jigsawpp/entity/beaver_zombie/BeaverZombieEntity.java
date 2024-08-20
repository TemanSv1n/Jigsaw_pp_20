package net.svisvi.jigsawpp.entity.beaver_zombie; 

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.Zombie.ZombieGroupData;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.registries.ForgeRegistries;



public class BeaverZombieEntity extends Monster {
   private static final UUID SPEED_MODIFIER_BABY_UUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier SPEED_MODIFIER_BABY;
   private static final EntityDataAccessor<Boolean> DATA_BABY_ID;
   private static final EntityDataAccessor<Integer> DATA_SPECIAL_TYPE_ID;
   private static final EntityDataAccessor<Boolean> DATA_DROWNED_CONVERSION_ID;
   public static final float ZOMBIE_LEADER_CHANCE = 0.05F;
   public static final int REINFORCEMENT_ATTEMPTS = 50;
   public static final int REINFORCEMENT_RANGE_MAX = 40;
   public static final int REINFORCEMENT_RANGE_MIN = 7;
   protected static final float BABY_EYE_HEIGHT_ADJUSTMENT = 0.81F;
   private static final float BREAK_DOOR_CHANCE = 0.1F;
   private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE;
   private boolean canBreakDoors;
   private int inWaterTime;
   private int conversionTime;

   public BeaverZombieEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }


   protected void registerGoals() {
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.addBehaviourGoals();
   }

   protected void addBehaviourGoals() {
      this.goalSelector.addGoal(2, new BeaverZombieAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(2, new BeaverZombieEatTreeGoal(this));
      this.goalSelector.addGoal(6, new BeaverZombieGoToWoodGoal(this, 1, 10));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(5, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
   }

   public static AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0).add(Attributes.MOVEMENT_SPEED, 0.23000000417232513).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.ARMOR, 2.0).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
   }

   protected void defineSynchedData() {
      super.defineSynchedData();
      this.getEntityData().define(DATA_BABY_ID, false);
      this.getEntityData().define(DATA_SPECIAL_TYPE_ID, 0);
      this.getEntityData().define(DATA_DROWNED_CONVERSION_ID, false);
   }

   public boolean isUnderWaterConverting() {
      return (Boolean)this.getEntityData().get(DATA_DROWNED_CONVERSION_ID);
   }

   public boolean canBreakDoors() {
      return this.canBreakDoors;
   }



   protected boolean supportsBreakDoorGoal() {
      return true;
   }

   public boolean isBaby() {
      return (Boolean)this.getEntityData().get(DATA_BABY_ID);
   }

   public int getExperienceReward() {
      if (this.isBaby()) {
         this.xpReward = (int)((double)this.xpReward * 2.5);
      }

      return super.getExperienceReward();
   }

   public void setBaby(boolean pChildZombie) {
      this.getEntityData().set(DATA_BABY_ID, pChildZombie);
      if (this.level() != null && !this.level().isClientSide) {
         AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
         attributeinstance.removeModifier(SPEED_MODIFIER_BABY);
         if (pChildZombie) {
            attributeinstance.addTransientModifier(SPEED_MODIFIER_BABY);
         }
      }

   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
      if (DATA_BABY_ID.equals(pKey)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(pKey);
   }

   protected boolean convertsInWater() {
      return true;
   }

   public void tick() {
      super.tick();
   }

   public void aiStep() {
      if (this.isAlive()) {
         boolean flag = this.isSunSensitive() && this.isSunBurnTick();
         if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            if (!itemstack.isEmpty()) {
               if (itemstack.isDamageableItem()) {
                  itemstack.setDamageValue(itemstack.getDamageValue() + this.random.nextInt(2));
                  if (itemstack.getDamageValue() >= itemstack.getMaxDamage()) {
                     this.broadcastBreakEvent(EquipmentSlot.HEAD);
                     this.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
                  }
               }

               flag = false;
            }

            if (flag) {
               this.setSecondsOnFire(8);
            }
         }
      }

      super.aiStep();
   }

   private void startUnderWaterConversion(int pConversionTime) {
      this.conversionTime = pConversionTime;
      this.getEntityData().set(DATA_DROWNED_CONVERSION_ID, true);
   }

   


   protected boolean isSunSensitive() {
      return true;
   }

   public boolean doHurtTarget(Entity pEntity) {
      boolean flag = super.doHurtTarget(pEntity);
      if (flag) {
         float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
         if (this.getMainHandItem().isEmpty() && this.isOnFire() && this.random.nextFloat() < f * 0.3F) {
            pEntity.setSecondsOnFire(2 * (int)f);
         }
      }

      return flag;
   }

   protected SoundEvent getAmbientSound() {
      return getRandomSoundEvents();
   }
  private SoundEvent getRandomSoundEvents(){
      Random rand = new Random();
      int i = rand.nextInt(1, 3);
      switch (i) {
        case 1:
          return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:beaverzombie_ambient_1"));
        
        case 2:
          return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:beaverzombie_ambient_2"));
     
        case 3:
          return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:beaverzombie_ambient_5"));

        default:
          break;
      }
      return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:beaverzombie_ambient_1"));
  }

   protected SoundEvent getHurtSound(DamageSource pDamageSource) {
      return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:beaverzombie_ambient_3"));
   }

   protected SoundEvent getDeathSound() {
      return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:beaverzombie_ambient_4"));
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.ZOMBIE_STEP;
   }

   protected void playStepSound(BlockPos pPos, BlockState pBlock) {
      this.playSound(this.getStepSound(), 0.15F, 1.0F);
   }

   public MobType getMobType() {
      return MobType.UNDEAD;
   }

   protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
      super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
      if (pRandom.nextFloat() < (this.level().getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
         int i = pRandom.nextInt(3);
         if (i == 0) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
         } else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
         }
      }

   }

   public void addAdditionalSaveData(CompoundTag pCompound) {
      super.addAdditionalSaveData(pCompound);
      pCompound.putBoolean("IsBaby", this.isBaby());
      pCompound.putBoolean("CanBreakDoors", this.canBreakDoors());
      pCompound.putInt("InWaterTime", this.isInWater() ? this.inWaterTime : -1);
      pCompound.putInt("DrownedConversionTime", this.isUnderWaterConverting() ? this.conversionTime : -1);
   }

   public void readAdditionalSaveData(CompoundTag pCompound) {
      super.readAdditionalSaveData(pCompound);
      this.setBaby(pCompound.getBoolean("IsBaby"));
      this.inWaterTime = pCompound.getInt("InWaterTime");
      if (pCompound.contains("DrownedConversionTime", 99) && pCompound.getInt("DrownedConversionTime") > -1) {
         this.startUnderWaterConversion(pCompound.getInt("DrownedConversionTime"));
      }

   }

   public boolean killedEntity(ServerLevel pLevel, LivingEntity pEntity) {
      boolean flag = super.killedEntity(pLevel, pEntity);
      if ((pLevel.getDifficulty() == Difficulty.NORMAL || pLevel.getDifficulty() == Difficulty.HARD) && pEntity instanceof Villager villager) {
         if (ForgeEventFactory.canLivingConvert(pEntity, EntityType.ZOMBIE_VILLAGER, (timer) -> {
         })) {
            if (pLevel.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
               return flag;
            }

            ZombieVillager zombievillager = (ZombieVillager)villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            if (zombievillager != null) {
               zombievillager.finalizeSpawn(pLevel, pLevel.getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new ZombieGroupData(false, true), (CompoundTag)null);
               zombievillager.setVillagerData(villager.getVillagerData());
               zombievillager.setGossips((Tag)villager.getGossips().store(NbtOps.INSTANCE));
               zombievillager.setTradeOffers(villager.getOffers().createTag());
               zombievillager.setVillagerXp(villager.getVillagerXp());
               ForgeEventFactory.onLivingConvert(pEntity, zombievillager);
               if (!this.isSilent()) {
                  pLevel.levelEvent((Player)null, 1026, this.blockPosition(), 0);
               }

               flag = false;
            }
         }
      }

      return flag;
   }

   protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
      return this.isBaby() ? 0.93F : 1.74F;
   }

   public boolean canHoldItem(ItemStack pStack) {
      return pStack.is(Items.EGG) && this.isBaby() && this.isPassenger() ? false : super.canHoldItem(pStack);
   }

   public boolean wantsToPickUp(ItemStack pStack) {
      return pStack.is(Items.GLOW_INK_SAC) ? false : super.wantsToPickUp(pStack);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(Level level, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
      //RandomSource randomsource = pLevel.getRandom();
      //SpawnGroupData pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
      //float f = pDifficulty.getSpecialMultiplier();
      //this.setCanPickUpLoot(randomsource.nextFloat() < 0.55F * f);
      //if (pSpawnData == null) {
      //   pSpawnData = new ZombieGroupData(getSpawnAsBabyOdds(randomsource), true);
      //}

      //if (pSpawnData instanceof ZombieGroupData zombie$zombiegroupdata) {
       //  if (zombie$zombiegroupdata.isBaby) {
         //   this.setBaby(true);
           // if (zombie$zombiegroupdata.canSpawnJockey) {
             //  if ((double)randomsource.nextFloat() < 0.05) {
               //   List<Chicken> list = pLevel.getEntitiesOfClass(Chicken.class, this.getBoundingBox().inflate(5.0, 3.0, 5.0), EntitySelector.ENTITY_NOT_BEING_RIDDEN);
                 // if (!list.isEmpty()) {
                   //  Chicken chicken = (Chicken)list.get(0);
                     //chicken.setChickenJockey(true);
                     //this.startRiding(chicken);
                  //}
               //} else if ((double)randomsource.nextFloat() < 0.05) {
                 // Chicken chicken1 = (Chicken)EntityType.CHICKEN.create(this.level());
                 // if (chicken1 != null) {
                   //  chicken1.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                   //  chicken1.finalizeSpawn(pLevel, pDifficulty, MobSpawnType.JOCKEY, (SpawnGroupData)null, (CompoundTag)null);
                   //  chicken1.setChickenJockey(true);
                   //  this.startRiding(chicken1);
                   //  pLevel.addFreshEntity(chicken1);
                  //}
               //}
            //}
         //}


      return (SpawnGroupData)pSpawnData;
   }

   public static boolean getSpawnAsBabyOdds(RandomSource pRandom) {
      return (double)pRandom.nextFloat() < (Double)ForgeConfig.SERVER.zombieBabyChance.get();
   }

   protected void handleAttributes(float pDifficulty) {
      this.randomizeReinforcementsChance();
      this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05000000074505806, Operation.ADDITION));
      double d0 = this.random.nextDouble() * 1.5 * (double)pDifficulty;
      if (d0 > 1.0) {
         this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random zombie-spawn bonus", d0, Operation.MULTIPLY_TOTAL));
      }

      if (this.random.nextFloat() < pDifficulty * 0.05F) {
         this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25 + 0.5, Operation.ADDITION));
         this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0 + 1.0, Operation.MULTIPLY_TOTAL));
      }

   }

   protected void randomizeReinforcementsChance() {
      this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.random.nextDouble() * (Double)ForgeConfig.SERVER.zombieBaseSummonChance.get());
   }

   public double getMyRidingOffset() {
      return this.isBaby() ? 0.0 : -0.45;
   }

   protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
      super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
      Entity entity = pSource.getEntity();
      if (entity instanceof Creeper creeper) {
         if (creeper.canDropMobsSkull()) {
            ItemStack itemstack = this.getSkull();
            if (!itemstack.isEmpty()) {
               creeper.increaseDroppedSkulls();
               this.spawnAtLocation(itemstack);
            }
         }
      }

   }

   protected ItemStack getSkull() {
      return new ItemStack(Items.ZOMBIE_HEAD);
   }

   static {
      SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_UUID, "Baby speed boost", 0.5, Operation.MULTIPLY_BASE);
      DATA_BABY_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
      DATA_SPECIAL_TYPE_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.INT);
      DATA_DROWNED_CONVERSION_ID = SynchedEntityData.defineId(Zombie.class, EntityDataSerializers.BOOLEAN);
      DOOR_BREAKING_PREDICATE = (p_34284_) -> {
         return p_34284_ == Difficulty.HARD;
      };
   }
}
