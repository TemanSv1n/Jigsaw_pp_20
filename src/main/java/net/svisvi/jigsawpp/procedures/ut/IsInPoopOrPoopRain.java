package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.world.entity.Entity;
import net.svisvi.jigsawpp.fluid.init.ModFluidTypes;

public class IsInPoopOrPoopRain {
    public static boolean isInPoopOrPoopRain(Entity entity){
        return isInPoop(entity);
    }

    public static boolean isInPoop(Entity entity){
//        System.out.println(entity.isInFluidType(ModFluidTypes.PONOS_TYPE.get()));
//        System.out.println("Pooping brothers");
        return entity.isInFluidType(ModFluidTypes.PONOS_TYPE.get());
        //THIS IS NOT ABOUT HAZMATS OR ETC!!! THIS IS ABOUT STANDING ПО КОЛЕНО В ГОВНЕ
    }
}
