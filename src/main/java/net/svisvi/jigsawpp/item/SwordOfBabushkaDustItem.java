package net.svisvi.jigsawpp.item;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.svisvi.jigsawpp.procedures.SwordOfBabushkaDustAttackProcedure;

import java.util.List;

public class SwordOfBabushkaDustItem extends SwordItem {
    public SwordOfBabushkaDustItem() {
        super(new Tier() {
            public int getUses() {
                return 1000;
            }
            public float getSpeed() {
                return 2f;
            }
            public float getAttackDamageBonus() {
                return 50f;
            }
            public int getLevel() {
                return 0;
            }
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, 3, -4f, new Item.Properties().fireResistant());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        SwordOfBabushkaDustAttackProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }
}
