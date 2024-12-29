package net.svisvi.jigsawpp.client.screen.space_lift;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.client.screen.FluidTankRenderer;
import net.svisvi.jigsawpp.procedures.ut.MouseUtil;
import net.svisvi.jigsawpp.recipe.SpaceLiftRecipe;

import java.util.Optional;

public class SpaceLiftScreen extends AbstractContainerScreen<SpaceLiftMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(JigsawPpMod.MODID, "textures/gui/space_lift_gui.png");

    public SpaceLiftScreen(SpaceLiftMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        //pGuiGraphics.blit(new ResourceLocation(JigsawPpMod.MODID, "textures/gui/wide.png"), x+ 62, y+ 11, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY){
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

//        renderFluidAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;
    }


    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
