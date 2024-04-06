package net.svisvi.jigsawpp.effect;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.entity.ai.attributes.AttributeMap;


import java.util.List;
import java.util.Random;

public class RandomBadEffect extends MobEffect {
    public static int color = -13434880;
    public RandomBadEffect(MobEffectCategory mobEffectCategory) {
        super(MobEffectCategory.HARMFUL, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.random_bad_effect";
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyInstantenousEffect(Entity source, Entity indirectSource, LivingEntity entity, int amplifier, double health) {
        List<MobEffect> all_effects = ImmutableList.copyOf(ForgeRegistries.MOB_EFFECTS.getValues());
        Random rand = new Random();
        MobEffect ef = all_effects.get(rand.nextInt(all_effects.size()));
        for(;ef.isBeneficial();){
            ef = all_effects.get(rand.nextInt(all_effects.size()));
        }
        entity.addEffect(new MobEffectInstance(ef, (int)health, amplifier));
    }

        @Override
    public void initializeClient(java.util.function.Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance effect) {
                return false;
            }

            @Override
            public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return false;
            }

            @Override
            public boolean isVisibleInGui(MobEffectInstance effect) {
                return false;
            }
        });
    }

}
