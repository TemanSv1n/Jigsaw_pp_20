package net.svisvi.jigsawpp.entity.beaver_zombie;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * BeaverZombieAttackGoal
 */
public class BeaverZombieAttackGoal extends MeleeAttackGoal {
  private final BeaverZombieEntity Bzombie;
  private int raiseArmTicks;

  public BeaverZombieAttackGoal(BeaverZombieEntity pZombie, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
     super(pZombie, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
     this.Bzombie = pZombie;
  }

  public void start() {
     super.start();
     this.raiseArmTicks = 0;
  }

  public void stop() {
     super.stop();
     this.Bzombie.setAggressive(false);
  }

  public void tick() {
     super.tick();
     ++this.raiseArmTicks;
     if (this.raiseArmTicks >= 5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
        this.Bzombie.setAggressive(true);
     } else {
        this.Bzombie.setAggressive(false);
     }
   }
  
}
