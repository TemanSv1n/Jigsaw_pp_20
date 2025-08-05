package net.svisvi.jigsawpp.entity.projectile.granadeProjectiles;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.emitters.GasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PurgativeGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.gas.PurgativeGasClass;
import net.svisvi.jigsawpp.item.init.ModItems;

public class PurgenGrenadeProjectile extends AbstractGrenadeProjectile{
    public int amplifier = 0;

    @Override
    public Class<? extends GasEmitterEntity> getEmitterClass() {
        return PurgativeGasEmitterEntity.class;
    }

    @Override
    public GasEmitterEntity getEmitter() {
        GasEmitterEntity gasz = super.getEmitter();
        gasz.setGas(new PurgativeGasClass(new MobEffectInstance(ModEffects.PURGATIVE.get(), 60, this.getAmplifier())));
        return gasz;

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

    public int getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
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
