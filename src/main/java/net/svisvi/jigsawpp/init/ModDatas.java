package net.svisvi.jigsawpp.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.*;

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

    public static HashSet<Item> TEAPOTS = new HashSet<Item>();
    static {
        TEAPOTS.add(ModItems.TEAPOT.get());
        TEAPOTS.add(ModItems.BLACK_TEAPOT.get());
        TEAPOTS.add(ModItems.RAW_TEAPOT.get());
        TEAPOTS.add(ModItems.FARFUHRER.get());
        TEAPOTS.add(ModItems.TEAPOT_DRILL.get());
        TEAPOTS.add(ModItems.TEAPOD_SPIDER_SPAWN_EGG.get());
        TEAPOTS.add(ModItems.BLACK_TEAPOD_SPIDER_SPAWN_EGG.get());
        TEAPOTS.add(ModItems.RAW_TEAPOD_SPIDER_SPAWN_EGG.get());
        TEAPOTS.add(ModItems.TEAPOT_HAMMER.get());
        TEAPOTS.add(ModItems.TNTPOT.get());
        TEAPOTS.add(ModItems.NUCLEAR_TEAPOT.get());
        TEAPOTS.add(ModItems.TEAPOT_RIFFLE.get());
        TEAPOTS.add(ModItems.TEAPOT_LAUNCHER.get());
    }

    public static HashSet<Block> DEFAULT_TEAPOTS = new HashSet<Block>(){};
    static {
        DEFAULT_TEAPOTS.add(ModBlocks.TEAPOT.get());
        DEFAULT_TEAPOTS.add(ModBlocks.BLACK_TEAPOT.get());
        DEFAULT_TEAPOTS.add(ModBlocks.RAW_TEAPOT.get());
    }

}
