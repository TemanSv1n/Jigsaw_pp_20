package net.svisvi.jigsawpp.entity.stone_beaver;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;


public class StoneBeaverRenderer extends MobRenderer<StoneBeaverEntity, StoneBeaverModel<StoneBeaverEntity>> {
    public StoneBeaverRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneBeaverModel(context.bakeLayer(ModModelLayers.STONE_BEAVER_LAYER)), 1f);
        this.addLayer(new EyesLayer<StoneBeaverEntity, StoneBeaverModel<StoneBeaverEntity>>(this) {
            @Override
            public RenderType renderType() {
                return RenderType.eyes(new ResourceLocation("jigsaw_pp:textures/entity/stone_beaver_2.png"));
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(StoneBeaverEntity entity) {
        return new ResourceLocation("jigsaw_pp:textures/entity/stone_beaver.png");
    }
}
