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

public class MoldItem extends Item {
    public MoldItem(){
       super(new Properties().stacksTo(64).rarity(Rarity.COMMON)
               .food((new FoodProperties.Builder()).nutrition(1)
                       .saturationMod(0.1f).alwaysEat().meat()
                       .effect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1F).build()));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }
}
