package net.svisvi.jigsawpp.block.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.svisvi.jigsawpp.block.teapot.BlackTeapotBlock;
import net.svisvi.jigsawpp.block.CoalFossilBlock;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.svisvi.jigsawpp.block.teapot.RawTeapotBlock;
import net.svisvi.jigsawpp.block.teapot.TeapotBlock;
import net.svisvi.jigsawpp.fluid.PonosFluidBlock;
import net.svisvi.jigsawpp.fluid.init.ModFluid;


public class ModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, JigsawPpMod.MODID);
    public static final RegistryObject<Block> COAL_FOSSIL = REGISTRY.register("coal_fossil", () -> new CoalFossilBlock());

    public static final RegistryObject<LiquidBlock> PONOS_WATER_BLOCK = REGISTRY.register("ponos_water_block",
            () -> new PonosFluidBlock());
    public static final RegistryObject<Block> RAW_TEAPOT = REGISTRY.register("raw_teapot", () -> new RawTeapotBlock());

    public static final RegistryObject<Block> TEAPOT = REGISTRY.register("teapot", () -> new TeapotBlock());
    public static final RegistryObject<Block> BLACK_TEAPOT = REGISTRY.register("black_teapot", () -> new BlackTeapotBlock());

}
