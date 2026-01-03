
package net.svisvi.jigsawpp.item.grenades;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PonosGrenadeProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;

public class PonosGrenadeItem extends AbstractGrenadeItem{
    @Override
    public ThrowableItemProjectile setProjectile(Level pLevel, Player pPlayer, ItemStack stack) {
        return new PonosGrenadeProjectile(pLevel, pPlayer);
    }

    @Override
    public ItemStack getUsedItem() {
        return new ItemStack(ModItems.PONOS_GRENADE_USED.get());
    }
}
