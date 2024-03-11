package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IPoopProtective {
     public void onLiquid(ItemStack itemStack, Entity entity);
     public void onGas(ItemStack itemStack, Entity entity);
}
