package net.svisvi.jigsawpp.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class BeaverSODOverlay {
    static final Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation BVSOD = new ResourceLocation(JigsawPpMod.MODID,
            "textures/gui/bvsod.png");

    public static final IGuiOverlay HUD_POOP = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, BVSOD);

        //guiGraphics.blit(POOP, );
        if(renderingCondition(mc.player)) {
            renderTextureOverlay(guiGraphics, BVSOD, 1.0F, screenWidth, screenHeight);
        }

    });

    protected static void renderTextureOverlay(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha, int screenWidth, int screenHeight) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    public static boolean renderingCondition(Entity entity) {
        if (entity == null)
            return false;
        if (entity instanceof Player _livEnt0 && _livEnt0.hasEffect(ModEffects.BVSOD.get())) {
            return true;
        }
        return false;
    }

}
