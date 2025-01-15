package net.svisvi.jigsawpp.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.entity.projectile.TreeProjectile;
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import net.svisvi.jigsawpp.procedures.ut.PurgenPiluleFinder;
import net.svisvi.jigsawpp.procedures.ut.TreeSaplingFinder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeLauncherItem extends Item implements CustomArmPoseItem {
    public TreeLauncherItem(){
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(250));
    }
    public TreeLauncherItem(int dur){
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(dur));
    }
    public static final int COOLDOWN = 40;
    public int getCooldown(){return COOLDOWN;}
    public static final int COOLDOWN_BREAK = 120;
    public int getCooldownBreak(){return COOLDOWN_BREAK;}
    public static float SPREAD = 30f;
    public float getSpread(){return SPREAD;}
    public static final int AMOUNT = 8;
    public static int getAmount(){return AMOUNT;}


    //@Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntity, int pTimeLeft) {
        ItemStack purgen_pilule;
        ArrayList<ItemStack> saplings = new ArrayList<ItemStack>(getAmount());
        if (pEntity instanceof Player pPlayer) {
            for (int i = 0; i < getAmount(); i++) {
                purgen_pilule = TreeSaplingFinder.findTree(pPlayer);
                saplings.add(purgen_pilule);
            }
            if (ableToShoot(saplings)) {
                pPlayer.getCooldowns().addCooldown(this, this.getCooldown());
                //ExtinguisherUse.Useclick(pPlayer);
                ItemStack _ist = pStack;
                if (_ist.hurt(1, RandomSource.create(), null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));


                for (ItemStack sapling : saplings){
                    this.new_shoot(pLevel, pPlayer, pStack, sapling.copy(), this.getSpread());
//                    System.out.println(sapling.toString());
                    ItemStack _stktoremove = new ItemStack(sapling.getItem());
                    pPlayer.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, pPlayer.inventoryMenu.getCraftSlots());
                }
                if (pEntity instanceof Player _player)
                    _player.getCooldowns().addCooldown(pStack.getItem(), 160);


            } else {
                pPlayer.getCooldowns().addCooldown(this, this.getCooldownBreak());
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1F, 1F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }


    }
    public static boolean ableToShoot(ArrayList<ItemStack> array){
        ItemStack stack = array.get(0);
        if (array.size() == getAmount()) {
            for (ItemStack item : array) {
                if (!item.is(ItemTags.SAPLINGS) && item.getItem() == stack.getItem()) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
    public void new_shoot(Level pLevel, Player pPlayer, ItemStack thisStack, ItemStack purgenPilule, float inaccuracy){
        if(!pLevel.isClientSide()) {
            Random rand = new Random();
            float randomValue = rand.nextFloat();

            TreeProjectile.shoot(pLevel, pPlayer, 1.3f, inaccuracy, purgenPilule);
        }

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
        list.add(Component.translatable("item.jigsaw_pp.tree_launcher.desc"));
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