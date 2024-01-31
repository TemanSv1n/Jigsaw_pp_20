package net.svisvi.jigsawpp.fluid; //java насильник, а я жертва по согласию. -© @hlnikniky

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import org.joml.Vector3f;

import java.rmi.registry.Registry;

public class ModFluidTypes {
    //текстуры
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation( JigsawPpMod.MODID, "block/drist_ponos_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation(JigsawPpMod.MODID, "block/drist_ponos_flow");
    public static final ResourceLocation PONOS_OVERLAY_RL = new ResourceLocation(JigsawPpMod.MODID, "block/water_overlay");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, JigsawPpMod.MODID );

    public static final RegistryObject<FluidType> PONOS_WATER_FLUID_TYPE = register("ponos_fluid",
            FluidType.Properties.create().lightLevel(5).density(10).viscosity(10).sound(SoundAction.get("drink"),
                    SoundEvents.ROOTED_DIRT_HIT));

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