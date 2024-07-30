package net.svisvi.jigsawpp.item;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class BeaverKnifeItem extends SwordItem {
    public BeaverKnifeItem() {
        super(new Tier() {
            public int getUses() {
                return 128;
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
                return 5;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        }, 3, -2.2000000000000002f, new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        {
            ItemStack _ist = itemstack;
            if (_ist.hurt(1, entity.level().random, null)) {
                _ist.shrink(1);
                _ist.setDamageValue(0);
            }
        }
        if (Mth.nextInt(entity.level().random, 1, 2) == 1) {
            itemstack.getOrCreateTag().putBoolean("chance", (true));
            if (entity.level().isClientSide())
                Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
        } else {
            itemstack.getOrCreateTag().putBoolean("chance", (false));
        }
        return retval;
    }
}
