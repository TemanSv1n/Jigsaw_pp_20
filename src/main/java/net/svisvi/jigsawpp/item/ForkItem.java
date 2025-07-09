package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.svisvi.jigsawpp.procedures.ut.PonosClear;

import java.util.List;

public class ForkItem extends ShovelItem {
    public ForkItem() {
        super(new Tier() {
            public int getUses() {
                return 1488;
            }

            public float getSpeed() {
                return 3f;
            }

            public float getAttackDamageBonus() {
                return 1f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.IRON_INGOT));
            }
        }, 3, -2f, new Item.Properties());
    }


    public void clear(ItemStack itemstack, LevelAccessor world, BlockPos pos){
        ItemStack _ist = itemstack;
        int a = PonosClear.clearPonosLiquid(world, pos.getX(), pos.getY(), pos.getZ(), 3);
        if (_ist.hurt(a, RandomSource.create(), null)) {
            _ist.shrink(1);
            _ist.setDamageValue(0);
        }

    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        clear(entity.getItemInHand(hand), world, new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ()));
        return ar;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.fork.desc"));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        Player p = context.getPlayer();
        clear(p.getItemInHand(context.getHand()), context.getLevel(), new BlockPos(p.getBlockX(), p.getBlockY(), p.getBlockZ()));
        return InteractionResult.SUCCESS;
    }
}
