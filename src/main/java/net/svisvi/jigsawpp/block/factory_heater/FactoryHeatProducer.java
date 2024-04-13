package net.svisvi.jigsawpp.block.factory_heater;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface FactoryHeatProducer {
    public float getHeat(BlockState pState, Level pLevel, BlockPos pPos);
}
