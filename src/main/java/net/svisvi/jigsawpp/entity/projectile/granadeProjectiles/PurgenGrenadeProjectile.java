package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.emitters.GasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PurgativeGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public class PurgenGrenadeProjectile extends AbstractGrenadeProjectile{

    @Override
    public Class<? extends GasEmitterEntity> getEmitterClass() {
        return PurgativeGasEmitterEntity.class;
    }

    public PurgenGrenadeProjectile(EntityType<? extends PurgenGrenadeProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PurgenGrenadeProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.PURGEN_GRENADE_PROJECTILE.get(), pShooter, pLevel);
    }

    public PurgenGrenadeProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.PURGEN_GRENADE_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public PurgenGrenadeProjectile(Level pLevel){
        super(ModEntities.PURGEN_GRENADE_PROJECTILE.get(), pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PURGEN_GRENADE_USED.get(); 
    }

//    @Override
//    protected void explode() {
//        PurgativeGasEmitterEntity poopgas = new PurgativeGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ());
//        poopgas.setDuration(300);
//        this.level().addFreshEntity(poopgas);
//        ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY() , this.getZ(), new ItemStack(ModItems.PURGEN_GRENADE_USED.get(), 1));
//        this.level().addFreshEntity(item);
//        super.explode();
//    }
}
