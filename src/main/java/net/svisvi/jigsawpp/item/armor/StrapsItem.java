
package net.svisvi.jigsawpp.item.armor;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public abstract class StrapsItem extends ArmorItem {
    public StrapsItem(Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{7, 15, 11, 6}[type.getSlot().getIndex()] * 6;
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{0, 0, 1, 0}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 9;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(Tags.Items.LEATHER);
            }

            @Override
            public String getName() {
                return "straps";
            }

            @Override
            public float getToughness() {
                return 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0f;
            }
        }, type, properties);
    }


    public static class Chestplate extends StrapsItem {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/straps.png";
        }
    }

}
