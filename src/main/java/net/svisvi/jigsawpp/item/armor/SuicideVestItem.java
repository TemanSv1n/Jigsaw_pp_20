
package net.svisvi.jigsawpp.item.armor;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.armor.SuicideVestModel;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;
import net.svisvi.jigsawpp.procedures.radio.IRadioActivatable;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class SuicideVestItem extends ArmorItem implements IRadioActivatable {
    public SuicideVestItem(Type type, Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 5;
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
                return "suicide_vest";
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

    @Override
    public void activate(@Nullable Level level, @Nullable BlockPos pos, @Nullable Entity entity, @Nullable Entity owner, @Nullable Entity activator, @Nullable ItemStack thisStack) {
        LivingEntity ownere = null;
        if (owner instanceof LivingEntity) {
            ownere = (LivingEntity) owner;
        }
        DristExplosion.harmfulDristExplode(level, pos, 5, Level.ExplosionInteraction.NONE, ownere);
        if (thisStack != null) {
            thisStack.shrink(1);
        }
        if (owner != null) {
            DamageSource damageSource = new DamageSource(owner.damageSources().explosion(activator, owner).typeHolder(), entity, activator);
            owner.hurt(damageSource, 32500);
        }

    }

    public static class Chestplate extends SuicideVestItem {
        public Chestplate() {
            super(Type.CHESTPLATE, new Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("body", new SuicideVestModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.SUICIDE_VEST_LAYER)).body, "hat",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "head",
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
            list.add(Component.translatable("item.jigsaw_pp.suicide_vest.desc"));
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/suicide_vest.png";
        }

    }
}
