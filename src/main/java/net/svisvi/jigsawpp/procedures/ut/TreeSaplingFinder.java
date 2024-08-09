package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.svisvi.jigsawpp.item.PurgenBundleItem;
import net.svisvi.jigsawpp.item.init.ModItems;

public class TreeSaplingFinder {

    public static ItemStack findTree(Player pPlayer){ //& remove pilule
        ItemStack find = ItemStack.EMPTY;
        for (ItemStack it: pPlayer.getInventory().items){
            if (it.is(ItemTags.create(new ResourceLocation("minecraft:saplings")))){
                if (it.getCount() >= 8) {
                    find = it.copy();
                    break;
                }


            }
        }

        find.setCount(1);
        return find;
    }
}
