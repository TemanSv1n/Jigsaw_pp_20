package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//Procedures
public class TreeProjectile extends ThrowableItemProjectile implements ItemSupplier {
    Item itemKeeper;
    public TreeProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public TreeProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.TREE_PROJECTILE.get(), pShooter, pLevel);
    }

    public TreeProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.TREE_PROJECTILE.get(), pX, pY, pZ, pLevel);
    }

    public TreeProjectile(Level pLevel){
        super(ModEntities.TREE_PROJECTILE.get(), pLevel);
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
//        if (pResult.getEntity() instanceof LivingEntity ent) {
//            if (PurgativeEffect.poopAdditionConditionLiquidWay(ent, new MobEffectInstance(ModEffects.PURGATIVE.get(), 21, 0))) {
//                this.getItem().getItem().finishUsingItem(this.getItem(), ent.level(), ent);
//            }
//        }
//        PoopEffect.addEffectLiquidWay(pResult.getEntity(),(new MobEffectInstance(ModEffects.POOP.get(), 60, 1, false, false)));
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    protected Item getDefaultItem() {
        return ModItems.EMPTY_PILULE.get();
    }
    public Item getItemKeeper(){return itemKeeper;}

    public void setItemKeeper(Item itemKeeper) {
        this.itemKeeper = itemKeeper;
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
//        autoAim(speeed, x, y, z, world, this, this.getOwner());



    }


    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
    @Override
    public void onHitBlock(BlockHitResult pResult){
        BlockPos FitPos = pResult.getBlockPos();
        BlockPos hitPos = pResult.getBlockPos();
        if (this.level().getBlockState(FitPos).canOcclude() && (this.level().getBlockState(FitPos.above())).isAir()) {
            hitPos = FitPos;
        } else if (this.level().getBlockState(FitPos.above(1)).canOcclude() && (this.level().getBlockState(FitPos.above(2))).isAir()){
            hitPos = FitPos.above();
        } else if (this.level().getBlockState(FitPos.below(1)).canOcclude() && (this.level().getBlockState(FitPos.below(2))).isAir()) {
            hitPos = FitPos.below();
        }
        if (this.level().getBlockState(hitPos).canOcclude() && (this.level().getBlockState(hitPos.above())).isAir()) {
            this.level().setBlock(hitPos.above(), Block.byItem(this.getItemKeeper()).defaultBlockState(), 3);
//            System.out.println(Block.byItem(this.getItemKeeper()).defaultBlockState().toString()); //EDGE
            for (int index0 = 0; index0 < (int) (16); index0++) {
                BlockPos _bp = hitPos.above();
                if (BoneMealItem.growCrop(new ItemStack(Items.BONE_MEAL), this.level(), _bp) || BoneMealItem.growWaterPlant(new ItemStack(Items.BONE_MEAL), this.level(), _bp, null)) {
                    if (!this.level().isClientSide())
                        this.level().levelEvent(2005, _bp, 0);
                }
            }
            if (this.level() instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.POOF, this.getOnPos().getX(), this.getOnPos().getY(), this.getOnPos().getZ(), 50, 2, 2, 2, 1);
        } else {
            if (!this.level().isClientSide())
                this.level().explode(null, this.getOnPos().getX(), this.getOnPos().getY(), this.getOnPos().getZ(), 1, Level.ExplosionInteraction.NONE);
            if (this.level() instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.HEART, this.getOnPos().getX(), this.getOnPos().getY(), this.getOnPos().getZ(), 50, 2, 2, 2, 1);
        }

    }

    public static Projectile shoot (Level pLevel, LivingEntity pEntity, float pVelocity, float pInaccuracy, ItemStack renderableItemStack){
        if (!pLevel.isClientSide) {
            TreeProjectile thrownegg = new TreeProjectile(pLevel, pEntity);
            thrownegg.setItem(renderableItemStack);
            thrownegg.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
            pLevel.addFreshEntity(thrownegg);
            return thrownegg;
        }
        return null;
    }



    public void setItem(ItemStack pStack) {
        super.setItem(pStack);
        setItemKeeper(pStack.getItem());

    }
}