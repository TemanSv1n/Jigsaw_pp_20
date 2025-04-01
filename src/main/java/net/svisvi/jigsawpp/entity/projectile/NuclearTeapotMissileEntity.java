package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.tntpot.PrimedNuclearTeapot;
import net.svisvi.jigsawpp.entity.tntpot.PrimedTntPot;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import java.util.HashSet;
import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class NuclearTeapotMissileEntity extends AbstractArrow implements ItemSupplier {
    public NuclearTeapotMissileEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.NUCLEAR_TEAPOT_MISSILE_ENTITY.get(), world);
    }

    public NuclearTeapotMissileEntity(EntityType<? extends NuclearTeapotMissileEntity> type, Level world) {
        super(type, world);
    }

    public NuclearTeapotMissileEntity(EntityType<? extends NuclearTeapotMissileEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public NuclearTeapotMissileEntity(EntityType<? extends NuclearTeapotMissileEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(ModBlocks.NUCLEAR_TEAPOT.get());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BIG_NUCLEAR_TEAPOT.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        PrimedNuclearTeapot primedtnt = new PrimedNuclearTeapot(this.level(), (double)this.getX() + 0.5, (double)this.getY(), (double)this.getZ() + 0.5, null);
        primedtnt.setFuse(3);
        this.level().addFreshEntity(primedtnt);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        PrimedNuclearTeapot primedtnt = new PrimedNuclearTeapot(this.level(), (double)this.getX() + 0.5, (double)this.getY(), (double)this.getZ() + 0.5, null);
        primedtnt.setFuse(3);
        this.level().addFreshEntity(primedtnt);
        this.discard();
    }

    private static void spawnParticle(LevelAccessor level, Double x, Double y, Double z){
        Random rand = new Random();
        Double xd = x + rand.nextDouble(0.1, 0.6);
        Double yd = y + rand.nextDouble(0.1, 0.6);
        Double zd = z + rand.nextDouble(0.1, 0.6);
        level.addParticle(ParticleTypes.FLAME, xd, yd, zd, 0, 0, 0);
        level.addParticle(ParticleTypes.POOF, xd, yd, zd, 0, 0, 0);
    }
    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 5; i++) {
            spawnParticle(this.level(), this.getX(), this.getY(), this.getZ());
            spawnParticle(this.level(), this.getX(), this.getY(), this.getZ());
            spawnParticle(this.level(), this.getX(), this.getY(), this.getZ());
            spawnParticle(this.level(), this.getX(), this.getY(), this.getZ());
        }
    }

    public static NuclearTeapotMissileEntity shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        NuclearTeapotMissileEntity entityarrow = new NuclearTeapotMissileEntity(ModEntities.NUCLEAR_TEAPOT_MISSILE_ENTITY.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ghast.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static NuclearTeapotMissileEntity shoot(LivingEntity entity, LivingEntity target) {
        NuclearTeapotMissileEntity entityarrow = new NuclearTeapotMissileEntity(ModEntities.NUCLEAR_TEAPOT_MISSILE_ENTITY.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 0.5f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(1);
        entityarrow.setKnockback(1);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ghast.shoot")), SoundSource.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
        return entityarrow;
    }

}
