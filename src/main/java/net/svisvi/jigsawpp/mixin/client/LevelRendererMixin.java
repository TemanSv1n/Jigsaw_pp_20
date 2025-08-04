package net.svisvi.jigsawpp.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.procedures.ut.PurgenApocalypseUtils;
//import net.svisvi.jigsawpp.procedures.ut.PurgenEnvironmentHandler;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    private static final ResourceLocation CUSTOM_SUN =
            new ResourceLocation("jigsaw_pp:textures/environment/purgen_sun.png");
    private static final Vec3 BROWN_SKY_COLOR = new Vec3(0.4, 0.2, 0.0);

    @Shadow private ClientLevel level;
    @Shadow private VertexBuffer starBuffer;

    @Inject(
            method = "renderSky",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderCustomSky(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick,
                                 Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo ci) {
        // Проверка игрового правила на клиенте
        if (!shouldApplyPurgenEffects()) return;

        ci.cancel();

        // 1. Коричневое небо
        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(
                (float)BROWN_SKY_COLOR.x,
                (float)BROWN_SKY_COLOR.y,
                (float)BROWN_SKY_COLOR.z,
                1.0f
        );

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        buffer.vertex(-1.0, -1.0, -1.0).endVertex();
        buffer.vertex(1.0, -1.0, -1.0).endVertex();
        buffer.vertex(1.0, 1.0, -1.0).endVertex();
        buffer.vertex(-1.0, 1.0, -1.0).endVertex();
        BufferUploader.drawWithShader(buffer.end());

        // 2. Звёзды (если правило активно)
        float starBrightness = level.getStarBrightness(partialTick) * (shouldApplyPurgenEffects() ? 1.0F : 0.0F);
        if (starBrightness > 0.0F) {
            RenderSystem.setShaderColor(starBrightness, starBrightness, starBrightness, starBrightness);
            FogRenderer.setupNoFog();
            this.starBuffer.bind();
            this.starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());
            VertexBuffer.unbind();
        }

        // 3. Кастомное солнце (если правило активно)
        if (shouldApplyPurgenEffects()) {
            renderCustomSun(poseStack, partialTick);
        }
    }

    @Inject(
            method = "renderChunkLayer",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderColor(FFFF)V"
//            ),
            at = @At("HEAD"),
            cancellable = true
    )
    public void renderCustomChunkLayer(RenderType pRenderType, PoseStack pPoseStack, double pCamX, double pCamY, double pCamZ, Matrix4f pProjectionMatrix, CallbackInfo ci){
        //RenderSystem.setShaderColor((float)BROWN_SKY_COLOR.x, (float)BROWN_SKY_COLOR.y, (float)BROWN_SKY_COLOR.z, 1.0f);
        if (shouldApplyPurgenEffects()) {
            // Override the shader color (forces brown + disables fade)
            RenderSystem.setShaderColor(
                    (float)BROWN_SKY_COLOR.x,
                    (float)BROWN_SKY_COLOR.y,
                    (float)BROWN_SKY_COLOR.z,
                    1.0f // Full opacity
            );
            // Re-apply the color to the shader (critical!)
            ShaderInstance shader = RenderSystem.getShader();
            if (shader.COLOR_MODULATOR != null) {
                shader.COLOR_MODULATOR.set(
                        (float)BROWN_SKY_COLOR.x,
                        (float)BROWN_SKY_COLOR.y,
                        (float)BROWN_SKY_COLOR.z,
                        1.0f
                );
            }
            if (shader.FOG_COLOR != null) {
                shader.FOG_COLOR.set(
                        (float)BROWN_SKY_COLOR.x,
                        (float)BROWN_SKY_COLOR.y,
                        (float)BROWN_SKY_COLOR.z,
                        1.0f
                );
            }
        }
    }


    private void renderCustomSun(PoseStack poseStack, float partialTick) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(level.getTimeOfDay(partialTick) * 360.0F));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CUSTOM_SUN);
        RenderSystem.setShaderColor(
                (float)BROWN_SKY_COLOR.x,
                (float)BROWN_SKY_COLOR.y,
                (float)BROWN_SKY_COLOR.z,
                1.0f
        );

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        float size = 30.0f;
        buffer.vertex(matrix, -size, 100.0F, -size).uv(0.0F, 0.0F).endVertex();
        buffer.vertex(matrix, size, 100.0F, -size).uv(1.0F, 0.0F).endVertex();
        buffer.vertex(matrix, size, 100.0F, size).uv(1.0F, 1.0F).endVertex();
        buffer.vertex(matrix, -size, 100.0F, size).uv(0.0F, 1.0F).endVertex();

        BufferUploader.drawWithShader(buffer.end());
        poseStack.popPose();
    }

    // Проверка игрового правила на клиенте
//    private boolean shouldApplyPurgenEffects() {
//        return PurgenApocalypseUtils.purgesEnvironmentCondition(level);
//    }
    @OnlyIn(Dist.CLIENT)
    private boolean shouldApplyPurgenEffects() {
        Player player = Minecraft.getInstance().player;

        if (player == null) return false;
        return PurgenApocalypseUtils.purgenPlayerRenderCondition(player);
    }
}