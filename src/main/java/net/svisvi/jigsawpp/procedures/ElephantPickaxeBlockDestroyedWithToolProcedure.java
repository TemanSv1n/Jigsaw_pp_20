package net.svisvi.jigsawpp.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.IsBiomeHotProcedure;


public class ElephantPickaxeBlockDestroyedWithToolProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		double temp = IsBiomeHotProcedure.execute(world, x, y, z);
		itemstack.getOrCreateTag().putDouble("DigSpeed", temp);
		if (temp >= 9){
			ItemStack _ist = itemstack;
			if (_ist.hurt(2, RandomSource.create(), null)) {
				_ist.shrink(1);
				_ist.setDamageValue(0);
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.FLAME, x, y, z, 5, 0.5, 0.5, 0.5, 0);

		} else if (temp <= 1){
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.POOF, x, y, z, 5, 0.5, 0.5, 0.5, 0);
		}

	}
}
