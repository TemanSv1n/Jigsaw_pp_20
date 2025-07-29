package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PirateCutlassItem extends SwordItem {
    public PirateCutlassItem() {
        super(new Tier() {
            public int getUses() {
                return 1200;
            }

            public float getSpeed() {
                return 6f;
            }

            public float getAttackDamageBonus() {
                return 2f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 24;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.SUNFLOWER));
            }
        }, 3, -1f, new Properties());
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        CutlassHotEventProcedure.execute(entity, ar.getObject());
        return ar;
    }
    public class CutlassHotEventProcedure {
        public static void execute(Entity entity, ItemStack itemstack) {
            if (entity == null)
                return;
            double speed = 0;
            double Yaw = 0;
            speed = 0.8;
            Yaw = entity.getYRot();
            entity.setDeltaMovement(new Vec3((speed * Math.cos((Yaw + 90) * (Math.PI / 180))), (entity.getDeltaMovement().y()), (speed * Math.sin((Yaw + 90) * (Math.PI / 180)))));
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            {
                ItemStack _ist = itemstack;
                if (_ist.hurt(1, RandomSource.create(), null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
            }
        }
    }
}
