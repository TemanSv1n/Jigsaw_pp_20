
package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public class PonosGrenadeProjectile extends AbstractGrenadeProjectile{

    public PonosGrenadeProjectile(EntityType<? extends PonosGrenadeProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PonosGrenadeProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.PONOS_GRENADE_PROJECTILE.get(), pShooter, pLevel);
    }

    public PonosGrenadeProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.PONOS_GRENADE_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public PonosGrenadeProjectile(Level pLevel){
        super(ModEntities.PONOS_GRENADE_PROJECTILE.get(), pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.PONOS_GRENADE_USED.get(); 
    }

    @Override
    protected void explode() {
        PoopGasEmitterEntity poopgas = new PoopGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ()); 
        poopgas.setDuration(600);
        this.level().addFreshEntity(poopgas);
        ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY() , this.getZ(), new ItemStack(ModItems.PONOS_GRENADE_USED.get(), 1));
        this.level().addFreshEntity(item);
        super.explode();
    }
}
