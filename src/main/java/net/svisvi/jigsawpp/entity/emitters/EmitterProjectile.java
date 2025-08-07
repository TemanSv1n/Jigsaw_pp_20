package net.svisvi.jigsawpp.entity.emitters;

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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import java.util.HashSet;
import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class EmitterProjectile extends AbstractArrow implements ItemSupplier {
    public AbstractEmitterEntity emitter;
    public float radius = 1f;

    public int maxLifeTime = 200;

    public void setMaxLifeTime(int maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }
    public int getMaxLifeTime() {
        return maxLifeTime;
    }
    public int getRemainingTime() {
        return (this.getMaxLifeTime() - this.tickCount);
    }

    public EmitterProjectile(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.EMITTER_PROJECTILE.get(), world);
    }

    public EmitterProjectile(EntityType<? extends EmitterProjectile> type, Level world) {
        super(type, world);
    }

    public EmitterProjectile(EntityType<? extends EmitterProjectile> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public EmitterProjectile(EntityType<? extends EmitterProjectile> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    public AbstractEmitterEntity getEmitter() {
        return emitter;
    }
    public void setEmitter(AbstractEmitterEntity hemitter) {
        hemitter.setDebugMode(true);
        hemitter.setAffectOwner(false);
        hemitter.setRadius(this.getRadius());
        hemitter.setDuration(400);
        if (this.getOwner() instanceof LivingEntity le) {
            hemitter.setOwner(le);
        }
        this.emitter = hemitter;
    }
    public void setEmitter(AbstractEmitterEntity hemitter, boolean defaultSetup) {
        if (defaultSetup){
            setEmitter(hemitter);
        } else {
            this.emitter = hemitter;
        }
    }

    public float getRadius() {
        return this.radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

//    @Override
//    protected void doPostHurtEffects(LivingEntity entity) {
//        super.doPostHurtEffects(entity);
//        entity.setArrowCount(entity.getArrowCount() - 1);
//    }

    @Override
    public void tick() {
        super.tick();
        if (this.getRemainingTime() <= 0){
            this.die();
        }

        //tickScript(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (this.getEmitter() != null){
            this.getEmitter().setPos(this.getX(), this.getY(), this.getZ());
            this.getEmitter().tick();
        }
        if (this.inGround)
            this.die();
    }

    public void die(){
        //githis.getEmitter().discard();
        this.discard();
    }

    public static EmitterProjectile shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        EmitterProjectile entityarrow = new EmitterProjectile(ModEntities.EMITTER_PROJECTILE.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ghast.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static EmitterProjectile shoot(LivingEntity entity, LivingEntity target) {
        EmitterProjectile entityarrow = new EmitterProjectile(ModEntities.EMITTER_PROJECTILE.get(), entity, entity.level());
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

    public static EmitterProjectile shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback, AbstractEmitterEntity emit, boolean default_emitter) {
        EmitterProjectile pr = shoot(world, entity, random, power, damage, knockback);
        pr.setEmitter(emit, default_emitter);
        return pr;
    }



    }
