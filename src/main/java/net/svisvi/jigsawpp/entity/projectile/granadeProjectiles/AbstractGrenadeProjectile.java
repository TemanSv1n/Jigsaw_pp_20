package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.entity.emitters.AbstractEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.EmitterUtils;
import net.svisvi.jigsawpp.entity.emitters.FartGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.GasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public abstract class AbstractGrenadeProjectile extends ThrowableItemProjectile{
    private int maxLifeTime = 80;
    private int lifeTime = 0;
    public boolean instaboom = false;
    public abstract Class<? extends GasEmitterEntity> getEmitterClass();

    public AbstractGrenadeProjectile(EntityType<? extends AbstractGrenadeProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public AbstractGrenadeProjectile(EntityType<? extends AbstractGrenadeProjectile> pEntityType, Level pLevel, int lifeTime, int maxLifeTime) {
        super(pEntityType, pLevel);
        this.maxLifeTime = maxLifeTime;
        this.lifeTime = lifeTime;
    }

    public AbstractGrenadeProjectile(EntityType<? extends AbstractGrenadeProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public AbstractGrenadeProjectile(EntityType<? extends AbstractGrenadeProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    public GasEmitterEntity getEmitter(){
        try {
            return EmitterUtils.createEmitter(this.getEmitterClass(), this.level(), this.getX(), this.getY(), this.getZ(), 2.5f, 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void explode() {
        try {
            GasEmitterEntity poopgas = this.getEmitter(); //new FartGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ());
            //poopgas.setDuration(600);
            if (this.getOwner() instanceof LivingEntity le) {
                poopgas.setOwner(le);
            }
            this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 0.5f, Level.ExplosionInteraction.NONE);
            this.level().addFreshEntity(poopgas);
            //ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY() , this.getZ(), new ItemStack(ModItems.GASSY_GRENADE_USED.get(), 1));
            //this.level().addFreshEntity(item);
            this.discard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return null;
    }

    @Override
    public void tick() {
        if(this.lifeTime >= this.maxLifeTime)
            this.explode();
        this.lifeTime++;
        super.tick();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (this.level().isClientSide )
            return;

        if (this.isInstaboom()){
            this.explode();
        }
        Direction hitDirection = blockHitResult.getDirection();
        Vec3 velocity = this.getDeltaMovement();

        double bounceFactor = 0.24; 

        double x = velocity.x / 1.4;
        double y = velocity.y / 1.4;
        double z = velocity.z / 1.4;
        switch (hitDirection.getAxis()) {
            case X:
            x = -x * bounceFactor;
            break;
            case Y:
            y = -y * bounceFactor;
            break;
            case Z:
            z = -z * bounceFactor;
            break;
        }
        this.setDeltaMovement(x, y, z);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (!(pResult.getEntity() instanceof AbstractEmitterEntity)) {
            pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
            this.explode();
        }
        super.onHitEntity(pResult);
    }

    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
    public int getLifeTime() {
        return lifeTime;
    }
    public void setInstaboom(boolean instaboom) {
        this.instaboom = instaboom;
    }
    public boolean isInstaboom() {
        return instaboom;
    }
}
