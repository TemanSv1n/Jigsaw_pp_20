package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.svisvi.jigsawpp.init.JigsawPpModItems;
import net.svisvi.jigsawpp.procedures.DustPickaxeDrops;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PickaxeDustItem extends PickaxeItem {
    public PickaxeDustItem() {
        super(new Tier() {
            public int getUses() {
                return 1200;
            }

            public float getSpeed() {
                return 10f;
            }

            public float getAttackDamageBonus() {
                return 2f;
            }

            public int getLevel() {
                return 1;
            }

            public int getEnchantmentValue() {
                return 15;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(JigsawPpModItems.DUST_OF_BABUSHKA.get()));
            }
        }, 1, -3f, new Item.Properties().fireResistant());
    }

    @Override
    public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
        boolean retval = super.mineBlock(itemstack, world, blockstate, pos, entity);
        DustPickaxeDrops.execute(world, pos.getX(), pos.getY(), pos.getZ());
        return retval;
    }

    @Override
    public float getDestroySpeed(ItemStack p_41004_, @NotNull BlockState p_41005_) {
        return 7f;
    }


    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }
}
