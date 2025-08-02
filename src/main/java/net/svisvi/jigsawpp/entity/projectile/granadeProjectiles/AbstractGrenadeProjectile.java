package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public abstract class AbstractGrenadeProjectile extends ThrowableItemProjectile{
    private int maxLifeTime = 80;
    private int lifeTime = 0;

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


    protected void explode() {
        this.discard();
    }

    @Override
    public void tick() {
        if(lifeTime >= maxLifeTime)
            explode();
        lifeTime++;
        super.tick();
    }
    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        if (this.level().isClientSide )
            return;

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
    
}
