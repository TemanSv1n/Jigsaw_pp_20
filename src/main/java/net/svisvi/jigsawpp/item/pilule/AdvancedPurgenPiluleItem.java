package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.world.effect.MobEffectInstance;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class AdvancedPurgenPiluleItem extends AbstractPiluleItem{
    static int duration = 400;
    int duration_buff = 0;

    @Override
    public int duration(){return this.duration;}
    @Override
    public int duration_buff(){
        return this.duration_buff;
    }
    public AdvancedPurgenPiluleItem(){
        super(new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, 1));
    }
    public AdvancedPurgenPiluleItem(int durationBuff){
        super(new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, 0));
        duration_buff = durationBuff;
    }
}
