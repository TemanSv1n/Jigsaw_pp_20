package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.SoundType;
import net.svisvi.jigsawpp.block.init.ModBlocks;


import java.util.ArrayList;


public class Carbonise {

    public static final ArrayList<SoundType> orgSounds = new ArrayList<>(); //ORG-anics sound list. Kostyl?
    static {
        //update this list when new organic sound types arrive. OR JUST WHEN NORMAL SYSTEM D BE CREATED
        orgSounds.add(SoundType.WOOD);
        orgSounds.add(SoundType.GRASS);
        orgSounds.add(SoundType.LILY_PAD);
        orgSounds.add(SoundType.WET_GRASS);
        orgSounds.add(SoundType.CORAL_BLOCK);
        orgSounds.add(SoundType.BAMBOO);
        orgSounds.add(SoundType.BAMBOO_SAPLING);
        orgSounds.add(SoundType.SWEET_BERRY_BUSH);
        orgSounds.add(SoundType.CROP);
        orgSounds.add(SoundType.HARD_CROP);
        orgSounds.add(SoundType.VINE);
        orgSounds.add(SoundType.NETHER_WART);
        orgSounds.add(SoundType.STEM);
        orgSounds.add(SoundType.NYLIUM);
        orgSounds.add(SoundType.FUNGUS);
        orgSounds.add(SoundType.ROOTS);
        orgSounds.add(SoundType.SHROOMLIGHT);
        orgSounds.add(SoundType.WEEPING_VINES);
        orgSounds.add(SoundType.TWISTING_VINES);
        orgSounds.add(SoundType.WART_BLOCK);
        orgSounds.add(SoundType.NETHER_SPROUTS);
        orgSounds.add(SoundType.CAVE_VINES);
        orgSounds.add(SoundType.SPORE_BLOSSOM);
        orgSounds.add(SoundType.AZALEA);
        orgSounds.add(SoundType.FLOWERING_AZALEA);
        orgSounds.add(SoundType.MOSS_CARPET);
        orgSounds.add(SoundType.MOSS);
        orgSounds.add(SoundType.PINK_PETALS);
        orgSounds.add(SoundType.BIG_DRIPLEAF);
        orgSounds.add(SoundType.SMALL_DRIPLEAF);
        orgSounds.add(SoundType.HANGING_ROOTS);
        orgSounds.add(SoundType.AZALEA_LEAVES);
        orgSounds.add(SoundType.GLOW_LICHEN);
        orgSounds.add(SoundType.FROGLIGHT);
        orgSounds.add(SoundType.FROGSPAWN);
        orgSounds.add(SoundType.MANGROVE_ROOTS);
        orgSounds.add(SoundType.BAMBOO_WOOD);
        orgSounds.add(SoundType.NETHER_WOOD);
        orgSounds.add(SoundType.CHERRY_WOOD);
        orgSounds.add(SoundType.CHERRY_SAPLING);
        orgSounds.add(SoundType.CHERRY_LEAVES);
    }
    public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate){ //that stuff replaces block with MOSS
        if ((blockstate.canOcclude()) && (world.getBlockEntity(BlockPos.containing(x, y, z)) == null)) {
            if (orgSounds.contains(blockstate.getSoundType())) {
                //replacing
                //world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(blockstate));
                world.setBlock(BlockPos.containing(x, y, z), Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(), 3);
                //decorative
                if (world instanceof ServerLevel _level)
                    _level.sendParticles(ParticleTypes.SQUID_INK, x, y, z, 10, 0.5, 0.5, 0.5, 0);
                if (world instanceof Level _level) {

                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")), SoundSource.BLOCKS, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.shoot")), SoundSource.BLOCKS, 0.5f, 1, false);

                    }
                }
                return true;
            } else {return false;}
        } else {return false;}
    }
}
