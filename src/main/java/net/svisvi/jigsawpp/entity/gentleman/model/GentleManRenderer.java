package net.svisvi.jigsawpp.entity.gentleman.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.svisvi.jigsawpp.entity.gentleman.GentleManEntity;

public class GentleManRenderer extends AbstractGentleManRenderer<GentleManEntity, GentlemanModel<GentleManEntity>> {

    public GentleManRenderer(EntityRendererProvider.Context context) {
        this(context, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public GentleManRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pZombieLayer, ModelLayerLocation pInnerArmor, ModelLayerLocation pOuterArmor) {
        super(pContext, new GentlemanModel(pContext.bakeLayer(pZombieLayer)), new GentlemanModel(pContext.bakeLayer(pInnerArmor)), new GentlemanModel(pContext.bakeLayer(pOuterArmor)));
    }
    
}
