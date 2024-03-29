
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.item.init;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.fluid.init.ModFluid;
import net.svisvi.jigsawpp.item.*;

import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.svisvi.jigsawpp.item.armor.GasMaskItem;
import net.svisvi.jigsawpp.item.armor.HazmatItem;
import net.svisvi.jigsawpp.item.armor.SlaveItem;
import net.svisvi.jigsawpp.item.sweet_bread.SweetBreadItem;


public class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, JigsawPpMod.MODID);
	public static final RegistryObject<Item> ELEPHANT_PICKAXE = REGISTRY.register("elephant_pickaxe", () -> new ElephantPickaxeItem());
	public static final RegistryObject<Item> MOSS_ELEPHANT_PICKAXE = REGISTRY.register("moss_elephant_pickaxe", () -> new MossElephantPickaxeItem());
	public static final RegistryObject<Item> STRAWBERRY_ELEPHANT_PICKAXE = REGISTRY.register("strawberry_elephant_pickaxe", () -> new StrawberryElephantPickaxeItem());

	public static final RegistryObject<Item> COAL_FOSSIL = block(ModBlocks.COAL_FOSSIL);
	public static final RegistryObject<Item> RAW_TEAPOT = block(ModBlocks.RAW_TEAPOT);
	public static final RegistryObject<Item> TEAPOT = block(ModBlocks.TEAPOT);
	public static final RegistryObject<Item> BLACK_TEAPOT = block(ModBlocks.BLACK_TEAPOT);

	public static final RegistryObject<Item> CUM_SHARD = REGISTRY.register("cum_shard", () -> new CumshardItem());
	public static final RegistryObject<Item> RAW_CUM = REGISTRY.register("raw_cum", () -> new RawCumItem());
	public static final RegistryObject<Item> DUST_OF_BABUSHKA = REGISTRY.register("dust_of_babushka", () -> new DustOfBabushkaItem());
	public static final RegistryObject<Item> SWORD_OF_BABUSHKA_DUST = REGISTRY.register("sword_of_babushka_dust", () -> new SwordOfBabushkaDustItem());

	public static final RegistryObject<Item> SWEET_BREAD = REGISTRY.register("sweet_bread", () -> new SweetBreadItem());
	public static final RegistryObject<Item> MOSS_ELEPHANT_SPAWN_EGG = REGISTRY.register("moss_elephant_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.MOSS_ELEPHANT, 0x213818, 0x509e31,
					new Item.Properties()));
	public static final RegistryObject<Item> MOSS_ELEPHANT_THUMB = REGISTRY.register("moss_elephant_thumb", () -> new MossElephantThumbItem());

	public static final RegistryObject<Item> HAZMAT_HELMET = REGISTRY.register("hazmat_helmet", () -> new HazmatItem.Helmet());
	public static final RegistryObject<Item> HAZMAT_CHESTPLATE = REGISTRY.register("hazmat_chestplate", () -> new HazmatItem.Chestplate());
	public static final RegistryObject<Item> HAZMAT_LEGGINGS = REGISTRY.register("hazmat_leggings", () -> new HazmatItem.Leggings());
	public static final RegistryObject<Item> HAZMAT_BOOTS = REGISTRY.register("hazmat_boots", () -> new HazmatItem.Boots());

	public static final RegistryObject<Item> GAS_MASK_HELMET = REGISTRY.register("gas_mask_helmet", () -> new GasMaskItem.Helmet());

	public static final RegistryObject<Item> SLAVE_HELMET = REGISTRY.register("slave_helmet", () -> new SlaveItem.Helmet());
	public static final RegistryObject<Item> SLAVE_CHESTPLATE = REGISTRY.register("slave_chestplate", () -> new SlaveItem.Chestplate());
	public static final RegistryObject<Item> SLAVE_LEGGINGS = REGISTRY.register("slave_leggings", () -> new SlaveItem.Leggings());
	public static final RegistryObject<Item> SLAVE_BOOTS = REGISTRY.register("slave_boots", () -> new SlaveItem.Boots());

	public static final RegistryObject<Item> DRIST_BUTTON = REGISTRY.register("drist_button", () -> new dristButtonItem());

	public static final RegistryObject<Item> BEAWEED_SEEDS = REGISTRY.register("beaweed_seeds", () -> new ItemNameBlockItem(ModBlocks.BEAWEED.get(), new Item.Properties()));

	public static final RegistryObject<Item> PONOS_BUCKET =REGISTRY.register(
			"ponos_bucket", () -> new BucketItem(ModFluid.SOURCE_PONOS_WATER,
					new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));


	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
