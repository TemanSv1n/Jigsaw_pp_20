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
import net.svisvi.jigsawpp.effect.init.ModEffects;

import java.util.List;

public class FriedSoupItem extends Item {

    public FriedSoupItem(){
        super(new Properties().stacksTo(16).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(3)
                        .saturationMod(0.4f).alwaysEat().meat()
                        .effect(new MobEffectInstance(ModEffects.METEORISM.get(), 300, 0), 1F)
                        .effect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0), 1F)
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 340, 0), 1F)
                        .build()));


    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.fried_soup.desc"));
    }
}
