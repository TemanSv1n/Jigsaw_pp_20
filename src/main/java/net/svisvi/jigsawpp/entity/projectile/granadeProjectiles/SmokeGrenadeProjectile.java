
package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.emitters.GasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.SmokeGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public class SmokeGrenadeProjectile extends AbstractGrenadeProjectile{

    @Override
    public Class<? extends GasEmitterEntity> getEmitterClass() {
        return SmokeGasEmitterEntity.class;
    }

    public SmokeGrenadeProjectile(EntityType<? extends SmokeGrenadeProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SmokeGrenadeProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.SMOKE_GRENADE_PROJECTILE.get(), pShooter, pLevel);
    }

    public SmokeGrenadeProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.SMOKE_GRENADE_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public SmokeGrenadeProjectile(Level pLevel){
        super(ModEntities.SMOKE_GRENADE_PROJECTILE.get(), pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SMOKE_GRENADE_USED.get();
    }

//    @Override
//    protected void explode() {
//        PoopGasEmitterEntity poopgas = new PoopGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ());
//        poopgas.setDuration(600);
//        this.level().addFreshEntity(poopgas);
//        ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY() , this.getZ(), new ItemStack(ModItems.PONOS_GRENADE_USED.get(), 1));
//        this.level().addFreshEntity(item);
//        super.explode();
//    }
}
