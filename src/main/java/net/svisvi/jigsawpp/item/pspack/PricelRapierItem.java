package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public class PricelRapierItem extends SwordItem {
    public PricelRapierItem() {
        super(new Tier() {
            public int getUses() {
                return 200;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 1.5f;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 50;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.GOLD_NUGGET));
            }
        }, 3, -2f, new Properties());
    }
}
