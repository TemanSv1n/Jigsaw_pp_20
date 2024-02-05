package net.svisvi.jigsawpp.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraft.client.gui.Gui;

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


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }




    //scripting

}
