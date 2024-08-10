package net.svisvi.jigsawpp.block.drist_tnt;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public interface IPoopExplosive {
    public void poopChainReactionExplode(Level world, BlockPos pos, LivingEntity igniter);

}
