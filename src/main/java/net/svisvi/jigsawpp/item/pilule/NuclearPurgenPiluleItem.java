package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.world.effect.MobEffectInstance;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class NuclearPurgenPiluleItem extends AbstractPiluleItem{
    static int duration = 800;
    int duration_buff = 0;
    static int amplifier = 3;
    static MobEffectInstance effect = new MobEffectInstance(ModEffects.PURGATIVE.get(), duration, amplifier);

    @Override
    public int duration(){return this.duration;}
    @Override
    public int duration_buff(){
        return this.duration_buff;
    }
    public int amplifier(){return this.amplifier;}
    public MobEffectInstance effect(){return this.effect;}
    public NuclearPurgenPiluleItem(){
        super(effect);
    }

}
