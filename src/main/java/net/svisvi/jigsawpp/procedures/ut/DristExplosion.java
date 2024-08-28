package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.drist_tnt.IPoopExplosive;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

import javax.annotation.Nullable;
import java.util.Random;

public class DristExplosion {
    public static void dristExplode(Level world, BlockPos pPos, int radii, @Nullable LivingEntity igniter){
        boolean found = false;
        int x = pPos.getX();
        int y = pPos.getY();
        int z = pPos.getZ();
        int sx = 0;
        int sy = 0;
        int sz = 0;
        sx = -4;
//        found = false;
//        for (int index0 = 0; index0 < (int) (8); index0++) {
//            sy = -2;
//            for (int index1 = 0; index1 < (int) (4); index1++) {
//                sz = -4;
//                for (int index2 = 0; index2 < (int) (8); index2++) {
//                    if ((world.getBlockState(new BlockPos(x + sx, y + sy, z + sz))).getMaterial() == net.minecraft.world.level.material.Material.AIR) {
//                        if (Mth.nextInt(new Random(), 0, 100) >= 69) {
//                            world.setBlock(new BlockPos(x + sx, y + sy, z + sz), JigsawModBlocks.PONOS.get().defaultBlockState(), 3);
//                            if (world instanceof ServerLevel _level)
//                                _level.sendParticles((SimpleParticleType) (JigsawModParticleTypes.SHIT.get()), (x + sx), (y + sy), (z + sz), 40, 1, 1, 1, 0);
//                        }
//                    }
//                    sz = sz + 1;
//                }
//                sy = sy + 1;
//            }
//            sx = sx + 1;
//        }
        sx = radii * (-1);
        for (int index0 = 0; index0 < (int) (radii * 2); index0++) {
            sy = radii * (-1);
            for (int index1 = 0; index1 < (int) (radii * 2); index1++) {
                sz = radii * (-1);
                for (int index2 = 0; index2 < (int) (radii * 2); index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).isAir()) {
                        if (Mth.nextInt(world.random, 0, 100) >= 69) {
                            world.setBlock(BlockPos.containing(x + sx, y + sy, z + sz), ModBlocks.PONOS_FLUID_BLOCK.get().defaultBlockState(), 3);
                            world.addParticle(ModParticleTypes.POOP_BUBBLE.get(), x + sx, y + sy, z + sz, 0, 0, 0);
                            if (!world.isClientSide()) {
                                world.playSound(null, BlockPos.containing(x + sx, y + sy, z + sz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.BLOCKS, 0.1f, 1);
                            } else {
                                world.playLocalSound(x + sx, y + sy, z + sz, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.BLOCKS, 0.1f, 1, false);
                            }
                        }
                    } else if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() instanceof IPoopExplosive pooper){
                        pooper.poopChainReactionExplode(world, new BlockPos(x + sx, y + sy, z + sz), igniter);
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
    }

    public static void harmfulDristExplode(Level world, BlockPos pPos, int radii, Level.ExplosionInteraction explosion, @Nullable LivingEntity igniter){
        world.explode(null,pPos.getX(), pPos.getY(), pPos.getZ(),radii, explosion);
        dristExplode(world,pPos,radii, igniter);

    }
    }

