package net.svisvi.jigsawpp.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.stats.Stats;
import net.svisvi.jigsawpp.entity.projectile.ThrownMadMilkProjectile;

import java.util.List;

/**
 * BadMilkItem
 */
public class MadMilkItem extends Item{
  public MadMilkItem() {
    super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON));
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
    ThrownMadMilkProjectile.shoot(pLevel, pPlayer, 0.6f, 0);
    ItemStack itemstack = pPlayer.getItemInHand(pUsedHand); 
    pPlayer.awardStat(Stats.ITEM_USED.get(this));
    if (!pPlayer.getAbilities().instabuild) {
       itemstack.shrink(1);
    }
    return super.use(pLevel, pPlayer, pUsedHand);
  }
}
