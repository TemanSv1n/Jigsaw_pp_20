package net.svisvi.jigsawpp.entity.jetstream_chair;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairEntity;

@OnlyIn(Dist.CLIENT)
public class JetstreamChairRenderer extends EntityRenderer<JetstreamChairEntity> {
    private static final ResourceLocation MINECART_LOCATION = new ResourceLocation("jigsaw_pp:textures/entity/jetstream_chair.png");
    protected final EntityModel<JetstreamChairEntity> model;
    private final BlockRenderDispatcher blockRenderer;

    public JetstreamChairRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pLayer) {
        super(pContext);
        this.shadowRadius = 0.5F;
        this.model = new JetstreamChairModel(pContext.bakeLayer(pLayer));
        this.blockRenderer = pContext.getBlockRenderDispatcher();
    }

    public void render(JetstreamChairEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();
        long $$6 = (long)pEntity.getId() * 493286711L;
        $$6 = $$6 * $$6 * 4392167121L + $$6 * 98761L;
        float $$7 = (((float)($$6 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float $$8 = (((float)($$6 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float $$9 = (((float)($$6 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        pPoseStack.translate($$7, $$8, $$9);
        double $$10 = Mth.lerp((double)pPartialTicks, pEntity.xOld, pEntity.getX());
        double $$11 = Mth.lerp((double)pPartialTicks, pEntity.yOld, pEntity.getY());
        double $$12 = Mth.lerp((double)pPartialTicks, pEntity.zOld, pEntity.getZ());
        double $$13 = 0.30000001192092896;
        Vec3 $$14 = pEntity.getPos($$10, $$11, $$12);
        float $$15 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
        if ($$14 != null) {
            Vec3 $$16 = pEntity.getPosOffs($$10, $$11, $$12, 0.30000001192092896);
            Vec3 $$17 = pEntity.getPosOffs($$10, $$11, $$12, -0.30000001192092896);
            if ($$16 == null) {
                $$16 = $$14;
            }

            if ($$17 == null) {
                $$17 = $$14;
            }

            pPoseStack.translate($$14.x - $$10, ($$16.y + $$17.y) / 2.0 - $$11, $$14.z - $$12);
            Vec3 $$18 = $$17.add(-$$16.x, -$$16.y, -$$16.z);
            if ($$18.length() != 0.0) {
                $$18 = $$18.normalize();
                pEntityYaw = (float)(Math.atan2($$18.z, $$18.x) * 180.0 / Math.PI);
                $$15 = (float)(Math.atan($$18.y) * 73.0);
            }
        }

        pPoseStack.translate(0.0F, 0.375F, 0.0F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(-$$15));
        float $$19 = (float)pEntity.getHurtTime() - pPartialTicks;
        float $$20 = pEntity.getDamage() - pPartialTicks;
        if ($$20 < 0.0F) {
            $$20 = 0.0F;
        }

        if ($$19 > 0.0F) {
            pPoseStack.mulPose(Axis.XP.rotationDegrees(Mth.sin($$19) * $$19 * $$20 / 10.0F * (float)pEntity.getHurtDir()));
        }

        int $$21 = pEntity.getDisplayOffset();
        BlockState $$22 = pEntity.getDisplayBlockState();
        if ($$22.getRenderShape() != RenderShape.INVISIBLE) {
            pPoseStack.pushPose();
            float $$23 = 0.75F;
            pPoseStack.scale(0.75F, 0.75F, 0.75F);
            pPoseStack.translate(-0.5F, (float)($$21 - 8) / 16.0F, 0.5F);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            this.renderMinecartContents(pEntity, pPartialTicks, $$22, pPoseStack, pBuffer, pPackedLight);
            pPoseStack.popPose();
        }

        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        pPoseStack.translate(0f, -1.125f, 0f);
        this.model.setupAnim(pEntity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        VertexConsumer $$24 = pBuffer.getBuffer(this.model.renderType(this.getTextureLocation(pEntity)));
        this.model.renderToBuffer(pPoseStack, $$24, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pPoseStack.popPose();
    }

    public ResourceLocation getTextureLocation(JetstreamChairEntity pEntity) {
        return MINECART_LOCATION;
    }

    protected void renderMinecartContents(JetstreamChairEntity pEntity, float pPartialTicks, BlockState pState, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.blockRenderer.renderSingleBlock(pState, pPoseStack, pBuffer, pPackedLight, OverlayTexture.NO_OVERLAY);
    }
}