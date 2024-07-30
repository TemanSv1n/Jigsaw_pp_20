package net.svisvi.jigsawpp.block.entity;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net. minecraft. world. level. block. entity. BlockEntity;
import net.svisvi.jigsawpp.block.lenin_bust.LeninBustBlock;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LeninBustBlockEntity extends BeehiveBlockEntity {
    public static final String TAG_FLOWER_POS = "FlowerPos";
    public static final String MIN_OCCUPATION_TICKS = "MinOccupationTicks";
    public static final String ENTITY_DATA = "EntityData";
    public static final String TICKS_IN_HIVE = "TicksInHive";
    public static final String HAS_NECTAR = "HasNectar";
    public static final String BEES = "Bees";
    private static final List<String> IGNORED_BEE_TAGS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID");
    public static final int MAX_OCCUPANTS = 3;
    private static final int MIN_TICKS_BEFORE_REENTERING_HIVE = 400;
    private static final int MIN_OCCUPATION_TICKS_NECTAR = 2400;
    public static final int MIN_OCCUPATION_TICKS_NECTARLESS = 600;
    private final List<LeninBustBlockEntity.BeeData> stored = Lists.newArrayList();
    @Nullable
    private BlockPos savedFlowerPos;

    public LeninBustBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState);
    }
    @Override
    public BlockEntityType<?> getType() {
        return ModBlockEntities.LENIN_BUST_BE.get();
    }

    public static int getHoneyLevel(BlockState pState) {
        return (Integer)pState.getValue(LeninBustBlock.HONEY_LEVEL);
    }

    @Override
    public List<Entity> releaseAllOccupants(BlockState pState, BeeReleaseStatus pReleaseStatus) {
        List<Entity> $$2 = Lists.newArrayList();
        this.stored.removeIf((p_272556_) -> {
            return releaseOccupant(this.level, this.worldPosition, pState, p_272556_, $$2, pReleaseStatus, this.savedFlowerPos);
        });
        if (!$$2.isEmpty()) {
            super.setChanged();
        }

        return $$2;
    }


    public static void tickOccupants(Level pLevel, BlockPos pPos, BlockState pState, List<BeeData> pData, @Nullable BlockPos pSavedFlowerPos) {
        boolean $$5 = false;

        BeeData $$7;
        for(Iterator<LeninBustBlockEntity.BeeData> $$6 = pData.iterator(); $$6.hasNext(); ++$$7.ticksInHive) {
            $$7 = (BeeData)$$6.next();
            if ($$7.ticksInHive > $$7.minOccupationTicks) {
                BeeReleaseStatus $$8 = $$7.entityData.getBoolean("HasNectar") ? LeninBustBlockEntity.BeeReleaseStatus.HONEY_DELIVERED : LeninBustBlockEntity.BeeReleaseStatus.BEE_RELEASED;
                if (releaseOccupant(pLevel, pPos, pState, $$7, (List)null, $$8, pSavedFlowerPos)) {
                    $$5 = true;
                    $$6.remove();
                }
            }
        }

        if ($$5) {
            setChanged(pLevel, pPos, pState);
        }

    }
    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, LeninBustBlockEntity pBeehive) {
        tickOccupants(pLevel, pPos, pState, pBeehive.stored, pBeehive.savedFlowerPos);
        if (!pBeehive.stored.isEmpty() && pLevel.getRandom().nextDouble() < 0.005) {
            double $$4 = (double)pPos.getX() + 0.5;
            double $$5 = (double)pPos.getY();
            double $$6 = (double)pPos.getZ() + 0.5;
            pLevel.playSound((Player)null, $$4, $$5, $$6, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        DebugPackets.sendHiveInfo(pLevel, pPos, pState, pBeehive);
    }

    public static boolean releaseOccupant(Level pLevel, BlockPos pPos, BlockState pState, BeeData pData, @Nullable List<Entity> pStoredInHives, BeeReleaseStatus pReleaseStatus, @Nullable BlockPos pSavedFlowerPos) {
        if ((pLevel.isNight() || pLevel.isRaining()) && pReleaseStatus != LeninBustBlockEntity.BeeReleaseStatus.EMERGENCY) {
            return false;
        } else {
            CompoundTag $$7 = pData.entityData.copy();
            removeIgnoredBeeTags($$7);
            $$7.put("HivePos", NbtUtils.writeBlockPos(pPos));
            $$7.putBoolean("NoGravity", true);
            Direction $$8 = (Direction)pState.getValue(LeninBustBlock.FACING);
            BlockPos $$9 = pPos.relative($$8);
            boolean $$10 = !pLevel.getBlockState($$9).getCollisionShape(pLevel, $$9).isEmpty();
            if ($$10 && pReleaseStatus != LeninBustBlockEntity.BeeReleaseStatus.EMERGENCY) {
                return false;
            } else {
                Entity $$11 = EntityType.loadEntityRecursive($$7, pLevel, (p_58740_) -> {
                    return p_58740_;
                });
                if ($$11 != null) {
                    if (!$$11.getType().is(EntityTypeTags.BEEHIVE_INHABITORS)) {
                        return false;
                    } else {
                        if ($$11 instanceof Bee) {
                            Bee $$12 = (Bee)$$11;
                            if (pSavedFlowerPos != null && !$$12.hasSavedFlowerPos() && pLevel.random.nextFloat() < 0.9F) {
                                $$12.setSavedFlowerPos(pSavedFlowerPos);
                            }

                            if (pReleaseStatus == LeninBustBlockEntity.BeeReleaseStatus.HONEY_DELIVERED) {
                                $$12.dropOffNectar();
                                if (pState.is(BlockTags.BEEHIVES, (p_202037_) -> {
                                    return p_202037_.hasProperty(LeninBustBlock.HONEY_LEVEL);
                                })) {
                                    int $$13 = getHoneyLevel(pState);
                                    if ($$13 < 5) {
                                        int $$14 = pLevel.random.nextInt(100) == 0 ? 2 : 1;
                                        if ($$13 + $$14 > 5) {
                                            --$$14;
                                        }

                                        pLevel.setBlockAndUpdate(pPos, (BlockState)pState.setValue(LeninBustBlock.HONEY_LEVEL, $$13 + $$14));
                                    }
                                }
                            }

                            setBeeReleaseData(pData.ticksInHive, $$12);
                            if (pStoredInHives != null) {
                                pStoredInHives.add($$12);
                            }

                            float $$15 = $$11.getBbWidth();
                            double $$16 = $$10 ? 0.0 : 0.55 + (double)($$15 / 2.0F);
                            double $$17 = (double)pPos.getX() + 0.5 + $$16 * (double)$$8.getStepX();
                            double $$18 = (double)pPos.getY() + 0.5 - (double)($$11.getBbHeight() / 2.0F);
                            double $$19 = (double)pPos.getZ() + 0.5 + $$16 * (double)$$8.getStepZ();
                            $$11.moveTo($$17, $$18, $$19, $$11.getYRot(), $$11.getXRot());
                        }

                        pLevel.playSound((Player)null, pPos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of($$11, pLevel.getBlockState(pPos)));
                        return pLevel.addFreshEntity($$11);
                    }
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public void addOccupant(Entity pOccupant, boolean pHasNectar) {
        BlockState pState = this.getBlockState();
        this.addOccupantWithPresetTicks(pOccupant, pHasNectar, 0);
//        if (pHasNectar) {
//            int $$13 = getHoneyLevel(pState);
//            if ($$13 < 5) {
//                int $$14 = pOccupant.level().random.nextInt(100) == 0 ? 2 : 1;
//                if ($$13 + $$14 > 5) {
//                    --$$14;
//                }
//
//                pOccupant.level().setBlockAndUpdate(this.getBlockPos(), (BlockState) pState.setValue(LeninBustBlock.HONEY_LEVEL, $$13 + $$14));
//            }
//        }
    }

    @Override
    public void addOccupantWithPresetTicks(Entity pOccupant, boolean pHasNectar, int pTicksInHive) {
        if (this.stored.size() < 3) {
            pOccupant.stopRiding();
            pOccupant.ejectPassengers();
            CompoundTag $$3 = new CompoundTag();
            pOccupant.save($$3);
            this.storeBee($$3, pTicksInHive, pHasNectar);
            if (this.level != null) {
                if (pOccupant instanceof Bee) {
                    Bee $$4 = (Bee)pOccupant;
                    if ($$4.hasSavedFlowerPos() && (!this.hasSavedFlowerPos() || this.level.random.nextBoolean())) {
                        this.savedFlowerPos = $$4.getSavedFlowerPos();
                    }
                }

                BlockPos $$5 = this.getBlockPos();
                this.level.playSound((Player)null, (double)$$5.getX(), (double)$$5.getY(), (double)$$5.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, $$5, GameEvent.Context.of(pOccupant, this.getBlockState()));
            }

            pOccupant.discard();
            super.setChanged();
        }
    }
    @Override
    public void storeBee(CompoundTag pEntityData, int pTicksInHive, boolean pHasNectar) {
        this.stored.add(new BeeData(pEntityData, pTicksInHive, pHasNectar ? 2400 : 600));

    }

    private boolean hasSavedFlowerPos() {
        return this.savedFlowerPos != null;
    }


    private static class BeeData {
        final CompoundTag entityData;
        int ticksInHive;
        final int minOccupationTicks;

        BeeData(CompoundTag pEntityData, int pTicksInHive, int pMinOccupationTicks) {
            LeninBustBlockEntity.removeIgnoredBeeTags(pEntityData);
            this.entityData = pEntityData;
            this.ticksInHive = pTicksInHive;
            this.minOccupationTicks = pMinOccupationTicks;
        }
    }

}
