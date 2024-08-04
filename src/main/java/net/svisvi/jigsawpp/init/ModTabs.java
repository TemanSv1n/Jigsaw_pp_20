
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
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.jigsaw_pp.jigsaw_tab")).icon(() -> new ItemStack(ModItems.BEAVER_BOMB.get())).displayItems((parameters, tabData) -> {
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

						tabData.accept(ModItems.EMPTY_PILULE.get());
						tabData.accept(ModItems.BASIC_PURGEN_PILULE.get());
						tabData.accept(ModItems.ADVANCED_PURGEN_PILULE.get());
						tabData.accept(ModItems.CRYSTAL_PURGEN_PILULE.get());
						tabData.accept(ModItems.EXTINGUISHER.get());

						tabData.accept(ModItems.BEAWEED_SOUP.get());
						tabData.accept(ModItems.SAWDUST_SOUP.get());
						tabData.accept(ModItems.BEAWEED_SCUM.get());
						tabData.accept(ModItems.BEAWEED_DUST.get());
						tabData.accept(ModItems.FRIED_BEAWEED_SEEDS.get());
						tabData.accept(ModItems.BEAWEED_BLOCK.get());
						tabData.accept(ModItems.BEAWEED_NUT.get());
						tabData.accept(ModItems.NUTCRACKER.get());

						tabData.accept(ModItems.PURGEN_BUNDLE.get());
						tabData.accept(ModItems.PURGEN_GUN.get());
						tabData.accept(ModItems.PURGEN_MACHINE_GUN.get());

						tabData.accept(ModItems.MARMOSET.get());
						tabData.accept(ModItems.FACTORY_HEATER.get());
						tabData.accept(ModItems.PURGEN_FACTORY.get());
						tabData.accept(ModItems.BATCH_SIZE_CARD.get());
				//		tabData.accept(ModItems.KEGA.get());
				//		tabData.accept(ModItems.KEGA_NULL.get());
						tabData.accept(ModItems.BLABBIT_SPAWN_EGG.get());

						tabData.accept(ModItems.CARROT_COIL.get());
						tabData.accept(ModItems.BEAWEED_PLATE.get());
						tabData.accept(ModItems.SMART_BEAWEED_PLATE.get());
						tabData.accept(ModItems.NUCLEAR_PURGEN_PILULE.get());
						tabData.accept(ModItems.FITTING.get());
						tabData.accept(ModItems.BLABBALL.get());
						tabData.accept(ModItems.BLABEGG.get());
						tabData.accept(ModItems.USELESS_PIE.get());
						tabData.accept(ModItems.YOBA.get());
						tabData.accept(ModItems.YOBA_POOP.get());
						tabData.accept(ModItems.POOPED_BRICKS.get());
						tabData.accept(ModItems.SLON_GUN.get());
						tabData.accept(ModItems.SLONGUN_GREEN.get());

						tabData.accept(ModItems.MOLD.get());
						tabData.accept(ModItems.MOLD_BLOCK.get());
						tabData.accept(ModItems.LENIN_BUST.get());
						tabData.accept(ModItems.BEAVER_COMPUTER.get());
						tabData.accept(ModItems.BEAVER_KNIFE.get());
						tabData.accept(ModItems.BEAVER_AXE.get());
						tabData.accept(ModItems.PIGKAXE.get());
						tabData.accept(ModItems.TUBE_19.get());
						tabData.accept(ModItems.EGGS.get());
						tabData.accept(ModItems.TOTEM_OF_SHIT.get());
						tabData.accept(ModItems.JOTARO_HAT.get());
						tabData.accept(ModItems.POOPED_BREAD.get());

						tabData.accept(ModItems.BEAVER_HELMET.get());
						tabData.accept(ModItems.BEAVER_CHESTPLATE.get());
						tabData.accept(ModItems.BEAVER_BOOTS.get());
						tabData.accept(ModItems.BEAVER_BOMB.get());
						tabData.accept(ModItems.BEAVERZOOKA.get());
						tabData.accept(ModItems.BEAVER_AMMO.get());
						tabData.accept(ModItems.SHIT_CUP.get());
						tabData.accept(ModItems.ADMIN_STICK.get());
						tabData.accept(ModItems.PW_HAT.get());

			}).build());


//	public static final RegistryObject<CreativeModeTab> JIGSAW_TAB_MATERIALS = REGISTRY.register("jigsaw_tab_materilas",
//			() -> CreativeModeTab.builder().title(Component.translatable("item_group.jigsaw_pp.jigsaw_tab_materials")).icon(() -> new ItemStack(Items.FLOWER_POT)).displayItems((parameters, tabData) -> {
//				tabData.accept(ModItems.FITTING.get());
//				tabData.accept(ModItems.BEAWEED_DUST.get());
//				tabData.accept(ModItems.BEAWEED_BLOCK.get());
//				tabData.accept(ModItems.COAL_FOSSIL.get());
//				tabData.accept(ModItems.CARROT_COIL.get());
//				tabData.accept(ModItems.BEAWEED_PLATE.get());
//				tabData.accept(ModItems.SMART_BEAWEED_PLATE.get());
//				tabData.accept(ModItems.RADIATION_CATALYST_ACTIVATED.get());
//				tabData.accept(ModItems.RADIATION_CATALYST_NON_ACTIVATED.get());
//
//			}).build());
}
