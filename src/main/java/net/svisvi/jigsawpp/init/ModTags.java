package net.svisvi.jigsawpp.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.svisvi.jigsawpp.JigsawPpMod;

public class ModTags {
    public static class Blocks{
        //public static final TagKey<Block> NAME = tag("abas");
        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(JigsawPpMod.MODID, name));
        }
    }

    public static class Items{
        public static final TagKey<Item> POOP_PROTECTIVE_LIQUID_FULL = tag("poop_protective_liquid_full");
        //where liquid stands for type of poop infection, and full is for ALL costume
        public static final TagKey<Item> POOP_PROTECTIVE_GAS_FULL = tag("poop_protective_gas_full");

        public static final TagKey<Item> POOP_PROTECTIVE_LIQUID_ONE = tag("poop_protective_liquid_one");
        public static final TagKey<Item> POOP_PROTECTIVE_GAS_ONE = tag("poop_protective_gas_one");
        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(JigsawPpMod.MODID, name));
        }
    }
}
