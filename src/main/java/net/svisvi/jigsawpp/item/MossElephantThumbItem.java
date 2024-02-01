package net.svisvi.jigsawpp.item;


import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.network.chat.Component;

import java.util.List;

public class MossElephantThumbItem extends Item {
    public MossElephantThumbItem() {
        super(new Item.Properties().stacksTo(14).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(-1).saturationMod(1f).alwaysEat().build()));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.moss_elephant_thumb.desc"));
    }
}

