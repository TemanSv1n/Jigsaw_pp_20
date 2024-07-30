package net.svisvi.jigsawpp.block.yoba;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;

import java.lang.reflect.Array;
import java.util.*;

public class YobaBlock extends Block implements Equipable {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    public static final BooleanProperty UPSIDE = BooleanProperty.create("upside");
    public static final IntegerProperty ROTATED = IntegerProperty.create("rotated", 0, 4);
    public static final DirectionProperty REAL_FACING = DirectionProperty.create("real_facing");
    public String MOVE_SOUND(){
        return "block.mud.place";
    }

    public YobaBlock() {
        super(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.GUITAR).sound(SoundType.STEM).strength(10f, 0f));
        this.registerDefaultState((BlockState) ((BlockState) ((BlockState) this.stateDefinition.any()).setValue(FACING, Direction.SOUTH))
                .setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.SOUTH));
        { //kinda of animation, but in blockstates. Couldn't make it STATIC, because of fisting anal mojang pidorasy
            // and default blockstate function requires either block instance or block from registry
            BlockState st = //new BlockState(ModBlocks.YOBA.get(),new ImmutableMap<>())
                    this.defaultBlockState();
            ArrayList<BlockState> north = new ArrayList<BlockState>(4);
            {
                north.add(st.setValue(FACING, Direction.NORTH).setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                north.add(st.setValue(FACING, Direction.DOWN).setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                north.add(st.setValue(FACING, Direction.SOUTH).setValue(UPSIDE, true).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                north.add(st.setValue(FACING, Direction.UP).setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
            }
            ArrayList<BlockState> south = new ArrayList<BlockState>(4);
            {
                south.add(st.setValue(FACING, Direction.SOUTH).setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                south.add(st.setValue(FACING, Direction.DOWN).setValue(UPSIDE, true).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                south.add(st.setValue(FACING, Direction.NORTH).setValue(UPSIDE, true).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                south.add(st.setValue(FACING, Direction.UP).setValue(UPSIDE, true).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
            }
            ArrayList<BlockState> west = new ArrayList<BlockState>(4);
            {
                west.add(st.setValue(FACING, Direction.WEST).setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                west.add(st.setValue(FACING, Direction.SOUTH).setValue(UPSIDE, false).setValue(ROTATED, 2).setValue(REAL_FACING, Direction.NORTH));
                west.add(st.setValue(FACING, Direction.EAST).setValue(UPSIDE, true).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                west.add(st.setValue(FACING, Direction.SOUTH).setValue(UPSIDE, false).setValue(ROTATED, 3).setValue(REAL_FACING, Direction.NORTH));
            }
            ArrayList<BlockState> east = new ArrayList<BlockState>(4);
            {
                east.add(st.setValue(FACING, Direction.EAST).setValue(UPSIDE, false).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                east.add(st.setValue(FACING, Direction.SOUTH).setValue(UPSIDE, false).setValue(ROTATED, 1).setValue(REAL_FACING, Direction.NORTH));
                east.add(st.setValue(FACING, Direction.WEST).setValue(UPSIDE, true).setValue(ROTATED, 0).setValue(REAL_FACING, Direction.NORTH));
                east.add(st.setValue(FACING, Direction.SOUTH).setValue(UPSIDE, false).setValue(ROTATED, 4).setValue(REAL_FACING, Direction.NORTH));
            }

            ROLL_FRAMES.put(Direction.NORTH, north);
            ROLL_FRAMES.put(Direction.SOUTH, south);
            ROLL_FRAMES.put(Direction.WEST, west);
            ROLL_FRAMES.put(Direction.EAST, east);
        }
    }

    public Block getTrace(){
        return Blocks.AIR;
    }
    public Block getDied(){
        return Blocks.HAY_BLOCK;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(new Property[]{FACING, UPSIDE, ROTATED, REAL_FACING});
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return (BlockState)pState.setValue(FACING, pRot.rotate((Direction)pState.getValue(FACING)));
    }
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation((Direction)pState.getValue(FACING)));
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite().getOpposite())
                .setValue(REAL_FACING, pContext.getHorizontalDirection().getOpposite().getOpposite());
    }








    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void entityInside(BlockState blockstate, Level world, BlockPos pos, Entity entity) {
        super.entityInside(blockstate, world, pos, entity);
        if (entity instanceof Fox){
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (!world.isClientSide()) {
                world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.eat")), SoundSource.HOSTILE, 1, 1);
            } else {
                world.playLocalSound(pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.eat")), SoundSource.HOSTILE, 1, 1, false);
            }
        } else {
        entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.CRAMMING)), 20);
        }
    }



    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 5);
    }
    //DEBUGG
//    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
////        BlockState placeState = roll(pState, pLevel, pState.getValue(REAL_FACING));
////        pLevel.setBlock(pPos, placeState, 3);
//        move(pState, pLevel, pPos, new RandomSource() {
//        });
//        return InteractionResult.SUCCESS;
//    }

    //intelligence
    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        move(blockstate, world, pos, random);
        world.scheduleTick(pos, this, 5);
    }


    public HashMap<Direction, ArrayList<BlockState>> ROLL_FRAMES = new HashMap<Direction, ArrayList<BlockState>>();
    //(BlockState)this.defaultBlockState().setValue(FACING, DIRECTION.NORTH)

    public BlockState roll(BlockState blockstate, Level world, Direction moveDirection){
        ArrayList<BlockState> animSchemeList = this.ROLL_FRAMES.get(moveDirection);
        Direction realDirection = blockstate.getValue(REAL_FACING);
        int i = 0;
        i = animSchemeList.indexOf(blockstate.setValue(REAL_FACING, Direction.NORTH));
        if (i == 3){
            i = -1;
        }
        return animSchemeList.get(i + 1).setValue(REAL_FACING, realDirection);

    }

    public BlockState rotateRoll(BlockState blockstate, Level world, Direction rotDirection){
        ArrayList<BlockState> thisList = this.ROLL_FRAMES.get(blockstate.getValue(REAL_FACING));
        ArrayList<BlockState> animSchemeList = this.ROLL_FRAMES.get(rotDirection);
        int i = 0;
        i = thisList.indexOf(blockstate.setValue(REAL_FACING, Direction.NORTH));
        return animSchemeList.get(i).setValue(REAL_FACING, rotDirection);

    }

    public void rotate(BlockState pState, Level pLevel, BlockPos pPos, RandomSource random){
        if (random.nextBoolean()){
            pLevel.setBlock(pPos, rotateRoll(pState, pLevel, pState.getValue(REAL_FACING).getClockWise()), 3);
        } else {
            pLevel.setBlock(pPos, rotateRoll(pState, pLevel, pState.getValue(REAL_FACING).getCounterClockWise()), 3);
        }
    }


    public void moveForward(BlockState pState, Level pLevel, BlockPos pPos){
        pLevel.setBlock(pPos.relative(pState.getValue(REAL_FACING), 1), roll(pState, pLevel, pState.getValue(REAL_FACING)), 3);
        pLevel.setBlock(pPos, getTrace().defaultBlockState(), 3);
            if (!pLevel.isClientSide()) {
                pLevel.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(MOVE_SOUND())), SoundSource.BLOCKS, 1, 1);
            } else {
                pLevel.playLocalSound(pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(MOVE_SOUND())), SoundSource.BLOCKS, 1, 1, false);
            }
    }
    public void moveUpForward(BlockState pState, Level pLevel, BlockPos pPos){
        pLevel.setBlock(pPos.above().relative(pState.getValue(REAL_FACING), 1), roll(pState, pLevel, pState.getValue(REAL_FACING)), 3);
        pLevel.setBlock(pPos, getTrace().defaultBlockState(), 3);
    }
    public void moveDownwards(BlockState pState, Level pLevel, BlockPos pPos){
        pLevel.setBlock(pPos.below(), pState, 3);
        pLevel.setBlock(pPos, getTrace().defaultBlockState(), 3);
    }

    public void move(BlockState pState, Level pLevel, BlockPos pPos, RandomSource random){
            if (!canReplaceBlock(pLevel.getBlockState(pPos.below()))) {
                if (canReplaceBlock(getBlockStateForward(pState, pPos, pLevel))) {
                    if (random.nextFloat() < 0.1F) {
                        rotate(pState, pLevel, pPos, random);
                    }
                    moveForward(pState, pLevel, pPos);
                } else if (canReplaceBlock(getBlockStateForward(pState, pPos.above(), pLevel))) {
                    moveUpForward(pState, pLevel, pPos);
                } else {
                    rotate(pState, pLevel, pPos, random);
                }
            } else {
                moveDownwards(pState, pLevel, pPos);
            }


    }

    public void die(BlockState pState, Level pLevel, BlockPos pPos){
        pLevel.setBlock(pPos, getDied().defaultBlockState(), 3);
    }

    public BlockPos getPosForward(BlockState pState, BlockPos pPos){
        return pPos.relative(pState.getValue(REAL_FACING), 1);
    }

    public BlockState getBlockStateForward(BlockState pState, BlockPos pPos, Level pLevel){
        return pLevel.getBlockState(getPosForward(pState, pPos));
    }

    public static HashSet<TagKey<Block>> replaceableTags = new HashSet<TagKey<Block>>();
    static {
        replaceableTags.add(BlockTags.TALL_FLOWERS);
        replaceableTags.add(BlockTags.FLOWERS);
        replaceableTags.add(BlockTags.UNDERWATER_BONEMEALS);
        replaceableTags.add(BlockTags.CROPS);
        replaceableTags.add(BlockTags.REPLACEABLE);
    }

    public static boolean canReplaceBlock(BlockState pState){
        return (pState.canBeReplaced() | replaceableTags.stream().anyMatch(pState::is));
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}

