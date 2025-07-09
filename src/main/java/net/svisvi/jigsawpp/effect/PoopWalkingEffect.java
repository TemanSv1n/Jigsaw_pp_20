package net.svisvi.jigsawpp.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class PoopWalkingEffect extends MobEffect {
    //reging
    public int color = -12372212;

    public PoopWalkingEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.poop_walking";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }


}

