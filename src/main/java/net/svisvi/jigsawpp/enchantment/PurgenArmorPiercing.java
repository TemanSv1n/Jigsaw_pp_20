package net.svisvi.jigsawpp.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenGunItem;

public class PurgenArmorPiercing extends Enchantment {

    public PurgenArmorPiercing() {
        super(
                Rarity.VERY_RARE, // Rarity (COMMON, UNCOMMON, RARE, VERY_RARE)
                EnchantmentCategory.BOW, // Category (WEAPON, ARMOR, DIGGER, etc.)
                new EquipmentSlot[]{EquipmentSlot.MAINHAND} // Applicable slots
        );
    }

    @Override
    public int getMinCost(int level) {
        return 10 + 30 * (level - 1); // Minimum XP cost
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMaxCost(level) + 50; // Maximum XP cost
    }

    @Override
    public int getMaxLevel() {
        return 1; // Maximum level of the enchantment
    }

    // Optional: Can enchant together with other enchantments?
    @Override
    protected boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other);
    }

    // Optional: Allow on specific items only
    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof PurgenGunItem;
    }
}
