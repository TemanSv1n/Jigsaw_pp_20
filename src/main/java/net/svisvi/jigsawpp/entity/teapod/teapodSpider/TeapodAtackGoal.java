package net.svisvi.jigsawpp.entity.teapod.teapodSpider;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 * TeapodAtackGoal
 */
public class TeapodAtackGoal extends MeleeAttackGoal  {

     public TeapodAtackGoal(TeapodSpider pSpider) {
      super(pSpider, 1.0, true);
   }

   public boolean canUse() {
      return super.canUse() && !this.mob.isVehicle();
   }

   public boolean canContinueToUse() {
     
      return super.canContinueToUse();
      /*
       * if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
       *  this.mob.setTarget((LivingEntity)null);
       *   return false;
       *} else {
       *   return super.canContinueToUse();
       *}*/
   }

   protected double getAttackReachSqr(LivingEntity pAttackTarget) {
      return (double)(6 + pAttackTarget.getBbWidth());
   }
}
