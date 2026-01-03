package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.init.ModSounds;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.List;

public class PooPickaxeItem extends PickaxeItem {
    public PooPickaxeItem() {
        super(new Tier() {
            public int getUses() {
                return 1;
            }

            public float getSpeed() {
                return 6f;
            }

            public float getAttackDamageBonus() {
                return 2f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.SWEET_BREAD.get()));
            }
        }, 1, -3f, new Properties().food((new FoodProperties.Builder()).nutrition(1)
                .saturationMod(0.1f).alwaysEat().meat().build()));
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide()) {
            pLevel.playSound(null, pPos, ModSounds.FART.get(), SoundSource.NEUTRAL, 0.5f, 1);
        } else {
            pLevel.playLocalSound(pPos, ModSounds.FART.get(), SoundSource.NEUTRAL, 0.5f, 1, false);
        }
        if (pStack.getDamageValue() == pStack.getMaxDamage() - 1){
            if (pEntityLiving instanceof Player _player) {
                ItemStack _setstack = new ItemStack(Items.STICK);
                _setstack.setCount(2);
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);

    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 32;
    }
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        this.stopUsing(pLivingEntity);
    }

    private void stopUsing(LivingEntity pUser) {
        pUser.playSound(ModSounds.FART.get(), 0.5F, 1.0F);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.EAT;
    }

//    @Override
//    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
//        super.appendHoverText(itemstack, world, list, flag);
//        list.add(Component.translatable("item.jigsaw_pp.pigkaxe.desc"));
//    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        //super.finishUsingItem()
//        pLevel.playSound((Player)null, this.getX(), this.getY(), this.getZ(), this.getEatingSound(pFood), SoundSource.NEUTRAL, 1.0F, 1.0F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.4F);
        if (!(entity instanceof Player) || !((Player)entity).getAbilities().instabuild) {
            if (itemstack.hurt(5, RandomSource.create(), null)) {
                itemstack.shrink(1);
                itemstack.setDamageValue(0);
                if (entity instanceof Player _player) {
                    ItemStack _setstack = new ItemStack(Items.STICK);
                    _setstack.setCount(2);
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                }
            }
        }

        entity.gameEvent(GameEvent.EAT);
            if (entity instanceof Player) {
                ((Player) entity).getCooldowns().addCooldown(this, 30);
                if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + ModItems.POO_PICKAXE.get().getFoodProperties().getNutrition() <= 20) {
                    if (entity instanceof Player _player) {
                        _player.getFoodData().setFoodLevel(((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + ModItems.POO_PICKAXE.get().getFoodProperties().getNutrition()));
                        _player.addEffect(new MobEffectInstance(ModEffects.POOP.get(), 40, 0));
                    }
                }
            }



        this.stopUsing(entity);



        return itemstack;

    }
}
