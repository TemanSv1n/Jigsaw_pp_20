package net.svisvi.jigsawpp.procedures;

import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
//import net.minecraft.util.RandomSource;


import net.svisvi.jigsawpp.procedures.ut.Mossify;


public class MossElephantPickaxeRightclickedOnBlockProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, ItemStack itemstack, Entity entity){
        if (Mossify.execute(world, x, y, z, blockstate)){ //boolean return type, if it works - its true
            // if (blockstate.getSoundType())

            if (entity == null)
                return;
            {
                ItemStack _ist = itemstack;
                if (_ist.hurt(2, RandomSource.create(), null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
            }
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(itemstack.getItem(), 30);
        }

    }
}

