package net.svisvi.jigsawpp.item.gas_bottle;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.svisvi.jigsawpp.entity.emitters.AbstractEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.EmitterUtils;
import net.svisvi.jigsawpp.entity.emitters.GasEmitterEntity;
import net.svisvi.jigsawpp.gas.AbstractGasClass;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class AbstractGasBottleItem extends Item {
    public Class<? extends GasEmitterEntity> emitterClass;
    public float radius = 1f;
    public int duration = 400;

    public Class<? extends GasEmitterEntity> getEmitterClass() {
        return emitterClass;
    }
    public void setEmitterClass(Class<? extends GasEmitterEntity> emitterClass) {
        this.emitterClass = emitterClass;
    }

    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

//    public GasEmitterEntity createEmitter(Level level, double x, double y, double z, float radiuss, int durra) throws NoSuchMethodException,
//            IllegalAccessException, InvocationTargetException, InstantiationException {
//        if (emitterClass == null) {
//            throw new IllegalStateException("Emitter class not set!");
//        }
//        // Use getConstructor() if you need to pass arguments
//        Constructor<? extends GasEmitterEntity> constructor = this.emitterClass.getConstructor(Level.class, double.class, double.class, double.class, float.class, int.class);
//        return constructor.newInstance(level, x, y, z, radiuss, durra);
//    }

    public AbstractGasBottleItem() {
        this(GasEmitterEntity.class, 1f, 140);

    }
    public AbstractGasBottleItem(Class<? extends GasEmitterEntity> emitteros) {
        this(emitteros, 1f, 140);
    }
    public AbstractGasBottleItem(Class<? extends GasEmitterEntity> emitteros, float radi, int dura) {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
        this.emitterClass = emitteros;
        this.radius = radi;
        this.duration = dura;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.bottles"));
    }

    public ItemStack getEmptySuccessItem(ItemStack pBucketStack, Player pPlayer) {
        return !pPlayer.getAbilities().instabuild ? new ItemStack(this.getUsedItem()) : pBucketStack;
    }
    public Item getUsedItem(){
        return Items.GLASS_BOTTLE;
    }

    public GasEmitterEntity createEmitter(Level pLevel, BlockPos pPos){
        try {
            GasEmitterEntity gassy = EmitterUtils.createEmitter(this.getEmitterClass(), pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), this.getRadius(), this.getDuration());
            return gassy;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean emptyContents(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, @Nullable BlockHitResult pResult, @Nullable ItemStack container) {
        if (emitterClass != null) {
                GasEmitterEntity gassy = this.createEmitter(pLevel, pPos);
                gassy.setPos(pPos.getX(), pPos.above().getY(), pPos.getZ());
                gassy.setOwner(pPlayer);
                pLevel.addFreshEntity(gassy);
                this.playEmptySound(pPlayer, pLevel, pPos);
                return true;
        }
        return false;
    }

    protected void playEmptySound(@Nullable Player pPlayer, LevelAccessor pLevel, BlockPos pPos) {
        pLevel.playSound(pPlayer, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        //pLevel.gameEvent(pPlayer, GameEvent.FLUID_PLACE, pPos);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(pPlayer, pLevel, itemstack, blockhitresult);
        if (ret != null) return ret;
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos1, direction, itemstack)) {
                {
                    BlockState blockstate = pLevel.getBlockState(blockpos);
                    BlockPos blockpos2 = blockpos;
                    if (this.emptyContents(pPlayer, pLevel, blockpos2, blockhitresult, itemstack)) {
                        if (pPlayer instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) pPlayer, blockpos2, itemstack);
                        }

                        pPlayer.awardStat(Stats.ITEM_USED.get(this));
                        ItemStack itemstack1 = this.getEmptySuccessItem(itemstack, pPlayer);
                        itemstack.shrink(1);
                        pPlayer.addItem(itemstack1);

                        //itemstack.shrink(1);
                        //pPlayer.addItem(getEmptySuccessItem(itemstack, pPlayer));
                        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
                        //return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemstack, pPlayer), pLevel.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
            //super.use(pLevel, pPlayer, pUsedHand);
        }
    }
}
