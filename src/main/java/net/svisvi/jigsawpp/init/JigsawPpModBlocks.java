package net.svisvi.jigsawpp.init;

import net.svisvi.jigsawpp.JigsawPpMod;

import net.svisvi.jigsawpp.block.CoalFossilBlock;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.Block;

public class JigsawPpModBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, JigsawPpMod.MODID);
    public static final RegistryObject<Block> COAL_FOSSIL = REGISTRY.register("coal_fossil", () -> new CoalFossilBlock());
}
