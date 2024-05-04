package net.svisvi.jigsawpp.entity.blabbit;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Slime;

import java.util.EnumSet;

public class BlabbitGoals {
    static class BlabbitAttackGoal extends Goal {
        private final BlabbitEntity blabbit;
        private int growTiredTimer;

        public BlabbitAttackGoal(BlabbitEntity pBlabbit) {
            this.blabbit = pBlabbit;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.blabbit.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return !this.blabbit.canAttack(livingentity) ? false : this.blabbit.getMoveControl() instanceof BlabbitGoals.BlabbitMoveControl;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            LivingEntity livingentity = this.blabbit.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!this.blabbit.canAttack(livingentity)) {
                return false;
            } else {
                return --this.growTiredTimer > 0;
            }
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
