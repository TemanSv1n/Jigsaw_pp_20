package net.svisvi.jigsawpp.block.purgen_factory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;

import java.util.Optional;

public class PurgenCatalystRecipeReader {
    public static Optional<PurgenCatalystRecipe> getCurrentRecipe(ItemStack catalyst, Level level) {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, catalyst);

        return level.getRecipeManager().getRecipeFor(PurgenCatalystRecipe.Type.INSTANCE, inventory, level);
    }
    public static float getMalChanceK(PurgenCatalystRecipe recipe){
        return recipe.getMalChanceK(null);
    }
    public static ItemStack getOutput(PurgenCatalystRecipe recipe){
        //System.out.println(recipe.getResultItem(null).toString());
        return recipe.getResultItem(null).getItem() == Items.BREAD ? ItemStack.EMPTY : recipe.getResultItem(null);
    }
}
