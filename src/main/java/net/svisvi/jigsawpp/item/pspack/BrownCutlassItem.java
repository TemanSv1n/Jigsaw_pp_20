package net.svisvi.jigsawpp.item.pspack;


import com.google.common.collect.ImmutableList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class BrownCutlassItem extends SwordItem {
    public BrownCutlassItem() {
        super(new Tier() {
            public int getUses() {
                return 1488;
            }

            public float getSpeed() {
                return 6f;
            }

            public float getAttackDamageBonus() {
                return 6f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Items.ROSE_BUSH));
            }
        }, 6, -2.4f, new Properties());
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.brown_cutlass.desc"));
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        //drainPoop(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);
        List<MobEffect> all_effects = ImmutableList.copyOf(ForgeRegistries.MOB_EFFECTS.getValues());
        Random rand = new Random();
        MobEffect ef = all_effects.get(rand.nextInt(all_effects.size()));
        MobEffect bad_ef = all_effects.get(rand.nextInt(all_effects.size()));
        for(;!ef.isBeneficial();){
            ef = all_effects.get(rand.nextInt(all_effects.size()));
        }
        for(;bad_ef.isBeneficial();){
            bad_ef = all_effects.get(rand.nextInt(all_effects.size()));
        }
        entity.addEffect(new MobEffectInstance(bad_ef, rand.nextInt(40, 200), rand.nextInt(2)));
        sourceentity.addEffect(new MobEffectInstance(ef, rand.nextInt(40, 200), rand.nextInt(2)));


        return retval;
    }
}
