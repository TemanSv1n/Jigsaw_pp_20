package net.svisvi.jigsawpp.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class EggedEffect extends MobEffect {
    public EggedEffect() {
        super(MobEffectCategory.BENEFICIAL, -10743135);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.egged";
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().getBlockState(entity.getOnPos().below(2)).canOcclude()) {
            entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 10, 0, (false), (false)));
            entity.removeEffect(ModEffects.EGGED.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
