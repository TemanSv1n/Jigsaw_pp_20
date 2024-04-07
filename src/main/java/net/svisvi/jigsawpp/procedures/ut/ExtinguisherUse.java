package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.ExtinguisherProjectile;


public class ExtinguisherUse {
    public static void Useclick(Entity entity) {
        if(entity == null) return;
        for (int i = 0; i < 10; i++ ){
            Entity _shootFrom = entity;
            Level projectileLevel = _shootFrom.level();
            if(!projectileLevel.isClientSide()) {
                Projectile _SpawnEntity = new Object(){
                    public Projectile getArrow(Level level, float damage, int knockback ){
                        AbstractArrow SpawnEntity = new ExtinguisherProjectile(ModEntities.EXTINGUISHER_PROJECTILE.get(), level);
                        SpawnEntity.setBaseDamage(damage);
                        SpawnEntity.setKnockback(knockback);
                        SpawnEntity.setSilent(true);
                        return SpawnEntity;
                    }
                }.getArrow(projectileLevel, 0, 0);
                _SpawnEntity.setPos(_shootFrom.getX(), _shootFrom.getEyeY() - 0.1, _shootFrom.getZ());
                _SpawnEntity.shoot(_shootFrom.getLookAngle().x, _shootFrom.getLookAngle().y, _shootFrom.getLookAngle().z, 1, 30);
                projectileLevel.addFreshEntity(_SpawnEntity);
            }
        }
    }
}
