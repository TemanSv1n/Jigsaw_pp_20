package net.svisvi.jigsawpp.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.entity.emitters.FartGasEmitterEntity;

public class MeteorismEffect extends MobEffect {
    //reging
    public int color = -34549881;

    public MeteorismEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.meteorism";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {

        Level level = pLivingEntity.level();
        if (pLivingEntity.level().random.nextInt(30) >= 27 ) {
            FartGasEmitterEntity farter = new FartGasEmitterEntity(level, pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), 1.5f * (pAmplifier / 4f + 1), 30);
            farter.setOwner(pLivingEntity);
            farter.setAffectOwner(false);
            level.addFreshEntity(farter);
        }



        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }


}

