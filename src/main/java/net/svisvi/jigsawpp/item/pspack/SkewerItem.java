package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class SkewerItem extends SwordItem {
    public SkewerItem() {
        super(new Tier() {
            public int getUses() {
                return 69;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 2.5f;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.IRON_NUGGET));
            }
        }, 3, -2.4f, new Properties());
    }
}
