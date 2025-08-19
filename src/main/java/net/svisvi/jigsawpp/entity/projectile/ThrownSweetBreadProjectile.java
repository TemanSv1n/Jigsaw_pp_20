package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

public class ThrownSweetBreadProjectile extends ThrowableItemProjectile {
    private boolean armor_piercing;

    public ThrownSweetBreadProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownSweetBreadProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.SWEET_BREAD.get(), pShooter, pLevel);
    }

    public ThrownSweetBreadProjectile(Level pLevel, LivingEntity pShooter, boolean pArmorPiercing) {
        super(ModEntities.SWEET_BREAD.get(), pShooter, pLevel);
        this.armor_piercing = pArmorPiercing;
    }

    public ThrownSweetBreadProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.SWEET_BREAD.get(), pX, pY, pZ, pLevel);
    }

    public ThrownSweetBreadProjectile(Level pLevel, double pX, double pY, double pZ, boolean pArmorPiercing) {
        super(ModEntities.SWEET_BREAD.get(), pX, pY, pZ, pLevel);
        this.armor_piercing = pArmorPiercing;
    }

    public ThrownSweetBreadProjectile(Level pLevel){
        super(ModEntities.SWEET_BREAD.get(), pLevel);
    }

    public boolean getArmorPiercing() { return armor_piercing; }

    public void setArmorPiercing(boolean pArmorPiercing) { this.armor_piercing = pArmorPiercing; };
    /**
     * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
        PoopEffect.addEffectLiquidWay(pResult.getEntity(),(new MobEffectInstance(ModEffects.POOP.get(), 60, 1, false, false)));
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    protected Item getDefaultItem() {
        return ModItems.SWEET_BREAD.get();
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
}
