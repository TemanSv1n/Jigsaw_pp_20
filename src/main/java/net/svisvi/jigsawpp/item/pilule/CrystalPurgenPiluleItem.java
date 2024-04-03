package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.world.effect.MobEffectInstance;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class CrystalPurgenPiluleItem extends AbstractPiluleItem{
    static int duration = 500;
    int duration_buff = 0;
    static int amplifier = 2;
    MobEffectInstance secondary_effect;

    @Override
    public int duration(){return this.duration;}
    @Override
    public int duration_buff(){
        return this.duration_buff;
    }
    public int amplifier(){return this.amplifier;}
    public MobEffectInstance secondary_effect(){return this.secondary_effect;}
    public CrystalPurgenPiluleItem(){
        super(new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, 2));
    }

}
