package net.svisvi.jigsawpp.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.emitters.EmitterProjectile;
import net.svisvi.jigsawpp.entity.emitters.GasEmitterEntity;
import net.svisvi.jigsawpp.entity.projectile.TeapotMissileEntity;
import net.svisvi.jigsawpp.item.gas_bottle.AbstractGasBottleItem;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class FartGunItem extends Item implements CustomArmPoseItem {
    public FartGunItem() {
        super(new Properties().durability(235));
    }

    @Override
    @Nullable
    public HumanoidModel.@Nullable ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging && !player.isUsingItem()) {
            return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
        }
        return null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.startUsingItem(hand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.fart_gun.desc"));
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
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityLiving, int timeLeft) {
        if (!world.isClientSide() && entityLiving instanceof ServerPlayer entity) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            if (true) {
//                    NuclearTeapotMissileEntity entityarrow = NuclearTeapotMissileEntity.shoot(world, entity, new Random(), 0.5f, 1, 1);
//                    itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
                ItemStack stack = ProjectileWeaponItem.getHeldProjectile(entity, e -> e.getItem() instanceof AbstractGasBottleItem);
                if (stack == ItemStack.EMPTY) {
                    for (int i = 0; i < entity.getInventory().items.size(); i++) {
                        ItemStack teststack = entity.getInventory().items.get(i);
                        if (teststack != null && teststack.getItem() instanceof AbstractGasBottleItem) {
                            stack = teststack;
                            break;
                        }
                    }
                }
                if (entity.getAbilities().instabuild || stack != ItemStack.EMPTY) {

                    if (stack.getItem() instanceof AbstractGasBottleItem bottle) {
                        GasEmitterEntity emi = bottle.createEmitter(world, entityLiving.getOnPos().above());
                        EmitterProjectile entityarrow = EmitterProjectile.shoot(world, entity, new Random(), 1.5f, 1, 1, emi, true);
                        entityarrow.setMaxLifeTime(200);
                        //TeapotMissileEntity.shoot(world, entity, new Random(), 1.5f, 1, 1);
                    }

                    itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));

                    if (entity.getAbilities().instabuild) {
                        //entityarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    } else {
                        if (stack.getItem() instanceof AbstractGasBottleItem bottle){
                            entity.getInventory().add(new ItemStack(bottle.getUsedItem()));
                        }
                        stack.shrink(1);
                        if (stack.isEmpty())
                            entity.getInventory().removeItem(stack);
                    }

                    entity.getCooldowns().addCooldown(itemstack.getItem(), 2);
                }
                }
            }
        }
    }
