package net.svisvi.jigsawpp.item.poops;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import net.svisvi.jigsawpp.entity.projectile.PoopsEntity;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Random;

public class PoopsItem extends Item {

    public Item self(){
        return ModItems.POOPS.get();
    }
    public PoopsItem() {
        super(new Item.Properties().stacksTo(16));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.startUsingItem(hand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(slot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Ranged item modifier", (double) 0, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Ranged item modifier", -2.4, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }

    public AbstractArrow newShoot(Level world, LivingEntity entity, Random rand){
        PoopsEntity entityarrow = PoopsEntity.shoot(world, entity, rand, 2.2f, 10, 0);
        return entityarrow;
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityLiving, int timeLeft) {
        if (!world.isClientSide() && entityLiving instanceof ServerPlayer entity) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            if (true) {
                ItemStack stack = ProjectileWeaponItem.getHeldProjectile(entity, e -> e.getItem() == self());
                if (stack == ItemStack.EMPTY) {
                    for (int i = 0; i < entity.getInventory().items.size(); i++) {
                        ItemStack teststack = entity.getInventory().items.get(i);
                        if (teststack != null && teststack.getItem() == self()) {
                            stack = teststack;
                            break;
                        }
                    }
                }
                Random rand = new Random();
                if (entity.getAbilities().instabuild || stack != ItemStack.EMPTY) {

                    itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
                    AbstractArrow entityarrow = newShoot(world, entity, rand);
                    if (entity.getAbilities().instabuild) {
                        entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    } else {
                        if (new ItemStack(self()).isDamageableItem()) {
                            if (stack.hurt(1, world.getRandom(), entity)) {
                                stack.shrink(1);
                                stack.setDamageValue(0);
                                if (stack.isEmpty())
                                    entity.getInventory().removeItem(stack);
                            }
                        } else {
                            stack.shrink(1);
                            if (stack.isEmpty())
                                entity.getInventory().removeItem(stack);
                        }
                    }

                    entity.getCooldowns().addCooldown(itemstack.getItem(), 120);
                        if (!world.isClientSide()) {
                            world.playSound(null, entity.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.launch")), SoundSource.PLAYERS, 1, -1);
                        } else {
                            world.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.firework_rocket.launch")), SoundSource.PLAYERS, 1, -1, false);
                        }
                }
            }
        }
    }
}
