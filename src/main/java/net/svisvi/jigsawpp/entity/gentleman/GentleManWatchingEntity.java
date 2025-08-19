package net.svisvi.jigsawpp.entity.gentleman;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;

public class GentleManWatchingEntity extends Monster {
    public GentleManWatchingEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.GENTLEMAN_WATCHING.get(), world);
    }

    public GentleManWatchingEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        xpReward = 10;
        setNoAi(false);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.step")), 0.15f, 1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.7);
        builder = builder.add(Attributes.MAX_HEALTH, 300);
        builder = builder.add(Attributes.ARMOR, 25);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 10);
        builder = builder.add(Attributes.FOLLOW_RANGE, 32);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.4);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1.5);
        return builder;
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return !this.isLeashed();
    }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0, (double)(0.6F * this.getEyeHeight()-0.35D), (double)(this.getBbWidth() * 0.4F)-0.2D);
    }

    @Override
    protected double followLeashSpeed() {
        return 1.5D;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, (float) 100));
    }

    public static boolean init(EntityType<? extends GentleManWatchingEntity> pType, ServerLevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom){
        int y = pPos.getY();
        return pLevel.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(pType, pLevel, pSpawnType, pPos, pRandom) && y > 60 && ((pLevel instanceof Level _lvl ? _lvl.dimension() : (pLevel instanceof WorldGenLevel _wgl ? _wgl.getLevel().dimension() : Level.OVERWORLD)) == Level.OVERWORLD) ;
    } 

    @Override
    public void tick() {
        super.tick();
        if(this.level().getNearestPlayer(this, 6) != null) {
            this.playSound(SoundEvents.COW_MILK);
            this.level().addFreshEntity(new PoopGasEmitterEntity(this.level(), this.getX(), this.getY(), this.getZ(), 2, 60));
            this.discard();
        }
    }
}
