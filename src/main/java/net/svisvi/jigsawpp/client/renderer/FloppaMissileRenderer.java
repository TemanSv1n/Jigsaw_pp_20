
package net.mcreator.jigsawpp.client.renderer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import net.mcreator.jigsawpp.entity.FloppaMissileEntity;
import net.mcreator.jigsawpp.client.model.ModelFloppaMeteor;

public class FloppaMissileRenderer extends MobRenderer<FloppaMissileEntity, ModelFloppaMeteor<FloppaMissileEntity>> {
	public FloppaMissileRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelFloppaMeteor(context.bakeLayer(ModelFloppaMeteor.LAYER_LOCATION)), 0.5f);
	}

	@Override
	public ResourceLocation getTextureLocation(FloppaMissileEntity entity) {
		return new ResourceLocation("jigsaw_pp:textures/entities/floppa_missile.png");
	}
}
