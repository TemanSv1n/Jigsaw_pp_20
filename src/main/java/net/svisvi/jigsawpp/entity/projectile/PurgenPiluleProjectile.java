package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

//Procedures
public class PurgenPiluleProjectile extends AbstractArrow implements ItemSupplier {

    public ItemStack PROJECTILE_ITEM = new ItemStack(ModItems.BASIC_PURGEN_PILULE.get());

    public PurgenPiluleProjectile(PlayMessages.SpawnEntity packet, Level world){
        super(ModEntities.PURGEN_PILULE_PROJECTILE.get(), world);
    }

    public PurgenPiluleProjectile(EntityType<? extends PurgenPiluleProjectile> type, Level world) {
        super(type, world);
    }

    public PurgenPiluleProjectile(EntityType<? extends PurgenPiluleProjectile> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public PurgenPiluleProjectile(EntityType<? extends PurgenPiluleProjectile> type, LivingEntity entity, Level world){
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

    public void setItem(ItemStack itemStack){
        this.PROJECTILE_ITEM = itemStack;
    }

    public void goyda(){
        System.out.println("GOYDA!!!");
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }




    //spawn particle
    @Override
    public void tick() {
        super.tick();
        //ExtinguisherProjectileParticle.particleSpawn(this.level(), this.getX(), this.getY(),this.getZ());
        if(this.inGround){this.discard();}
    }
    //extinguish fire
    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        //FireClear.clearFire(this.level(), pResult.getBlockPos().getX(), pResult.getBlockPos().getY(), pResult.getBlockPos().getZ(), 2);
        this.setSoundEvent(SoundEvents.COW_MILK);
        super.onHitBlock(pResult);
    }
    //projectile properties
    public static PurgenPiluleProjectile shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        PurgenPiluleProjectile entityarrow = new PurgenPiluleProjectile(ModEntities.PURGEN_PILULE_PROJECTILE.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(true);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.PLAYERS, 1, 0f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }
    public static PurgenPiluleProjectile shoot(LivingEntity entity, LivingEntity target) {
        PurgenPiluleProjectile entityarrow = new PurgenPiluleProjectile(ModEntities.PURGEN_PILULE_PROJECTILE.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(0);
        entityarrow.setKnockback(10);
        entityarrow.setCritArrow(true);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.PLAYERS, 1, 0f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}