package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

import java.util.List;

public class PigkaxeItem extends PickaxeItem {
    public PigkaxeItem() {
        super(new Tier() {
            public int getUses() {
                return 200;
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
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.PORKCHOP));
            }
        }, 1, -3f, new Item.Properties());
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (!pLevel.isClientSide()) {
            pLevel.playSound(null, pPos, SoundEvents.PIG_HURT, SoundSource.NEUTRAL, 1, 1);
        } else {
            pLevel.playLocalSound(pPos, SoundEvents.PIG_HURT, SoundSource.NEUTRAL, 1, 1, false);
        }
        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);

    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.pigkaxe.desc"));
    }
}
