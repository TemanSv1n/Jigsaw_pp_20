package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;

//Procedures
public class PurgenPiluleProjectile extends ThrowableItemProjectile implements ItemSupplier {
    public PurgenPiluleProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PurgenPiluleProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.PURGEN_PILULE_PROJECTILE.get(), pShooter, pLevel);
    }

    public PurgenPiluleProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.PURGEN_PILULE_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public PurgenPiluleProjectile(Level pLevel){
        super(ModEntities.PURGEN_PILULE_PROJECTILE.get(), pLevel);
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
        if (pResult.getEntity() instanceof LivingEntity ent) {
            this.getItem().getItem().finishUsingItem(this.getItem(), ent.level(), ent);
        }
//        PoopEffect.addEffectLiquidWay(pResult.getEntity(),(new MobEffectInstance(ModEffects.POOP.get(), 60, 1, false, false)));
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    protected Item getDefaultItem() {
        return ModItems.EMPTY_PILULE.get();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level() instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), 3, 0.2, 0.2, 0.2, 0);
        }

    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    public static Projectile shoot (Level pLevel, LivingEntity pEntity, float pVelocity, float pInaccuracy, ItemStack renderableItemStack){
        if (!pLevel.isClientSide) {
            PurgenPiluleProjectile thrownegg = new PurgenPiluleProjectile(pLevel, pEntity);
            thrownegg.setItem(renderableItemStack);
            thrownegg.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
            pLevel.addFreshEntity(thrownegg);
            return thrownegg;
        }
        return null;
    }


}