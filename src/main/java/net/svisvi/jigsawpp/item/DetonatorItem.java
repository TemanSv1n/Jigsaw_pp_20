package net.svisvi.jigsawpp.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.procedures.radio.RadioUtils;

import java.util.List;

public class DetonatorItem extends Item {
    public DetonatorItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));


    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.detonator.desc"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        RadioUtils.activateWithItem(pPlayer.getItemInHand(pUsedHand), pLevel, pPlayer.getOnPos(), pPlayer);
        pPlayer.getCooldowns().addCooldown(this, 150);
            if (!pLevel.isClientSide()) {
                pLevel.playSound(null, pPlayer.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.yes")), SoundSource.PLAYERS, 1, -1);
            } else {
                pLevel.playLocalSound(pPlayer.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.yes")), SoundSource.PLAYERS, 1, -1, false);
            }

        if (pLevel instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 30, 0.2, 0.4, 0.2, 1);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
