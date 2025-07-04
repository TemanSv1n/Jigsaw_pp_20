package net.svisvi.jigsawpp.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.svisvi.jigsawpp.gamerules.ModGameRules;
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
        if (shouldApplyPurgenEffects(level)) {
            // Directly override the fog color in the render system
            RenderSystem.clearColor(0.4f, 0.2f, 0.0f, 0.0f);
        }
    }

    private static boolean shouldApplyPurgenEffects(ClientLevel level) {
        return true; //level != null && level.getGameRules().getBoolean(ModGameRules.RULE_PURGEN_ENVIROMENT);
    }
}