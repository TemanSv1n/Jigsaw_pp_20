package net.svisvi.jigsawpp.block.factory_heater;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
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
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.svisvi.jigsawpp.block.entity.FactoryHeaterBlockEntity;
import net.svisvi.jigsawpp.init.ModDatas;
import net.svisvi.jigsawpp.init.ModSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Collections;


public class FactoryHeaterBlock extends Block implements FactoryHeatProducer {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty IS_FURNACED = IntegerProperty.create("is_furnaced", 0, 1);

    public FactoryHeaterBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(2f, 10f).noOcclusion().requiresCorrectToolForDrops());

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        this.registerDefaultState(this.stateDefinition.any().setValue(IS_FURNACED, 0));
    }
    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("block.jigsaw_pp.factory_heater.desc"));
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.tick(pState, pLevel, pPos, pRandom);
        int i;
        if (pState.getValue(IS_FURNACED) != (i = getFurnacedState(pState, pLevel, pPos))){
            pLevel.setBlock(pPos, pState.setValue(IS_FURNACED, i), 3);

        }
        //getHeat(pState, pLevel, pPos);
        pLevel.scheduleTick(pPos, this, 10);
    }

    public int getFurnacedState(BlockState pState, ServerLevel pLevel, BlockPos pPos) {
        if (ModDatas.factoryHeaterFurnaceModeList.contains((pLevel.getBlockState(BlockPos.containing(pPos.getX(), pPos.getY() - 1, pPos.getZ()))).getBlock())) {
            return 1;

        }
        return 0;
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 0);
    }
    

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(IS_FURNACED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(IS_FURNACED, 0);
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

//    @Override
//    public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
//        if (player.getInventory().getSelected().getItem() instanceof PickaxeItem tieredItem)
//            return tieredItem.getTier().getLevel() >= 0;
//        return false;
//    }

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
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FactoryHeaterBlockEntity be) {
                Containers.dropContents(world, pos, be);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof FactoryHeaterBlockEntity be)
            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
        else
            return 0;
    }

    @Override
    public float getHeat(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockState blockState = pLevel.getBlockState(BlockPos.containing(pPos.getX(), pPos.getY() - 1, pPos.getZ()));
        Block block = blockState.getBlock();
        float f = 0;
        f = ModDatas.factoryHeaterCoefficients.get(block) != null ? ModDatas.factoryHeaterCoefficients.get(block) : 0;
        if (f == 0) {
            return 0;
        } else {

            if (block.getStateDefinition().getProperty("lit") instanceof BooleanProperty _integerProp && _integerProp.getPossibleValues().contains(true)) {
                if (block.getStateDefinition().getProperty("lit") instanceof BooleanProperty _getbp1 && blockState.getValue(_getbp1) == false){
                    f = 0;
                }
            }
            if (f > 0){
                if (pLevel instanceof ServerLevel _level)
                    _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pPos.getX() + 0.5, pPos.getY() + 1, pPos.getZ() + 0.5, 5, 0.1, 0.1, 0.1, 0);
                    pLevel.playSound((Player) null, pPos.getX(), pPos.getY(), pPos.getZ(), ModSounds.FACTORY_HEATER.get(), SoundSource.BLOCKS, 1F, 1F);

            }
            return f;
        }
    }

}
