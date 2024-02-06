
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

public abstract class SlaveItem extends ArmorItem {
    public SlaveItem(Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{7, 10, 11, 6}[type.getSlot().getIndex()] * 6;
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{1, 2, 3, 1}[type.getSlot().getIndex()];
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
                return "slave";
            }

            @Override
            public float getToughness() {
                return 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0.1f;
            }
        }, type, properties);
    }

    public static class Helmet extends SlaveItem {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/slave_layer_1.png";
        }

    }

    public static class Chestplate extends SlaveItem {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/slave_layer_1.png";
        }
    }

    public static class Leggings extends SlaveItem {
        public Leggings() {
            super(Type.LEGGINGS, new Properties());
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/slave_layer_2.png";
        }
    }

    public static class Boots extends SlaveItem {
        public Boots() {
            super(Type.BOOTS, new Properties());
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/slave_layer_1.png";
        }
    }
}
