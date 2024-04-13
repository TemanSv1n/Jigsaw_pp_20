package net.svisvi.jigsawpp.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;

public class FactoryHeaterBlockEntity extends HopperBlockEntity{
    public FactoryHeaterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }
}
