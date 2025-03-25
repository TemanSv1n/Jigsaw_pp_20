package net.svisvi.jigsawpp.entity.teapod.blackTeapodSpider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpiderModel;

/**
 * TeapodSpiderRender
 */
public class BlackTeapodSpiderRender extends MobRenderer<BlackTeapodSpider, TeapodSpiderModel<BlackTeapodSpider>> {

    public BlackTeapodSpiderRender(EntityRendererProvider.Context pContext) {
        super(pContext, new TeapodSpiderModel<>(pContext.bakeLayer(ModModelLayers.BLACK_TEAPOD_SPIDER_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(BlackTeapodSpider pEntity) {
        return new ResourceLocation(JigsawPpMod.MODID, "textures/entity/black_kettlespider.png");
    }

//  @Override
//  public void render(TeapodSpider pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
//          MultiBufferSource pBuffer, int pPackedLight) {
//      super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
//  }


}
