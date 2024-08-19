package net.svisvi.jigsawpp.entity.beaver_zombie;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;

/**
 * AbstractBeaverZombieRenderer
 */
public class AbstractBeaverZombieRenderer <T extends BeaverZombieEntity, M extends BeaverZombieModel<T>> extends HumanoidMobRenderer<T, M>{
   private static final ResourceLocation TEXTURE = new ResourceLocation(JigsawPpMod.MODID,"textures/entity/zombie_beaver.png");

   protected AbstractBeaverZombieRenderer(EntityRendererProvider.Context pContext, M pModel, M pInnerModel, M pOuterModel) {
      super(pContext, pModel, 0.5F);
      this.addLayer(new HumanoidArmorLayer(this, pInnerModel, pOuterModel, pContext.getModelManager()));
   }


   protected boolean isShaking(T pEntity) {
      return super.isShaking(pEntity) || pEntity.isUnderWaterConverting();
   }

  @Override
  public ResourceLocation getTextureLocation(T pEntity) {
    return TEXTURE;
  }

  
}
