package net.svisvi.jigsawpp.procedures.radio;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface IRadioActivatable {
    public void activate(@Nullable Level level, @Nullable BlockPos pos, @Nullable Entity entity, @Nullable Entity owner, @Nullable Entity activator, ItemStack thisStack);
}
