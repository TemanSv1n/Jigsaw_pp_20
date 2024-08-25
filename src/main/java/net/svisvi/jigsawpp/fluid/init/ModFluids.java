package net.svisvi.jigsawpp.fluid.init;

import net.svisvi.jigsawpp.fluid.fat.FatFluid;
import net.svisvi.jigsawpp.fluid.ponos.PonosFluid;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

public class ModFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, JigsawPpMod.MODID);
    public static final RegistryObject<FlowingFluid> PONOS = REGISTRY.register("ponos", () -> new PonosFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_PONOS = REGISTRY.register("flowing_ponos", () -> new PonosFluid.Flowing());
    public static final RegistryObject<FlowingFluid> FAT = REGISTRY.register("fat", () -> new FatFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_FAT = REGISTRY.register("flowing_fat", () -> new FatFluid.Flowing());

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(PONOS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_PONOS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FAT.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_FAT.get(), RenderType.translucent());
        }
    }
}

