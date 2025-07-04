package net.svisvi.jigsawpp.event;

import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.fluid.init.ModFluidTypes;

public class PonosFluidInteractionHandler {
    public static void registerInteractions() {
        System.out.println("GOON REGGED");
        // Custom Fluid + Lava
        FluidInteractionRegistry.addInteraction(
                ModFluidTypes.PONOS_TYPE.get(),
                new FluidInteractionRegistry.InteractionInformation(
                        ForgeMod.LAVA_TYPE.get(),
                        fluidState -> {
                            if (fluidState.isSource()) {
                                return ModBlocks.COAL_FOSSIL.get().defaultBlockState();
                            } else {
                                return ModBlocks.SHIT_BLOCK.get().defaultBlockState();
                            }
                        }
                )
        );

        // Lava + Custom Fluid (same result)
        FluidInteractionRegistry.addInteraction(
                ForgeMod.LAVA_TYPE.get(),
                new FluidInteractionRegistry.InteractionInformation(
                        ModFluidTypes.PONOS_TYPE.get(),
                        fluidState -> {
                            if (fluidState.isSource()) {
                                return ModBlocks.COAL_FOSSIL.get().defaultBlockState();
                            } else {
                                return ModBlocks.SHIT_BLOCK.get().defaultBlockState();
                            }
                        }
                )
        );

        // Example of more complex interaction
//        FluidInteractionRegistry.addInteraction(
//                ModFluids.CUSTOM_FLUID_TYPE.get(),
//                new FluidInteractionRegistry.InteractionInformation(
//                        (level, currentPos, relativePos, currentState) -> {
//                            // Check for blue ice and soul soil like basalt formation
//                            return level.getBlockState(currentPos.below()).is(Blocks.SOUL_SOIL)
//                                    && level.getBlockState(relativePos).is(Blocks.BLUE_ICE);
//                        },
//                        ModBlocks.CUSTOM_BASALT.get().defaultBlockState()
//                )
//        );
    }
}