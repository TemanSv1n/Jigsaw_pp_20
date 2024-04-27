package net.svisvi.jigsawpp.block.purgen_factory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PurgenPiluleBuilder {
    public static ItemStack build_main(Optional<PurgenFactoryRecipe> o_recipe, ItemStack food1, ItemStack food2, ItemStack catalyst, Level level, BlockPos pos, BlockState state){
        return buildFromPurity_5(buildFromRandom_4(buildFromWorld_3(
                buildFromCatalyst_2(buildFromFood_1(buildFromRecipe_0(o_recipe),
                                food1, food2),
                        catalyst, level),
                level, pos)));
    }

    public static ItemStack buildFromRecipe_0(Optional<PurgenFactoryRecipe> o_recipe) {
        PurgenFactoryRecipe recipe = o_recipe.get();
        ItemStack output_stack = recipe.getResultItem(null);
        AbstractPiluleItem.setDurationBuff(recipe.getAdditionalTime(null), output_stack);
        AbstractPiluleItem.setPurity(recipe.getPurity(null), output_stack);
        PotionUtils.setCustomEffects(output_stack, recipe.getEffects());
        return output_stack;
    }

    public static ItemStack buildFromFood_1(ItemStack purgen_stack, ItemStack food1, ItemStack food2) {

        int dur_buff = purgen_stack.getOrCreateTag().getInt("duration_buff");
        int purity = purgen_stack.getOrCreateTag().getInt("purity");
        List<MobEffectInstance> efs = new ArrayList<MobEffectInstance>();

        float multiplication = food1.getItem().getFoodProperties().getNutrition()
                + food1.getItem().getFoodProperties().getNutrition() * food1.getItem().getFoodProperties().getSaturationModifier();

        dur_buff += (int) multiplication / 2;
        purity *= multiplication / 10;


        if (food1.getItem().getFoodProperties().getEffects() != null) {
            for (Pair<MobEffectInstance, Float> pair : food1.getItem().getFoodProperties().getEffects()) {
                efs.add(pair.getFirst());
            }

        }

        if (food2 != ItemStack.EMPTY) {
            multiplication = food2.getItem().getFoodProperties().getNutrition()
                    + food2.getItem().getFoodProperties().getNutrition() * food2.getItem().getFoodProperties().getSaturationModifier();

            dur_buff += (int) multiplication / 2;
            purity *= multiplication / 10;
            purity /= 1.9;

            if (food1.getItem() == food2.getItem()){
                purity /= 10;
            }

            if (food2.getItem().getFoodProperties().getEffects() != null) {
                for (Pair<MobEffectInstance, Float> pair : food2.getItem().getFoodProperties().getEffects()) {
                    efs.add(pair.getFirst());
                }

            }
        }
        AbstractPiluleItem.setDurationBuff((int) dur_buff, purgen_stack);
        AbstractPiluleItem.setPurity((int) purity, purgen_stack);
        PotionUtils.setCustomEffects(purgen_stack, efs);
        return purgen_stack;

    }

    public static ItemStack buildFromCatalyst_2(ItemStack purgen_stack, ItemStack catalyst, Level level) {
        if (catalyst == ItemStack.EMPTY){
            return purgen_stack;
        }
        PurgenCatalystRecipe catalyst_recipe = PurgenCatalystRecipeReader.getCurrentRecipe(catalyst, level).get();
        if(catalyst_recipe == null) {
            return purgen_stack;
        }

        int dur_buff = purgen_stack.getOrCreateTag().getInt("duration_buff");
        int purity = purgen_stack.getOrCreateTag().getInt("purity");
        List<MobEffectInstance> efs = new ArrayList<MobEffectInstance>();

        dur_buff *= catalyst_recipe.getAdditionalTimeK(null);
        purity *= catalyst_recipe.getPurityK(null);
        efs = catalyst_recipe.getEffects();

        AbstractPiluleItem.setDurationBuff((int) dur_buff, purgen_stack);
        AbstractPiluleItem.setPurity((int) purity, purgen_stack);
        if (efs != null) {
            PotionUtils.setCustomEffects(purgen_stack, efs);
        }
        return purgen_stack;



    }

    public static ItemStack buildFromWorld_3(ItemStack purgen_stack, Level level, BlockPos pos){
        return purgen_stack;
    }

    public static ItemStack buildFromRandom_4(ItemStack purgen_stack){
        return purgen_stack;
    }
    public static ItemStack buildFromPurity_5(ItemStack purgen_stack){
        return purgen_stack;
    }
}
