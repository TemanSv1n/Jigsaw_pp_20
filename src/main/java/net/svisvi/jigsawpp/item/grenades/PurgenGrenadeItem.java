package net.svisvi.jigsawpp.item.grenades;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PurgenGrenadeProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.svisvi.jigsawpp.procedures.ut.PiluleStyles.getPurgenLocalisationFromAmp;

public class PurgenGrenadeItem extends AbstractGrenadeItem{
    @Override
    public ThrowableItemProjectile setProjectile(Level pLevel, Player pPlayer, ItemStack stack) {
        PurgenGrenadeProjectile grenade = new PurgenGrenadeProjectile(pLevel, pPlayer);
        grenade.setAmplifier(getAmplifier(stack));
        return grenade;
    }

    @Override
    public ItemStack getUsedItem() {
        return new ItemStack(ModItems.PURGEN_GRENADE_USED.get());
    }

    public static void setAmplifier(int purity, ItemStack itemStack){
        itemStack.getOrCreateTag().putInt("amplifier", purity);
    }
    public static int getAmplifier(ItemStack itemStack){
        return itemStack.getOrCreateTag().getInt("amplifier");
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(getPurgenLocalisationFromAmp(getAmplifier(pStack)));
    }
}
