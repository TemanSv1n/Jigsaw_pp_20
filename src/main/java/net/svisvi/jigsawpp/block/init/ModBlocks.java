package net.svisvi.jigsawpp.block.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.svisvi.jigsawpp.block.BeaverComputerBlock;
import net.svisvi.jigsawpp.block.BeaweedBlock;
import net.svisvi.jigsawpp.block.MoldBlock;
import net.svisvi.jigsawpp.block.kega.KegaBlock;
import net.svisvi.jigsawpp.block.kega.KegaNullBlock;
import net.svisvi.jigsawpp.block.crops.Beaweed;
import net.svisvi.jigsawpp.block.factory_heater.FactoryHeaterBlock;
import net.svisvi.jigsawpp.block.lenin_bust.LeninBustBlock;
import net.svisvi.jigsawpp.block.purgen_factory.PurgenFactoryBlock;
import net.svisvi.jigsawpp.block.teapot.BlackTeapotBlock;
import net.svisvi.jigsawpp.block.CoalFossilBlock;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

import net.svisvi.jigsawpp.block.teapot.RawTeapotBlock;
import net.svisvi.jigsawpp.block.teapot.TeapotBlock;
import net.svisvi.jigsawpp.block.yoba.YobaBlock;
import net.svisvi.jigsawpp.block.yoba.YobaPoopBlock;
import net.svisvi.jigsawpp.fluid.ponos.PonosFluidBlock;


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
    public static final RegistryObject<Block> PURGEN_FACTORY = REGISTRY.register("purgen_factory", () -> new PurgenFactoryBlock());
    public static final RegistryObject<Block> BEAWEED = REGISTRY.register("beaweed",
            () -> new Beaweed(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()
            ));
    public static final RegistryObject<Block> KEGA = REGISTRY.register("kega", () -> new KegaBlock());
    public static final RegistryObject<Block> KEGA_NULL = REGISTRY.register("kega_null", () -> new KegaNullBlock());
    public static final RegistryObject<Block> YOBA = REGISTRY.register("yoba", () -> new YobaBlock());
    public static final RegistryObject<Block> YOBA_POOP = REGISTRY.register("yoba_poop", () -> new YobaPoopBlock());
    public static final RegistryObject<Block> POOPED_BRICKS = REGISTRY.register("pooped_bricks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Block> LENIN_BUST = REGISTRY.register("lenin_bust", () -> new LeninBustBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASS).strength(0.6F).noOcclusion().sound(SoundType.STONE).ignitedByLava()));
    public static final RegistryObject<Block> MOLD_BLOCK = REGISTRY.register("mold_block", () -> new MoldBlock());
    public static final RegistryObject<Block> BEAVER_COMPUTER = REGISTRY.register("beaver_computer", () -> new BeaverComputerBlock());
}
