package net.svisvi.jigsawpp.entity.beaverSpider;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

/**
 * BeaverSpiderNearestAttackableTarget
 */
public class BeaverSpiderNearestAttackableTarget<T extends LivingEntity> extends NearestAttackableTargetGoal {
    
      
  public BeaverSpiderNearestAttackableTarget(BeaverSpiderEntity pSpider, Class<T> pEntityTypeToTarget) {
    super(pSpider, pEntityTypeToTarget, true);
  }

  public boolean canUse() {
    return super.canUse();
  }


}
