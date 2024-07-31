
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.item.init;


import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.fluid.init.ModFluids;
import net.svisvi.jigsawpp.item.FriedBeaweedSeedsItem;
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
import net.svisvi.jigsawpp.item.materials.FittingItem;
import net.svisvi.jigsawpp.item.materials.RadiationCatalystActivatedItem;
import net.svisvi.jigsawpp.item.materials.RadiationCatalystNonActivatedItem;
import net.svisvi.jigsawpp.item.pilule.*;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenGunItem;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenMachineGunItem;
import net.svisvi.jigsawpp.item.purgen_recipe_helpers.AbstractPurgenRecipeHelperItem;
import net.svisvi.jigsawpp.item.slon_gun.SlonGunGreenItem;
import net.svisvi.jigsawpp.item.slon_gun.SlonGunItem;
import net.svisvi.jigsawpp.item.sweet_bread.SweetBreadItem;
import net.svisvi.jigsawpp.item.ut.RadiationItem;


public class ModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, JigsawPpMod.MODID);
	public static final RegistryObject<Item> ELEPHANT_PICKAXE = REGISTRY.register("elephant_pickaxe", () -> new ElephantPickaxeItem());
	public static final RegistryObject<Item> MOSS_ELEPHANT_PICKAXE = REGISTRY.register("moss_elephant_pickaxe", () -> new MossElephantPickaxeItem());
	public static final RegistryObject<Item> STRAWBERRY_ELEPHANT_PICKAXE = REGISTRY.register("strawberry_elephant_pickaxe", () -> new StrawberryElephantPickaxeItem());

	public static final RegistryObject<Item> COAL_FOSSIL = block(ModBlocks.COAL_FOSSIL);
	public static final RegistryObject<Item> RAW_TEAPOT = block(ModBlocks.RAW_TEAPOT);
	public static final RegistryObject<Item> TEAPOT = block(ModBlocks.TEAPOT);
	public static final RegistryObject<Item> BLACK_TEAPOT = block(ModBlocks.BLACK_TEAPOT);
	public static final RegistryObject<Item> YOBA = block(ModBlocks.YOBA);
	public static final RegistryObject<Item> YOBA_POOP = block(ModBlocks.YOBA_POOP);
	public static final RegistryObject<Item> POOPED_BRICKS = block(ModBlocks.POOPED_BRICKS);
	public static final RegistryObject<Item> LENIN_BUST = block(ModBlocks.LENIN_BUST);
	public static final RegistryObject<Item> MOLD_BLOCK = block(ModBlocks.MOLD_BLOCK);
	public static final RegistryObject<Item> BEAVER_COMPUTER = block(ModBlocks.BEAVER_COMPUTER);

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

	public static final RegistryObject<Item> SLON_GUN = REGISTRY.register("slon_gun", () -> new SlonGunItem());

	public static final RegistryObject<Item> SLAVE_HELMET = REGISTRY.register("slave_helmet", () -> new SlaveItem.Helmet());
	public static final RegistryObject<Item> SLAVE_CHESTPLATE = REGISTRY.register("slave_chestplate", () -> new SlaveItem.Chestplate());
	public static final RegistryObject<Item> SLAVE_LEGGINGS = REGISTRY.register("slave_leggings", () -> new SlaveItem.Leggings());
	public static final RegistryObject<Item> SLAVE_BOOTS = REGISTRY.register("slave_boots", () -> new SlaveItem.Boots());

	public static final RegistryObject<Item> DRIST_BUTTON = REGISTRY.register("drist_button", () -> new DristButtonItem());

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
	public static final RegistryObject<Item> NUTCRACKER = REGISTRY.register("nutcracker", () -> new NutcrackerItem());

	public static final RegistryObject<Item> FRIED_BEAWEED_SEEDS = REGISTRY.register("fried_beaweed_seeds", () -> new FriedBeaweedSeedsItem());
	public static final RegistryObject<Item> BEAWEED_NUT = REGISTRY.register("beaweed_nut", () -> new BeaweedNutItem());

	public static final RegistryObject<Item> BEAWEED_BLOCK = block(ModBlocks.BEAWEED_BLOCK);
	public static final RegistryObject<Item> FACTORY_HEATER = block(ModBlocks.FACTORY_HEATER);

	// #################################################################
	// #################################################################
	// Kega components
	public static final RegistryObject<Item> NUCLEAR_PURGEN_PILULE = REGISTRY.register("nuclear_purgen_pilule", () -> new NuclearPurgenPiluleItem());
	public static final RegistryObject<Item> FITTING = REGISTRY.register("fitting", () -> new FittingItem());
	public static final RegistryObject<Item> PURGEN_FACTORY = block(ModBlocks.PURGEN_FACTORY);
	public static final RegistryObject<Item> PURGEN_FACTORY_BIG_THUMB = REGISTRY.register("purgen_factory_big_thumb", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> KEGA = block(ModBlocks.KEGA);
	public static final RegistryObject<Item> KEGA_NULL = block(ModBlocks.KEGA_NULL);
	public static final RegistryObject<Item> RADIATION_CATALYST_NON_ACTIVATED = REGISTRY.register("radiation_catalyst_non_activated", () -> new RadiationCatalystNonActivatedItem());
	public static final RegistryObject<Item> RADIATION_CATALYST_ACTIVATED = REGISTRY.register("radiation_catalyst_activated", () -> new RadiationCatalystActivatedItem());
	// #################################################################
	// #################################################################
	public static final RegistryObject<Item> MARMOSET = REGISTRY.register("marmoset", () -> new MarmosetItem());

	public static final RegistryObject<Item> GAY = REGISTRY.register("gay", () -> new RadiationItem(new Item.Properties().stacksTo(14), 0.001F));
	public static final RegistryObject<Item> PURGEN_RECIPE_HELPER_CLOCK = REGISTRY.register("prh_clock", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> PURGEN_RECIPE_HELPER_PURITY = REGISTRY.register("prh_purity", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> PURGEN_RECIPE_HELPER_TNT = REGISTRY.register("prh_tnt", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> BATCH_SIZE_CARD = REGISTRY.register("batch_size_card", () -> new Item(new Item.Properties().stacksTo(8)));
	public static final RegistryObject<Item> BLABBIT_SPAWN_EGG = REGISTRY.register("blabbit_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.BLABBIT, 0xf3f6f4, 0x8e7cc3,
					new Item.Properties()));
	public static final RegistryObject<Item> BEAWEED_PLATE = REGISTRY.register("beaweed_plate", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> SMART_BEAWEED_PLATE = REGISTRY.register("smart_beaweed_plate", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CARROT_COIL = REGISTRY.register("carrot_coil", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BLABBALL = REGISTRY.register("blabball", () -> new BlabballItem());
	public static final RegistryObject<Item> BLABEGG = REGISTRY.register("blabegg", () -> new BlabEggItem());
	public static final RegistryObject<Item> USELESS_PIE = REGISTRY.register("useless_pie", () -> new UselessPieItem());

	public static final RegistryObject<Item> SLONGUN_COSTIL =REGISTRY.register("slongun_costil", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> SLONGUN_GREEN = REGISTRY.register("slon_gun_green", () -> new SlonGunGreenItem());
	public static final RegistryObject<Item> MOLD = REGISTRY.register("mold", () -> new MoldItem());
	public static final RegistryObject<Item> BEAVER_KNIFE = REGISTRY.register("beaver_knife", () -> new BeaverKnifeItem());
	public static final RegistryObject<Item> BEAVER_AXE = REGISTRY.register("beaver_axe", () -> new BeaverAxeItem());
	public static final RegistryObject<Item> PIGKAXE = REGISTRY.register("pigkaxe", () -> new PigkaxeItem());

	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
