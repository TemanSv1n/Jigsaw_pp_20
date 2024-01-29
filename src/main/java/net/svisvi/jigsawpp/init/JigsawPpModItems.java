
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.init;

import net.svisvi.jigsawpp.item.ElephantPickaxeItem;
import net.svisvi.jigsawpp.item.MossElephantPickaxeItem;
import net.svisvi.jigsawpp.item.StrawberryElephantPickaxeItem;

import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;


public class JigsawPpModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, JigsawPpMod.MODID);
	public static final RegistryObject<Item> ELEPHANT_PICKAXE = REGISTRY.register("elephant_pickaxe", () -> new ElephantPickaxeItem());
	public static final RegistryObject<Item> MOSS_ELEPHANT_PICKAXE = REGISTRY.register("moss_elephant_pickaxe", () -> new MossElephantPickaxeItem());
	public static final RegistryObject<Item> STRAWBERRY_ELEPHANT_PICKAXE = REGISTRY.register("strawberry_elephant_pickaxe", () -> new StrawberryElephantPickaxeItem());


	public static final RegistryObject<Item> COAL_FOSSIL = block(JigsawPpModBlocks.COAL_FOSSIL);
	public static final RegistryObject<Item> RAW_TEAPOT = block(JigsawPpModBlocks.RAW_TEAPOT);



	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
