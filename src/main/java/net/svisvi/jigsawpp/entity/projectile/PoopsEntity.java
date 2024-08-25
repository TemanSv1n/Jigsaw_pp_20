package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.*;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class PoopsEntity extends AbstractArrow implements ItemSupplier {
    public PoopsEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.POOPS.get(), world);
    }

    public PoopsEntity(EntityType<? extends PoopsEntity> type, Level world) {
        super(type, world);
    }

    public PoopsEntity(EntityType<? extends PoopsEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public PoopsEntity(EntityType<? extends PoopsEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(ModItems.POOPS.get());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.POOPS.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public void hit(Level world, BlockPos pos){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
                if (!world.isClientSide()) {
                    world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.NEUTRAL, 1, 1);
                } else {
                    world.playLocalSound(pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.NEUTRAL, 1, 1, false);
                }
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.HEART, x, y, z, 20, 1, 1, 1, 0.1);
            if (!world.isClientSide())
                world.explode(null, x, y, z, 4, Level.ExplosionInteraction.NONE);

            if (Math.random() < 0.2) {
                if (!world.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, new ItemStack(ModItems.POOPS.get()));
                    entityToSpawn.setPickUpDelay(10);
                    world.addFreshEntity(entityToSpawn);
                }
            }
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        //PoopsProjectileHitsLivingEntityProcedure.execute(this.level(), this.getX(), this.getY(), this.getZ(), entityHitResult.getEntity(), this, this.getOwner());
        hit(this.level(), entityHitResult.getEntity().getOnPos());
        entityHitResult.getEntity().hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.CRAMMING), this.getOwner(), this), 4);
    }

    @Override
    public void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        hit(this.level(), blockHitResult.getBlockPos());
       // PoopsProjectileHitsBlockProcedure.execute(this.level(), blockHitResult.getBlockPos().getX(), blockHitResult.getBlockPos().getY(), blockHitResult.getBlockPos().getZ());
    }

    @Override
    public void tick() {
        super.tick();
        double dis = 0;
        double speeed = 0;
        speeed = 0.5;
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Entity entity = this.getOwner();
        Entity immediatesourceentity = this;
        Level world = this.level();
        {
            final Vec3 _center = new Vec3(x, y, z);
            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (!(immediatesourceentity == entityiterator) && !(entity == entityiterator)) {
                    if (((Entity) world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 32, 32, 32), e -> true).stream().sorted(new Object() {
                        Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
                            return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
                        }
                    }.compareDistOf(x, y, z)).findFirst().orElse(null)) == entityiterator) {
                        dis = Math.sqrt(Math.pow(entityiterator.getX() - immediatesourceentity.getX(), 2) + Math.pow(entityiterator.getY() - immediatesourceentity.getY(), 2) + Math.pow(entityiterator.getZ() - immediatesourceentity.getZ(), 2));
                        immediatesourceentity.setDeltaMovement(new Vec3((((entityiterator.getX() - immediatesourceentity.getX()) / dis) * speeed), (((entityiterator.getY() - immediatesourceentity.getY()) / dis) * speeed),
                                (((entityiterator.getZ() - immediatesourceentity.getZ()) / dis) * speeed)));
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles(ParticleTypes.FLASH, x, y, z, 15, 0.7, 0.7, 0.7, 0.2);
                        if (world instanceof ServerLevel _level)
                            _level.sendParticles(ParticleTypes.SMOKE, x, y, z, 15, 0.7, 0.7, 0.7, 0.2);
                        if (!world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(new Vec3(x, y, z), 5, 5, 5), e -> true).isEmpty()) {
                            speeed = speeed * 1.2;
                        }
                    }
                }
            }
        }
        if (this.inGround)
            this.discard();
    }

    public static PoopsEntity shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        PoopsEntity entityarrow = new PoopsEntity(ModEntities.POOPS.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.no")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static PoopsEntity shoot(LivingEntity entity, LivingEntity target) {
        PoopsEntity entityarrow = new PoopsEntity(ModEntities.POOPS.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2.2f * 2, 12.0F);
        entityarrow.setSilent(true);
        entityarrow.setBaseDamage(10);
        entityarrow.setKnockback(0);
        entityarrow.setCritArrow(false);
        entity.level().addFreshEntity(entityarrow);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.no")), SoundSource.PLAYERS, 1, 1f / (new Random().nextFloat() * 0.5f + 1));
        return entityarrow;
    }
}
