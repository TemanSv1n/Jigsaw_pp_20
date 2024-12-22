package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.List;
import java.util.Comparator;
import net.minecraft.world.level.LevelAccessor;



public class BeaverStaffHoneyItem extends BeaverStaffItem {
    public BeaverStaffHoneyItem() {
        super(new Tier() {
            public int getUses() {
                return 300;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 1f;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 50;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.HONEY_BLOCK));
            }
        });
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        TesttoolLivingEntityIsHitWithToolProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);
        return retval;
    }
    public class TesttoolLivingEntityIsHitWithToolProcedure {
        public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
            if (entity == null || sourceentity == null)
                return;
            double speed = 0;
            double Yaw = 0;
            speed = 3;
            Yaw = sourceentity.getYRot();
            {
                final Vec3 _center = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    entityiterator.setDeltaMovement(new Vec3((speed * Math.cos((Yaw + 90) * (Math.PI / 180))), (entity.getDeltaMovement().y()), (speed * Math.sin((Yaw + 90) * (Math.PI / 180)))));
                }
            }
        }
    }


}
