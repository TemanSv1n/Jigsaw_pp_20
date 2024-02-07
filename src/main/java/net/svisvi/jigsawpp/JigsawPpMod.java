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


import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileRenderer;
import net.svisvi.jigsawpp.fluid.init.ModFluid;
import net.svisvi.jigsawpp.fluid.init.ModFluidTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantRenderer;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.init.ModSounds;
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

@Mod("jigsaw_pp")
public class JigsawPpMod {
	public static final Logger LOGGER = LogManager.getLogger(JigsawPpMod.class);
	public static final String MODID = "jigsaw_pp";

	public JigsawPpMod() {

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		ModItems.REGISTRY.register(bus);

		ModBlocks.REGISTRY.register(bus);

		ModTabs.REGISTRY.register(bus);

		ModFluidTypes.register(bus);

		ModFluid.register(bus);

		ModSounds.REGISTRY.register(bus);

		ModEntities.register(bus);

		ModRecipes.register(bus);

		ModEffects.register(bus);

		MinecraftForge.EVENT_BUS.register(this);

	}


	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			EntityRenderers.register(ModEntities.MOSS_ELEPHANT.get(), MossElephantRenderer::new);
			EntityRenderers.register(ModEntities.SWEET_BREAD.get(), ThrownItemRenderer::new);
			EntityRenderers.register(ModEntities.FLOPPA_MISSILE.get(), FloppaMissileRenderer::new);
		}
	}
}
