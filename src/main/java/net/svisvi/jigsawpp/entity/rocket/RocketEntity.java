//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.svisvi.jigsawpp.entity.rocket;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IMinecartCollisionHandler;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.networking.packet.SyncRocketDataPacket;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.radio.IRadioActivatable;
import net.svisvi.jigsawpp.procedures.ut.NuclearShroom;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RocketEntity extends Entity implements IRadioActivatable {
    private static final EntityDataAccessor<Integer> DATA_ID_HURT;
    private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR;
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE;
    private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_BLOCK;
    private static final EntityDataAccessor<Integer> DATA_ID_DISPLAY_OFFSET;
    private static final EntityDataAccessor<Boolean> DATA_ID_CUSTOM_DISPLAY;
    private static final EntityDataAccessor<ItemStack> DATA_ID_POOPS = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_BOMB = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ID_NAMETAG = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final ImmutableMap<Pose, ImmutableList<Integer>> POSE_DISMOUNT_HEIGHTS;
    protected static final float WATER_SLOWDOWN_FACTOR = 0.95F;
    private boolean flipped;
    private boolean onRails;
    private static IMinecartCollisionHandler COLLISIONS;
    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;
    private double lastYd;
    private boolean canBePushed;
    private boolean canUseRail;
    private float currentSpeedCapOnRail;
    private @Nullable Float maxSpeedAirLateral;
    private float maxSpeedAirVertical;
    private double dragAir;

    public RocketEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.canBePushed = true;
        this.canUseRail = true;
        this.maxSpeedAirLateral = null;
        this.maxSpeedAirVertical = -1.0F;
        this.dragAir = 0.949999988079071;
        this.blocksBuilding = true;
    }

    public RocketEntity(EntityType<?> pEntityType, Level pLevel, double pX, double pY, double pZ) {
        this(pEntityType, pLevel);
        this.setPos(pX, pY, pZ);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    public IMinecartCollisionHandler getCollisionHandler() {
        return COLLISIONS;
    }

    public static void registerCollisionHandler(@javax.annotation.Nullable IMinecartCollisionHandler handler) {
        COLLISIONS = handler;
    }
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if (this.level().isClientSide()) {
            // Let client return PASS so server can handle it
            return InteractionResult.PASS;
        }

        ItemStack handStack = pPlayer.getItemInHand(pHand);
        Level level = this.level();


        // POOPS Interaction
        if (handStack.is(ModItems.POOPS.get())) {
            if (this.getPoops().isEmpty()) {
                //System.out.println("POOPS TIME!!!");
                String poopsName = getItemStackName(handStack);
                if (validatePoopsName(poopsName)) {
                    if (!level.isClientSide()) {
                        ItemStack poopsCopy = new ItemStack(ModItems.POOPS.get());
                        poopsCopy.setHoverName(Component.literal(poopsName)); // Preserve name

                        handStack.shrink(1);
                        this.setPoops(poopsCopy);
                        correctReaction();
                        syncEntity(); // Force sync to client
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide());
                } else {
                    //System.out.println("WRONG POOPS");
                    if (!level.isClientSide()) incorrectReaction();
                    return InteractionResult.FAIL;
                }
            } else {
                if (!level.isClientSide()) incorrectReaction();
                return InteractionResult.FAIL;
            }
        }

        // SHEARS Interaction
        else if (handStack.is(Items.SHEARS)) {
            if (!this.getPoops().isEmpty()) {
                if (!level.isClientSide()) {
                    handStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pHand));
                    pPlayer.addItem(this.getPoops().copy());
                    this.setPoops(ItemStack.EMPTY);
                    shears();
                    syncEntity();
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }

            if (!this.getBomb().isEmpty()) {
                if (!level.isClientSide()) {
                    handStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pHand));
                    pPlayer.addItem(this.getBomb().copy());
                    this.setBomb(ItemStack.EMPTY);
                    shears();
                    syncEntity();
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }

            if (!this.getNameTagItem().isEmpty()) {
                if (!level.isClientSide()) {
                    handStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pHand));
                    pPlayer.addItem(this.getNameTagItem().copy());
                    this.setNameTagItem(ItemStack.EMPTY);
                    shears();
                    syncEntity();
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }

        // TNT Interaction
        else if (handStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof TntBlock) {
            if (this.getBomb().isEmpty()) {
                if (!level.isClientSide()) {
                    ItemStack tntStack = new ItemStack(blockItem);
                    handStack.shrink(1);
                    this.setBomb(tntStack);
                    correctReaction();
                    syncEntity();
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else {
                if (!level.isClientSide()) incorrectReaction();
                return InteractionResult.FAIL;
            }
        } // NameTag interaction
        else if (handStack.is(Items.NAME_TAG)) {
            if (this.getNameTagItem().isEmpty()) {
                if (!level.isClientSide()) {
                    String poopsName = getItemStackName(handStack);
                    ItemStack poopsCopy = new ItemStack(Items.NAME_TAG);
                    poopsCopy.setHoverName(Component.literal(poopsName)); // Preserve name

                    handStack.shrink(1);
                    this.setNameTagItem(poopsCopy);
                    correctReaction();
                    syncEntity();
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            } else {
                if (!level.isClientSide()) incorrectReaction();
                return InteractionResult.FAIL;
            }
        }

        // Default interactions (riding, etc.)
        InteractionResult ret = super.interact(pPlayer, pHand);
        if (ret.consumesAction()) return ret;

        if (!level.isClientSide() && !pPlayer.isSecondaryUseActive() && !this.isVehicle()) {
            return pPlayer.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }

    private void oldsyncEntity() {
        if (!this.level().isClientSide()) {
            // Force data sync of both items
            this.getEntityData().set(DATA_ID_POOPS, this.getPoops());
            this.getEntityData().set(DATA_ID_BOMB, this.getBomb());
            this.getEntityData().set(DATA_ID_NAMETAG, this.getNameTagItem());

            // Send update packet
            Packet<?> packet = this.getAddEntityPacket();
            if (packet != null) {
                ((ServerLevel)this.level()).getChunkSource().broadcast(this, packet);
            }
        }
    }

    private void syncEntity() {
        if (!this.level().isClientSide()) {
            // Force all data to sync
            this.getEntityData().set(DATA_ID_POOPS, this.getPoops());
            this.getEntityData().set(DATA_ID_BOMB, this.getBomb());
            this.getEntityData().set(DATA_ID_NAMETAG, this.getNameTagItem());

            // Send position update too
            this.hasImpulse = true;
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (DATA_ID_POOPS.equals(key) || DATA_ID_BOMB.equals(key) || DATA_ID_NAMETAG.equals(key)) {
            // Force render update
            if (this.level().isClientSide) {
                this.level().sendBlockUpdated(this.blockPosition(),
                        this.level().getBlockState(this.blockPosition()),
                        this.level().getBlockState(this.blockPosition()),
                        3);
            }
        }
    }

    public static RocketEntity createChair(Level pLevel, double pX, double pY, double pZ) {
            return new RocketEntity(ModEntities.ROCKET.get(), pLevel, pX, pY, pZ);
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.EVENTS;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_HURT, 0);
        this.entityData.define(DATA_ID_HURTDIR, 1);
        this.entityData.define(DATA_ID_DAMAGE, 0.0F);
        this.entityData.define(DATA_ID_DISPLAY_BLOCK, Block.getId(Blocks.AIR.defaultBlockState()));
        this.entityData.define(DATA_ID_DISPLAY_OFFSET, 6);
        this.entityData.define(DATA_ID_CUSTOM_DISPLAY, false);
        this.entityData.define(DATA_ID_POOPS, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_BOMB, ItemStack.EMPTY);
        this.entityData.define(DATA_ID_NAMETAG, ItemStack.EMPTY);
    }

    public boolean canCollideWith(Entity pEntity) {
        return Boat.canVehicleCollide(this, pEntity);
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean isPushable() {
        return this.canBePushed;
    }

    protected Vec3 getRelativePortalPosition(Axis pAxis, BlockUtil.FoundRectangle pPortal) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(pAxis, pPortal));
    }

    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.2f;
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Direction direction = this.getMotionDirection();
        if (direction.getAxis() == Axis.Y) {
            return super.getDismountLocationForPassenger(pLivingEntity);
        } else {
            int[][] aint = DismountHelper.offsetsForDirection(direction);
            BlockPos blockpos = this.blockPosition();
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            ImmutableList<Pose> immutablelist = pLivingEntity.getDismountPoses();
            UnmodifiableIterator var7 = immutablelist.iterator();

            while(var7.hasNext()) {
                Pose pose = (Pose)var7.next();
                EntityDimensions entitydimensions = pLivingEntity.getDimensions(pose);
                float f = Math.min(entitydimensions.width, 1.0F) / 2.0F;
                UnmodifiableIterator var11 = ((ImmutableList)POSE_DISMOUNT_HEIGHTS.get(pose)).iterator();

                while(var11.hasNext()) {
                    int i = (Integer)var11.next();
                    int[][] var13 = aint;
                    int var14 = aint.length;

                    for(int var15 = 0; var15 < var14; ++var15) {
                        int[] aint1 = var13[var15];
                        blockpos$mutableblockpos.set(blockpos.getX() + aint1[0], blockpos.getY() + i, blockpos.getZ() + aint1[1]);
                        double d0 = this.level().getBlockFloorHeight(DismountHelper.nonClimbableShape(this.level(), blockpos$mutableblockpos), () -> {
                            return DismountHelper.nonClimbableShape(this.level(), blockpos$mutableblockpos.below());
                        });
                        if (DismountHelper.isBlockFloorValid(d0)) {
                            AABB aabb = new AABB((double)(-f), 0.0, (double)(-f), (double)f, (double)entitydimensions.height, (double)f);
                            Vec3 vec3 = Vec3.upFromBottomCenterOf(blockpos$mutableblockpos, d0);
                            if (DismountHelper.canDismountTo(this.level(), pLivingEntity, aabb.move(vec3))) {
                                pLivingEntity.setPose(pose);
                                return vec3;
                            }
                        }
                    }
                }
            }

            double d1 = this.getBoundingBox().maxY;
            blockpos$mutableblockpos.set((double)blockpos.getX(), d1, (double)blockpos.getZ());
            UnmodifiableIterator var22 = immutablelist.iterator();

            while(var22.hasNext()) {
                Pose pose1 = (Pose)var22.next();
                double d2 = (double)pLivingEntity.getDimensions(pose1).height;
                int j = Mth.ceil(d1 - (double)blockpos$mutableblockpos.getY() + d2);
                double d3 = DismountHelper.findCeilingFrom(blockpos$mutableblockpos, j, (p_289495_) -> {
                    return this.level().getBlockState(p_289495_).getCollisionShape(this.level(), p_289495_);
                });
                if (d1 + d2 <= d3) {
                    pLivingEntity.setPose(pose1);
                    break;
                }
            }

            return super.getDismountLocationForPassenger(pLivingEntity);
        }
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.level().isClientSide && !this.isRemoved()) {
            if (this.isInvulnerableTo(pSource)) {
                return false;
            } else {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.markHurt();
                this.setDamage(this.getDamage() + pAmount * 10.0F);
                this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
                boolean flag = pSource.getEntity() instanceof Player && ((Player)pSource.getEntity()).getAbilities().instabuild;
                if (flag || this.getDamage() > 40.0F) {
                    this.ejectPassengers();
                    if (flag && !this.hasCustomName()) {
                        this.discard();
                    } else {
                        this.destroy(pSource);
                    }
                }

                return true;
            }
        } else {
            return true;
        }
    }

    @javax.annotation.Nullable
    public Vec3 getPosOffs(double pX, double pY, double pZ, double pOffset) {
        return null;
    }
    @javax.annotation.Nullable
    public Vec3 getPos(double pX, double pY, double pZ) {
        return null;
    }

    public void destroy(DamageSource pDamageSource) {
        this.kill(); // Marks the entity for removal

        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            // Drop the rocket itself (if named, keep the name)
            ItemStack rocketStack = new ItemStack(this.getDropItem());
            if (this.hasCustomName()) {
                rocketStack.setHoverName(this.getCustomName());
            }
            this.spawnAtLocation(rocketStack);

            // Drop Poops if present
            if (!this.getPoops().isEmpty()) {
                this.spawnAtLocation(this.getPoops().copy()); // Always use copy() to avoid reference issues
            }

            // Drop Bomb if present
            if (!this.getBomb().isEmpty()) {
                this.spawnAtLocation(this.getBomb().copy()); // Always use copy() to avoid reference issues
            }

            if (!this.getNameTagItem().isEmpty()) {
                this.spawnAtLocation(this.getNameTagItem().copy()); // Always use copy() to avoid reference issues
            }

            this.setPoops(ItemStack.EMPTY);
            this.setBomb(ItemStack.EMPTY);
            this.setNameTagItem(ItemStack.EMPTY);
        }
    }

    protected void clampRotation(Entity pEntityToUpdate) {
        pEntityToUpdate.setYBodyRot(this.getYRot());
        float f = Mth.wrapDegrees(pEntityToUpdate.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);
        pEntityToUpdate.yRotO += f1 - f;
        pEntityToUpdate.setYRot(pEntityToUpdate.getYRot() + f1 - f);
        pEntityToUpdate.setYHeadRot(pEntityToUpdate.getYRot());
    }

    public void onPassengerTurned(Entity pEntityToUpdate) {
        this.clampRotation(pEntityToUpdate);
    }

    public Item getDropItem(){
        return ModItems.ROCKET.get();
    };

    public void animateHurt(float pYaw) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }


    public Direction getMotionDirection() {
        return this.flipped ? this.getDirection().getOpposite().getClockWise() : this.getDirection().getClockWise();
    }


    public void tick() {

        legacyTick();
//        System.out.println(this.getPoops().toString());
//        System.out.println(this.getBomb().toString());
//        if (!this.level().isClientSide()) {
//            System.out.println("Server (tick) Poops - start: " + this.getPoops());
//            System.out.println("Server (tick) Bomb - start: " + this.getBomb());
//        }

        if (this.getHurtTime() > 0) {
            this.setHurtTime(this.getHurtTime() - 1);
        }

        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }

        this.checkBelowWorld();
        this.handleNetherPortal();
        double d5;
        if (this.level().isClientSide) {
            if (this.lSteps > 0) {
                d5 = this.getX() + (this.lx - this.getX()) / (double)this.lSteps;
                double d6 = this.getY() + (this.ly - this.getY()) / (double)this.lSteps;
                double d7 = this.getZ() + (this.lz - this.getZ()) / (double)this.lSteps;
                double d2 = Mth.wrapDegrees(this.lyr - (double)this.getYRot());
                this.setYRot(this.getYRot() + (float)d2 / (float)this.lSteps);
                this.setXRot(this.getXRot() + (float)(this.lxr - (double)this.getXRot()) / (float)this.lSteps);
                --this.lSteps;
                this.setPos(d5, d6, d7);
                this.setRot(this.getYRot(), this.getXRot());
            } else {
                this.reapplyPosition();
                this.setRot(this.getYRot(), this.getXRot());
            }
        } else {
            if (!this.isNoGravity()) {
                d5 = this.isInWater() ? -0.005 : -0.04;
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, d5, 0.0));
            }

            int k = Mth.floor(this.getX());
            int i = Mth.floor(this.getY());
            int j = Mth.floor(this.getZ());
            if (this.level().getBlockState(new BlockPos(k, i - 1, j)).is(BlockTags.RAILS)) {
                --i;
            }

            BlockPos blockpos = new BlockPos(k, i, j);
            BlockState blockstate = this.level().getBlockState(blockpos);
            this.onRails = BaseRailBlock.isRail(blockstate);
            if (this.canUseRail() && this.onRails) {
                if (blockstate.getBlock() instanceof PoweredRailBlock && ((PoweredRailBlock)blockstate.getBlock()).isActivatorRail()) {
                    this.activateMinecart(k, i, j, (Boolean)blockstate.getValue(PoweredRailBlock.POWERED));
                }
            } else {
                this.comeOffTrack();
            }

            this.checkInsideBlocks();
            this.setXRot(0.0F);
            double d1 = this.xo - this.getX();
            double d3 = this.zo - this.getZ();
            if (d1 * d1 + d3 * d3 > 0.001) {
                this.setYRot((float)(Mth.atan2(d3, d1) * 180.0 / Math.PI));
                if (this.flipped) {
                    this.setYRot(this.getYRot() + 180.0F);
                }
            }

            double d4 = (double)Mth.wrapDegrees(this.getYRot() - this.yRotO);
            if (d4 < -170.0 || d4 >= 170.0) {
                this.setYRot(this.getYRot() + 180.0F);
                this.flipped = !this.flipped;
            }

            this.setRot(this.getYRot(), this.getXRot());
            AABB box;
            if (this.getCollisionHandler() != null) {
                box = this.getBoundingBox();
            } else {
                box = this.getBoundingBox().inflate(0.20000000298023224, 0.0, 0.20000000298023224);
            }

            if (this.getDeltaMovement().horizontalDistanceSqr() > 0.01) {
                List<Entity> list = this.level().getEntities(this, box, EntitySelector.pushableBy(this));
                if (!list.isEmpty()) {
                    for(int l = 0; l < list.size(); ++l) {
                        Entity entity1 = (Entity)list.get(l);
                        if (!(entity1 instanceof Player) && !(entity1 instanceof IronGolem) && !(entity1 instanceof AbstractMinecart) && !this.isVehicle() && !entity1.isPassenger()) {
                            entity1.startRiding(this);
                        } else {
                            entity1.push(this);
                        }
                    }
                }
            } else {
                Iterator var13 = this.level().getEntities(this, box).iterator();

                while(var13.hasNext()) {
                    Entity entity = (Entity)var13.next();
                    if (!this.hasPassenger(entity) && entity.isPushable() && entity instanceof AbstractMinecart) {
                        entity.push(this);
                    }
                }
            }

            this.updateInWaterStateAndDoFluidPushing();
            if (this.isInLava()) {
                this.lavaHurt();
                this.fallDistance *= 0.5F;
            }

            this.firstTick = false;
        }

    }

    public void explode(){
        if (!this.level().isClientSide()){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3, Level.ExplosionInteraction.TNT);
            if (this.level() instanceof ServerLevel slevel){
                NuclearShroom.sendFarParticles(slevel,
                        ParticleTypes.EXPLOSION,
                        this.getX(), this.getY(), this.getZ(),
                        7,
                        7,
                        7,
                        100, 0);
            }
            if (!this.getBomb().isEmpty()){
                if (this.getBomb().getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof TntBlock tnt){
                    tnt.onCaughtFire(blockItem.getBlock().defaultBlockState(), this.level(), this.getOnPos().above(), null, null);
                }
            }
        }
        this.discard();
    }

    public void land(){
        if (!this.getBomb().isEmpty()){
            this.explode();
            return;
        }
        if (!this.level().isClientSide) {
            this.getPersistentData().putBoolean("fused", false);
            this.getPersistentData().putBoolean("fall_rocket", false);
            this.getPersistentData().putDouble("rocket_counter1", 0);

            ModMessages.sendToTrackingEntity(
                    new SyncRocketDataPacket(getId(), getPersistentData()),
                    this
            );
        }
    }

    public void legacyTick() {
        if (this == null)
            return;
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        BlockPos thisPos = BlockPos.containing(x,y,z);

        //EXPLOSION IF BLOCKED
        if (this.getDeltaMovement().y() == 0 && this.getPersistentData().getBoolean("fused") && this.getPersistentData().getDouble("rocket_counter1") > 210) {
            this.explode();
        }
        if (this.getPersistentData().getBoolean("fused") == true && this.getPersistentData().getBoolean("fall_rocket") == false) {
            this.getPersistentData().putDouble("rocket_counter1", (this.getPersistentData().getDouble("rocket_counter1") + 1));
            if (this.getPersistentData().getDouble("rocket_counter1") >= 200) {
                this.setDeltaMovement(new Vec3((this.getDeltaMovement().x()), 0.4, (this.getDeltaMovement().z())));
                if (this.level() instanceof ServerLevel _level)
                    _level.sendParticles(ParticleTypes.FLAME, x, (y - 2.8), z, 60, 0.1, 1.4, 0.1, 0.1);
                if (this.level() instanceof ServerLevel _level)
                    _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP.get()), x, (y - 4), z, 80, 0.3, 2, 0.3, 0.1);
                if (this.getPersistentData().getDouble("rocket_counter1") % 5 == 0) {
                        if (!this.level().isClientSide()) {
                            this.level().playSound(null, thisPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.BLOCKS, 1, -1);
                        } else {
                            this.level().playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.BLOCKS, 1, -1, false);
                        }

                    if ((this.level().getBlockState(thisPos)).getBlock() instanceof AirBlock) {
                        if (this.getDeltaMovement().y() > 0 && this.getPersistentData().getBoolean("fall_rocket") == false) {
                            if (this.level() instanceof ServerLevel _level){
                                _level.setBlock(thisPos, ModBlocks.PONOS_FLUID_BLOCK.get().defaultBlockState(), 3);
                            }
                        }
                    }
                }
                if (y >= 319) {
                    ItemStack poops = this.getPoops();
                    if (!poops.isEmpty()) {
                        {
                            BlockPos tpPos = parsePoopsCoords(getItemStackName(poops));
                            if (this.getFirstPassenger() instanceof Player player){
                                this.ejectPassengers();
                                if (player instanceof ServerPlayer splayer) {
                                    splayer.connection.teleport((double)tpPos.getX(), this.getY()-1, (double)tpPos.getZ(), splayer.getYRot(), splayer.getXRot());
                                } else {
                                    player.teleportTo(tpPos.getX(), this.getY()-1, tpPos.getZ());
                                }
                                player.startRiding(this, true);
                            }
                            this.teleportTo(tpPos.getX(), this.getY()-1, tpPos.getZ());
                        }
                        this.getPersistentData().putBoolean("fall_rocket", (true));
                    } else {
                        this.explode();
                    }
                }
            } else {
                if (this.level() instanceof ServerLevel _level)
                    _level.sendParticles(ParticleTypes.POOF, x, y, z, 60, 2, 0.4, 2, 0.1);
                if (this.getPersistentData().getDouble("rocket_counter1") % 5 == 0) {
                        if (!this.level().isClientSide()) {
                            this.level().playSound(null, thisPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wool.break")), SoundSource.BLOCKS, 1, -1);
                        } else {
                            this.level().playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.wool.break")), SoundSource.BLOCKS, 1, -1, false);
                        }

                }
            }
        } else if (this.getPersistentData().getBoolean("fused") == true && this.getPersistentData().getBoolean("fall_rocket") == true) {
            this.setDeltaMovement(new Vec3((this.getDeltaMovement().x()), (-1.4), (this.getDeltaMovement().z())));
            this.setSecondsOnFire(1);
            if (this.level() instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.FLAME, x, (y + 2.3), z, 30, 0.1, 1.4, 0.1, 0.1);
            if (this.onGround()) {
                this.land();
            }
        }
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (pOnGround) {
                if (this.fallDistance > 3.0F) {
                    if (!this.onGround()) {
                        this.resetFallDistance();
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
                    if (!this.level().isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            int j;
                            this.spawnAtLocation(this.getPickResult());
                        }
                    }
                }

                this.resetFallDistance();
            } else if (this.onGround() && pY < 0.0) {
                this.fallDistance -= (float)pY;
            }
        }

    }


    protected double getMaxSpeed() {
        return (this.isInWater() ? 4.0 : 8.0) / 20.0;
    }

    public void activateMinecart(int pX, int pY, int pZ, boolean pPowered) {
    }

    protected void comeOffTrack() {
        double d0 = this.onGround() ? this.getMaxSpeed() : (double)this.getMaxSpeedAirLateral();
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(Mth.clamp(vec3.x, -d0, d0), vec3.y, Mth.clamp(vec3.z, -d0, d0));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
        }

        if (this.getMaxSpeedAirVertical() > 0.0F && this.getDeltaMovement().y > (double)this.getMaxSpeedAirVertical()) {
            if (Math.abs(this.getDeltaMovement().x) < 0.30000001192092896 && Math.abs(this.getDeltaMovement().z) < 0.30000001192092896) {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x, 0.15000000596046448, this.getDeltaMovement().z));
            } else {
                this.setDeltaMovement(new Vec3(this.getDeltaMovement().x, (double)this.getMaxSpeedAirVertical(), this.getDeltaMovement().z));
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        if (!this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().scale(this.getDragAir()));
        }

    }


    public boolean isOnRails() {
        return this.onRails;
    }


    public AABB getBoundingBoxForCulling() {
        AABB aabb = this.getBoundingBox();
        return this.hasCustomDisplay() ? aabb.inflate((double)Math.abs(this.getDisplayOffset()) / 16.0) : aabb;
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.getBoolean("CustomDisplayTile")) {
            this.setDisplayBlockState(NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), pCompound.getCompound("DisplayState")));
            this.setDisplayOffset(pCompound.getInt("DisplayOffset"));
        }

        if (pCompound.contains("Poops")) {
            this.setPoops(ItemStack.of(pCompound.getCompound("Poops")));
        }
        if (pCompound.contains("Bomb")) {
            this.setBomb(ItemStack.of(pCompound.getCompound("Bomb")));
        }
        if (pCompound.contains("Nametag")) {
            this.setNameTagItem(ItemStack.of(pCompound.getCompound("Nametag")));
        }

    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        if (this.hasCustomDisplay()) {
            pCompound.putBoolean("CustomDisplayTile", true);
            pCompound.put("DisplayState", NbtUtils.writeBlockState(this.getDisplayBlockState()));
            pCompound.putInt("DisplayOffset", this.getDisplayOffset());
        }
        if (!this.getPoops().isEmpty()) {
            pCompound.put("Poops", this.getPoops().save(new CompoundTag()));
        }
        if (!this.getBomb().isEmpty()) {
            pCompound.put("Bomb", this.getBomb().save(new CompoundTag()));
        }
        if (!this.getNameTagItem().isEmpty()) {
            pCompound.put("Nametag", this.getNameTagItem().save(new CompoundTag()));
        }

    }

    public void push(Entity pEntity) {
        if (this.getCollisionHandler() != null) {
        } else {
            if (!this.level().isClientSide && !pEntity.noPhysics && !this.noPhysics && !this.hasPassenger(pEntity)) {
                double d0 = pEntity.getX() - this.getX();
                double d1 = pEntity.getZ() - this.getZ();
                double d2 = d0 * d0 + d1 * d1;
                if (d2 >= 9.999999747378752E-5) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0 / d2;
                    if (d3 > 1.0) {
                        d3 = 1.0;
                    }

                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.10000000149011612;
                    d1 *= 0.10000000149011612;
                    d0 *= 0.5;
                    d1 *= 0.5;
                    if (pEntity instanceof RocketEntity) {
                        double d4 = pEntity.getX() - this.getX();
                        double d5 = pEntity.getZ() - this.getZ();
                        Vec3 vec3 = (new Vec3(d4, 0.0, d5)).normalize();
                        Vec3 vec31 = (new Vec3((double)Mth.cos(this.getYRot() * 0.017453292F), 0.0, (double)Mth.sin(this.getYRot() * 0.017453292F))).normalize();
                        double d6 = Math.abs(vec3.dot(vec31));
                        if (d6 < 0.800000011920929) {
                            return;
                        }

                        Vec3 vec32 = this.getDeltaMovement();
                        Vec3 vec33 = pEntity.getDeltaMovement();
                            double d7 = (vec33.x + vec32.x) / 2.0;
                            double d8 = (vec33.z + vec32.z) / 2.0;
                            this.setDeltaMovement(vec32.multiply(0.2, 1.0, 0.2));
                            this.push(d7 - d0, 0.0, d8 - d1);
                            pEntity.setDeltaMovement(vec33.multiply(0.2, 1.0, 0.2));
                            pEntity.push(d7 + d0, 0.0, d8 + d1);
                    } else {
                        this.push(-d0, 0.0, -d1);
                        pEntity.push(d0 / 4.0, 0.0, d1 / 4.0);
                    }
                }
            }

        }
    }

    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lx = pX;
        this.ly = pY;
        this.lz = pZ;
        this.lyr = (double)pYaw;
        this.lxr = (double)pPitch;
        this.lSteps = pPosRotationIncrements + 2;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    public void lerpMotion(double pX, double pY, double pZ) {
        this.lxd = pX;
        this.lyd = pY;
        this.lzd = pZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    public void setDamage(float pDamage) {
        this.entityData.set(DATA_ID_DAMAGE, pDamage);
    }

    public float getDamage() {
        return (Float)this.entityData.get(DATA_ID_DAMAGE);
    }

    public void setHurtTime(int pHurtTime) {
        this.entityData.set(DATA_ID_HURT, pHurtTime);
    }

    public int getHurtTime() {
        return (Integer)this.entityData.get(DATA_ID_HURT);
    }

    public void setHurtDir(int pHurtDirection) {
        this.entityData.set(DATA_ID_HURTDIR, pHurtDirection);
    }

    public int getHurtDir() {
        return (Integer)this.entityData.get(DATA_ID_HURTDIR);
    }

    public BlockState getDisplayBlockState() {
        return !this.hasCustomDisplay() ? this.getDefaultDisplayBlockState() : Block.stateById((Integer)this.getEntityData().get(DATA_ID_DISPLAY_BLOCK));
    }

    public BlockState getDefaultDisplayBlockState() {
        return Blocks.AIR.defaultBlockState();
    }

    public int getDisplayOffset() {
        return !this.hasCustomDisplay() ? this.getDefaultDisplayOffset() : (Integer)this.getEntityData().get(DATA_ID_DISPLAY_OFFSET);
    }

    public int getDefaultDisplayOffset() {
        return 6;
    }

    public void setDisplayBlockState(BlockState pDisplayState) {
        this.getEntityData().set(DATA_ID_DISPLAY_BLOCK, Block.getId(pDisplayState));
        this.setCustomDisplay(true);
    }

    public void setDisplayOffset(int pDisplayOffset) {
        this.getEntityData().set(DATA_ID_DISPLAY_OFFSET, pDisplayOffset);
        this.setCustomDisplay(true);
    }

    public boolean hasCustomDisplay() {
        return (Boolean)this.getEntityData().get(DATA_ID_CUSTOM_DISPLAY);
    }

    public void setCustomDisplay(boolean pCustomDisplay) {
        this.getEntityData().set(DATA_ID_CUSTOM_DISPLAY, pCustomDisplay);
    }

    //GOONNER LOGIC
    public static String getItemStackName(ItemStack stack) {
        String name = stack.getDisplayName().getString();
        // Remove square brackets if present
        if (name.startsWith("[") && name.endsWith("]")) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    public String getNameTag(){
        if (this.getNameTagItem().isEmpty()){
            return null;
        }
        return getItemStackName(this.getNameTagItem());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(this.getNameTag() != null ? this.getNameTag(): "Rocket");
    }

    public static boolean validatePoopsName(String string){
        return parsePoopsCoords(string) != null;
    }
    public static BlockPos parsePoopsCoords(String poopsName) {
        if (poopsName == null || poopsName.trim().isEmpty()) {
            return null;
        }

        // Remove all whitespace and split by comma
        String clean = poopsName.replaceAll("\\s+", "");
        //System.out.println(clean);
        String[] parts = clean.split(",");
        //System.out.println(parts.toString());

        // Must have exactly 2 parts (x and z)
        if (parts.length != 2) {
            return null;
        }
        try {
            int x = Integer.parseInt(parts[0]);
            int z = Integer.parseInt(parts[1]);
            //System.out.println(x);
            //System.out.println(z);

            // Optional: Validate coordinate ranges
            // Minecraft's world border is at ±30,000,000
            if (Math.abs(x) > 30_000_000 || Math.abs(z) > 30_000_000) {
                return null;
            }

            return new BlockPos(x, 0, z);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void incorrectReaction(){
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.getOnPos().above(2), SoundEvents.ANVIL_LAND, SoundSource.NEUTRAL, 1, 0);
        } else {
            this.level().playLocalSound(this.getOnPos().above(2), SoundEvents.ANVIL_LAND, SoundSource.NEUTRAL, 1, 0, false);
        }
        if (this.level() instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY()+2, this.getZ(), 30, 0.2, 0.4, 0.2, 1);
    }

    public void correctReaction(){
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.getOnPos().above(2), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1, 0);
        } else {
            this.level().playLocalSound(this.getOnPos().above(2), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1, 0, false);
        }
        if (this.level() instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY()+2, this.getZ(), 30, 0.2, 0.4, 0.2, 1);
    }

    public void shears(){
        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.getOnPos().above(2), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1, 0);
        } else {
            this.level().playLocalSound(this.getOnPos().above(2), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1, 0, false);
        }
        if (this.level() instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY()+2, this.getZ(), 30, 0.2, 0.4, 0.2, 1);
    }

    //END OF GOONNER LOGIC

    public ItemStack getPoops() {
        return this.entityData.get(DATA_ID_POOPS);
    }

