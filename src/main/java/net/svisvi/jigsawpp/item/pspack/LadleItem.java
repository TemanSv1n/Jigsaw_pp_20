package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class LadleItem extends SwordItem {
    public LadleItem() {
        super(new Tier() {
            public int getUses() {
                return 320;
            }

            public float getSpeed() {
                return 4f;
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
                return Ingredient.of(new ItemStack(Items.IRON_NUGGET));
            }
        }, 3, -2.4f, new Properties());
    }
}
