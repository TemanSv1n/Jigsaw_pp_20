
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.svisvi.jigsawpp.item.init;


import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairItem;
import net.svisvi.jigsawpp.fluid.init.ModFluids;
import net.svisvi.jigsawpp.item.FriedBeaweedSeedsItem;
import net.svisvi.jigsawpp.item.*;

import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;


import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.svisvi.jigsawpp.item.armor.*;
import net.svisvi.jigsawpp.item.beaver_bomb.BeaverBombItem;
import net.svisvi.jigsawpp.item.drist_tnt_stick.DristTntStickItem;
import net.svisvi.jigsawpp.item.extinguisher.ExtinguisherItem;
import net.svisvi.jigsawpp.item.materials.FittingItem;
import net.svisvi.jigsawpp.item.materials.RadiationCatalystActivatedItem;
import net.svisvi.jigsawpp.item.materials.RadiationCatalystNonActivatedItem;
import net.svisvi.jigsawpp.item.pilule.*;
import net.svisvi.jigsawpp.item.plunger.PlungerItem;
import net.svisvi.jigsawpp.item.poops.PoopisItem;
import net.svisvi.jigsawpp.item.poops.PoopsItem;
import net.svisvi.jigsawpp.item.pspack.*;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenGunItem;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenMachineGunItem;
import net.svisvi.jigsawpp.item.purgen_recipe_helpers.AbstractPurgenRecipeHelperItem;
import net.svisvi.jigsawpp.item.shit_cup.ShitCupItem;
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
	public static final RegistryObject<Item> FAT_BLOCK = block(ModBlocks.FAT_BLOCK);
	public static final RegistryObject<Item> PORK_BLOCK = block(ModBlocks.PORK_BLOCK);
	public static final RegistryObject<Item> RAW_TEAPOT = block(ModBlocks.RAW_TEAPOT);
	public static final RegistryObject<Item> TEAPOT = block(ModBlocks.TEAPOT);
	public static final RegistryObject<Item> BLACK_TEAPOT = block(ModBlocks.BLACK_TEAPOT);
	public static final RegistryObject<Item> YOBA = block(ModBlocks.YOBA);
	public static final RegistryObject<Item> YOBA_POOP = block(ModBlocks.YOBA_POOP);
	public static final RegistryObject<Item> POOPED_BRICKS = block(ModBlocks.POOPED_BRICKS);
	public static final RegistryObject<Item> LENIN_BUST = block(ModBlocks.LENIN_BUST);
	public static final RegistryObject<Item> MOLD_BLOCK = block(ModBlocks.MOLD_BLOCK);
	public static final RegistryObject<Item> BEAVER_COMPUTER = block(ModBlocks.BEAVER_COMPUTER);
	public static final RegistryObject<Item> PW_HAT = block(ModBlocks.PW_HAT);
	public static final RegistryObject<Item> PLATE_EMPTY = block(ModBlocks.PLATE_EMPTY);
	public static final RegistryObject<Item> PLATE_BREAD = block(ModBlocks.PLATE_BREAD);
	public static final RegistryObject<Item> BLABEGG = block(ModBlocks.BLABEGG);
	public static final RegistryObject<Item> PIG_TUFFYAK = block(ModBlocks.PIG_TUFFYAK);
	public static final RegistryObject<Item> PSYCHO_STONE = block(ModBlocks.PSYCHO_STONE);
	public static final RegistryObject<Item> SHIT_BLOCK = block(ModBlocks.SHIT_BLOCK);
	public static final RegistryObject<Item> CREATIVE_HEAT_SOURCE = block(ModBlocks.CREATIVE_HEAT_SOURCE);


	public static final RegistryObject<Item> ZOMBIE_BEAVER_SPAWN_EGG = REGISTRY.register("zombie_beaver_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntities.ZOMBIE_BEAVER, 0x114f02, 0xf21313,
                new Item.Properties()));

  public static final RegistryObject<Item> ZOMBIE_BEAVER_SPAWNER_SPAWN_EGG = REGISTRY.register("zombie_beaver_spawner_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntities.ZOMBIE_BEAVER_SPAWNER, 0x114f02, 0x7f3300,
                new Item.Properties()));
  public static final RegistryObject<Item> BEAVER_SPIDER_SPAWN_EGG = REGISTRY.register("beaver_spider_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntities.BEAVER_SPIDER, 0x451d02, 0x0d0703,
                new Item.Properties()));

	public static final RegistryObject<Item> SWEET_BREAD = REGISTRY.register("sweet_bread", () -> new SweetBreadItem());
    public static final RegistryObject<Item> TEAPOD_SPIDER_SPAWN_EGG = REGISTRY.register("teapod_spider_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TEAPOD_SPIDER, 0xcfb1a0, 0xc06c3e,
                    new Item.Properties()));
	public static final RegistryObject<Item> BLACK_TEAPOD_SPIDER_SPAWN_EGG = REGISTRY.register("black_teapod_spider_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.BLACK_TEAPOD_SPIDER, 0x241610, 0xc06c3e,
					new Item.Properties()));
	public static final RegistryObject<Item> RAW_TEAPOD_SPIDER_SPAWN_EGG = REGISTRY.register("raw_teapod_spider_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.RAW_TEAPOD_SPIDER, 0xadb7d3, 0xc06c3e,
					new Item.Properties()));
	public static final RegistryObject<Item> MOSS_ELEPHANT_SPAWN_EGG = REGISTRY.register("moss_elephant_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.MOSS_ELEPHANT, 0x213818, 0x509e31,
					new Item.Properties()));
	public static final RegistryObject<Item> STONE_BEAVER_SPAWN_EGG = REGISTRY.register("stone_beaver_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.STONE_BEAVER, 0x482911, 0x1910e0,
					new Item.Properties()));
	public static final RegistryObject<Item> MOSS_ELEPHANT_THUMB = REGISTRY.register("moss_elephant_thumb", () -> new MossElephantThumbItem());

	public static final RegistryObject<Item> HAZMAT_HELMET = REGISTRY.register("hazmat_helmet", () -> new HazmatItem.Helmet());
	public static final RegistryObject<Item> HAZMAT_CHESTPLATE = REGISTRY.register("hazmat_chestplate", () -> new HazmatItem.Chestplate());
	public static final RegistryObject<Item> HAZMAT_LEGGINGS = REGISTRY.register("hazmat_leggings", () -> new HazmatItem.Leggings());
	public static final RegistryObject<Item> HAZMAT_BOOTS = REGISTRY.register("hazmat_boots", () -> new HazmatItem.Boots());

	public static final RegistryObject<Item> BEAVER_HELMET = REGISTRY.register("beaver_helmet", () -> new BeaverItem.Helmet());
	public static final RegistryObject<Item> BEAVER_CHESTPLATE = REGISTRY.register("beaver_chestplate", () -> new BeaverItem.Chestplate());
	public static final RegistryObject<Item> BEAVER_BOOTS = REGISTRY.register("beaver_boots", () -> new BeaverItem.Boots());

	public static final RegistryObject<Item> GAS_MASK_HELMET = REGISTRY.register("gas_mask_helmet", () -> new GasMaskItem.Helmet());
	public static final RegistryObject<Item> JOTARO_HAT = REGISTRY.register("jotaro_hat", () -> new JotaroHatItem.Helmet());
	public static final RegistryObject<Item> STRAPS = REGISTRY.register("straps", () -> new StrapsItem.Chestplate());
	public static final RegistryObject<Item> DOCTOR_HELMET = REGISTRY.register("doctor_helmet", () -> new DoctorHelmetItem.Helmet());


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
	public static final RegistryObject<Item> FAT_BUCKET =REGISTRY.register(
			"fat_bucket", () -> new BucketItem(ModFluids.FAT,
					new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
	public static final RegistryObject<Item> SHIT_CUP =REGISTRY.register(
			"shit_cup", () -> new ShitCupItem(ModFluids.PONOS, new Item.Properties().stacksTo(1)));

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
	public static final RegistryObject<Item> FARFUHRER = block(ModBlocks.FARFUHRER);
	public static final RegistryObject<Item> RANDOMPOT = block(ModBlocks.RANDOMPOT);
	public static final RegistryObject<Item> TNTPOT = block(ModBlocks.TNTPOT);
	public static final RegistryObject<Item> NUCLEAR_TEAPOT = block(ModBlocks.NUCLEAR_TEAPOT);


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
	public static final RegistryObject<Item> BIG_NUCLEAR_TEAPOT = REGISTRY.register("big_nuclear_teapot", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> BIG_TEAPOT = REGISTRY.register("big_teapot", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> PURGEN_RECIPE_HELPER_CLOCK = REGISTRY.register("prh_clock", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> PURGEN_RECIPE_HELPER_PURITY = REGISTRY.register("prh_purity", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> PURGEN_RECIPE_HELPER_TNT = REGISTRY.register("prh_tnt", () -> new AbstractPurgenRecipeHelperItem());
	public static final RegistryObject<Item> BATCH_SIZE_CARD = REGISTRY.register("batch_size_card", () -> new BatchSizeCardItem());
	public static final RegistryObject<Item> BLABBIT_SPAWN_EGG = REGISTRY.register("blabbit_spawn_egg",
			() -> new ForgeSpawnEggItem(ModEntities.BLABBIT, 0xf3f6f4, 0x8e7cc3,
					new Item.Properties()));
	public static final RegistryObject<Item> BEAWEED_PLATE = REGISTRY.register("beaweed_plate", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> SMART_BEAWEED_PLATE = REGISTRY.register("smart_beaweed_plate", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> CARROT_COIL = REGISTRY.register("carrot_coil", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BLABBALL = REGISTRY.register("blabball", () -> new BlabballItem());
	public static final RegistryObject<Item> USELESS_PIE = REGISTRY.register("useless_pie", () -> new UselessPieItem());

	public static final RegistryObject<Item> SLONGUN_COSTIL =REGISTRY.register("slongun_costil", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> SLONGUN_GREEN = REGISTRY.register("slon_gun_green", () -> new SlonGunGreenItem());
	public static final RegistryObject<Item> MOLD = REGISTRY.register("mold", () -> new MoldItem());
	public static final RegistryObject<Item> BEAVER_KNIFE = REGISTRY.register("beaver_knife", () -> new BeaverKnifeItem());
	public static final RegistryObject<Item> BEAVER_AXE = REGISTRY.register("beaver_axe", () -> new BeaverAxeItem());
	public static final RegistryObject<Item> PIGKAXE = REGISTRY.register("pigkaxe", () -> new PigkaxeItem());
	public static final RegistryObject<Item> TUBE_19 = REGISTRY.register("tube_19", () -> new Tube19Item());
	public static final RegistryObject<Item> EGGS = REGISTRY.register("eggs", () -> new EggsItem());
	public static final RegistryObject<Item> TOTEM_OF_SHIT = REGISTRY.register("totem_of_shit", () -> new TotemOfShitItem());
	public static final RegistryObject<Item> POOPED_BREAD = REGISTRY.register("pooped_bread", () -> new PoopedBreadItem());
	public static final RegistryObject<Item> BEAVER_BOMB = REGISTRY.register("beaver_bomb", () -> new BeaverBombItem());
	public static final RegistryObject<Item> BEAVER_AMMO = REGISTRY.register("beaver_ammo", () -> new Item(new Item.Properties().stacksTo(64)));
	public static final RegistryObject<Item> BEAVERZOOKA = REGISTRY.register("beaverzooka", () -> new BeaverzookaItem());
	public static final RegistryObject<Item> ADMIN_STICK = REGISTRY.register("admin_stick", () -> new AdminStickItem());
	public static final RegistryObject<Item> TREE_LAUNCHER = REGISTRY.register("tree_launcher", () -> new TreeLauncherItem());

	//pspack

	public static final RegistryObject<Item> DIAMOND_IRON_INGOT = REGISTRY.register("diamond_iron_ingot", () -> new DiamondIronIngotItem());
	public static final RegistryObject<Item> ARM = REGISTRY.register("arm", () -> new ArmItem());
	public static final RegistryObject<Item> BEAVER_STAFF = REGISTRY.register("beaver_staff", () -> new BeaverStaffItem(new Tier() {
		public int getUses() {
			return 100;
		}

		public float getSpeed() {
			return 4f;
		}

		public float getAttackDamageBonus() {
			return 0f;
		}

		public int getLevel() {
			return 1;
		}

		public int getEnchantmentValue() {
			return 2;
		}

		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(Items.STICK));
		}
	}));
	public static final RegistryObject<Item> BEAVER_STAFF_HONEY = REGISTRY.register("beaver_staff_honey", () -> new BeaverStaffHoneyItem());
	public static final RegistryObject<Item> BROWN_CUTLASS = REGISTRY.register("brown_cutlass", () -> new BrownCutlassItem());
	public static final RegistryObject<Item> CROSS = REGISTRY.register("cross", () -> new CrossItem());
	public static final RegistryObject<Item> LADLE = REGISTRY.register("ladle", () -> new LadleItem());
	public static final RegistryObject<Item> EMPTY_BOTTLE = REGISTRY.register("empty_bottle", () -> new EmptyBottleItem());
	public static final RegistryObject<Item> ROSE_BOTTLE = REGISTRY.register("rose_bottle", () -> new RoseBottleItem());
	public static final RegistryObject<Item> ERSHIK = REGISTRY.register("ershik", () -> new ErshikItem());
	public static final RegistryObject<Item> PADDLE = REGISTRY.register("paddle", () -> new PaddleItem());
	public static final RegistryObject<Item> PIRATE_CUTLASS = REGISTRY.register("pirate_cutlass", () -> new PirateCutlassItem());
	public static final RegistryObject<Item> PIZDOOR = REGISTRY.register("pizdoor", () -> new PizdoorItem());
	public static final RegistryObject<Item> PONOS_CUTLASS = REGISTRY.register("ponos_cutlass", () -> new PonosCutlassItem());
	public static final RegistryObject<Item> PRICEL_RAPIER = REGISTRY.register("pricel_rapier", () -> new PricelRapierItem());
	public static final RegistryObject<Item> ROAD_SIGN = REGISTRY.register("road_sign", () -> new RoadSignItem());
	public static final RegistryObject<Item> SHISHKIN = REGISTRY.register("shishkin", () -> new ShishkinItem());
	public static final RegistryObject<Item> SHIT_STICK = REGISTRY.register("shit_stick", () -> new ShitStickItem());
	public static final RegistryObject<Item> SKEWER = REGISTRY.register("skewer", () -> new SkewerItem());
	public static final RegistryObject<Item> BEAWEED_CHECKER = REGISTRY.register("beaweed_checker", () -> new BeaweedCheckerItem());
	public static final RegistryObject<Item> POMATO = REGISTRY.register("pomato", () -> new PomatoItem());
	public static final RegistryObject<Item> RADIO_HAT = REGISTRY.register("radio_hat", () -> new RadioHatItem());
	public static final RegistryObject<Item> HALF_HEART_OF_THE_SEA = REGISTRY.register("half_heart_of_the_sea", () -> new HalfHeartOfTheSeaItem());
	public static final RegistryObject<Item> DELLIST = REGISTRY.register("dellist", () -> new DellistItem());
	public static final RegistryObject<Item> FRIENDSHIP_FAN = REGISTRY.register("friendship_fan", () -> new FriendshipFanItem());
	public static final RegistryObject<Item> BOTTLE_O_PRICEL = block(ModBlocks.BOTTLE_O_PRICEL);
	public static final RegistryObject<Item> DIAMOND_IRON_BLOCK = block(ModBlocks.DIAMOND_IRON_BLOCK);
	public static final RegistryObject<Item> PLUNGER = REGISTRY.register("plunger", () -> new PlungerItem((new Item.Properties()).durability(250)));
	public static final RegistryObject<Item> DRIST_TNT = block(ModBlocks.DRIST_TNT);
	public static final RegistryObject<Item> DRIST_TNT_STICK = REGISTRY.register("drist_tnt_stick", () -> new DristTntStickItem());
	public static final RegistryObject<Item> JETSTREAM_CHAIR = REGISTRY.register("jetstream_chair", () -> new JetstreamChairItem(new Item.Properties().stacksTo(1)));
	public static final RegistryObject<Item> HOT_FAT = REGISTRY.register("hot_fat", () -> new HotFatItem(new Item.Properties().stacksTo(16), 0.9f));
	public static final RegistryObject<Item> TALLOW = REGISTRY.register("tallow", () -> new TallowItem());
	public static final RegistryObject<Item> POOPS = REGISTRY.register("poops", () -> new PoopsItem());
	public static final RegistryObject<Item> POOPIS = REGISTRY.register("poopis", () -> new PoopisItem());
	public static final RegistryObject<Item> TEAPOT_DRILL = REGISTRY.register("teapot_drill", () -> new TeapotDrillItem());
	public static final RegistryObject<Item> TEAPOT_HAMMER = REGISTRY.register("teapot_hammer", () -> new TeapotHammerItem());
	public static final RegistryObject<Item> TEAPOT_RIFLE = REGISTRY.register("teapot_rifle", () -> new TeapotRiffleItem());
	public static final RegistryObject<Item> TEAPOT_LAUNCHER = REGISTRY.register("teapot_launcher", () -> new TeapotLauncherItem());
	public static final RegistryObject<Item> TEAPOT_CANNON = REGISTRY.register("teapot_cannon", () -> new TeapotCannonItem());
	public static final RegistryObject<Item> GRUEL = REGISTRY.register("gruel", () -> new GruelItem());
	public static final RegistryObject<Item> FRIED_SOUP = REGISTRY.register("fried_soup", () -> new FriedSoupItem());
	public static final RegistryObject<Item> POONAMI = block(ModBlocks.POONAMI);

	private static RegistryObject<Item> block(RegistryObject<Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

}
