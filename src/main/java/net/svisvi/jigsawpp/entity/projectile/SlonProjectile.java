package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.item.slon_gun.SlonGunItem;


public class SlonProjectile extends ThrowableItemProjectile implements ItemSupplier {
    public SlonProjectile(EntityType<?extends ThrowableItemProjectile> entityType, Level level){
        super(entityType, level);
    }
    public SlonProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.SLONGUN_PROJECTILE.get(), pShooter, pLevel);
    }
    public SlonProjectile(Level level, double x, double y , double z){
        super(ModEntities.SLONGUN_PROJECTILE.get(), x, y, z, level);
    }
    public SlonProjectile(Level level) {
        super(ModEntities.SLONGUN_PROJECTILE.get(), level);
    }
    protected Item getDefaultItem(){
        return ModItems.SLONGUN_COSTIL.get();
    }

    protected void onHit(HitResult result){
        super.onHit(result);
        if(!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.8D;
        }
    }

    private static void spawnParticle(LevelAccessor level, Double x, Double y, Double z){
        level.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
    }
    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 5; i++) {
            spawnParticle(this.level(), this.getX(), this.getY(), this.getZ());
        }
    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 2);

    }
    public static Projectile shoot (Level pLevel, LivingEntity pEntity, float pVelocity, float pInaccuracy){
        if (!pLevel.isClientSide) {
            SlonProjectile thrownegg = new SlonProjectile(pLevel, pEntity);
            thrownegg.shootFromRotation(pEntity, pEntity.getXRot(), pEntity.getYRot(), 0.0F, pVelocity, pInaccuracy);
            pLevel.addFreshEntity(thrownegg);
            return thrownegg;
        }
        return null;
    }


}
