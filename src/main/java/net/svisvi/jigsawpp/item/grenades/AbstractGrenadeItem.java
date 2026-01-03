package net.svisvi.jigsawpp.item.grenades;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
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
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGrenadeItem extends Item implements CustomArmPoseItem {

    public AbstractGrenadeItem() {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON));
    }

    public abstract ThrowableItemProjectile setProjectile(Level pLevel, Player pPlayer, ItemStack stack);
    public abstract ItemStack getUsedItem();

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack pStack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);

        if (!pLevel.isClientSide) {
            ThrowableItemProjectile grenade = this.setProjectile(pLevel, pPlayer, pPlayer.getItemInHand(pUsedHand));
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

    @Override
    @Nullable
    public HumanoidModel.@Nullable ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging) {
            return HumanoidModel.ArmPose.TOOT_HORN;
        }
        return null;
    }
}
