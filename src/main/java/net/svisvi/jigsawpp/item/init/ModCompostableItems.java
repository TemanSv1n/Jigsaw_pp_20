
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.item.init;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.block.ComposterBlock;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCompostableItems {
	@SubscribeEvent
	public static void addComposterItems(FMLCommonSetupEvent event) {
		ComposterBlock.COMPOSTABLES.put(ModItems.BEAWEED_DUST.get(), 0.1f);
		ComposterBlock.COMPOSTABLES.put(ModItems.BEAWEED_BLOCK.get(), 0.85f);
	}
}

