package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class IsBiomeHotProcedure {
	public static double execute(LevelAccessor world, double x, double y, double z) {
		return world.getBiome(BlockPos.containing(x, y, z)).value().getBaseTemperature() * 1f * 5;
	}
}
