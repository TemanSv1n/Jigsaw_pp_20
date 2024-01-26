package net.svisvi.jigsawpp.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.custom.MossElephantEntity;

public class MossElephantRenderer extends MobRenderer<MossElephantEntity, MossElephantModel<MossElephantEntity>> {
    public MossElephantRenderer(EntityRendererProvider.Context pContext){
        super(pContext, new MossElephantModel<>(pContext.bakeLayer(ModModelLayers.MOSS_ELEPHANT_LAYER)), 2f);
    }
    @Override
    public ResourceLocation getTextureLocation(MossElephantEntity pEntity) {
        return new ResourceLocation(JigsawPpMod.MODID, "textures/entity/moss_elephant.png");
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
