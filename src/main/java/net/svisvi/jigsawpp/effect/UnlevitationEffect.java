package net.svisvi.jigsawpp.effect;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

public class UnlevitationEffect extends MobEffect {
    //reging
    public int color = -15040820;

    public UnlevitationEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.unlevitation";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //pDuration is just a random number))) Don't use it in the logic
        pLivingEntity.setDeltaMovement(new Vec3(pLivingEntity.getDeltaMovement().x, -0.8 * (pAmplifier + 1 * 0.6), pLivingEntity.getDeltaMovement().z));


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }


}

