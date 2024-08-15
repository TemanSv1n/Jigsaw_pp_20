package net.svisvi.jigsawpp.entity.beaverSpider;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;

/**
 * BeaverSpiderRenderer
 */
public class BeaverSpiderRenderer extends MobRenderer <BeaverSpiderEntity, BeaverSpiderModel<BeaverSpiderEntity>>{

  
	protected static final ResourceLocation Texture = new ResourceLocation(JigsawPpMod.MODID, "textures/entity/beaver_spider_model.png");
	public BeaverSpiderRenderer(Context context) {
		super(context, new BeaverSpiderModel<>(context.bakeLayer(ModModelLayers.BEAVER_SPIDER_LAYER)), 1f);
	}
	
	@Override
	public ResourceLocation getTextureLocation(BeaverSpiderEntity p_114482_) {
		return Texture;
	}
}
