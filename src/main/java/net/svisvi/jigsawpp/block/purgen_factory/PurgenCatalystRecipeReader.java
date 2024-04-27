package net.svisvi.jigsawpp.block.purgen_factory;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;

import java.util.Optional;

public class PurgenCatalystRecipeReader {
    public static Optional<PurgenCatalystRecipe> getCurrentRecipe(ItemStack catalyst, Level level) {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, catalyst);

        return level.getRecipeManager().getRecipeFor(PurgenCatalystRecipe.Type.INSTANCE, inventory, level);
    }
}
