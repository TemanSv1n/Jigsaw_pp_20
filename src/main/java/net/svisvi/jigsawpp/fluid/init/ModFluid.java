package net.svisvi.jigsawpp.fluid.init;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.item.init.ModItems;

public class ModFluid {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, JigsawPpMod.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_PONOS_WATER =FLUIDS.register("ponos_fluid", () -> new ForgeFlowingFluid.Source(ModFluid.PONOS_WATER_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_PONOS_WATER = FLUIDS.register("flowing_ponos_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluid.PONOS_WATER_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties PONOS_WATER_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.PONOS_WATER_FLUID_TYPE, SOURCE_PONOS_WATER, FLOWING_PONOS_WATER).slopeFindDistance(2).levelDecreasePerBlock(1).block(ModBlocks.PONOS_WATER_BLOCK).bucket(ModItems.PONOS_BUCKET);


    public static void register(IEventBus bus) {
        FLUIDS.register(bus);
    }
}




