package net.svisvi.jigsawpp.item.grenades;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.GassyGrenadeProjectile;

public class GassyGrenadeItem extends AbstractGrenadeItem{
    @Override
    protected ThrowableItemProjectile setProjectile(Level pLevel, Player pPlayer) {
        return new GassyGrenadeProjectile(pLevel, pPlayer);
    }
}
