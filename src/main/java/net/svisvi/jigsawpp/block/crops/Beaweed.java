package net.svisvi.jigsawpp.block.crops;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.svisvi.jigsawpp.item.init.ModItems;

public class Beaweed extends CropBlock {
    public static final int MAX_AGE = 5;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
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

}
