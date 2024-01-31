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


import net.svisvi.jigsawpp.fluid.ModFluid;
import net.svisvi.jigsawpp.fluid.ModFluidTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.svisvi.jigsawpp.entity.ModEntities;
import net.svisvi.jigsawpp.entity.client.ModModelLayers;
import net.svisvi.jigsawpp.entity.client.MossElephantModel;
import net.svisvi.jigsawpp.entity.client.MossElephantRenderer;
import net.svisvi.jigsawpp.init.JigsawPpModBlocks;
import net.svisvi.jigsawpp.init.JigsawPpModSounds;
import net.svisvi.jigsawpp.recipe.ModRecipes;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import net.svisvi.jigsawpp.init.JigsawPpModTabs;
import net.svisvi.jigsawpp.init.JigsawPpModItems;

import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.AbstractMap;

@Mod("jigsaw_pp")
public class JigsawPpMod {
	public static final Logger LOGGER = LogManager.getLogger(JigsawPpMod.class);
	public static final String MODID = "jigsaw_pp";

	public JigsawPpMod() {

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		JigsawPpModItems.REGISTRY.register(bus);

		JigsawPpModBlocks.REGISTRY.register(bus);

		JigsawPpModTabs.REGISTRY.register(bus);

		ModFluidTypes.register(bus);

		ModFluid.register(bus);

		JigsawPpModSounds.REGISTRY.register(bus);

		ModEntities.register(bus);

		ModRecipes.register(bus);

		MinecraftForge.EVENT_BUS.register(this);

	}


	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			EntityRenderers.register(ModEntities.MOSS_ELEPHANT.get(), MossElephantRenderer::new);
		}
	}
}
