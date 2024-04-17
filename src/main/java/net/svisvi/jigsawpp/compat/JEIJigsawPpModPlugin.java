package net.svisvi.jigsawpp.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.recipe.ElephantingRecipe;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;

import java.util.List;

@JeiPlugin
public class JEIJigsawPpModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(JigsawPpMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ElephantingCategory(registration.getJeiHelpers().getGuiHelper()), new PurgenCatalystCategory(registration.getJeiHelpers().getGuiHelper()));
        //registration.addRecipeCategories(new PurgenCatalystCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ElephantingRecipe> elephantingRecipes = recipeManager.getAllRecipesFor(ElephantingRecipe.Type.INSTANCE);
        List<PurgenCatalystRecipe> purgenCatalystRecipes = recipeManager.getAllRecipesFor(PurgenCatalystRecipe.Type.INSTANCE);

        registration.addRecipes(ElephantingCategory.ELEPHANTING_TYPE, elephantingRecipes);

        registration.addRecipes(PurgenCatalystCategory.PURGEN_CATALYST_TYPE, purgenCatalystRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }
}
