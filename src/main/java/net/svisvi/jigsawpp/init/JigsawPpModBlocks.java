package net.svisvi.jigsawpp.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.svisvi.jigsawpp.block.CoalFossilBlock;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;
import net.svisvi.jigsawpp.fluid.ModFluid;

public class JigsawPpModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, JigsawPpMod.MODID);
    public static final RegistryObject<Block> COAL_FOSSIL = REGISTRY.register("coal_fossil", () -> new CoalFossilBlock());
    public static final RegistryObject<LiquidBlock> PONOS_WATER_BLOCK = REGISTRY.register("ponos_water_block",
            () -> new LiquidBlock(ModFluid.SOURCE_PONOS_WATER, BlockBehaviour.Properties.copy(Blocks.WATER)));
}
