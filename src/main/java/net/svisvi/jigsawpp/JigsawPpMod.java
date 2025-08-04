/*
 *  JigsawPpMod
 *  Guano
 *
 */
package net.svisvi.jigsawpp;


import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.client.screen.ModMenuTypes;
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryScreen;
import net.svisvi.jigsawpp.config.ModServerConfigs;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.enchantment.ModEnchantments;
import net.svisvi.jigsawpp.event.PonosFluidInteractionHandler;
import net.svisvi.jigsawpp.fluid.init.ModFluids;
import net.svisvi.jigsawpp.fluid.init.ModFluidTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.svisvi.jigsawpp.entity.init.EntityRenders;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.block.init.ItemBlockRenders;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.gamerules.ModGameRules;
import net.svisvi.jigsawpp.init.ModPaintings;
import net.svisvi.jigsawpp.init.ModSounds;
import net.svisvi.jigsawpp.item.init.ModItemProperties;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.poi_types.ModPoiTypes;
import net.svisvi.jigsawpp.potion.ModPotions;
import net.svisvi.jigsawpp.recipe.ModRecipes;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.svisvi.jigsawpp.init.ModTabs;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.MinecraftForge;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


@Mod("jigsaw_pp")
public class JigsawPpMod {
	public static final Logger LOGGER = LogManager.getLogger(JigsawPpMod.class);
	public static final String MODID = "jigsaw_pp";

	public JigsawPpMod() {

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.REGISTRY.register(bus);
		ModBlocks.REGISTRY.register(bus);
		ModTabs.REGISTRY.register(bus);
		ModFluidTypes.REGISTRY.register(bus);
		//ModParticles.REGISTRY.register(bus);
		ModPaintings.REGISTRY.register(bus);
		ModParticleTypes.REGISTRY.register(bus);
		ModFluids.REGISTRY.register(bus);
		ModSounds.REGISTRY.register(bus);
		ModEntities.register(bus);
		ModRecipes.register(bus);
		ModEffects.register(bus);
		ModBlockEntities.REGISTRY.register(bus);
		ModMenuTypes.REGISTRY.register(bus);
		ModPoiTypes.POI_TYPES.register(bus);
		ModPotions.register(bus);
		ModMessages.register();
		ModGameRules.register();
		ModEnchantments.register(bus);
		MinecraftForge.EVENT_BUS.register(this);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModServerConfigs.SPEC, "jigsaw-server.toml");




	}

	private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();
	public static void queueServerWork(int tick, Runnable action) {
		workQueue.add(new AbstractMap.SimpleEntry(action, tick));
	}
	@SubscribeEvent
	public void tick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
			workQueue.forEach(work -> {
				work.setValue(work.getValue() - 1);
				if (work.getValue() == 0)
					actions.add(work);
			});
			actions.forEach(e -> e.getKey().run());
			workQueue.removeAll(actions);
		}
	}


	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		/**
		 * @param event
		 */
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			ModItemProperties.addCustomItemProperties();

            EntityRenders.registerEntityRenders();

            ItemBlockRenders.registerItemBlockRenders();

			MenuScreens.register(ModMenuTypes.PURGEN_FACTORY_MENU.get(), PurgenFactoryScreen::new);
		}
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
	public static class ServerModEvents {
		@SubscribeEvent
		public static void onServerSetup(FMLDedicatedServerSetupEvent event) {
			//ModDatas.addFactoryHeaterFurnaceModeList(ModBlocks.TEAPOT.get());


		}
	}

	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public class ModSetup {
		@SubscribeEvent
		public static void onCommonSetup(FMLCommonSetupEvent event) {
			event.enqueueWork(PonosFluidInteractionHandler::registerInteractions);
		}
	}

	public static boolean isModLoaded(String modId) {
		return FMLLoader.getLoadingModList().getMods().stream()
				.anyMatch(m -> m.getModId().equals(modId));
	}


//private void clientSetup(final FMLClientSetupEvent event) {
//	event.enqueueWork(ModKeyBinds::register);
//}

}
