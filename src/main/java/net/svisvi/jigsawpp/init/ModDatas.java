package net.svisvi.jigsawpp.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.world.level.block.AbstractFurnaceBlock.LIT;

public class ModDatas {
    public static List<Block> factoryHeaterFurnaceModeList = new ArrayList<Block>();
    static {
        factoryHeaterFurnaceModeList.add(Blocks.FURNACE);
        factoryHeaterFurnaceModeList.add(Blocks.BLAST_FURNACE);
        factoryHeaterFurnaceModeList.add(Blocks.SMOKER);
        factoryHeaterFurnaceModeList.add(Blocks.CAMPFIRE);
        factoryHeaterFurnaceModeList.add(Blocks.SOUL_CAMPFIRE);
        factoryHeaterFurnaceModeList.add(Blocks.FIRE);
        factoryHeaterFurnaceModeList.add(Blocks.SOUL_FIRE);
        factoryHeaterFurnaceModeList.add(Blocks.LAVA);

    }
    public static void addFactoryHeaterFurnaceModeList(Block block){
        factoryHeaterFurnaceModeList.add(block);
    }

    public static Map<Block, Float> factoryHeaterCoefficients = new HashMap<Block, Float>();
    static {
        factoryHeaterCoefficients.put(Blocks.FURNACE, 1f);
        factoryHeaterCoefficients.put(Blocks.BLAST_FURNACE, 1.5f);
        factoryHeaterCoefficients.put(Blocks.SMOKER, 1.2f);
        factoryHeaterCoefficients.put(Blocks.CAMPFIRE, 0.2f);
        factoryHeaterCoefficients.put(Blocks.SOUL_CAMPFIRE, 0.2f);
        factoryHeaterCoefficients.put(Blocks.FIRE, 0.1f);
        factoryHeaterCoefficients.put(Blocks.SOUL_FIRE, 0.1f);
        factoryHeaterCoefficients.put(Blocks.LAVA, 0.4f);
    }

}
