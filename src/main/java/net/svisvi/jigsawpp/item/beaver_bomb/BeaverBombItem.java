package net.svisvi.jigsawpp.item.beaver_bomb;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.server.level.ServerPlayer;
import net.svisvi.jigsawpp.entity.projectile.BeaverBombProjectile;
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;

public class BeaverBombItem extends Item {
    public BeaverBombItem() {
        super(new Item.Properties().durability(1));
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
//
//    @Override
//    public boolean isEnchantable(ItemStack pStack) {
//        return super.isEnchantable(pStack);
//    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.BINDING_CURSE;
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityLiving, int timeLeft) {
        if (!world.isClientSide() && entityLiving instanceof ServerPlayer entity) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            if (true) {
//                BeaverbombEntity entityarrow = BeaverbombEntity.shoot(world, entity, world.getRandom(), 0.8f, 0.1, 1);
//                itemstack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));
//                entityarrow.pickup = AbstractArrow.Pickup.DISALLOWED;

                world.playSound((Player) null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
                if (!world.isClientSide) {
                    BeaverBombProjectile thrownegg = new BeaverBombProjectile(world, entityLiving);
                    thrownegg.setItem(itemstack);
                    if (itemstack.getEnchantmentLevel(Enchantments.BINDING_CURSE) > 0) {
                        thrownegg.setBinding(true);
                    }
                    thrownegg.shootFromRotation(entityLiving, entityLiving.getXRot(), entityLiving.getYRot(), 0.0F, 0.6F, 1.0F);
                    world.addFreshEntity(thrownegg);
                }

                if (entityLiving instanceof Player pPlayer) {
                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    if (!pPlayer.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                } else {
                    itemstack.shrink(1);
                }
            }
        }
    }
}
