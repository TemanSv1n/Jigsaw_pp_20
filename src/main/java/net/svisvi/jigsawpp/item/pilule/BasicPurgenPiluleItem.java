package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class BasicPurgenPiluleItem extends AbstractPiluleItem{
    static int duration = 300;
    int duration_buff = 0;
    static int amplifier = 0;
    MobEffectInstance secondary_effect;

    @Override
    public int duration(){return this.duration;}
    @Override
    public int duration_buff(){
        return this.duration_buff;
    }
    public int amplifier(){return this.amplifier;}
    public MobEffectInstance secondary_effect(){return this.secondary_effect;}
    public BasicPurgenPiluleItem(){
        super(new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, 0));
    }

}
