package net.svisvi.jigsawpp.entity.projectile.floppa_missile;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.SlonProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.particles.ModParticles;

public class SlonGunGreenProjectile extends ThrowableItemProjectile implements ItemSupplier {
    public SlonGunGreenProjectile(EntityType<?extends ThrowableItemProjectile> entityType, Level level){
        super(entityType, level);
    }

    private static int damage = 1;
    public SlonGunGreenProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.SLONGUN_GREEN_PROJECTILE.get(), pShooter, pLevel);
    }
    public SlonGunGreenProjectile(Level level, double x, double y , double z){
        super(ModEntities.SLONGUN_GREEN_PROJECTILE.get(), x, y, z, level);
    }
    public SlonGunGreenProjectile(Level level) {
        super(ModEntities.SLONGUN_GREEN_PROJECTILE.get(), level);
    }
    protected Item getDefaultItem(){
        return ModItems.SLONGUN_COSTIL.get();
    }

    protected void onHit(HitResult result){
        super.onHit(result);
        if(!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.8D;
        }
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    private static void spawnParticle(LevelAccessor level, Double x, Double y, Double z){
        level.addParticle(ModParticleTypes.POOP.get(), x, y, z, 0, 0, 0);
    }
    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 5; i++) {
            spawnParticle(this.level(), this.getX(), this.getY(), this.getZ());
        }
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), damage);
        PoopEffect.addEffectLiquidWay(pResult.getEntity(),(new MobEffectInstance(ModEffects.POOP.get(), 120, 1, false, false)));
        //PurgativeEffect.addEffectLiquidWay(pResult.getEntity(), (new MobEffectInstance(ModEffects.PURGATIVE.get(), 40, 1, false, false)));
    }
    public static Projectile shoot (Level pLevel, LivingEntity pEntity, float pVelocity, float pInaccuracy){
        if (!pLevel.isClientSide) {
            SlonGunGreenProjectile thrownegg = new SlonGunGreenProjectile(pLevel, pEntity);
            thrownegg.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
            pLevel.addFreshEntity(thrownegg);
            return thrownegg;
        }
        return null;
    }


}
