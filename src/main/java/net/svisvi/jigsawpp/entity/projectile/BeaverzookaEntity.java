package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import java.util.HashSet;
import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class BeaverzookaEntity extends AbstractArrow implements ItemSupplier {
    public BeaverzookaEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.BEAVERZOOKA_ENTITY.get(), world);
    }

    public BeaverzookaEntity(EntityType<? extends BeaverzookaEntity> type, Level world) {
        super(type, world);
    }

    public BeaverzookaEntity(EntityType<? extends BeaverzookaEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public BeaverzookaEntity(EntityType<? extends BeaverzookaEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return new ItemStack(ModBlocks.BEAWEED_BLOCK.get());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.BEAVER_AMMO.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    @Override
    public void tick() {
        super.tick();
        tickScript(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (this.inGround)
            this.discard();
    }

    public static BeaverzookaEntity shoot(Level world, LivingEntity entity, Random random, float power, double damage, int knockback) {
        BeaverzookaEntity entityarrow = new BeaverzookaEntity(ModEntities.BEAVERZOOKA_ENTITY.get(), entity, world);
        entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        entityarrow.setSilent(true);
        entityarrow.setCritArrow(false);
        entityarrow.setBaseDamage(damage);
        entityarrow.setKnockback(knockback);
        world.addFreshEntity(entityarrow);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ghast.shoot")), SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return entityarrow;
    }

    public static BeaverzookaEntity shoot(LivingEntity entity, LivingEntity target) {
        BeaverzookaEntity entityarrow = new BeaverzookaEntity(ModEntities.BEAVERZOOKA_ENTITY.get(), entity, entity.level());
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

    public void tickScript(Level world, double x, double y, double z, Entity immediatesourceentity){
        BlockPos pos = new BlockPos((int)x,(int)y,(int)z);
        if (immediatesourceentity == null)
            return;
        boolean found = false;
        int sx = 0;
        int sy = 0;
        int sz = 0;
        double despawn = 0;
        found = false;
        if (despawn >= 20) {
            if (!immediatesourceentity.level().isClientSide())
                immediatesourceentity.discard();
        }
        new Object() {
            private int ticks = 0;
            private float waitTicks;
            private LevelAccessor world;

            public void start(LevelAccessor world, int waitTicks) {
                this.waitTicks = waitTicks;
                MinecraftForge.EVENT_BUS.register(this);
                this.world = world;
            }

            @SubscribeEvent
            public void tick(TickEvent.ServerTickEvent event) {
                if (event.phase == TickEvent.Phase.END) {
                    this.ticks += 1;
                    if (this.ticks >= this.waitTicks)
                        run();
                }
            }

            private void run() {
                if (!immediatesourceentity.level().isClientSide())
                    immediatesourceentity.discard();
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }.start(world, 110);
        immediatesourceentity.setNoGravity((true));
        if (world instanceof ServerLevel _level)
            _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP.get()), x, y, z, 5, 0.1, 0.1, 0.1, 0);
        sx = -3;
        found = false;
        for (int index0 = 0; index0 < (int) (6); index0++) {
            sy = -3;
            for (int index1 = 0; index1 < (int) (6); index1++) {
                sz = -3;
                for (int index2 = 0; index2 < (int) (6); index2++) {
                    if (breakBlock(world, new BlockPos(pos.getX()+sx, pos.getY()+sy, pos.getZ()+sz))){
                        despawn++;
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }

    }
    public static HashSet<TagKey<Block>> notReplaceableTags = new HashSet<TagKey<Block>>();
    static {
        notReplaceableTags.add(BlockTags.create(new ResourceLocation("forge:barrels/wooden")));
        notReplaceableTags.add(BlockTags.create(new ResourceLocation("forge:chests/wooden")));
    }
    public static HashSet<SoundType> replaceableSoundTypes = new HashSet<SoundType>();
    static {
        replaceableSoundTypes.add(SoundType.VINE);
        replaceableSoundTypes.add(SoundType.AZALEA);
        replaceableSoundTypes.add(SoundType.AZALEA_LEAVES);
        replaceableSoundTypes.add(SoundType.BAMBOO);
        replaceableSoundTypes.add(SoundType.BAMBOO_SAPLING);
        replaceableSoundTypes.add(SoundType.BAMBOO_WOOD_HANGING_SIGN);
        replaceableSoundTypes.add(SoundType.BAMBOO_WOOD);
        replaceableSoundTypes.add(SoundType.BIG_DRIPLEAF);
        replaceableSoundTypes.add(SoundType.CAVE_VINES);
        replaceableSoundTypes.add(SoundType.CHERRY_LEAVES);
        replaceableSoundTypes.add(SoundType.CHERRY_SAPLING);
        replaceableSoundTypes.add(SoundType.CHERRY_WOOD);
        replaceableSoundTypes.add(SoundType.CHERRY_WOOD_HANGING_SIGN);
        replaceableSoundTypes.add(SoundType.FLOWERING_AZALEA);
        replaceableSoundTypes.add(SoundType.HANGING_ROOTS);
        replaceableSoundTypes.add(SoundType.LADDER);
        replaceableSoundTypes.add(SoundType.LILY_PAD);
        replaceableSoundTypes.add(SoundType.MANGROVE_ROOTS);
        replaceableSoundTypes.add(SoundType.NETHER_WOOD);
        replaceableSoundTypes.add(SoundType.NETHER_WOOD_HANGING_SIGN);
        replaceableSoundTypes.add(SoundType.ROOTS);
        replaceableSoundTypes.add(SoundType.SCAFFOLDING);
        replaceableSoundTypes.add(SoundType.SMALL_DRIPLEAF);
        replaceableSoundTypes.add(SoundType.TWISTING_VINES);
        replaceableSoundTypes.add(SoundType.WEEPING_VINES);
        replaceableSoundTypes.add(SoundType.WOOD);
    }

    public static HashSet<TagKey<Block>> replaceableTags = new HashSet<TagKey<Block>>();
    static {
        replaceableTags.add(BlockTags.PLANKS);
        replaceableTags.add(BlockTags.WOODEN_BUTTONS);
        replaceableTags.add(BlockTags.WOODEN_DOORS);
        replaceableTags.add(BlockTags.WOODEN_STAIRS);
        replaceableTags.add(BlockTags.WOODEN_SLABS);
        replaceableTags.add(BlockTags.WOODEN_FENCES);
        replaceableTags.add(BlockTags.WOODEN_PRESSURE_PLATES);
        replaceableTags.add(BlockTags.WOODEN_TRAPDOORS);
        replaceableTags.add(BlockTags.SAPLINGS);
        replaceableTags.add(BlockTags.LOGS_THAT_BURN);
        replaceableTags.add(BlockTags.OVERWORLD_NATURAL_LOGS);
        replaceableTags.add(BlockTags.LOGS);
        replaceableTags.add(BlockTags.DARK_OAK_LOGS);
        replaceableTags.add(BlockTags.OAK_LOGS);
        replaceableTags.add(BlockTags.BIRCH_LOGS);
        replaceableTags.add(BlockTags.ACACIA_LOGS);
        replaceableTags.add(BlockTags.CHERRY_LOGS);
        replaceableTags.add(BlockTags.JUNGLE_LOGS);
        replaceableTags.add(BlockTags.SPRUCE_LOGS);
        replaceableTags.add(BlockTags.MANGROVE_LOGS);
        replaceableTags.add(BlockTags.BAMBOO_BLOCKS);
        replaceableTags.add(BlockTags.LEAVES);

    }

    public boolean breakBlock(Level world, BlockPos pos){
        BlockState state = world.getBlockState(pos);
        if (!notReplaceableTags.stream().anyMatch(state::is)) {
            if (replaceableTags.stream().anyMatch(state::is)
            || replaceableSoundTypes.stream().anyMatch(soundType -> soundType == state.getSoundType())){

                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                if (!world.isClientSide()) {
                    world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.step")), SoundSource.BLOCKS, 1, 1);
                } else {
                    world.playLocalSound(pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wood.step")), SoundSource.BLOCKS, 1, 1, false);
                }
                if (world instanceof ServerLevel _level)
                    _level.sendParticles(ParticleTypes.CRIT, pos.getX(), pos.getY(), pos.getZ(), 2, 0.1, 0.1, 0.1, 0);
            return true;
            }

        }
        return false;
    }

}
