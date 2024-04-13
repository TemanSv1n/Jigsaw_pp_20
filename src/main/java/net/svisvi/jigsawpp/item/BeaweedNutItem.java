package net.svisvi.jigsawpp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.particles.ModParticleTypes;


import java.util.List;

public class BeaweedNutItem extends Item {
    public BeaweedNutItem() {
        super(new Properties().durability(8).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(0).saturationMod(0f).alwaysEat().build()));
    }
    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 5;
    }
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        //ItemStack itemstack = pPlayer.getItemInHand(pHand);

        super.use(pLevel, pPlayer, pHand);
        ItemStack stack = pPlayer.getItemInHand(pHand);
        double x = pPlayer.getX();
        double y = pPlayer.getY();
        double z = pPlayer.getZ();
        if (pPlayer == null)
            return(null);
        if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
            if ((pPlayer instanceof ServerPlayer _plr ? _plr.getFoodData().getFoodLevel() : 0) > 0) {
                if (pPlayer instanceof ServerPlayer _player)
                    _player.getFoodData().setFoodLevel((int) ((pPlayer instanceof ServerPlayer _plr ? _plr.getFoodData().getFoodLevel() : 0) - 1));
                {
                    ItemStack _ist = stack;
                    if (_ist.hurt(1, RandomSource.create(), null)) {
                        _ist.shrink(1);
                        _ist.setDamageValue(0);
                    }
                }
                if (pLevel instanceof ServerLevel _level) {
                        _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP.get()), x, (y + 1), z, 5, 0.5, 0.5, 0.5, 1);
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_crack")), SoundSource.PLAYERS, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_crack")), SoundSource.PLAYERS, 1, 1, false);
                    }
                }
                if (pPlayer instanceof ServerPlayer _player)
                    _player.getCooldowns().addCooldown(stack.getItem(), 5);
            }
        } else {
            if (pLevel instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP.get()), x, (y + 1), z, 20, 0.5, 0.5, 0.5, 1);
            if (pPlayer instanceof ServerPlayer _player) {
                ItemStack _setstack = new ItemStack(ModItems.BEAWEED_SEEDS.get());
                _setstack.setCount((int) Mth.nextDouble(RandomSource.create(), 1, 3));
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
            if (pLevel instanceof ServerLevel _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_break")), SoundSource.PLAYERS, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.turtle.egg_break")), SoundSource.PLAYERS, 1, 1, false);
                }
            }
            stack.shrink(1);

        }                 return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
    }
}


