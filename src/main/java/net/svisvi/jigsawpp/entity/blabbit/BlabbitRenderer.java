package net.svisvi.jigsawpp.entity.blabbit;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantModel;

public class BlabbitRenderer extends MobRenderer<BlabbitEntity, BlabbitModel<BlabbitEntity>> {
    public BlabbitRenderer(EntityRendererProvider.Context pContext){
        super(pContext, new BlabbitModel<>(pContext.bakeLayer(ModModelLayers.BLABBIT_LAYER)), 1f);
    }
    @Override
    public ResourceLocation getTextureLocation(BlabbitEntity pEntity) {
        return new ResourceLocation(JigsawPpMod.MODID, "textures/entity/blabbit.png");
    }

//    @Override
//    public void render(MossElephantEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
//                       MultiBufferSource pBuffer, int pPackedLight) {
//        if(pEntity.isBaby()) {
//            pPoseStack.scale(0.5f, 0.5f, 0.5f);
//        }
//
//        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
//    }
}
