package net.svisvi.jigsawpp.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class DetonatorItem extends Item {
    public DetonatorItem() {
        super(new Item.Properties().stacksTo(56).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(1)
                        .saturationMod(0.1f).alwaysEat().meat()
                        .effect(new MobEffectInstance(MobEffects.UNLUCK, 200, 0), 1F).build()));


    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.detonator.desc"));
    }
}
