package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.List;

public class PaddleItem extends SwordItem {
    public PaddleItem() {
        super(new Tier() {
            public int getUses() {
                return 128;
            }

            public float getSpeed() {
                return 5f;
            }

            public float getAttackDamageBonus() {
                return 3f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 10;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.STRING));
            }
        }, 1, -3f, new Properties());
    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.paddle.desc"));
    }
    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (entity == null)
            return;
        if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == ModItems.PADDLE.get()
                && (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.PADDLE.get() && entity.isInWater()) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                _entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20, 0, false, false));
            if (Math.random() < 0.01) {
                {
                    ItemStack _ist = itemstack;
                    if (_ist.hurt(1, RandomSource.create(), null)) {
                        _ist.shrink(1);
                        _ist.setDamageValue(0);
                    }
                }
            }
        }
    }
}



