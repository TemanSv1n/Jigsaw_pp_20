package net.svisvi.jigsawpp.item.drist_tnt_stick;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.projectile.DristTntStickProjectile;
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;

import java.util.List;


public class DristTntStickItem extends Item {
    public DristTntStickItem() {
        super(new Item.Properties().stacksTo(32).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(1).saturationMod(0.1f).alwaysEat().effect(new MobEffectInstance(ModEffects.POOP.get(), 40, 0), 0.7F).build()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        //ItemStack itemstack = pPlayer.getItemInHand(pHand);

        InteractionResultHolder<ItemStack> ar = super.use(pLevel, pPlayer, pHand);
        ItemStack itemstack = ar.getObject();

        if (!pPlayer.isShiftKeyDown()) {

            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                DristTntStickProjectile thrownegg = new DristTntStickProjectile(pLevel, pPlayer);
                thrownegg.setItem(itemstack);
                thrownegg.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
                pLevel.addFreshEntity(thrownegg);
                pPlayer.getCooldowns().addCooldown(itemstack.getItem(), 20);
            }

            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            if (!pPlayer.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

        }
        //return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
        return ar;

    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        ItemStack retval = super.finishUsingItem(itemstack, world, entity);
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        DristExplosion.harmfulDristExplode(world, entity.getOnPos(), 2, Level.ExplosionInteraction.NONE, entity);

        return retval;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }
}
