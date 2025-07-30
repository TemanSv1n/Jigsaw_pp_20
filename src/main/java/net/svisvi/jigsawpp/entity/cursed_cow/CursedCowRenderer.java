package net.svisvi.jigsawpp.entity.cursed_cow;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.CowModel;
import net.svisvi.jigsawpp.JigsawPpMod;


public class CursedCowRenderer extends MobRenderer<CursedCowEntity, CowModel<CursedCowEntity>> {
    public CursedCowRenderer(EntityRendererProvider.Context context) {
        super(context, new CowModel<CursedCowEntity>(context.bakeLayer(ModelLayers.COW)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CursedCowEntity entity) {
        return new ResourceLocation(JigsawPpMod.MODID, "textures/entity/cursed_cow.png");
    }
}