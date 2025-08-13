

package net.svisvi.jigsawpp.item.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;

import net.minecraft.world.item.ItemStack;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber
public class ModFuels {
	@SubscribeEvent
	public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
		ItemStack itemstack = event.getItemStack();
		if (itemstack.getItem() == ModItems.BEAWEED_DUST.get()) event.setBurnTime(400);
		if (itemstack.getItem() == ModItems.COAL_FOSSIL.get()) event.setBurnTime(1);
		if (itemstack.getItem() == ModItems.PONOS_BUCKET.get()) event.setBurnTime(200);
		if (itemstack.getItem() == ModItems.BEAWEED_BLOCK.get()) event.setBurnTime(4000);
		if (itemstack.getItem() == ModItems.SWEET_BREAD.get()) event.setBurnTime(200);
		if (itemstack.getItem() == ModItems.SHIT_BLOCK.get()) event.setBurnTime(200);
		if (itemstack.getItem() == ModItems.SHISHKIN.get()) event.setBurnTime(500000);
	}
}
