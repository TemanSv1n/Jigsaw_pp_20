package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;
//Procedures
import net.svisvi.jigsawpp.procedures.ut.FireClear;
import net.svisvi.jigsawpp.procedures.ExtinguisherProjectileParticle;
public class ExtinguisherProjectile extends AbstractArrow implements ItemSupplier {

    public static final ItemStack PROJECTILE_ITEM = new ItemStack(Blocks.FIRE);

    public ExtinguisherProjectile(PlayMessages.SpawnEntity packet, Level world){
        super(ModEntities.EXTINGUISHER_PROJECTILE.get(), world);
    }

    public ExtinguisherProjectile(EntityType<? extends ExtinguisherProjectile> type, Level world) {
        super(type, world);
    }

    public ExtinguisherProjectile(EntityType<? extends  ExtinguisherProjectile> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ExtinguisherProjectile(EntityType<? extends ExtinguisherProjectile> type, LivingEntity entity, Level world){
        super(type, entity, world);
    }



    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }

    @Override
    public ItemStack getItem() {
        return PROJECTILE_ITEM;
    }

    @Override
    protected ItemStack getPickupItem() {
        return PROJECTILE_ITEM;
    }




    //spawn particle
    @Override
    public void tick() {
        super.tick();
        ExtinguisherProjectileParticle.particleSpawn(this.level(), this.getX(), this.getY(),this.getZ());
        if ((this.level().getBlockState(BlockPos.containing(this.getBlockX(), this.getBlockY(), this.getBlockZ()))).getBlock() == Blocks.FIRE) {
            if (this.level() instanceof ServerLevel _level) {
                FireClear.clearFire(this.level(), this.getBlockX(), this.getBlockY(), this.getBlockZ(), 3);
                this.discard();
            }
        }

        if(this.inGround){this.discard();}
    }
    //extinguish fire
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        FireClear.clearFire(this.level(), pResult.getBlockPos().getX(), pResult.getBlockPos().getY(), pResult.getBlockPos().getZ(), 3);
        this.setSoundEvent(SoundEvents.LAVA_EXTINGUISH);
        super.onHitBlock(pResult);
    }
    //projectile properties
    public static ExtinguisherProjectile shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        ExtinguisherProjectile entityarrow = new ExtinguisherProjectile(ModEntities.EXTINGUISHER_PROJECTILE.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(true);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }
    public static ExtinguisherProjectile shoot(LivingEntity entity, LivingEntity target) {
        ExtinguisherProjectile entityarrow = new ExtinguisherProjectile(ModEntities.EXTINGUISHER_PROJECTILE.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(0);
        entityarrow.setKnockback(10);
        entityarrow.setCritArrow(true);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}

