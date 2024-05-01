package net.svisvi.jigsawpp.block.entity.init;


import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.block.entity.FactoryHeaterBlockEntity;
import net.svisvi.jigsawpp.block.entity.PurgenFactoryBlockEntity;
import net.svisvi.jigsawpp.block.init.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, JigsawPpMod.MODID);
    //public static final RegistryObject<BlockEntityType<?>> FACTORY_HEATER_BLOCK = register("factory_heater", ModBlocks.FACTORY_HEATER, FactoryHeaterBlockEntity::new);

    public static final RegistryObject<BlockEntityType<PurgenFactoryBlockEntity>> PURGEN_FACTORY_BE = REGISTRY.register("purgen_factory_be", () ->
            BlockEntityType.Builder.of(PurgenFactoryBlockEntity::new,
                    ModBlocks.PURGEN_FACTORY.get()).build(null));


    public static void register(IEventBus eventBus){
        REGISTRY.register(eventBus);
    }
}

