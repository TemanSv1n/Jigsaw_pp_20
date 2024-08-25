package net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;

/**
 * BeaverSpiderRenderer
 */
public class BeaverZombieSpawnerRenderer extends MobRenderer <BeaverZombieSpawner, BeaverZombieSpawnerModel<BeaverZombieSpawner>>{

  
	protected static final ResourceLocation Texture = new ResourceLocation(JigsawPpMod.MODID, "textures/entity/beaver_zombie_spawner.png");
	public BeaverZombieSpawnerRenderer(Context context) {
		super(context, new BeaverZombieSpawnerModel<>(context.bakeLayer(ModModelLayers.BEAVER_ZOMBIE_SPAWNER)), 1f);
	}
  @Override
  public ResourceLocation getTextureLocation(BeaverZombieSpawner pEntity) {
    // TODO Auto-generated method stub
    return Texture;
  }
	
}
