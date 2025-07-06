package net.svisvi.jigsawpp.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class GruelItem extends Item {

    public GruelItem(){
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(3)
                        .saturationMod(0.4f).alwaysEat().meat()
                        .effect(new MobEffectInstance(MobEffects.CONFUSION, 40, 0), 0.7F).build()));
    }

}
