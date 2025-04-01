package net.svisvi.jigsawpp.entity.teapod.teapodSpider;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

/**
 * TeapodSpiderGoals
 */
public class TeapodSpiderGoals<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
  public TeapodSpiderGoals(TeapodSpider pSpider, Class<T> pEntityTypeToTarget) {
    super(pSpider, pEntityTypeToTarget, true);
  }

  public boolean canUse() {
    return super.canUse();
  }

}
