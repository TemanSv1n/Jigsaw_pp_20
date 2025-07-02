package net.svisvi.jigsawpp.block.purgen_factory;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PurgenPiluleBuilder {

    public static List <Item> PILULE_PROGRESSION = new ArrayList<Item>();
    static {
        PILULE_PROGRESSION.add(ModItems.BASIC_PURGEN_PILULE.get());
        PILULE_PROGRESSION.add(ModItems.ADVANCED_PURGEN_PILULE.get());
        PILULE_PROGRESSION.add(ModItems.CRYSTAL_PURGEN_PILULE.get());
    }

    public static List<MobEffect> mediumEffects = new ArrayList<MobEffect>();
    static{
        mediumEffects.add(MobEffects.BLINDNESS);
        mediumEffects.add(MobEffects.CONFUSION);
        mediumEffects.add(MobEffects.DARKNESS);
        mediumEffects.add(MobEffects.DIG_SLOWDOWN);
        mediumEffects.add(MobEffects.GLOWING);
        mediumEffects.add(MobEffects.HUNGER);
        mediumEffects.add(MobEffects.INVISIBILITY);
        mediumEffects.add(MobEffects.MOVEMENT_SLOWDOWN);
        mediumEffects.add(MobEffects.WEAKNESS);
    }
    public static ItemStack build_main(Optional<PurgenFactoryRecipe> o_recipe, ItemStack food1, ItemStack food2, ItemStack catalyst, Level level, BlockPos pos, BlockState state){
        return buildFromPurity_5(buildFromCatalyst_2(buildFromRandom_4(buildFromWorld_3(
                buildFromCatalyst_2(buildFromFood_1(buildFromRecipe_0(o_recipe),
                                food1, food2),
                        catalyst, level),
                level, pos)), catalyst, level));
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

        float multiplication = food1.getItem().getFoodProperties().getNutrition() /4f
                + food1.getItem().getFoodProperties().getNutrition() / 2f * (food1.getItem().getFoodProperties().getSaturationModifier() / 1.2f);
        multiplication*=1.5;

        dur_buff += (int) multiplication / 2;
        purity *= multiplication / 9;


        if (food1.getItem().getFoodProperties().getEffects() != null) {
            for (Pair<MobEffectInstance, Float> pair : food1.getItem().getFoodProperties().getEffects()) {
                efs.add(pair.getFirst());
            }

        }

        if (food2 != ItemStack.EMPTY) {
            multiplication = food2.getItem().getFoodProperties().getNutrition() /4f
                    + food2.getItem().getFoodProperties().getNutrition() /2f * food2.getItem().getFoodProperties().getSaturationModifier() /1.2f;
            multiplication*=1.5;

            dur_buff += (int) multiplication / 2;
            purity *= multiplication / 4;
            purity /= 2.3;

            if (food1.getItem() == food2.getItem()){
                purity /= 9;
            }

            if (food2.getItem().getFoodProperties().getEffects() != null) {
                for (Pair<MobEffectInstance, Float> pair : food2.getItem().getFoodProperties().getEffects()) {
                    efs.add(pair.getFirst());
                }

            }
        } else {
            purity *= 0.6;
        }
        AbstractPiluleItem.setDurationBuff((int) dur_buff, purgen_stack);
        AbstractPiluleItem.setPurity((int) (purity /2), purgen_stack);
        PotionUtils.setCustomEffects(purgen_stack, efs);
        return purgen_stack;

    }

    public static ItemStack buildFromCatalyst_2(ItemStack purgen_stack, ItemStack catalyst, Level level) {
        if (catalyst == ItemStack.EMPTY || !catalyst.is(ItemTags.create(new ResourceLocation("jigsaw_pp:purgen_catalysts")))){
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
        System.out.println(efs);

        AbstractPiluleItem.setDurationBuff((int) dur_buff, purgen_stack);
        AbstractPiluleItem.setPurity((int) purity, purgen_stack);
        if (efs != null) {
            PotionUtils.setCustomEffects(purgen_stack, efs);
        }
        System.out.println(purgen_stack);
        return purgen_stack;



    }

    public static ItemStack buildFromWorld_3(ItemStack purgen_stack, Level level, BlockPos pos){
        double koeff = 1;

        koeff =
                Math.abs(
                        dayTimeCalc(level)
                         *
                 ((16 - level.getMaxLocalRawBrightness(pos)) / 10)
                * moonCalc(level)
                * temperatureCalc(level, pos)
                );

        //System.out.println(koeff);

        int purity = purgen_stack.getOrCreateTag().getInt("purity");

        //System.out.println(purity);
        //koeff = koeff + 0.2;
        purity = (int) (purity * koeff);

        //System.out.println(purity);
        AbstractPiluleItem.setPurity(purity, purgen_stack);
        return purgen_stack;
    }

    public static ItemStack buildFromRandom_4(ItemStack purgen_stack){
        int purity = purgen_stack.getOrCreateTag().getInt("purity");
        Random random = new Random();
        AbstractPiluleItem.setPurity((int) (purity + random.nextInt(18)+14 * random.nextFloat(2.3f)+1.8f), purgen_stack);
        return purgen_stack;
    }
    public static ItemStack buildFromPurity_5(ItemStack purgen_stack){
        Random random = new Random();
        ItemStack retStack;
        int purity = purgen_stack.getOrCreateTag().getInt("purity");
        if (purity == 0){
            purity = 1;
        }
        //System.out.println(purity);
        //upgraded pilule
        List<MobEffectInstance> previous_list = PotionUtils.getCustomEffects(purgen_stack);

        if (purity > 100){
            retStack = purgen_stack;
            AbstractPiluleItem.setPurity(100,retStack);
        }
        if (purity >=65 && purity < 90){
            retStack = new ItemStack(ModItems.ADVANCED_PURGEN_PILULE.get());
            AbstractPiluleItem.setPurity(purgen_stack.getOrCreateTag().getInt("purity"), retStack);
            AbstractPiluleItem.setDurationBuff(purgen_stack.getOrCreateTag().getInt("duration_buff") + (int)((((purity+1%7) * (purity+1/10))*random.nextInt(purity+1/8))/100), retStack);
            PotionUtils.setCustomEffects(retStack, concatEffectsLists(effectsFromPurity(purgen_stack.getOrCreateTag().getInt("purity"), 200+ purgen_stack.getOrCreateTag().getInt("duration_buff")), previous_list));
        } else if (purity >= 90){
            retStack = new ItemStack(ModItems.CRYSTAL_PURGEN_PILULE.get());
            AbstractPiluleItem.setPurity(purgen_stack.getOrCreateTag().getInt("purity"), retStack);
            AbstractPiluleItem.setDurationBuff(purgen_stack.getOrCreateTag().getInt("duration_buff") + (int)((((purity+1%7) * (purity+1/10))*random.nextInt(purity+1/8))/100), retStack);
            PotionUtils.setCustomEffects(retStack, concatEffectsLists(effectsFromPurity(purgen_stack.getOrCreateTag().getInt("purity"), 200+ purgen_stack.getOrCreateTag().getInt("duration_buff")), previous_list));
        } else {
            retStack = purgen_stack;
            PotionUtils.setCustomEffects(retStack, concatEffectsLists(effectsFromPurity(purgen_stack.getOrCreateTag().getInt("purity"), 200+ purgen_stack.getOrCreateTag().getInt("duration_buff")), previous_list));
        }


        return retStack;

    }

    public static List<MobEffectInstance> concatEffectsLists(@Nullable List<MobEffectInstance> list1, @Nullable List<MobEffectInstance> list2){
        if (list1.isEmpty() && list2.isEmpty()){
            return new ArrayList<MobEffectInstance>();
        }
        if (list1.isEmpty()){
            return list2;
        }
        if (list2.isEmpty()){
            return list1;
        }
        return Stream.concat(list1.stream(), list2.stream())
                .collect(Collectors.toList());
    }


    public static double dayTimeCalc(Level level){
        return Math.cbrt((level.dayTime() % 9000) + 1 / 6000) * 2;
    }
    public static double moonCalc(Level level){
        double q1 = 0;
        if (level.getLevelData().isThundering()) {
            q1 = 4;
        } else {
            q1 = 3;
        }
        return (level.dimensionType().moonPhase(level.dayTime()) % q1 - ((level.dimensionType().moonPhase(level.dayTime())) % q1 == 0 ? -1 : 0.5 ));
    }
    public static double temperatureCalc(Level level, BlockPos pos){
        return Math.abs(level.getBiome(pos).value().getBaseTemperature() / 100f);
    }

    public static List<MobEffectInstance> effectsFromPurity(int purity, int duration){
        List<MobEffectInstance> effects = new ArrayList<MobEffectInstance>();
        List<MobEffect> all_effects = ImmutableList.copyOf(ForgeRegistries.MOB_EFFECTS.getValues());
        Random random = new Random();
        if (purity >= 0 && purity < 20){
            for (int i = 0; i < Math.ceil(purity%5f + 1f); i++){
                MobEffectInstance mef = new MobEffectInstance(ModEffects.BAD_EFFECT.get(), checkedNextInt(random, duration * 2, purity)*3, random.nextInt((Math.abs((int)Math.ceil((21 - purity)/4))+1)));
                effects.add(mef);
            }

        }else if (purity >= 20 && purity < 40){
            for (int i = 0; i < Math.ceil(purity%5f + 1f); i++){
                MobEffect ef = all_effects.get(random.nextInt(all_effects.size()));
                for(;ef.isBeneficial();){
                    ef = all_effects.get(random.nextInt(all_effects.size()));
                }

                MobEffectInstance mef = new MobEffectInstance(ef, checkedNextInt(random, duration * 24, purity)*3, random.nextInt((Math.abs((int)Math.ceil((40 - purity)/5))+1)));
                effects.add(mef);
            }

        }else if (purity >= 40 && purity < 60){
            for (int i = 0; i < Math.ceil(purity%5f + 1f); i++){
                MobEffect ef = mediumEffects.get(random.nextInt(mediumEffects.size()));

                MobEffectInstance mef = new MobEffectInstance(ef, checkedNextInt(random, duration * 24, purity)*3, random.nextInt((Math.abs((int)Math.ceil((60 - purity)/5))+1)));
                effects.add(mef);
            }

        }else if (purity >= 60 && purity < 80){
            //no effects
        }else if (purity >= 80 && purity <= 100){
            for (int i = 0; i < Math.ceil(purity%5f + 1f); i++){


                MobEffect ef = all_effects.get(random.nextInt(all_effects.size()));
                for(;!ef.isBeneficial();){
                    ef = all_effects.get(random.nextInt(all_effects.size()));
                }

                MobEffectInstance mef = new MobEffectInstance(ef, checkedNextInt(random, duration * 24, purity)*3, random.nextInt((Math.abs((int)Math.ceil((101 - purity)/4))+1)));
                effects.add(mef);
            }
        }
        return effects;
    }

    public static int checkedNextInt(Random random, double n, int purity){
        return random.nextInt((int)(Math.abs(n / purity + 1)+1));
    }
}
