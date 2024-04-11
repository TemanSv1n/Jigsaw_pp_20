package net.svisvi.jigsawpp.item.purgen_gun;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import net.svisvi.jigsawpp.procedures.ut.PurgenPiluleFinder;
import org.jetbrains.annotations.Nullable;

public class PurgenMachineGunItem extends PurgenGunItem implements CustomArmPoseItem {
    public PurgenMachineGunItem(){
        super(1024);
    }
    public static final int COOLDOWN = 20;
    public int getCooldown(){return COOLDOWN;}
    public static final int COOLDOWN_BREAK = 240;
    public int getCooldownBreak(){return COOLDOWN_BREAK;}
    public static float SPREAD = 14f;
    public float getSpread(){return SPREAD;}
    public static float MISSFIRE = 0.00825f;
    public float getMissfire(){return MISSFIRE;}

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntity, int pTimeLeft) {}

    @Override
    public void onUseTick(Level world, LivingEntity entityLiving, ItemStack itemstack, int count) {
        if (!world.isClientSide() && entityLiving instanceof ServerPlayer entity) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            super.releaseUsing(itemstack, world, entity, count);
        }
    }




    public int getUseDuration(ItemStack pStack) {
        return 16;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }


    @Override
    @Nullable
    public HumanoidModel.@Nullable ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging && !player.isUsingItem()) {
            return HumanoidModel.ArmPose.BOW_AND_ARROW;
        }
        return null;
    }
}
