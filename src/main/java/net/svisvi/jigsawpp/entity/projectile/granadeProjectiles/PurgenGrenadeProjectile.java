package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public class PurgenGrenadeProjectile extends AbstractGrenadeProjectile{

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
        return ModItems.PURGEN_GRENADE.get(); 
    }

    @Override
    protected void explode() {
        super.explode();
    }
}
