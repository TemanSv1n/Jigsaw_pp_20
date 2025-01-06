
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
				        tabData.accept(ModItems.THRON.get());
						tabData.accept(ModItems.BEAVER_KNIFE.get());
						tabData.accept(ModItems.BEAVER_AXE.get());
						tabData.accept(ModItems.PIGKAXE.get());
				        tabData.accept(ModItems.SAPPER_SHOVEL.get());
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
						tabData.accept(ModItems.TREE_LAUNCHER.get());


						tabData.accept(ModItems.DIAMOND_IRON_INGOT.get());
						tabData.accept(ModItems.DIAMOND_IRON_BLOCK.get());
						tabData.accept(ModItems.ARM.get());
						tabData.accept(ModItems.BEAVER_STAFF.get());
						tabData.accept(ModItems.BEAVER_STAFF_HONEY.get());
						tabData.accept(ModItems.BROWN_CUTLASS.get());
						tabData.accept(ModItems.CROSS.get());
						tabData.accept(ModItems.LADLE.get());
						tabData.accept(ModItems.EMPTY_BOTTLE.get());
						tabData.accept(ModItems.ROSE_BOTTLE.get());
						tabData.accept(ModItems.ERSHIK.get());
						tabData.accept(ModItems.LADLE.get());
						tabData.accept(ModItems.PADDLE.get());
						tabData.accept(ModItems.PIRATE_CUTLASS.get());
						tabData.accept(ModItems.PIZDOOR.get());
						tabData.accept(ModItems.PONOS_CUTLASS.get());
						tabData.accept(ModItems.PRICEL_RAPIER.get());
						tabData.accept(ModItems.ROAD_SIGN.get());
						tabData.accept(ModItems.SHISHKIN.get());
						tabData.accept(ModItems.SHIT_STICK.get());
						tabData.accept(ModItems.SKEWER.get());
						tabData.accept(ModItems.POMATO.get());
						//tabData.accept(ModItems.RADIO_HAT.get());
						tabData.accept(ModItems.HALF_HEART_OF_THE_SEA.get());
						tabData.accept(ModItems.DELLIST.get());
						tabData.accept(ModItems.BEAWEED_CHECKER.get());
						tabData.accept(ModItems.BOTTLE_O_PRICEL.get());

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
