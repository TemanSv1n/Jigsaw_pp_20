package net.svisvi.jigsawpp.item.purgen_gun;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import net.svisvi.jigsawpp.procedures.ut.PurgenPiluleFinder;
import org.jetbrains.annotations.Nullable;

public class PurgenGunItem extends Item implements CustomArmPoseItem {
    public PurgenGunItem(){
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(250));
    }

    //@Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntity, int pTimeLeft) {
        ItemStack purgen_pilule;
        if (pEntity instanceof Player pPlayer) {
            purgen_pilule = PurgenPiluleFinder.findPilule(pPlayer);
            if (purgen_pilule != ItemStack.EMPTY) {
                pPlayer.getCooldowns().addCooldown(this, 40);
                //ExtinguisherUse.Useclick(pPlayer);
                ItemStack _ist = pStack;
                if (_ist.hurt(1, RandomSource.create(), null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.COW_MILK, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

                new_shoot(pLevel, pPlayer, purgen_pilule);
            } else {
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

//    public void shoot(Level pLevel, Player pPlayer, ItemStack purgenPilule){
//
//
//        Entity _shootFrom = pPlayer;
//        Level projectileLevel = pLevel;
//        if(!projectileLevel.isClientSide()) {
//            Projectile _SpawnEntity = new Object(){
//                public Projectile getArrow(Level level, float damage, int knockback, ItemStack itemStack){
//                    PurgenPiluleProjectile SpawnEntity = new PurgenPiluleProjectile(ModEntities.PURGEN_PILULE_PROJECTILE.get(), level);
//                    SpawnEntity.setItem(itemStack);
//                    SpawnEntity.setBaseDamage(damage);
//                    SpawnEntity.setKnockback(knockback);
//                    SpawnEntity.setSilent(true);
//                    return SpawnEntity;
//                }
//            }.getArrow(projectileLevel, 0, 0, purgenPilule);
//            _SpawnEntity.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
//            _SpawnEntity.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 0);
//            projectileLevel.addFreshEntity(_SpawnEntity);
//        }
//    }
    public void new_shoot(Level pLevel, Player pPlayer, ItemStack purgenPilule){
        if(!pLevel.isClientSide()) {
            PurgenPiluleProjectile.shoot(pLevel, pPlayer, 1.3f, 0f, purgenPilule);
        }

    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * Returns the action that specifies what animation to play when the item is being used.
     */
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.SPYGLASS;
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
