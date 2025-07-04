package net.svisvi.jigsawpp.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.gamerules.ModGameRules;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    private static final ResourceLocation CUSTOM_SUN =
            new ResourceLocation("jigsaw_pp:textures/environment/purgen_sun.png");

    @Shadow private ClientLevel level;

    // 1. Sun Texture Replacement
    @Inject(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void replaceSunTexture(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick,
                                   Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        if (shouldApplyPurgenEffects()) {
            RenderSystem.setShaderTexture(0, CUSTOM_SUN);
        }
    }

    // 2. Sky Color Calculation Override
    @Redirect(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/multiplayer/ClientLevel;getSkyColor(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;"
            )
    )
    private Vec3 overrideSkyColor(ClientLevel instance, Vec3 position, float partialTick) {
        if (shouldApplyPurgenEffects()) {
            return new Vec3(0.4, 0.2, 0.0); // Brown color
        }
        return instance.getSkyColor(position, partialTick);
    }

    // 3. Force Sky Color
    @Inject(
            method = "renderSky",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V",
                    ordinal = 0,
                    shift = At.Shift.AFTER
            )
    )
    private void forceSkyColor(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick,
                               Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        if (shouldApplyPurgenEffects()) {
            RenderSystem.setShaderColor(0.4f, 0.2f, 0.0f, 1.0f);
        }
    }

    private boolean shouldApplyPurgenEffects() {
        return true;//level != null && level.getGameRules().getBoolean(ModGameRules.RULE_PURGEN_ENVIROMENT);
    }
}