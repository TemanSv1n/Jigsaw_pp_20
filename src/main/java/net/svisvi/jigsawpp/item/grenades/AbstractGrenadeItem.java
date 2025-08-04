package net.svisvi.jigsawpp.item.grenades;

import net.minecraft.server.level.ChunkTaskPriorityQueueSorter.Release;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public abstract class AbstractGrenadeItem extends Item {

    public AbstractGrenadeItem() {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON));
    }

    protected abstract ThrowableItemProjectile setProjectile(Level pLevel, Player pPlayer);
    public abstract ItemStack getUsedItem();

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);

        if (!pLevel.isClientSide) {
            ThrowableItemProjectile grenade = setProjectile(pLevel, pPlayer);
            grenade.setItem(this.getUsedItem());
            grenade.setOwner(pPlayer);
            grenade.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.0F, 1.0F);
            pLevel.addFreshEntity(grenade);
        }

        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if (!pPlayer.getAbilities().instabuild) {
            pStack.shrink(1);
        }
        pPlayer.getCooldowns().addCooldown(this, 20);
        return InteractionResultHolder.consume(pStack);
    }
}
