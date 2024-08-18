package net.svisvi.jigsawpp.entity.stone_beaver;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.EnumSet;
import java.util.function.Predicate;

public class StoneBeaverGoals {

    public static class EatOreGoal extends Goal {
        private static final int EAT_ANIMATION_TICKS = 40;
        private final Mob mob;
        private final Level level;
        private int eatAnimationTick;

        public EatOreGoal(Mob pMob) {
            this.mob = pMob;
            this.level = pMob.level();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        public boolean canUse() {
            if (this.mob.getRandom().nextInt(5) != 0) {
                return false;
            } else {
//                System.out.println("EDGER");
                BlockPos blockpos = getLookPos(mob);
                BlockPos downPos = mob.blockPosition().below();
                return this.level.getBlockState(blockpos).is(BlockTags.create(new ResourceLocation("forge:ores"))) || this.level.getBlockState(downPos).is(BlockTags.create(new ResourceLocation("forge:ores")));
            }
        }

        public void start() {
            this.eatAnimationTick = this.adjustedTickDelay(40);
            this.level.broadcastEntityEvent(this.mob, (byte)10);
            this.mob.getNavigation().stop();
        }

        public void stop() {
            this.eatAnimationTick = 0;
        }

        public boolean canContinueToUse() {
            return this.eatAnimationTick > 0;
        }

        public int getEatAnimationTick() {
            return this.eatAnimationTick;
        }

        public void tick() {
            this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
            if (this.eatAnimationTick == this.adjustedTickDelay(4)) {
                BlockPos blockpos = getLookPos(mob);
                BlockPos downPos = mob.blockPosition().below();
                if (this.level.getBlockState(blockpos).is(BlockTags.create(new ResourceLocation("forge:ores")))) {
//                    System.out.println("GOONER");
                    if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, blockpos, Block.getId(this.level.getBlockState(blockpos).getBlock().defaultBlockState()));
                        this.level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
                        if (!this.level.isClientSide()) {
                            this.level.playSound(null, blockpos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1);
                        } else {
                            this.level.playLocalSound(blockpos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1, false);
                        }
                    }

                    this.mob.ate();
                } else if (this.level.getBlockState(downPos).is(BlockTags.create(new ResourceLocation("forge:ores")))) {
                    if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, downPos, Block.getId(this.level.getBlockState(downPos).getBlock().defaultBlockState()));
                        this.level.setBlock(downPos, Blocks.AIR.defaultBlockState(), 2);
                        if (!this.level.isClientSide()) {
                            this.level.playSound(null, blockpos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1);
                        } else {
                            this.level.playLocalSound(blockpos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1, false);
                        }

                    }
                }
            }

        }

        public static BlockPos getLookPos(Entity entity){
            BlockPos pPos = new BlockPos(entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(3)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getX(),
                    entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(3)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getY(),
                    entity.level().clip(new ClipContext(entity.getEyePosition(1f), entity.getEyePosition(1f).add(entity.getViewVector(1f).scale(3)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity)).getBlockPos().getZ());
            return pPos;
        }
    }

    public static class MoveToOreGoal extends Goal {
        private static final int GIVE_UP_TICKS = 1200;
        private static final int STAY_TICKS = 1200;
        private static final int INTERVAL_TICKS = 200;
        protected final PathfinderMob mob;
        public final double speedModifier;
        protected int nextStartTick;
        protected int tryTicks;
        private int maxStayTicks;
        protected BlockPos blockPos;
        private boolean reachedTarget;
        private final int searchRange;
        private final int verticalSearchRange;
        protected int verticalSearchStart;

        public MoveToOreGoal(PathfinderMob pMob, double pSpeedModifier, int pSearchRange) {
            this(pMob, pSpeedModifier, pSearchRange, 1);
        }

        public MoveToOreGoal(PathfinderMob pMob, double pSpeedModifier, int pSearchRange, int pVerticalSearchRange) {
            this.blockPos = BlockPos.ZERO;
            this.mob = pMob;
            this.speedModifier = pSpeedModifier;
            this.searchRange = pSearchRange;
            this.verticalSearchStart = 0;
            this.verticalSearchRange = pVerticalSearchRange;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        public boolean canUse() {
            if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.mob);
                return this.findNearestBlock();
            }
        }

        protected int nextStartTick(PathfinderMob pCreature) {
            return reducedTickDelay(200 + pCreature.getRandom().nextInt(200));
        }

        public boolean canContinueToUse() {
            return this.tryTicks >= -this.maxStayTicks && this.tryTicks <= 1200 && this.isValidTarget(this.mob.level(), this.blockPos);
        }

        public void start() {
            this.moveMobToBlock();
            this.tryTicks = 0;
            this.maxStayTicks = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
        }

        protected void moveMobToBlock() {
            this.mob.getNavigation().moveTo((double)((float)this.blockPos.getX()) + 0.5, (double)(this.blockPos.getY() + 1), (double)((float)this.blockPos.getZ()) + 0.5, this.speedModifier);
        }

        public double acceptedDistance() {
            return 1.0;
        }

        protected BlockPos getMoveToTarget() {
            return this.blockPos.above();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            BlockPos $$0 = this.getMoveToTarget();
            if (!$$0.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
                this.reachedTarget = false;
                ++this.tryTicks;
                if (this.shouldRecalculatePath()) {
                    this.mob.getNavigation().moveTo((double)((float)$$0.getX()) + 0.5, (double)$$0.getY(), (double)((float)$$0.getZ()) + 0.5, this.speedModifier);
                }
            } else {
                this.reachedTarget = true;
                --this.tryTicks;
            }

        }

        public boolean shouldRecalculatePath() {
            return this.tryTicks % 40 == 0;
        }

        protected boolean isReachedTarget() {
            return this.reachedTarget;
        }

        protected boolean findNearestBlock() {
            int $$0 = this.searchRange;
            int $$1 = this.verticalSearchRange;
            BlockPos $$2 = this.mob.blockPosition();
            BlockPos.MutableBlockPos $$3 = new BlockPos.MutableBlockPos();

            for(int $$4 = this.verticalSearchStart; $$4 <= $$1; $$4 = $$4 > 0 ? -$$4 : 1 - $$4) {
                for(int $$5 = 0; $$5 < $$0; ++$$5) {
                    for(int $$6 = 0; $$6 <= $$5; $$6 = $$6 > 0 ? -$$6 : 1 - $$6) {
                        for(int $$7 = $$6 < $$5 && $$6 > -$$5 ? $$5 : 0; $$7 <= $$5; $$7 = $$7 > 0 ? -$$7 : 1 - $$7) {
                            $$3.setWithOffset($$2, $$6, $$4 - 1, $$7);
                            if (this.mob.isWithinRestriction($$3) && this.isValidTarget(this.mob.level(), $$3)) {
                                this.blockPos = $$3;
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        protected boolean isValidTarget(LevelReader level, BlockPos blockpos){
            return (level.getBlockState(blockpos).is(BlockTags.create(new ResourceLocation("forge:ores"))));
        };
    }
}
