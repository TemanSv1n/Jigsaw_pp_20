
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.init;

import io.netty.util.Attribute;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class JigsawPpModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JigsawPpMod.MODID);
	public static final RegistryObject<CreativeModeTab> JIGSAW_TAB = REGISTRY.register("jigsaw_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.jigsaw_pp.jigsaw_tab")).icon(() -> new ItemStack(Items.FLOWER_POT)).displayItems((parameters, tabData) -> {
				tabData.accept(JigsawPpModItems.ELEPHANT_PICKAXE.get());
				tabData.accept(JigsawPpModItems.MOSS_ELEPHANT_PICKAXE.get());
				tabData.accept(JigsawPpModItems.STRAWBERRY_ELEPHANT_PICKAXE.get());
				tabData.accept(JigsawPpModItems.COAL_FOSSIL.get());
				tabData.accept(JigsawPpModItems.CUM_SHARD.get());
				tabData.accept(JigsawPpModItems.RAW_CUM.get());
				tabData.accept(JigsawPpModItems.DUST_OF_BABUSHKA.get());
			})

					.build());
}
