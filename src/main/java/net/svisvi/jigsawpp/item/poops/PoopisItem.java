package net.svisvi.jigsawpp.item.poops;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.PoopisEntity;
import net.svisvi.jigsawpp.entity.projectile.PoopsEntity;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Random;

public class PoopisItem extends PoopsItem {
    @Override
    public Item self(){
        return ModItems.POOPS.get();
    }
    @Override
    public AbstractArrow newShoot(Level world, LivingEntity entity, Random rand){
        PoopisEntity entityarrow = PoopisEntity.shoot(world, entity, rand, 2.2f, 10, 0);
        return entityarrow;
    }
}
