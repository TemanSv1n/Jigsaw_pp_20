package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
//import net.minecraft.util.RandomSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;


import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.init.JigsawPpModBlocks;


public class Mossify {
    public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate){ //that stuff replaces block with MOSS
        if (!(blockstate.getBlock() == Blocks.MOSS_BLOCK) && blockstate.is(BlockTags.create(new ResourceLocation("minecraft:sculk_replaceable"))) | blockstate.getBlock() == JigsawPpModBlocks.COAL_FOSSIL.get()){
            //replacing
            //world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(blockstate));
            world.setBlock(BlockPos.containing(x, y, z), Blocks.MOSS_BLOCK.defaultBlockState(), 3);
            //decorative
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 10, 0.5, 0.5, 0.5, 0);
            if (world instanceof Level _level) {

                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bone_meal.use")), SoundSource.BLOCKS, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.enchantment_table.use")), SoundSource.BLOCKS, 1, 1, false);

                }
            }
            return true;
        } else {return false;}
    }
}
