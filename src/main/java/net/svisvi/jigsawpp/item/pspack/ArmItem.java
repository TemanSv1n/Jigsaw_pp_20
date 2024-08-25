package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ArmItem extends SwordItem {
    public ArmItem() {
        super(new Tier() {
            public int getUses() {
                return 128;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return -1f;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 84;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.LEATHER));
            }
        }, 3, 1f, new Item.Properties());
    }
}
