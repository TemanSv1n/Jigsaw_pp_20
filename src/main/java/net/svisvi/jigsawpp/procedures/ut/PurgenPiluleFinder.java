package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.svisvi.jigsawpp.item.PurgenBundleItem;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.stream.Stream;

public class PurgenPiluleFinder {

    public static ItemStack findPilule(Player pPlayer){ //& remove pilule
        ItemStack find = ItemStack.EMPTY;
        for (ItemStack it: pPlayer.getInventory().items){
            if (it.is(ItemTags.create(new ResourceLocation("jigsaw_pp:pilules")))){
                find = it.copy();
                ItemStack _stktoremove = new ItemStack(it.getItem());
                pPlayer.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, pPlayer.inventoryMenu.getCraftSlots());

                break;
            } else if (it.getItem() == ModItems.PURGEN_BUNDLE.get() && PurgenBundleItem.getContentWeight(it) != 0){
                find = PurgenBundleItem.useFirst(it, pPlayer).copy();
                break;
            }
        }

        find.setCount(1);
        return find;
    }
}
