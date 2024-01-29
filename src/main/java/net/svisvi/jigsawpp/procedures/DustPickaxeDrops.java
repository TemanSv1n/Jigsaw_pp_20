package net.svisvi.jigsawpp.procedures;


import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;

import net.svisvi.jigsawpp.init.JigsawPpModItems;

public class DustPickaxeDrops {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (Mth.nextInt(RandomSource.create(), 1, 175) < world.dimensionType().moonPhase(world.dayTime())) {
            if (world instanceof ServerLevel _level) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(JigsawPpModItems.DUST_NOTHING.get()));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);
            }
        }
    }
}

