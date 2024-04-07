package net.svisvi.jigsawpp.entity.projectile.floppa_missile;


import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;


import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;

public class FloppaMissileRenderer extends EntityRenderer<FloppaMissileEntity> {
    private static final ResourceLocation texture = new ResourceLocation("jigsaw_pp:textures/entity/floppa_missile.png");
    private final FloppaMissileModel model;

    public FloppaMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new FloppaMissileModel(context.bakeLayer(ModModelLayers.FLOPPA_MISSILE_LAYER));
    }

    @Override
    public void render(FloppaMissileEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.0625f);
        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(FloppaMissileEntity entity) {
        return texture;
    }
}