//    public void setPoops(ItemStack stack) {
//        this.entityData.set(DATA_ID_POOPS, stack.copy()); // Always use copy() to avoid reference issues
//    }
    public void setPoops(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1); // Ensure single item
        this.entityData.set(DATA_ID_POOPS, copy);
        this.syncEntity();
    }

    public ItemStack getBomb() {
        return this.entityData.get(DATA_ID_BOMB);
    }

    public void setBomb(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1); // Ensure single item
        this.entityData.set(DATA_ID_BOMB, copy);
        this.syncEntity();
    }

    public ItemStack getNameTagItem() {
        return this.entityData.get(DATA_ID_NAMETAG);
    }

    public void setNameTagItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1); // Ensure single item
        this.entityData.set(DATA_ID_NAMETAG, copy);
        this.syncEntity();
    }

    public boolean canUseRail() {
        return this.canUseRail;
    }

    public void setCanUseRail(boolean value) {
        this.canUseRail = value;
    }

    public float getCurrentCartSpeedCapOnRail() {
        return this.currentSpeedCapOnRail;
    }

    public void setCurrentCartSpeedCapOnRail(float value) {
        this.currentSpeedCapOnRail = Math.min(value, 1.2F);
    }

    public float getMaxSpeedAirLateral() {
        return this.maxSpeedAirLateral == null ? (float)this.getMaxSpeed() : this.maxSpeedAirLateral;
    }

    public void setMaxSpeedAirLateral(float value) {
        this.maxSpeedAirLateral = value;
    }

    public float getMaxSpeedAirVertical() {
        return this.maxSpeedAirVertical;
    }

    public void setMaxSpeedAirVertical(float value) {
        this.maxSpeedAirVertical = value;
    }

    public double getDragAir() {
        return this.dragAir;
    }

    public void setDragAir(double value) {
        this.dragAir = value;
    }

    public ItemStack getPickResult() {
        return new ItemStack(ModItems.ROCKET.get());
    }

    static {
        DATA_ID_HURT = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
        DATA_ID_HURTDIR = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
        DATA_ID_DAMAGE = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.FLOAT);
        DATA_ID_DISPLAY_BLOCK = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
        DATA_ID_DISPLAY_OFFSET = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.INT);
        DATA_ID_CUSTOM_DISPLAY = SynchedEntityData.defineId(RocketEntity.class, EntityDataSerializers.BOOLEAN);
        POSE_DISMOUNT_HEIGHTS = ImmutableMap.of(Pose.STANDING, ImmutableList.of(0, 1, -1), Pose.CROUCHING, ImmutableList.of(0, 1, -1), Pose.SWIMMING, ImmutableList.of(0, 1));
        ;
        COLLISIONS = null;
    }

    @Override
    public void activate(@Nullable Level level, @Nullable BlockPos pos, @Nullable Entity entity, @Nullable Entity owner, @Nullable Entity activator, ItemStack thisStack) {
        if (!this.getPersistentData().getBoolean("fused")) {
            this.getPersistentData().putBoolean("fused", (true));
        }
    }

}
