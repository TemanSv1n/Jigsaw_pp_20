package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;


public class FireClear {
    public static int clearFire(LevelAccessor world, int x, int y, int z, int radii) {
        int sx = 0;
        int sy = 0;
        int sz = 0;
        int counter = 0;
        counter = 0;
        sx = radii * (-1);
        for (int index0 = 0; index0 < (int) (radii * 2); index0++) {
            sy = radii * (-1);
            for (int index1 = 0; index1 < (int) (radii * 2); index1++) {
                sz = radii * (-1);
                for (int index2 = 0; index2 < (int) (radii * 2); index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == Blocks.FIRE) {
                        world.setBlock(BlockPos.containing(x + sx, y + sy, z + sz), Blocks.AIR.defaultBlockState(), 3);
                        counter = counter + 1;
                        world.addParticle(ParticleTypes.POOF, x + sx, y + sy, z + sz, 0, 0, 0);
                        if (world instanceof Level _level) {
                            if (!_level.isClientSide()) {
                                _level.playSound(null, BlockPos.containing(x + sx, y + sy, z + sz), SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.2f, 1);
                            } else {
                                _level.playLocalSound(x + sx, y + sy, z + sz, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.2f, 1, false);
                            }
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
        return counter;
    }

}
