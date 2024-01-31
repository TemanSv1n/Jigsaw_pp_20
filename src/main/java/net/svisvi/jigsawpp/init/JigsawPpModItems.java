
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.init;


import net.minecraft.world.item.*;
import net.svisvi.jigsawpp.fluid.ModFluid;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.svisvi.jigsawpp.entity.ModEntities;
import net.svisvi.jigsawpp.item.*;

import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;


public class JigsawPpModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, JigsawPpMod.MODID);
	public static final RegistryObject<Item> ELEPHANT_PICKAXE = REGISTRY.register("elephant_pickaxe", () -> new ElephantPickaxeItem());
	public static final RegistryObject<Item> MOSS_ELEPHANT_PICKAXE = REGISTRY.register("moss_elephant_pickaxe", () -> new MossElephantPickaxeItem());
	public static final RegistryObject<Item> STRAWBERRY_ELEPHANT_PICKAXE = REGISTRY.register("strawberry_elephant_pickaxe", () -> new StrawberryElephantPickaxeItem());

	public static final RegistryObject<Item> COAL_FOSSIL = block(JigsawPpModBlocks.COAL_FOSSIL);
	public static final RegistryObject<Item> RAW_TEAPOT = block(JigsawPpModBlocks.RAW_TEAPOT);

	public static final RegistryObject<Item> CUM_SHARD = REGISTRY.register("cum_shard", () -> new CumshardItem());
	public static final RegistryObject<Item> RAW_CUM = REGISTRY.register("raw_cum", () -> new RawCumItem());
	public static final RegistryObject<Item> DUST_OF_BABUSHKA = REGISTRY.register("dust_of_babushka", () -> new DustOfBabushkaItem());
	public static final RegistryObject<Item> SWORD_OF_BABUSHKA_DUST = REGISTRY.register("sword_of_babushka_dust", () -> new SwordOfBabushkaDustItem());

	public static final RegistryObject<Item> SWEET_BREAD = REGISTRY.register("sweet_bread", () -> new SweetBreadItem());
	public static final RegistryObject<Item> MOSS_ELEPHANT_SPAWN_EGG = REGISTRY.register("moss_elephant_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.MOSS_ELEPHANT, 0x213818, 0x509e31,
					new Item.Properties()));


	public static final RegistryObject<Item> PONOS_BUCKET =REGISTRY.register(
			"ponos_bucket", () -> new BucketItem(ModFluid.SOURCE_PONOS_WATER,
					new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
