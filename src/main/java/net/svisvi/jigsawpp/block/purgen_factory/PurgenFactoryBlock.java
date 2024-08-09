package net.svisvi.jigsawpp.block.purgen_factory;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.entity.PurgenFactoryBlockEntity;
import org.jetbrains.annotations.Nullable;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;

import java.util.List;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;


public class PurgenFactoryBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public PurgenFactoryBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(4f, 1f).requiresCorrectToolForDrops().noOcclusion().pushReaction(PushReaction.BLOCK));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }


    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
//        world.scheduleTick(pos, this, 0);
    }
//    @Override
//    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
//        super.tick(pState, pLevel, pPos, pRandom);
//        pLevel.scheduleTick(pPos, this, 20);
//    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("block.jigsaw_pp.purgen_factory.desc"));
        list.add(Component.translatable("block.jigsaw_pp.purgen_factory.desc_two"));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
            return tieredItem.getTier().getLevel() >= 0;
        return false;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }


    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PurgenFactoryBlockEntity(pos, state);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PurgenFactoryBlockEntity be) {
                be.drops();
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

//    @Override
//    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
//        BlockEntity tileentity = world.getBlockEntity(pos);
//        if (tileentity instanceof PurgenFactoryBlockEntity be)
//            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
//        else
//            return 0;
//    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).getItem() instanceof BucketItem bucketItem) {
                BlockEntity entity = pLevel.getBlockEntity(pPos);
                if (entity instanceof PurgenFactoryBlockEntity pf){
                    if ((pf.FLUID_TANK.getFluid().isFluidEqual(pPlayer.getItemInHand(pHand)) || pf.FLUID_TANK.getFluid().isEmpty()) && pf.FLUID_TANK.getFluidAmount() <= 7000){
                    pf.FLUID_TANK.fill(new FluidStack(bucketItem.getFluid(), 1000), IFluidHandler.FluidAction.EXECUTE);

                    if (!pLevel.isClientSide()) {
                        pLevel.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty")), SoundSource.BLOCKS, 0.7f, 1);
                    } else {
                        pLevel.playLocalSound(pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty")), SoundSource.BLOCKS, 0.7f, 1, false);
                    }
                    if (!pPlayer.isCreative()) {
                        pPlayer.getItemInHand(pHand).shrink(1);
                        ItemStack _setstack = BucketItem.getEmptySuccessItem(pPlayer.getItemInHand(pHand), pPlayer);
                        _setstack.setCount(1);
                        ItemHandlerHelper.giveItemToPlayer(pPlayer, _setstack);
                    }
                    }
                }




            } else {

                BlockEntity entity = pLevel.getBlockEntity(pPos);
                if (entity instanceof PurgenFactoryBlockEntity) {
                    NetworkHooks.openScreen(((ServerPlayer) pPlayer), (PurgenFactoryBlockEntity) entity, pPos);
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }
        }



        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.PURGEN_FACTORY_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }

    }

