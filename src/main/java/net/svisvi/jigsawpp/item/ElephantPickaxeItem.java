
package net.svisvi.jigsawpp.item;

//import net.minecraft.client.Minecraft;
//import net.minecraft.tags.TagKey;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.*;
//import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraft.world.level.LevelAccessor;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
//import net.minecraft.world.level.block.Block;
import net.minecraft.tags.BlockTags;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.util.Mth.*;
//import net.minecraft.core.BlockPos;

import net.minecraft.world.item.ItemStack;
import net.svisvi.jigsawpp.procedures.ut.IsBiomeHotProcedure;
import org.jetbrains.annotations.NotNull;
//import net.minecraft.world.item.ItemStack;

//import net.svisvi.jigsawpp.procedures.ut.IsBiomeHotProcedure;
//import net.minecraft.world.level.LevelAccessor;
//import net.minecraft.world.level.LevelReader;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.server.MinecraftServer;
//import net.minecraftforge.server.ServerLifecycleHooks;


import java.util.List;

public class ElephantPickaxeItem extends PickaxeItem {
	public ElephantPickaxeItem() {
		super(new Tier() {
			public int getUses() {
				return 700;
			}

			public float getSpeed() {
				//return 7f + .getOrCreateTag().getDouble("digSpeed");
				return 6f;// + IsNightProcedure.execute(world);
			}

			public float getAttackDamageBonus() {
				return 2f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 15;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(Items.IRON_INGOT));
			}
		}, 1, -2.6f, new Item.Properties());
	}

	@Override
	public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
		boolean retval = super.mineBlock(itemstack, world, blockstate, pos, entity);
		this.blockDestroyed(world, pos.getX(), pos.getY(), pos.getZ(), itemstack);
		return retval;
	}

	@Override
	public float getDestroySpeed(ItemStack p_41004_, @NotNull BlockState p_41005_) {
		double tagResult = p_41004_.getOrCreateTag().getDouble("DigSpeed");

		if (tagResult <= 1.0D) {
			return 1.0F;
		}
		float new_speed = this.speed + (float)tagResult;
		return (p_41005_.is(BlockTags.MINEABLE_WITH_PICKAXE) ? new_speed : 1.0F);
	}

	@Override
	public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(itemstack, world, list, flag);
		list.add(Component.translatable("item.jigsaw_pp.elephant_pickaxe.desc"));
	}

	public static void blockDestroyed(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
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
