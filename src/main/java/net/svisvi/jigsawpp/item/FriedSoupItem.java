package net.svisvi.jigsawpp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class FriedSoupItem extends Item {

    public FriedSoupItem(){
        super(new Properties().stacksTo(64).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(3)
                        .saturationMod(0.4f).alwaysEat().meat()
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 40, 0), 1F).build()));


    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.fried_soup.desc"));
    }
}
