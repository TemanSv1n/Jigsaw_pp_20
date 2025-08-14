package net.svisvi.jigsawpp.entity.cursed_cow;

import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarrotBlock;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.BlockPos;

import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

import javax.annotation.Nullable;
import java.time.temporal.ChronoField;
import java.util.function.IntFunction;


public class CursedCowEntity extends Cow {
    // hindi code
    public CursedCowEntity(EntityType<? extends Cow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.getNavigation().getNodeEvaluator().setCanOpenDoors(true);

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, (float) 6, 1.5, 2));
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, true) {

            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return 9;
            }
        });
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 32.0F));
        //this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Cow.class, 10, true, true,
                // Predicate filter: only vanilla cows
                target -> target.getType() == EntityType.COW && !(target instanceof CursedCowEntity)
        ));}


















    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Mob.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.2);
        builder = builder.add(Attributes.MAX_HEALTH, 11);
        builder = builder.add(Attributes.ARMOR, 0);
        builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
        builder = builder.add(Attributes.FOLLOW_RANGE, 10);
        builder = builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
        builder = builder.add(Attributes.ATTACK_KNOCKBACK, 1);
        return builder;
    }
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.BUCKET) && !this.isBaby()) {
            pPlayer.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, pPlayer, pPlayer.level().random.nextFloat() >= 0.2 ? ModItems.PONOS_BUCKET.get().getDefaultInstance() : Items.MILK_BUCKET.getDefaultInstance());
            pPlayer.setItemInHand(pHand, itemstack1);
            return InteractionResult.sidedSuccess(this.level().isClientSide);

        }
        else if (itemstack.is(Items.WHEAT)) {
            this.level().explode(this,
                    this.getX(), this.getY(), this.getZ(),
                    4.0F, Level.ExplosionInteraction.MOB);
                    pPlayer.discard();
            return InteractionResult.SUCCESS;
        }
        else {return super.mobInteract(pPlayer, pHand);}
    }

    @Override
    public boolean killedEntity(ServerLevel level, LivingEntity target) {
        boolean originalResult = super.killedEntity(level, target);

        // Only convert vanilla cows (not cursed cows)
        if (target.getType() == EntityType.COW && !(target instanceof CursedCowEntity)) {

                this.convertCowToCursedCow(level, (Cow) target);
                return false; // jojo reference

        }

        return originalResult;
    }

    private void convertCowToCursedCow(ServerLevel level, Cow vanillaCow) {
        CursedCowEntity cursedCow = ModEntities.CURSED_COW.get().create(level);
        if (cursedCow == null) return;

        // Transfer properties
        cursedCow.copyPosition(vanillaCow);
        cursedCow.setYHeadRot(vanillaCow.getYHeadRot());
        cursedCow.setXRot(vanillaCow.getXRot());


        // Finalize spawn
        cursedCow.finalizeSpawn(level, level.getCurrentDifficultyAt(cursedCow.blockPosition()),
                MobSpawnType.CONVERSION, null, null);

        // Replace entities
        vanillaCow.discard();
        level.addFreshEntity(cursedCow);

        // Visual and sound effects
        if (!this.isSilent()) {
            level.levelEvent(null, 1026, this.blockPosition(), 0); // Zombie conversion effect
            level.playSound(null, cursedCow.getX(), cursedCow.getY(), cursedCow.getZ(),
                    SoundEvents.ZOMBIE_INFECT, SoundSource.HOSTILE, 1.0F, 1.0F);
        }
    }
}





























