package net.svisvi.jigsawpp.item;

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
import net.svisvi.jigsawpp.effect.init.ModEffects;

import java.util.List;

public class DristoCupItem extends Item {
    public DristoCupItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON)
            .food((new FoodProperties.Builder()).nutrition(-1)
            .saturationMod(-0.1f).alwaysEat().meat()
            .effect(new MobEffectInstance(ModEffects.PURGATIVE.get(), 30 * 20, 2), 1F)
            .build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        double x = pLivingEntity.getX();
        double y = pLivingEntity.getY();
        double z = pLivingEntity.getZ();

        pLivingEntity.push(0, 50, 0);

        if (!pLevel.isClientSide()) {
            pLevel.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1);
        } else {
            pLevel.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1, false);
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }
}
