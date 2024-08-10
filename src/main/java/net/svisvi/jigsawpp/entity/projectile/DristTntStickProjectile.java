package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;

public class DristTntStickProjectile extends ThrowableItemProjectile {
    public DristTntStickProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DristTntStickProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.DRIST_TNT_STICK.get(), pShooter, pLevel);
    }

    public DristTntStickProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.DRIST_TNT_STICK.get(), pX, pY, pZ, pLevel);
    }

    public DristTntStickProjectile(Level pLevel){
        super(ModEntities.DRIST_TNT_STICK.get(), pLevel);
    }

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
//        PoopEffect.addEffectLiquidWay(pResult.getEntity(),(new MobEffectInstance(ModEffects.POOP.get(), 60, 1, false, false)));

    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    protected Item getDefaultItem() {
        return ModItems.DRIST_TNT_STICK.get();
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {

            this.level().broadcastEntityEvent(this, (byte)3);
            DristExplosion.harmfulDristExplode(this.level(), new BlockPos((int)pResult.getLocation().x, (int)pResult.getLocation().y, (int)pResult.getLocation().z), 2, Level.ExplosionInteraction.NONE, (LivingEntity)this.getOwner());
            this.discard();
        }

    }
}