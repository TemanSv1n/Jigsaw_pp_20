package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.world.item.ItemStack;

public interface IPoopProtective {
     public default boolean protectsFromLiquid(){
         return false;
     }
}
