package net.svisvi.jigsawpp.block.entity.init;


import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.block.entity.FactoryHeaterBlockEntity;
import net.svisvi.jigsawpp.block.init.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JigsawPpMod.MODID);
    //public static final RegistryObject<BlockEntityType<?>> FACTORY_HEATER_BLOCK = register("factory_heater", ModBlocks.FACTORY_HEATER, FactoryHeaterBlockEntity::new);

    private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
        return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
}

