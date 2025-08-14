package net.svisvi.jigsawpp.item.grenades;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.enchantment.ModEnchantments;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.AbstractGrenadeProjectile;
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import net.svisvi.jigsawpp.procedures.ut.GrenadeFinder;
import net.svisvi.jigsawpp.procedures.ut.PurgenPiluleFinder;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class GrenadeLauncherItem extends Item implements CustomArmPoseItem {
    public GrenadeLauncherItem(){
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(250));
    }
    public GrenadeLauncherItem(int dur){
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON).durability(dur));
    }
    public static final int COOLDOWN = 20;
    public int getCooldown(){return COOLDOWN;}
    public static final int COOLDOWN_BREAK = 60;
    public int getCooldownBreak(){return COOLDOWN_BREAK;}
    public static float SPREAD = 0f;
    public float getSpread(){return SPREAD;}

    public static float MISSFIRE = 0.02f;
    public float getMissfire(){return MISSFIRE;}

    //@Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntity, int pTimeLeft) {
        ItemStack purgen_pilule;
        if (pEntity instanceof Player pPlayer) {
            purgen_pilule = GrenadeFinder.findGrenade(pPlayer);
            if (purgen_pilule != ItemStack.EMPTY) {
                pPlayer.getCooldowns().addCooldown(this, this.getCooldown());
                //ExtinguisherUse.Useclick(pPlayer);
                if (!pPlayer.getAbilities().instabuild) {
                    ItemStack _ist = pStack;
                    if (_ist.hurt(1, RandomSource.create(), null)) {
                        _ist.shrink(1);
                        _ist.setDamageValue(0);
                    }
                }
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.COW_MILK, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

                this.new_shoot(pLevel, pPlayer, pStack, purgen_pilule, this.getSpread());
            } else {
                pPlayer.getCooldowns().addCooldown(this, this.getCooldownBreak());
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }


    }
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        //boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, true);
        if (ret != null) return ret;

            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
    }

    public void new_shoot(Level pLevel, Player pPlayer, ItemStack thisStack, ItemStack purgenPilule, float inaccuracy){
        if(!pLevel.isClientSide()) {
            if (purgenPilule.getItem() instanceof AbstractGrenadeItem grenadeitem) {
                Random rand = new Random();
                float randomValue = rand.nextFloat();

                if (randomValue < getMissfire()) { //осечка
                    missfire(pLevel, pPlayer, thisStack, purgenPilule);
                } else {
                    ThrowableItemProjectile grenade = grenadeitem.setProjectile(pLevel, pPlayer, purgenPilule);
                    grenade.setItem(grenadeitem.getUsedItem());
                    grenade.setOwner(pPlayer);
                    grenade.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 2.0F, 0.2f);
                    if (grenade instanceof AbstractGrenadeProjectile ge){
                        ge.setLifeTime(40);
                        ge.setInstaboom(true);
                    }
                    pLevel.addFreshEntity(grenade);
                }
            }
        }

    }

    public void missfire(Level pLevel, Player pPlayer, ItemStack thisStack, ItemStack purgenPilule){
        if(!pLevel.isClientSide()) {
            if (purgenPilule.getItem() instanceof AbstractGrenadeItem grenadeitem) {
                ThrowableItemProjectile grenade = grenadeitem.setProjectile(pLevel, pPlayer, purgenPilule);
                grenade.setItem(grenadeitem.getUsedItem());
                grenade.setOwner(pPlayer);
                if (grenade instanceof AbstractGrenadeProjectile ge){
                    ge.explode();
                }
            }
        }
        pPlayer.getCooldowns().addCooldown(this, this.getCooldown());
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.grenade_launcher.desc"));
    }

    @Override
    @Nullable
    public HumanoidModel.@Nullable ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging && !player.isUsingItem()) {
            return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
        }
        return null;
    }
}
