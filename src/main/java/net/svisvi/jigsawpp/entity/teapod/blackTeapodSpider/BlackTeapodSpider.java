package net.svisvi.jigsawpp.entity.teapod.blackTeapodSpider;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.SlonProjectile;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.SlonGunGreenProjectile;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpider;

public class BlackTeapodSpider extends TeapodSpider {
    public BlackTeapodSpider(EntityType<? extends TeapodSpider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        if(!this.level().isClientSide){
            SlonGunGreenProjectile slonProjectile = new SlonGunGreenProjectile(this.level());
            slonProjectile.setDamage(1);
            playSound(SoundEvents.DROWNED_HURT_WATER, 1f, 2f);
            slonProjectile.shoot(this.level(), this, pVelocity * 3, 0);
        }
    }
}
