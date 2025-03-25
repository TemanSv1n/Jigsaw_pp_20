package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.procedures.ut.Carbonise;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;


public class TeapotDrillItem extends PickaxeItem {
    public TeapotDrillItem() {
        super(new Tier() {
            public int getUses() {
                return 2048;
            }

            public float getSpeed() {
                return 22f;
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
        list.add(Component.translatable("item.jigsaw_pp.teapot_drill.desc"));
    }

//    @Override
//    public float getDestroySpeed(ItemStack p_41004_, @NotNull BlockState p_41005_) {
//
//        float new_speed = this.speed + 10;
//        return (p_41005_.is(BlockTags.MINEABLE_WITH_PICKAXE) ? new_speed : 1.0F);
//    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!(pEntity instanceof Player _playerHasItem ? _playerHasItem.getInventory().contains(new ItemStack(Items.COAL)) : false)) {
            if (pEntity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                _entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 21, 4, false, false));
        }
    }

    @Override
    public boolean mineBlock(ItemStack pStack, Level pLevel, BlockState pState, BlockPos pPos, LivingEntity pEntityLiving) {
        if (Mth.nextInt(pLevel.random, 1, 10) <= 2) {
            if (pEntityLiving instanceof Player _player) {
                ItemStack _stktoremove = new ItemStack(Items.COAL);
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
            }
        }
        if (pLevel instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.POOF, pPos.getX(), pPos.getY(), pPos.getZ(), 5, 0.1, 0.1, 0.1, 0);
            if (!pLevel.isClientSide()) {
                pLevel.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")), SoundSource.BLOCKS, 1, 2);
            } else {
                pLevel.playLocalSound(pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.lava.extinguish")), SoundSource.BLOCKS, 1, 2, false);
            }

        return super.mineBlock(pStack, pLevel, pState, pPos, pEntityLiving);
    }
}
