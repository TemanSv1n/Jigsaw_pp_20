package net.svisvi.jigsawpp.fluid.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.svisvi.jigsawpp.fluid.ponos.PonosFluidType;
import net.svisvi.jigsawpp.JigsawPpMod;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fluids.FluidType;
import org.joml.Vector3f;

public class ModFluidTypes {

//    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation( JigsawPpMod.MODID, "block/drist_ponos_still");
//    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation(JigsawPpMod.MODID, "block/drist_ponos_flow");
//    public static final ResourceLocation PONOS_OVERLAY_RL = new ResourceLocation(JigsawPpMod.MODID, "block/water_overlay");
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, JigsawPpMod.MODID);
    public static final RegistryObject<FluidType> PONOS_TYPE = REGISTRY.register("ponos", () -> new PonosFluidType());
}


//    public static final RegistryObject<FluidType> PONOS_TYPE = ponos_register("ponos",
//            FluidType.Properties.create().lightLevel(5).density(10).viscosity(10).sound(SoundAction.get("drink"),
//                            SoundEvents.ROOTED_DIRT_HIT).fallDistanceModifier(0F).canExtinguish(false).supportsBoating(true).canHydrate(true).motionScale(-0.014D).temperature(400).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
//                    .sound(SoundActions.BUCKET_EMPTY, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk"))).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
    //public static final RegistryObject<FluidType> PONOS_TYPE = REGISTRY.register("ponos", () -> new PonosFluidType());

//    private static RegistryObject<FluidType> ponos_register(String name, FluidType.Properties properties) {
//        return REGISTRY.register(name, () -> new PonosFluidType(WATER_STILL_RL, WATER_FLOWING_RL, PONOS_OVERLAY_RL,
//                0xFFFFFF, new Vector3f(98f / 255f, 42f / 255f, 15f / 255f), properties));
//    }

//    public  static void register(IEventBus Bus ) {
//        REGISTRY.register(Bus);
//    }


