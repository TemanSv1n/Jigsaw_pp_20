package net.svisvi.jigsawpp.entity.gentleman.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieModel;
import net.svisvi.jigsawpp.entity.gentleman.GentleManEntity;
import net.svisvi.jigsawpp.entity.gentleman.GentleManWatchingEntity;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;

public abstract class AbstractGentleManWatchingRenderer <T extends GentleManWatchingEntity, M extends GentlemanModel<T>> extends HumanoidMobRenderer<T, M> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(JigsawPpMod.MODID,"textures/entity/gentleman.png");

    protected AbstractGentleManWatchingRenderer (EntityRendererProvider.Context pContext, M pModel, M pInnerModel, M pOuterModel) {
        super(pContext, pModel, 0.5F);
        this.addLayer(new HumanoidArmorLayer(this, pInnerModel, pOuterModel, pContext.getModelManager()));
    } 

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return TEXTURE;
    }
    
}
