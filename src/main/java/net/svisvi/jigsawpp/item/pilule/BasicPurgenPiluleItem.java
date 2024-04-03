package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class BasicPurgenPiluleItem extends AbstractPiluleItem{
    static int duration = 300;
    int duration_buff = 0;

    @Override
    public int duration(){return this.duration;}
    @Override
    public int duration_buff(){
        return this.duration_buff;
    }
    public BasicPurgenPiluleItem(){
        super(new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, 0));
    }
    public BasicPurgenPiluleItem(int durationBuff){
        super(new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, 0));
        duration_buff = durationBuff;
    }
}
