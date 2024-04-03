
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.init;

import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.svisvi.jigsawpp.item.init.ModItems;

public class ModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, JigsawPpMod.MODID);
	public static final RegistryObject<CreativeModeTab> JIGSAW_TAB = REGISTRY.register("jigsaw_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.jigsaw_pp.jigsaw_tab")).icon(() -> new ItemStack(Items.FLOWER_POT)).displayItems((parameters, tabData) -> {
						tabData.accept(ModItems.ELEPHANT_PICKAXE.get());
						tabData.accept(ModItems.MOSS_ELEPHANT_PICKAXE.get());
						tabData.accept(ModItems.STRAWBERRY_ELEPHANT_PICKAXE.get());
						tabData.accept(ModItems.COAL_FOSSIL.get());
						tabData.accept(ModItems.RAW_TEAPOT.get());
						tabData.accept(ModItems.TEAPOT.get());
						tabData.accept(ModItems.BLACK_TEAPOT.get());

						tabData.accept(ModItems.PONOS_BUCKET.get());
						tabData.accept(ModItems.SWEET_BREAD.get());
						tabData.accept(ModItems.MOSS_ELEPHANT_SPAWN_EGG.get());

						tabData.accept(ModItems.HAZMAT_BOOTS.get());
						tabData.accept(ModItems.HAZMAT_LEGGINGS.get());
						tabData.accept(ModItems.HAZMAT_CHESTPLATE.get());
						tabData.accept(ModItems.HAZMAT_HELMET.get());
						tabData.accept(ModItems.GAS_MASK_HELMET.get());
						tabData.accept(ModItems.SLAVE_BOOTS.get());
						tabData.accept(ModItems.SLAVE_LEGGINGS.get());
						tabData.accept(ModItems.SLAVE_CHESTPLATE.get());
						tabData.accept(ModItems.SLAVE_HELMET.get());

						tabData.accept(ModItems.DRIST_BUTTON.get());
						tabData.accept(ModItems.BEAWEED_SEEDS.get());
						tabData.accept(ModItems.FORK.get());

						tabData.accept(ModItems.BASIC_PURGEN_PILULE.get());
						tabData.accept(ModItems.ADVANCED_PURGEN_PILULE.get());
						tabData.accept(ModItems.CRYSTAL_PURGEN_PILULE.get());

			})

					.build());
}
