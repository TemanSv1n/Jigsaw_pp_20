
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.item.init;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.fluid.init.ModFluids;
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
import net.svisvi.jigsawpp.item.extinguisher.ExtinguisherItem;
import net.svisvi.jigsawpp.item.pilule.AdvancedPurgenPiluleItem;
import net.svisvi.jigsawpp.item.pilule.BasicPurgenPiluleItem;
import net.svisvi.jigsawpp.item.pilule.CrystalPurgenPiluleItem;
import net.svisvi.jigsawpp.item.pilule.EmptyPiluleItem;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenGunItem;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenMachineGunItem;
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
	public static final RegistryObject<Item> FORK = REGISTRY.register("fork", () -> new ForkItem());
	public static final RegistryObject<Item> PONOS_BUCKET =REGISTRY.register(
			"ponos_bucket", () -> new BucketItem(ModFluids.PONOS,
					new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

	public static final RegistryObject<Item> EMPTY_PILULE = REGISTRY.register("empty_pilule", () -> new EmptyPiluleItem());
	public static final RegistryObject<Item> BASIC_PURGEN_PILULE = REGISTRY.register("basic_purgen_pilule", () -> new BasicPurgenPiluleItem());
	public static final RegistryObject<Item> ADVANCED_PURGEN_PILULE = REGISTRY.register("advanced_purgen_pilule", () -> new AdvancedPurgenPiluleItem());
	public static final RegistryObject<Item> CRYSTAL_PURGEN_PILULE = REGISTRY.register("crystal_purgen_pilule", () -> new CrystalPurgenPiluleItem());

	public static final RegistryObject<Item> EXTINGUISHER = REGISTRY.register("extinguisher", () -> new ExtinguisherItem());

	public static final RegistryObject<Item> PURGEN_BUNDLE = REGISTRY.register("purgen_bundle", () -> new PurgenBundleItem());

	public static final RegistryObject<Item> PURGEN_GUN = REGISTRY.register("purgen_gun", () -> new PurgenGunItem());
	public static final RegistryObject<Item> PURGEN_MACHINE_GUN = REGISTRY.register("purgen_machine_gun", () -> new PurgenMachineGunItem());

	public static final RegistryObject<Item> BEAWEED_SOUP = REGISTRY.register("beaweed_soup", () -> new BeaweedSoupItem());
	public static final RegistryObject<Item> SAWDUST_SOUP = REGISTRY.register("sawdust_soup", () -> new SawdustSoupItem());
	public static final RegistryObject<Item> BEAWEED_SCUM = REGISTRY.register("beaweed_scum", () -> new BeaweedScumItem());
	public static final RegistryObject<Item> BEAWEED_DUST = REGISTRY.register("beaweed_dust", () -> new BeaweedDustItem());
	public static final RegistryObject<Item> BEAWEED_NUT = REGISTRY.register("beaweed_nut", () -> new BeaweedNutItem());
	public static final RegistryObject<Item> BEAWEED_BLOCK = block(ModBlocks.BEAWEED_BLOCK);

	public static final RegistryObject<Item> MARMOSET = REGISTRY.register("marmoset", () -> new MarmosetItem());




	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
