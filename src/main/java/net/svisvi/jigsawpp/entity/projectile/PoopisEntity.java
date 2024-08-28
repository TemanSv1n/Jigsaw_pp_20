package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;

import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class PoopisEntity extends PoopsEntity{
    public PoopisEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.POOPIS.get(), world);
    }

    public PoopisEntity(EntityType<? extends PoopisEntity> type, Level world) {
        super(type, world);
    }

    public PoopisEntity(EntityType<? extends PoopisEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public PoopisEntity(EntityType<? extends PoopisEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(ModItems.POOPIS.get());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.POOPIS.get());
    }

    @Override
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
            _level.sendParticles(ModParticleTypes.POOP_BUBBLE.get(), x, y, z, 20, 1, 1, 1, 0.1);
        if (!world.isClientSide())
            DristExplosion.harmfulDristExplode(world, pos, 4, Level.ExplosionInteraction.NONE, null);

        if (Math.random() < 0.2) {
            if (!world.isClientSide()) {
                ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, new ItemStack(ModItems.POOPIS.get()));
                entityToSpawn.setPickUpDelay(10);
                world.addFreshEntity(entityToSpawn);
            }
        }
    }

    public static PoopisEntity shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        PoopisEntity entityarrow = new PoopisEntity(ModEntities.POOPIS.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.no")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static PoopisEntity shoot(LivingEntity entity, LivingEntity target) {
        PoopisEntity entityarrow = new PoopisEntity(ModEntities.POOPIS.get(), entity, entity.level());
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
