
package net.svisvi.jigsawpp.item.armor;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.armor.ChickMaskModel;
import net.svisvi.jigsawpp.entity.armor.JotaroHatModel;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.item.grenades.AbstractGrenadeItem;
import net.svisvi.jigsawpp.item.grenades.PonosGrenadeItem;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ChickMaskItem extends ArmorItem {
    public ChickMaskItem(Type type, Properties properties) {
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
                return "chick_mask";
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

    public static class Helmet extends ChickMaskItem {
        public Helmet() {
            super(Type.HELMET, new Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(),
                            Map.of("head", new ChickMaskModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.CHICK_MASK_LAYER)).head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
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
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "jigsaw_pp:textures/models/armor/chick.png";
        }

        @Override
        public void inventoryTick(ItemStack itemstack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
            super.inventoryTick(itemstack, pLevel, pEntity, pSlotId, pIsSelected);
            Level world = pEntity.level();
            if (!world.isClientSide()) {
                boolean enable = false;
                if (itemstack.getOrCreateTag().getDouble("penis") < 120) {
                    itemstack.getOrCreateTag().putDouble("penis", (itemstack.getOrCreateTag().getDouble("penis") + 1));
                }

                if (!pEntity.isShiftKeyDown()) {
                    itemstack.getOrCreateTag().putBoolean("sus", (false));
                } else if (itemstack.getOrCreateTag().getDouble("penis") >= 30
                        && !itemstack.getOrCreateTag().getBoolean("sus")
                        && pEntity.isShiftKeyDown() && (itemstack.getOrCreateTag().getDouble("penis") < 130)) {

                    itemstack.getOrCreateTag().putDouble("chick", (itemstack.getOrCreateTag().getDouble("chick") + 1));
                    if (world instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.FLAME, pEntity.getX(), pEntity.getY(), pEntity.getZ(), 1, 0.1, 0.1, 0.1, 0.01);
                    }
                    if (pLevel.getRandom().nextFloat() >= 0.97) {
                        pLevel.playSound(null, pEntity.getOnPos(),
                                SoundEvents.CHICKEN_AMBIENT,
                                SoundSource.PLAYERS, 2, 1);
                    }
                    }
                }
            if ((itemstack.getOrCreateTag().getDouble("chick") > 100) && pEntity.isShiftKeyDown()){

                    // Play sound only once (server will broadcast to clients)
                    Level level = (Level) world;
                    level.playSound(null, pEntity.getOnPos(),
                            SoundEvents.CHICKEN_EGG,
                            SoundSource.PLAYERS, 2, -1);
                    if (level instanceof ServerLevel serverLevel){
                        serverLevel.sendParticles(ParticleTypes.POOF, pEntity.getX(), pEntity.getY(), pEntity.getZ(), 100, 0.3, 1, 0.3, 0.01);
                        if (pLevel.getRandom().nextFloat() <= 0.93) {
                            ItemEntity entityToSpawn = new ItemEntity(serverLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), new ItemStack(Items.EGG));
                            entityToSpawn.setPickUpDelay(20);
                            serverLevel.addFreshEntity(entityToSpawn);
                        } else {
                            Item grenadeItem = ModItems.PONOS_GRENADE.get();
                            if (grenadeItem instanceof AbstractGrenadeItem grenadeItemz) {
                                ThrowableItemProjectile grenade = grenadeItemz.setProjectile(pLevel, pEntity instanceof Player pla ? pla : (Player) null, itemstack);
                                grenade.setItem(new ItemStack(grenadeItem));
                                grenade.setOwner(pEntity);
                                grenade.shootFromRotation(pEntity, 90, pEntity.getYRot(), 0.0F, 0.5F, 0.1F);
                                pLevel.addFreshEntity(grenade);
                            }
                        }
                    }

                    itemstack.getOrCreateTag().putBoolean("sus", (true));
                    if (pEntity instanceof Player _player)
                        _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
                    itemstack.getOrCreateTag().putDouble("penis", 0);
                    itemstack.getOrCreateTag().putDouble("chick", 0);
                }
            }
        }
    }