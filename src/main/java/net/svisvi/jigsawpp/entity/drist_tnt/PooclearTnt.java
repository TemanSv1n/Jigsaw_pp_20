package net.svisvi.jigsawpp.entity.drist_tnt;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.tntpot.PrimedTntPot;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;
import net.svisvi.jigsawpp.procedures.ut.NuclearShroom;

import javax.annotation.Nullable;

public class PooclearTnt extends Entity implements TraceableEntity {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID;
    private static final int DEFAULT_FUSE_TIME = 150;
    @Nullable
    private LivingEntity owner;

    public PooclearTnt(EntityType<? extends PooclearTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public PooclearTnt(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(ModEntities.POOCLEAR_TNT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double $$5 = pLevel.random.nextDouble() * 6.2831854820251465;
        this.setDeltaMovement(-Math.sin($$5) * 0.02, 0.20000000298023224, -Math.cos($$5) * 0.02);
        this.setFuse(150);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = pOwner;
    }

    protected void defineSynchedData() {
        this.entityData.define(DATA_FUSE_ID, 80);
    }

    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    public boolean isPickable() {
        return !this.isRemoved();
    }

    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int $$0 = this.getFuse() - 1;
        this.setFuse($$0);
        if ($$0 <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    public void explode() {
//        float $$0 = 4.0F;
////        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 4.0F, ExplosionInteraction.TNT);
//        DristExplosion.harmfulDristExplode(this.level(), this.getOnPos(), 3 , ExplosionInteraction.NONE, this.owner);

        float $$0 = 16.0F;
        //this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(), 64.0F, ExplosionInteraction.TNT);


        JigsawPpMod.queueServerWork(15, () -> {
            if (this.level() instanceof ServerLevel _level)
                for (int i = 0; i < 400; i++){
                    PrimedDristTnt primedtnt = new PrimedDristTnt(this.level(), (double)this.getX() + (-2 + random.nextFloat() * (4)), (double)this.getY() + (-2 + random.nextFloat() * (4)), (double)this.getZ() + (-2 + random.nextFloat() * (4)), this.getOwner());
                    primedtnt.setFuse((primedtnt.getFuse()-random.nextInt(primedtnt.getFuse()))/2);
                    this.level().addFreshEntity(primedtnt);

                }

        });
        JigsawPpMod.queueServerWork(10, () -> {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 32, ExplosionInteraction.TNT);
            DristExplosion.harmfulDristExplode(this.level(), this.getOnPos(), 10 , ExplosionInteraction.TNT, this.owner);
        });
        for (int i = 0; i < 20; i++){
            JigsawPpMod.queueServerWork(i*10 + 1, () -> {
                NuclearShroom.createShitNuclearMushroom(this.level(),this.getX(),this.getY(0.0625),this.getZ());
            });
        }
    }

    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short)this.getFuse());
    }

    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
    }

    @Nullable
    public LivingEntity getOwner() {
        return this.owner;
    }

    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
        return 0.15F;
    }

    public void setFuse(int pLife) {
        this.entityData.set(DATA_FUSE_ID, pLife);
    }

    public int getFuse() {
        return (Integer)this.entityData.get(DATA_FUSE_ID);
    }

    static {
        DATA_FUSE_ID = SynchedEntityData.defineId(PooclearTnt.class, EntityDataSerializers.INT);
    }
}
