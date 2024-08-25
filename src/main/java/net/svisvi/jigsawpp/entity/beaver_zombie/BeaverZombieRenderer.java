package net.svisvi.jigsawpp.entity.beaver_zombie;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;

/**
 * BeaverZombieRenderer
 */
public class BeaverZombieRenderer extends AbstractBeaverZombieRenderer<BeaverZombieEntity, BeaverZombieModel<BeaverZombieEntity>>{
  public BeaverZombieRenderer(EntityRendererProvider.Context context) {
      this(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
  }

   public BeaverZombieRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pZombieLayer, ModelLayerLocation pInnerArmor, ModelLayerLocation pOuterArmor) {
      super(pContext, new BeaverZombieModel(pContext.bakeLayer(pZombieLayer)), new BeaverZombieModel(pContext.bakeLayer(pInnerArmor)), new BeaverZombieModel(pContext.bakeLayer(pOuterArmor)));
   }
  
}
