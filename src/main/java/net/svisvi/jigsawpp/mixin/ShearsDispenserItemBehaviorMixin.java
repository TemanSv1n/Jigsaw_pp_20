package net.svisvi.jigsawpp.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.block.lenin_bust.LeninBustBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShearsDispenseItemBehavior.class)
public class ShearsDispenserItemBehaviorMixin {
    @Inject(method = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;tryShearBeehive(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)Z", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private static void jigsaw$shear(ServerLevel pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir, BlockState blockstate){
        if (blockstate.is(BlockTags.BEEHIVES, (p_202454_) -> {
            return p_202454_.hasProperty(BeehiveBlock.HONEY_LEVEL) && p_202454_.getBlock() instanceof BeehiveBlock;
        })) {
            int i = blockstate.getValue(BeehiveBlock.HONEY_LEVEL);
            if (i >= 5) {
                if (blockstate.is(ModBlocks.LENIN_BUST.get())) {
                    pLevel.playSound((Player) null, pPos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                    LeninBustBlock.dropHoneycomb(pLevel, pPos);
                    ((LeninBustBlock) blockstate.getBlock()).releaseBeesAndResetHoneyLevel(pLevel, blockstate, pPos, (Player) null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
                    pLevel.gameEvent((Entity) null, GameEvent.SHEAR, pPos);
                    cir.setReturnValue(true);
                }
            }
        }

    }
}
