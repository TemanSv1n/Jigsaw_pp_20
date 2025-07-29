package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//Procedures
public class PurgenPiluleProjectile extends ThrowableItemProjectile implements ItemSupplier {

    public boolean armor_piercing;

    public PurgenPiluleProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PurgenPiluleProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.PURGEN_PILULE_PROJECTILE.get(), pShooter, pLevel);
    }
    public PurgenPiluleProjectile(Level pLevel, LivingEntity pShooter, boolean ap) {
        super(ModEntities.PURGEN_PILULE_PROJECTILE.get(), pShooter, pLevel);
        armor_piercing = ap;
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

    public void setArmor_piercing(boolean armor_piercing) {
        this.armor_piercing = armor_piercing;
    }

    public boolean getArmor_piercing(){return armor_piercing;}

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
        if (pResult.getEntity() instanceof LivingEntity ent) {
            if (PurgativeEffect.poopAdditionConditionLiquidWay(ent, new MobEffectInstance(ModEffects.PURGATIVE.get(), 21, 0)) || this.armor_piercing) {
                this.getItem().getItem().finishUsingItem(this.getItem(), ent.level(), ent);
            }
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
            _level.sendParticles(ParticleTypes.ASH, this.getX(), this.getY(), this.getZ(), 3, 0.2, 0.2, 0.2, 0);
        }

        if (this.getOwner() == null || this == null){
            return;}



        double speeed = 0.5;
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Level world = this.level();
        autoAim(speeed, x, y, z, world, this, this.getOwner());



    }

    public void autoAim(double speed, double x, double y, double z, Level world, Projectile immediatesourceentity, Entity entity){
        double dis = 0;
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (!(immediatesourceentity == entityiterator) && !(entity == entityiterator)) {
                    if (((Entity) world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 3, 3, 3), e -> true).stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
                        }
                    }.compareDistOf(x, y, z)).findFirst().orElse(null)) == entityiterator) {
                        dis = Math.sqrt(Math.pow(entityiterator.getX() - immediatesourceentity.getX(), 2) + Math.pow(entityiterator.getY() - immediatesourceentity.getY(), 2) + Math.pow(entityiterator.getZ() - immediatesourceentity.getZ(), 2));
                        immediatesourceentity.setDeltaMovement(new Vec3((((entityiterator.getX() - immediatesourceentity.getX()) / dis) * speed), (((entityiterator.getY() - immediatesourceentity.getY()) / dis) * speed),
                                (((entityiterator.getZ() - immediatesourceentity.getZ()) / dis) * speed)));
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles(ParticleTypes.SMOKE, x, y, z, 15, 0.7, 0.7, 0.7, 0.2);
                        if (!world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 5, 5, 5), e -> true).isEmpty()) {
                            speed = speed * 1.2;
                        }
                    }
                }
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
        return shoot(pLevel, pEntity, pVelocity, pInaccuracy, renderableItemStack, false);
    }

    public static Projectile shoot (Level pLevel, LivingEntity pEntity, float pVelocity, float pInaccuracy, ItemStack renderableItemStack, boolean ap){
        if (!pLevel.isClientSide) {
            PurgenPiluleProjectile thrownegg = new PurgenPiluleProjectile(pLevel, pEntity, ap);
            thrownegg.setItem(renderableItemStack);
            thrownegg.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
            pLevel.addFreshEntity(thrownegg);
            return thrownegg;
        }
        return null;
    }



    public void setItem(ItemStack pStack) {
        super.setItem(pStack);

    }
}