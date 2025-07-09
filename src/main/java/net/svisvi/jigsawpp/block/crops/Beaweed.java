package net.svisvi.jigsawpp.block.crops;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import java.util.Collections;
import java.util.List;

public class Beaweed extends CropBlock {
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_4;
    public Beaweed(Properties Prop) {
        super(Prop);
    }
    @Override
    protected ItemLike getBaseSeedId(){
        return ModItems.BEAWEED_SEEDS.get();
    }
    @Override
    public IntegerProperty getAgeProperty(){
        return AGE;
    }
    @Override
    public int getMaxAge(){
        return MAX_AGE;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder){
        pBuilder.add(AGE);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(BlockTags.DIRT) || pState.is(Blocks.FARMLAND) || pState.is(ModBlocks.BEAWEED_BLOCK.get());
    }
    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 5);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        this.ticking(world, pos, blockstate);
        world.scheduleTick(pos, this, 5);
    }
    public void ticking(Level level, BlockPos pos, BlockState state) {
        if ((state.getBlock().getStateDefinition().getProperty("age") instanceof IntegerProperty _getip1 ? state.getValue(_getip1) : -1) == 2) {
            if (level instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.FLY.get()), pos.getX(), pos.getY(), pos.getZ(), 3, 3, 3, 3, 0.2);
        }
    }
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this));
    }
}


