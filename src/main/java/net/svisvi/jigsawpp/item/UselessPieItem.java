package net.svisvi.jigsawpp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.init.ModEffects;

import java.util.List;

public class UselessPieItem extends Item {
    public UselessPieItem(){
       super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)
               .food((new FoodProperties.Builder()).nutrition(1)
                       .saturationMod(0.1f).alwaysEat().meat()
                       .effect(new MobEffectInstance(ModEffects.POOP.get(), 40, 0), 0.7F).build()));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.useless_pie.desc"));
    }
}
