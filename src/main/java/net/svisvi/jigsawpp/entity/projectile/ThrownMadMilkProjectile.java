package net.svisvi.jigsawpp.entity.projectile;

import org.joml.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.commands.SayCommand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.procedures.MadMilkUt;

/**
 * ThrownMadMilkProjectile
 */
public class ThrownMadMilkProjectile extends ThrowableItemProjectile{
  public ThrownMadMilkProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
  }

  public ThrownMadMilkProjectile(Level pLevel, LivingEntity pShooter) {
    super(ModEntities.MAD_MILK_PROJECTILE.get(), pShooter, pLevel);
  }

  public ThrownMadMilkProjectile(Level pLevel, double pX, double pY, double pZ) {
    super(ModEntities.MAD_MILK_PROJECTILE.get(), pX, pY, pZ, pLevel);
  }
  public ThrownMadMilkProjectile(Level pLevel){
    super(ModEntities.MAD_MILK_PROJECTILE.get(), pLevel);
  }
  public static Projectile shoot (Level pLevel, LivingEntity pEntity, float pVelocity, float pInaccuracy){
    if(!pLevel.isClientSide) {
      ThrownMadMilkProjectile thrownegg = new ThrownMadMilkProjectile(pLevel, pEntity);
        thrownegg.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
        pLevel.addFreshEntity(thrownegg);
        return thrownegg;
    }
    return null;
  }
 
  @Override
  protected void onHit(HitResult pResult) {
    Vec3 pos = pResult.getLocation();
    this.discard();
    if(!this.level().isClientSide()){
    MadMilkUt.exec(pos, this.level());
    }
  }

  @Override
  protected Item getDefaultItem() {
    return ModItems.MAD_MILK.get();
  }  
}
