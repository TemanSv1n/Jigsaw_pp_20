package net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.google.common.eventbus.DeadEvent;

import net.minecraft.client.gui.font.providers.UnihexProvider.Dimensions;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage.Player;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.client.event.ScreenEvent.Init;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.Difficulty;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.BlockPos;
import net.svisvi.jigsawpp.entity.init.ModEntities;



/**
 * BeaverZombieSpawner
 */
public class BeaverZombieSpawner extends Monster{
  private EntityType eType;
  public BeaverZombieSpawner(EntityType<? extends Monster> pEntityType, Level pLevel){
    super(pEntityType, pLevel);
    this.eType = pEntityType;
  }

  @Override
  protected void registerGoals() {
    this.goalSelector.addGoal(1, new SpawnBeaverZombieGoal(this )); 
  }
  public static AttributeSupplier.Builder createAttributes() {
    return Animal.createLivingAttributes()
      .add(Attributes.MAX_HEALTH, 10)
      .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
      .add(Attributes.FOLLOW_RANGE, 30);
  }
  @Override
  public boolean isPushable() {
      return false;
  }
  
  public static boolean init(EntityType<? extends BeaverZombieSpawner> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
    int y = pPos.getY();
    return checkMonsterSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom) && y > 40 && moonPhaseBeaverZombie(pLevel);
  } 
  
  public static boolean moonPhaseBeaverZombie(ServerLevelAccessor pLevel){
    return pLevel.getMoonPhase() == 0 || pLevel.getMoonPhase() == 4;
  }
  

 @Override
 public MobType getMobType() {
      return MobType.UNDEFINED;
  }
  
  
}
