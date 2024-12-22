package net.svisvi.jigsawpp.effect;

import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraft.client.gui.Gui;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

public class PoopEffect extends MobEffect {
    //reging
    public int color = -12372212;
    public PoopEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.poop";
    }
    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof Player)){
            pLivingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4, 0, false, false));
        }


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }




    //scripting
    //conditions
    public static boolean poopAdditionConditionLiquidWay(Entity entity, MobEffectInstance mobEffectInstance){
        boolean logic = true;
        if (PoopProtectionArmorConditions.isProtectedFromLiquid(entity)){
            logic = false;
        }
        return logic;
    }
    public static boolean poopAdditionConditionGasWay(Entity entity, MobEffectInstance mobEffectInstance){
        boolean logic = true;
        if (PoopProtectionArmorConditions.isProtectedFromGas(entity)){
            logic = false;
        }
        return logic;
    }
    public static boolean poopAdditionConditionInnerWay(Entity entity, MobEffectInstance mobEffectInstance){
        boolean logic = true;
        return logic;
    }

    //effect adders
    public static boolean addEffectLiquidWay(Entity entity, MobEffectInstance mobEffectInstance){
        if (poopAdditionConditionLiquidWay(entity,mobEffectInstance)){
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
                return true;
            }

        }
        return false;
    }
    public static boolean addEffectGasWay(Entity entity, MobEffectInstance mobEffectInstance){
        if (poopAdditionConditionGasWay(entity,mobEffectInstance)){
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
                return true;
            }

        }
        return false;
    }
    public static boolean addEffectInnerWay(Entity entity, MobEffectInstance mobEffectInstance){
        if (poopAdditionConditionInnerWay(entity,mobEffectInstance)){
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
                return true;
            }

        }
        return false;
    }
}
