package net.svisvi.jigsawpp.entity.beaver_zombie;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;

/**
 * BeaverZombieModel
 */
public class BeaverZombieModel<T extends BeaverZombieEntity> extends AbstractBeaverZombieModel<T> {
  public BeaverZombieModel(ModelPart pRoot) {
      super(pRoot);
  }


  @Override
  public boolean isAggressive(T var1) {
      return var1.isAggressive();
  }
  
}
