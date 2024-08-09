package net.svisvi.jigsawpp.entity.teapodSpider;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.LevelAccessor;

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
