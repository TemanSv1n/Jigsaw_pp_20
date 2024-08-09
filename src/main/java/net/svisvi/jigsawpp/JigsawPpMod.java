/*
 *    MCreator note:
 *
 *    If you lock base mod element files, you can edit this file and it won't get overwritten.
 *    If you change your modid or package, you need to apply these changes to this file MANUALLY.
 *
 *    Settings in @Mod annotation WON'T be changed in case of the base mod element
 *    files lock too, so you need to set them manually here in such case.
 *
 *    If you do not lock base mod element files in Workspace settings, this file
 *    will be REGENERATED on each build.
 *
 */
package net.svisvi.jigsawpp;


import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.client.screen.ModMenuTypes;
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryScreen;
import net.svisvi.jigsawpp.effect.init.ModEffects;

import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerEntity;
import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerRenderer;
import net.svisvi.jigsawpp.entity.projectile.SlonProjectile;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileRenderer;
//import net.svisvi.jigsawpp.fluid.init.ModFluid;

import net.svisvi.jigsawpp.fluid.init.ModFluids;

import net.svisvi.jigsawpp.fluid.init.ModFluidTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantRenderer;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.init.ModDatas;
import net.svisvi.jigsawpp.init.ModPaintings;
import net.svisvi.jigsawpp.init.ModSounds;
import net.svisvi.jigsawpp.item.init.ModItemProperties;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.poi_types.ModPoiTypes;
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
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
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
			EntityRenderers.register(ModEntities.MOSS_ELEPHANT.get(), MossElephantRenderer::new);
			EntityRenderers.register(ModEntities.SWEET_BREAD.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.FLOPPA_MISSILE.get(), FloppaMissileRenderer::new);
			EntityRenderers.register(ModEntities.PURGEN_PILULE_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.EXTINGUISHER_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.BLABBIT.get(), BlabbitRenderer::new);
			EntityRenderers.register(ModEntities.SLONGUN_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.SLONGUN_GREEN_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.BEAVER_BOMB.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.BEAVERZOOKA_ENTITY.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.TREE_PROJECTILE.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.THROWN_PLUNGER.get(), ThrownPlungerRenderer::new);

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


}
