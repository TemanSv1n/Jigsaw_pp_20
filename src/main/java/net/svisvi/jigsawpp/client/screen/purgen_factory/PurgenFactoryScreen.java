package net.svisvi.jigsawpp.client.screen.purgen_factory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.client.screen.FluidTankRenderer;
import net.svisvi.jigsawpp.procedures.ut.MouseUtil;

import java.util.Optional;

public class PurgenFactoryScreen extends AbstractContainerScreen<PurgenFactoryMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(JigsawPpMod.MODID, "textures/gui/purgen_factory_gui.png");
    private FluidTankRenderer renderer;

    public PurgenFactoryScreen(PurgenFactoryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
        assignFluidRenderer();
    }
    public void assignFluidRenderer(){
        renderer = new FluidTankRenderer(8000, true, 64, 48);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderer.render(pGuiGraphics.pose(), x + 62, y + 11, menu.getFluidStack());
        //pGuiGraphics.blit(new ResourceLocation(JigsawPpMod.MODID, "textures/gui/wide.png"), x+ 62, y+ 11, 0, 0, imageWidth, imageHeight);
    }

    private void renderProgressArrow(GuiGraphics pGuiGraphics, int x, int y){
        if(menu.isCrafting()){
            pGuiGraphics.blit(TEXTURE, x + 85, y + 30, 176, 0, 8, menu.getScaledProgress());
        }
    }

    @Override
    public void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

//        renderFluidAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGtaphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15)) {
            pGuiGtaphics.renderTooltip(Minecraft.getInstance().font,renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX, pMouseY);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;
        renderFluidAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
