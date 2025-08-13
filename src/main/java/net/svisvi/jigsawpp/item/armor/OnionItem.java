
package net.svisvi.jigsawpp.item.armor;


import com.google.common.collect.Iterables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.armor.JotaroHatModel;
import net.svisvi.jigsawpp.entity.armor.OnionModel;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.procedures.Honk;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public abstract class OnionItem extends ArmorItem {
    public OnionItem(Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 128}[type.getSlot().getIndex()];
            }

            @Override
            public int getDefenseForType(Type type) {
                return new int[]{0, 0, 0, 1}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 4;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }

            @Override
            public String getName() {
                return "onion";
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

    public static class Helmet extends OnionItem {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("head", new OnionModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.ONION_LAYER)).head, "hat",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
            super.appendHoverText(itemstack, world, list, flag);
            list.add(Component.translatable("item.jigsaw_pp.onion.desc"));
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/onion.png";
        }

////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐



        @Override ////////////////////////////////// LEGACY MCREATOR CODE//////////////////////////卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐卐
        public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
            super.inventoryTick(itemstack, world, entity, slot, selected);
            if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
                if (entity == null)
                    return;
                double T = 0;
                double Zo = 0;
                double Yo = 0;
                double Za = 0;
                double Xo = 0;
                double Ya = 0;
                double Xa = 0;
                itemstack.getOrCreateTag().putDouble("cc2", (itemstack.getOrCreateTag().getDouble("cc2") + 1));
                if (itemstack.getOrCreateTag().getDouble("cc2") % 60 == 0) {
                    {
                        final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
                        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                                .collect(Collectors.toList());
                        for (Entity entityiterator : _entfound) {
                            if (entityiterator instanceof Cow) {
                                if (entityiterator instanceof Mob _entity)
                                    _entity.getNavigation().moveTo((entity.getX()), (entity.getY()), (entity.getZ()), 3);
                                Xo = entity.getX() - entityiterator.getX();
                                Yo = entity.getY() - entityiterator.getY();
                                Zo = entity.getZ() - entityiterator.getZ();
                                if (Math.floor(entity.getX()) <= Math.floor(entityiterator.getX())) {
                                    if (Math.floor(entity.getX()) == Math.floor(entityiterator.getX())) {
                                        if (Math.floor(entity.getY()) == Math.floor(entityiterator.getY())) {
                                            if (Math.floor(entity.getZ()) <= Math.floor(entityiterator.getZ())) {
                                                Za = Math.floor(entity.getZ()) + 0.2;
                                                while (Za <= Math.floor(entityiterator.getZ())) {
                                                    T = (Za - entity.getZ()) / Zo;
                                                    Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
                                                    Xa = entity.getX() + Xo * T;
                                                    if (world instanceof ServerLevel _level)
                                                        _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, Xa, Ya, Za, 1, 0, 0, 0, 0);
                                                    Za = 0.2 + Za;
                                                }
                                            } else {
                                                Za = Math.floor(entityiterator.getZ()) + 0.2;
                                                while (Za <= Math.floor(entity.getZ())) {
                                                    T = (Za - entity.getZ()) / Zo;
                                                    Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
                                                    Xa = entity.getX() + Xo * T;
                                                    if (world instanceof ServerLevel _level)
                                                        _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, Xa, Ya, Za, 1, 0, 0, 0, 0);
                                                    Za = 0.2 + Za;
                                                }
                                            }
                                        } else {
                                            if (Math.floor(entity.getY()) <= Math.floor(entityiterator.getY())) {
                                                Ya = Math.floor(entity.getY()) + 0.2;
                                                while (Ya <= Math.floor(entityiterator.getY())) {
                                                    T = (Ya - entity.getY()) / Yo;
                                                    Xa = entity.getX() + Xo * T;
                                                    Za = entity.getZ() + Zo * T;
                                                    if (world instanceof ServerLevel _level)
                                                        _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, Xa, Ya, Za, 1, 0, 0, 0, 0);
                                                    Ya = 0.2 + Ya;
                                                }
                                            } else {
                                                Ya = Math.floor(entityiterator.getY()) + 0.2;
                                                while (Ya <= Math.floor(entity.getY())) {
                                                    T = (Ya - entity.getY()) / Yo;
                                                    Xa = entity.getX() + Xo * T;
                                                    Za = entity.getZ() + Zo * T;
                                                    if (world instanceof ServerLevel _level)
                                                        _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, Xa, Ya, Za, 1, 0, 0, 0, 0);
                                                    Ya = 0.2 + Ya;
                                                }
                                            }
                                        }
                                    } else {
                                        Xa = Math.floor(entity.getX()) + 0.2;
                                        while (Xa <= Math.floor(entityiterator.getX())) {
                                            T = (Xa - entity.getX()) / Xo;
                                            Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
                                            Za = entity.getZ() + Zo * T;
                                            if (world instanceof ServerLevel _level)
                                                _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, Xa, Ya, Za, 1, 0, 0, 0, 0);
                                            Xa = 0.2 + Xa;
                                        }
                                    }
                                } else {
                                    Xa = entityiterator.getX() + 0.2;
                                    while (Xa < Math.floor(entity.getX())) {
                                        T = (Xa - entity.getX()) / Xo;
                                        Ya = entity.getBbHeight() / 2 + entity.getY() + Yo * T;
                                        Za = entity.getZ() + Zo * T;
                                        if (world instanceof ServerLevel _level)
                                            _level.sendParticles(ParticleTypes.ELECTRIC_SPARK, Xa, Ya, Za, 1, 0, 0, 0, 0);
                                        Xa = 0.2 + Xa;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }








    }
}
