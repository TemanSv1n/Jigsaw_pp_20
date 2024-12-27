package net.svisvi.jigsawpp.entity.blabbit;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Slime;
import net.svisvi.jigsawpp.JigsawPpMod;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;

public class BlabbitGoals {
    static class BlabbitAttackGoal extends MeleeAttackGoal {
        private final BlabbitEntity blabbit;
        private int attackDelay = 10;
        private int ticksUntilNextAttack = 10;
        private boolean shouldCountTillNextAttack = false;
        private int growTiredTimer;

        public BlabbitAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.blabbit = (BlabbitEntity) pMob;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
//            System.out.println("BIT CABYNY!");
            double d0 = this.getAttackReachSqr(pEnemy);
            if (pDistToEnemySqr <= d0) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(pEnemy);
            }
        }





        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
//            LivingEntity livingentity = this.blabbit.getTarget();
//            if (livingentity == null) {
//                return false;
//            } else {
//                return !this.blabbit.canAttack(livingentity) ? false : this.blabbit.getMoveControl() instanceof BlabbitGoals.BlabbitMoveControl;
//            }
            return super.canUse();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            attackDelay = 40;
            ticksUntilNextAttack = 40;
            this.mob.setAggressive(true);
            super.start();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
//            LivingEntity livingentity = this.blabbit.getTarget();
//            if (livingentity == null) {
//                return false;
//            } else if (!this.blabbit.canAttack(livingentity)) {
//                return false;
//            } else {
//                return --this.growTiredTimer > 0;
//            }
            return super.canContinueToUse();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.blabbit.getTarget();
            if (livingentity != null) {
                this.blabbit.lookAt(livingentity, 10.0F, 10.0F);
            }

            MoveControl movecontrol = this.blabbit.getMoveControl();
            if (movecontrol instanceof BlabbitGoals.BlabbitMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setDirection(this.blabbit.getYRot(), this.blabbit.isDealsDamage());
            }
            if(shouldCountTillNextAttack) {
                this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            }
//            if (JigsawPpMod.isModLoaded("jcraft")){
//                Collection<MobEffectInstance> effects = this.blabbit.getActiveEffects();
//                for (MobEffectInstance effect : effects){
//                    MobEffect ef = effect.getEffect();
////                    if (ef.getDescriptionId().equals("effect.jcraft.dazed_effect")){
//                    if (ef.getDescriptionId().contains("jcraft")){
//                        return;
//                    }
//                }
//            }
            double d0 = this.mob.getPerceivedTargetDistanceSquareForMeleeAttack(livingentity);
            checkAndPerformAttack(livingentity, d0);

        }
        @Override
        protected double getAttackReachSqr(LivingEntity entity) {
            if (entity == null){
                return 0;
            }
            return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
        }

    }

    static class BlabbitMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final BlabbitEntity blabbit;
        private boolean isAggressive;

        public BlabbitMoveControl(BlabbitEntity pBlabbit) {
            super(pBlabbit);
            this.blabbit = pBlabbit;
            this.yRot = 180.0F * pBlabbit.getYRot() / (float)Math.PI;
        }

        public void setDirection(float pYRot, boolean pAggressive) {
            this.yRot = pYRot;
            this.isAggressive = pAggressive;
        }

        public void setWantedMovement(double pSpeed) {
            this.speedModifier = pSpeed;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.blabbit.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.blabbit.getJumpControl().jump();
                        if (true) {
                            this.blabbit.playSound(this.blabbit.getJumpSound(), this.blabbit.getSoundVolume(), this.blabbit.getSoundPitch());
                        }
                    } else {
                        this.blabbit.xxa = 0.0F;
                        this.blabbit.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class BlabbitRandomDirectionGoal extends Goal {
        private final BlabbitEntity blabbit;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public BlabbitRandomDirectionGoal(BlabbitEntity pBlabbit) {
            this.blabbit = pBlabbit;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.blabbit.getTarget() == null && (this.blabbit.onGround() || this.blabbit.isInWater() || this.blabbit.isInLava() || this.blabbit.hasEffect(MobEffects.LEVITATION)) && this.blabbit.getMoveControl() instanceof BlabbitGoals.BlabbitMoveControl;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.blabbit.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.blabbit.getRandom().nextInt(360);
            }

            MoveControl movecontrol = this.blabbit.getMoveControl();
            if (movecontrol instanceof BlabbitGoals.BlabbitMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setDirection(this.chosenDegrees, false);
            }

        }
    }

    static class BlabbitKeepOnJumpingGoal extends Goal {
        private final BlabbitEntity blabbit;

        public BlabbitKeepOnJumpingGoal(BlabbitEntity pBlabbit) {
            this.blabbit= pBlabbit;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.blabbit.isPassenger();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            MoveControl movecontrol = this.blabbit.getMoveControl();
            if (movecontrol instanceof BlabbitGoals.BlabbitMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setWantedMovement(3.0D);
            }

        }
    }
}
