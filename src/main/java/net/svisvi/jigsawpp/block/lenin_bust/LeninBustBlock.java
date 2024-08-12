package net.svisvi.jigsawpp.block.lenin_bust;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ToolActions;
import net.svisvi.jigsawpp.block.entity.LeninBustBlockEntity;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class LeninBustBlock extends BeehiveBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;;
    public static final IntegerProperty HONEY_LEVEL = IntegerProperty.create("honey_level", 0, 5);;
    public static final int MAX_HONEY_LEVELS = 5;
    private static final int SHEARED_HONEYCOMB_COUNT = 3;

    public LeninBustBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(HONEY_LEVEL, 0)).setValue(FACING, Direction.NORTH));
    }
    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("block.jigsaw_pp.lenin_bust.desc"));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(3, 0, 3, 13, 13, 13);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{HONEY_LEVEL, FACING});
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if ((Integer)pState.getValue(HONEY_LEVEL) >= 5) {
            for(int i = 0; i < pRandom.nextInt(1) + 1; ++i) {
                this.trySpawnDripParticles(pLevel, pPos, pState);
            }
        }

    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int i = (Integer)pState.getValue(HONEY_LEVEL);
        boolean flag = false;
        if (i >= MAX_HONEY_LEVELS) {
            Item item = itemstack.getItem();
            if (itemstack.canPerformAction(ToolActions.SHEARS_HARVEST)) {
                pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                dropHoneycomb(pLevel, pPos);
                itemstack.hurtAndBreak(1, pPlayer, (p_49571_) -> {
                    p_49571_.broadcastBreakEvent(pHand);
                });
                flag = true;
                pLevel.gameEvent(pPlayer, GameEvent.SHEAR, pPos);
            } else if (itemstack.is(Items.GLASS_BOTTLE)) {
                itemstack.shrink(1);
                pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (itemstack.isEmpty()) {
                    pPlayer.setItemInHand(pHand, new ItemStack(Items.HONEY_BOTTLE));
                } else if (!pPlayer.getInventory().add(new ItemStack(Items.HONEY_BOTTLE))) {
                    pPlayer.drop(new ItemStack(Items.HONEY_BOTTLE), false);
                }

                flag = true;
                pLevel.gameEvent(pPlayer, GameEvent.FLUID_PICKUP, pPos);
            }

            if (!pLevel.isClientSide() && flag) {
                pPlayer.awardStat(Stats.ITEM_USED.get(item));
            }
        }

        if (flag) {
            if (!CampfireBlock.isSmokeyPos(pLevel, pPos)) {
                if (this.hiveContainsBees(pLevel, pPos)) {
                    this.angerNearbyBees(pLevel, pPos);
                }

                this.releaseBeesAndResetHoneyLevel(pLevel, pState, pPos, pPlayer, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            } else {
                this.resetHoneyLevel(pLevel, pState, pPos);
            }

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    public static void dropHoneycomb(Level pLevel, BlockPos pPos) {
        popResource(pLevel, pPos, new ItemStack(ModItems.MOLD.get(), SHEARED_HONEYCOMB_COUNT));
    }
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LeninBustBlockEntity(pPos, pState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.LENIN_BUST_BE.get(), LeninBustBlockEntity::serverTick);
    }


}
