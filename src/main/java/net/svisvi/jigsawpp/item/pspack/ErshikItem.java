package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class ErshikItem extends SwordItem {
    public ErshikItem() {
        super(new Tier() {
            public int getUses() {
                return 250;
            }

            public float getSpeed() {
                return 7f;
            }

            public float getAttackDamageBonus() {
                return 5f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 24;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.WARPED_ROOTS));
            }
        }, 0, -2f, new Properties());
    }
}
