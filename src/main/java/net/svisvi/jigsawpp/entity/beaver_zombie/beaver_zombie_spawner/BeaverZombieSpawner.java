package net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner;


import net.minecraft.client.multiplayer.chat.LoggedChatMessage.Player;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieEntity;

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
    this.goalSelector.addGoal(3, new SpawnBeaverZombieGoal(this )); 
    this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0f));
    this.goalSelector.addGoal(1, new FloatGoal(this));
    this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
  }
  public static AttributeSupplier.Builder createAttributes() {
    return Animal.createLivingAttributes()
      .add(Attributes.MAX_HEALTH, 1)
      .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
      .add(Attributes.FOLLOW_RANGE, 30);
  }

  
}
