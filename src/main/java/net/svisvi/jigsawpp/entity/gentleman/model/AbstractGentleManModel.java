package net.svisvi.jigsawpp.entity.gentleman.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public abstract class AbstractGentleManModel <T extends Monster> extends HumanoidModel<T> {
    
   protected AbstractGentleManModel(ModelPart pRoot) {
      super(pRoot);
   }

   public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
      super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
      // AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(pEntity), this.attackTime, pAgeInTicks);
   }

   // public abstract boolean isAggressive(T var1);
}
