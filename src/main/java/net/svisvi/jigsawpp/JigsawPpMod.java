/*
 *  JigsawPpMod
 *  Guano
 *
 */
package net.svisvi.jigsawpp;


import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.loading.FMLLoader;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.client.screen.ModMenuTypes;
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryScreen;
import net.svisvi.jigsawpp.effect.init.ModEffects;

import net.svisvi.jigsawpp.entity.drist_tnt.DristTntRenderer;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairRenderer;
import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerRenderer;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileRenderer;
//import net.svisvi.jigsawpp.fluid.init.ModFluid;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverRenderer;
import net.svisvi.jigsawpp.entity.teapod.blackTeapodSpider.BlackTeapodSpiderRender;
import net.svisvi.jigsawpp.entity.teapod.rawTeapodSpider.RawTeapodSpider;
import net.svisvi.jigsawpp.entity.teapod.rawTeapodSpider.RawTeapodSpiderRender;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpiderRender;
import net.svisvi.jigsawpp.entity.tntpot.NuclearTeapotRenderer;
import net.svisvi.jigsawpp.entity.tntpot.TntPotRenderer;
import net.svisvi.jigsawpp.fluid.init.ModFluids;

import net.svisvi.jigsawpp.fluid.init.ModFluidTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantRenderer;
import net.svisvi.jigsawpp.block.init.ModBlocks;
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
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import net.svisvi.jigsawpp.entity.beaverSpider.BeaverSpiderRenderer;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieRenderer;
import net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner.BeaverZombieSpawnerRenderer;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitRenderer;

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

		MinecraftForge.EVENT_BUS.register(this);


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
			//ModDatas.addFactoryHeaterFurnaceModeList(ModBlocks.TEAPOT.get());


			//predicates)))
			ModItemProperties.addCustomItemProperties();

			ItemBlockRenderTypes.setRenderLayer(ModBlocks.BEAWEED.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.FACTORY_HEATER.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.LENIN_BUST.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURGEN_FACTORY.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.KEGA.get(), RenderType.translucent());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.FARFUHRER.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.RANDOMPOT.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.TNTPOT.get(), RenderType.cutout());
			ItemBlockRenderTypes.setRenderLayer(ModBlocks.NUCLEAR_TEAPOT.get(), RenderType.cutout());

			EntityRenderers.register(ModEntities.MOSS_ELEPHANT.get(), MossElephantRenderer::new);
			EntityRenderers.register(ModEntities.SWEET_BREAD.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.POOPS.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.POOPIS.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.FLOPPA_MISSILE.get(), FloppaMissileRenderer::new);
			EntityRenderers.register(ModEntities.PURGEN_PILULE_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.EXTINGUISHER_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.BLABBIT.get(), BlabbitRenderer::new);
			EntityRenderers.register(ModEntities.SLONGUN_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.SLONGUN_GREEN_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.BEAVER_BOMB.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.BEAVERZOOKA_ENTITY.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.NUCLEAR_TEAPOT_MISSILE_ENTITY.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.TEAPOT_MISSILE_ENTITY.get(), ThrownItemRenderer::new);

      EntityRenderers.register(ModEntities.TEAPOD_SPIDER.get(), TeapodSpiderRender::new);
			EntityRenderers.register(ModEntities.BLACK_TEAPOD_SPIDER.get(), BlackTeapodSpiderRender::new);
			EntityRenderers.register(ModEntities.RAW_TEAPOD_SPIDER.get(), RawTeapodSpiderRender::new);

			EntityRenderers.register(ModEntities.STONE_BEAVER.get(), StoneBeaverRenderer::new);
      EntityRenderers.register(ModEntities.ZOMBIE_BEAVER.get(), BeaverZombieRenderer::new);
      EntityRenderers.register(ModEntities.ZOMBIE_BEAVER_SPAWNER.get(), BeaverZombieSpawnerRenderer::new);

 			EntityRenderers.register(ModEntities.TREE_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.THROWN_PLUNGER.get(), ThrownPlungerRenderer::new);
			EntityRenderers.register(ModEntities.DRIST_TNT.get(), DristTntRenderer::new);
			EntityRenderers.register(ModEntities.TNTPOT.get(), TntPotRenderer::new);
			EntityRenderers.register(ModEntities.NUCLEAR_TEAPOT.get(), NuclearTeapotRenderer::new);
			EntityRenderers.register(ModEntities.DRIST_TNT_STICK.get(), ThrownItemRenderer::new);
      EntityRenderers.register(ModEntities.BEAVER_SPIDER.get(), BeaverSpiderRenderer::new);
			EntityRenderers.register(ModEntities.JETSTREAM_CHAIR.get(), (p_174090_) -> {
				return new JetstreamChairRenderer(p_174090_, ModModelLayers.JETSTREAM_CHAIR_LAYER);
			});



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

	public static boolean isModLoaded(String modId) {
		return FMLLoader.getLoadingModList().getMods().stream()
				.anyMatch(m -> m.getModId().equals(modId));
	}


}
