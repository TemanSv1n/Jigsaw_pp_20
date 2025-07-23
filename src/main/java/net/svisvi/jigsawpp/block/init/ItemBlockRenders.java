package net.svisvi.jigsawpp.block.init;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ItemBlockRenders {
    public static void registerItemBlockRenders() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BEAWEED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FACTORY_HEATER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LENIN_BUST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.PURGEN_FACTORY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.KEGA.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FARFUHRER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RANDOMPOT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TNTPOT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NUCLEAR_TEAPOT.get(), RenderType.cutout());
    }
}
