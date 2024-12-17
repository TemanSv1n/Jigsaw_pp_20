package net.svisvi.jigsawpp.entity.beaver_zombie;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.obj.ObjMaterialLibrary;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.projectile.BeaverzookaEntity;

/**
 * BeaverZombieEatTreeGoal
 */
public class BeaverZombieEatTreeGoal extends Goal {
  private static final int EAT_ANIMATION_TICKS = 40;
  private final Mob mob;
  private final Level level;
        private int eatAnimationTick;

        public BeaverZombieEatTreeGoal(Mob pMob) {
            this.mob = pMob;
            this.level = pMob.level();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        public boolean canUse() {
            if (this.mob.getRandom().nextInt(5) != 0) {
                return false;
            } else {
                BlockPos blockpos = getLookPos(mob);
                BlockPos downPos = mob.blockPosition().below();
                BlockPos blockPosDown = getLookPosDown(blockpos); 
                return isBlockWood(blockpos) || isBlockWood(downPos) || isBlockWood(blockPosDown) || hasWoodenBlockUp(4); 
            }
        }

        private  boolean isBlockWood(BlockPos pos){
            BlockState state = this.level.getBlockState(pos);
            return  !BeaverzookaEntity.notReplaceableTags.stream().anyMatch(state::is) &&
                     BeaverzookaEntity.replaceableTags.stream().anyMatch(state::is);
        }

        private BlockPos getLookPosDown(BlockPos pos){
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            int yd = y - 1;
            return new BlockPos(x, yd, z);
        }

        public void start() {
            this.eatAnimationTick = this.adjustedTickDelay(10);
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
            if (this.eatAnimationTick == this.adjustedTickDelay(2)) {
                BlockPos blockpos = getLookPos(mob);
                BlockPos downPos = mob.blockPosition().below();
                
                if(isBlockWood(blockpos)) {
                    if(isBlockWood(getLookPosDown(blockpos))) breakBlockHorizontaly(getLookPosDown(blockpos));
                    else breakBlockHorizontaly(blockpos);
                    
                } else if(hasWoodenBlockUp(4)){
                    for(int i = 1; i < 4; i++) {
                        if (isBlockWood(this.mob.blockPosition().above(i))) {
                                breakBlockVerticaly(this.mob.blockPosition().above(i));
                                break;
                        }
                    }
                } else if(isBlockWood(downPos)) breakBlockVerticaly(downPos);
            }

        }
        
        private boolean hasWoodenBlockUp(int distance) {
            boolean b = false;
            for(int i = 1; i < distance; i++) {
                if (isBlockWood(this.mob.blockPosition().above(i))) {
                    b = true;
                    break;
                }
            }
            return b;
        }

        public void breakBlockVerticaly(BlockPos downPos) {
            if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, downPos, Block.getId(this.level.getBlockState(downPos).getBlock().defaultBlockState()));
                        this.level.setBlock(downPos, Blocks.AIR.defaultBlockState(), 2);
                        if (!this.level.isClientSide()) {
                            this.level.playSound(null, downPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1);
                        } else {
                            this.level.playLocalSound(downPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1, false);
                        }
                    }
        }      

        public void breakBlockHorizontaly(BlockPos blockpos){
            if (ForgeEventFactory.getMobGriefingEvent(this.level, this.mob)) {
                 this.level.levelEvent(2001, blockpos, Block.getId(this.level.getBlockState(blockpos).getBlock().defaultBlockState()));
                 this.level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 2);
                 if (!this.level.isClientSide()) {
                       this.level.playSound(null, blockpos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1);
                 } else {
                    this.level.playLocalSound(blockpos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.BLOCKS, 1, -1, false);
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
