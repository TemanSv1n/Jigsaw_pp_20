package net.svisvi.jigsawpp.fluid.init; //java насильник, а я жертва по согласию. -© @hlnikniky

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.fluid.BaseFluidType;
import org.joml.Vector3f;

import java.rmi.registry.Registry;

public class ModFluidTypes {
    //текстуры
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation( JigsawPpMod.MODID, "block/drist_ponos_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation(JigsawPpMod.MODID, "block/drist_ponos_flow");
    public static final ResourceLocation PONOS_OVERLAY_RL = new ResourceLocation(JigsawPpMod.MODID, "block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, JigsawPpMod.MODID );

    //HERE YOU PUT PROPERTIES, LIKE INVERSED FLOW
    public static final RegistryObject<FluidType> PONOS_WATER_FLUID_TYPE = register("ponos_fluid",
            FluidType.Properties.create().lightLevel(5).density(10).viscosity(10).sound(SoundAction.get("drink"),
                    SoundEvents.ROOTED_DIRT_HIT).fallDistanceModifier(0F).canExtinguish(false).supportsBoating(true).canHydrate(true).motionScale(-0.014D).temperature(400).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk"))).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));

    //Цвет
    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, PONOS_OVERLAY_RL,
                0xFFFFFF, new Vector3f(98f / 255f, 42f / 255f, 15f / 255f), properties));
    }

    public  static void register(IEventBus Bus ) {
        FLUID_TYPES.register(Bus);
    }
}
//AAAA СВИСТИИТ ОТ java