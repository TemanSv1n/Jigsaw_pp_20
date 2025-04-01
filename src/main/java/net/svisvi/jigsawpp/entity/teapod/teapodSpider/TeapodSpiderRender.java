package net.svisvi.jigsawpp.entity.teapod.teapodSpider;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;

/**
 * TeapodSpiderRender
 */
public class TeapodSpiderRender extends MobRenderer<TeapodSpider, TeapodSpiderModel<TeapodSpider>> {

  public TeapodSpiderRender(EntityRendererProvider.Context pContext) {
    super(pContext, new TeapodSpiderModel<>(pContext.bakeLayer(ModModelLayers.TEAPOD_SPIDER_LAYER)), 1f);
  }

  @Override
  public ResourceLocation getTextureLocation(TeapodSpider pEntity) {
    return new ResourceLocation(JigsawPpMod.MODID, "textures/entity/kettlespider.png");
  }

//  @Override
//  public void render(TeapodSpider pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
//          MultiBufferSource pBuffer, int pPackedLight) {
//      super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
//  }

  
}
