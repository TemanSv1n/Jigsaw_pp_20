package net.svisvi.jigsawpp.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.svisvi.jigsawpp.gamerules.ModGameRules;
import net.svisvi.jigsawpp.procedures.ut.PurgenApocalypseUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {

    @Inject(
            method = "setupColor(Lnet/minecraft/client/Camera;FLnet/minecraft/client/multiplayer/ClientLevel;IF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void overrideFogColor(Camera camera, float partialTicks, ClientLevel level,
                                         int renderDistanceChunks, float bossColorModifier, CallbackInfo ci) {
        if (shouldApplyPurgenEffects()) {
            // Directly override the fog color in the render system
            RenderSystem.clearColor(0.4f, 0.2f, 0.0f, 0.0f);
            //RenderSystem.setShaderFogColor(0.4f, 0.2f, 0.0f, 0.0f);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static boolean shouldApplyPurgenEffects() {
        Player player = Minecraft.getInstance().player;

        if (player == null) return false;
        return PurgenApocalypseUtils.purgenPlayerRenderCondition(player);
    }
}