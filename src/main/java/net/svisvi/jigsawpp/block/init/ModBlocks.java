package net.svisvi.jigsawpp.block.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.svisvi.jigsawpp.block.BeaweedBlock;
import net.svisvi.jigsawpp.block.KegaBlock;
import net.svisvi.jigsawpp.block.KegaNullBlock;
import net.svisvi.jigsawpp.block.crops.Beaweed;
import net.svisvi.jigsawpp.block.factory_heater.FactoryHeaterBlock;
import net.svisvi.jigsawpp.block.teapot.BlackTeapotBlock;
import net.svisvi.jigsawpp.block.CoalFossilBlock;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.svisvi.jigsawpp.block.teapot.RawTeapotBlock;
import net.svisvi.jigsawpp.block.teapot.TeapotBlock;
import net.svisvi.jigsawpp.fluid.ponos.PonosFluidBlock;
import net.svisvi.jigsawpp.fluid.init.ModFluids;


public class ModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, JigsawPpMod.MODID);
    public static final RegistryObject<Block> COAL_FOSSIL = REGISTRY.register("coal_fossil", () -> new CoalFossilBlock());

    public static final RegistryObject<LiquidBlock> PONOS_FLUID_BLOCK = REGISTRY.register("ponos_fluid_block",
            () -> new PonosFluidBlock());
    public static final RegistryObject<Block> RAW_TEAPOT = REGISTRY.register("raw_teapot", () -> new RawTeapotBlock());

    public static final RegistryObject<Block> TEAPOT = REGISTRY.register("teapot", () -> new TeapotBlock());
    public static final RegistryObject<Block> BEAWEED_BLOCK = REGISTRY.register("beaweed_block", () -> new BeaweedBlock());
    public static final RegistryObject<Block> BLACK_TEAPOT = REGISTRY.register("black_teapot", () -> new BlackTeapotBlock());
    public static final RegistryObject<Block> FACTORY_HEATER = REGISTRY.register("factory_heater", () -> new FactoryHeaterBlock());
    public static final RegistryObject<Block> BEAWEED = REGISTRY.register("beaweed",
            () -> new Beaweed(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()
            ));
    public static final RegistryObject<Block> KEGA = REGISTRY.register("kega", () -> new KegaBlock());
    public static final RegistryObject<Block> KEGA_NULL = REGISTRY.register("kega_null", () -> new KegaNullBlock());

}
