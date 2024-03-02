package net.svisvi.jigsawpp.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;


public class dristButtonItem extends Item {

    public dristButtonItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        player.getCooldowns().addCooldown(this, 150);
        String itemName = stack.getDisplayName().getString();
        System.out.println(itemName);
        return super.onItemUseFirst(stack, context);
    }
}

