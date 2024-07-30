package net.svisvi.jigsawpp.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

public class BeaverSODeffect extends MobEffect {
    public BeaverSODeffect() {
        super(MobEffectCategory.HARMFUL, -10007531);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw.beaver_so_deffect";
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
