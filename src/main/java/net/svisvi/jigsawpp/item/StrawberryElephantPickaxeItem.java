package net.svisvi.jigsawpp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.svisvi.jigsawpp.procedures.ut.Carbonise;

import java.util.List;


public class StrawberryElephantPickaxeItem extends PickaxeItem {
    public StrawberryElephantPickaxeItem() {
        super(new Tier() {
            public int getUses() {
                return 700;
            }

            public float getSpeed() {
                return 8f;
            }

            public float getAttackDamageBonus() {
                return 2f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 20;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 2, -2.6f, new Properties());

    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.strawberry_elephant_pickaxe.desc"));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        this.blockClicked(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()),
                context.getItemInHand(), context.getPlayer());
        return InteractionResult.SUCCESS;
    }

    public static void blockClicked(LevelAccessor world, double x, double y, double z, BlockState blockstate, ItemStack itemstack, Entity entity){
        if (Carbonise.execute(world, x, y, z, blockstate)){ //boolean return type, if it works - its true
            // if (blockstate.getSoundType())

            if (entity == null)
                return;
            {
                ItemStack _ist = itemstack;
                if (_ist.hurt(2, RandomSource.create(), null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
            }
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(itemstack.getItem(), 5);
        }

    }

}
