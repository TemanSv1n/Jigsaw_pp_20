package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.svisvi.jigsawpp.item.PurgenBundleItem;
import net.svisvi.jigsawpp.item.grenades.AbstractGrenadeItem;
import net.svisvi.jigsawpp.item.init.ModItems;

public class GrenadeFinder {

    public static ItemStack findGrenade(Player pPlayer){ //& remove grenade
        ItemStack find = ItemStack.EMPTY;
        for (ItemStack it: pPlayer.getInventory().items){
            if (it.getItem() instanceof AbstractGrenadeItem){
                find = it.copy();
                ItemStack _stktoremove = new ItemStack(it.getItem());
                pPlayer.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, pPlayer.inventoryMenu.getCraftSlots());

                break;


            }
        }

        find.setCount(1);
        return find;
    }
}
