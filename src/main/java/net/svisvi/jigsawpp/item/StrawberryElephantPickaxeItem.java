package net.svisvi.jigsawpp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.procedures.StrawberryElephantPickaxeRightclickedOnBlockProcedure;

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
        StrawberryElephantPickaxeRightclickedOnBlockProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getLevel().getBlockState(context.getClickedPos()),
                context.getItemInHand(), context.getPlayer());
        return InteractionResult.SUCCESS;
    }

}
