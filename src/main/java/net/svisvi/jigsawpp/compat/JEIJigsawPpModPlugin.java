package net.svisvi.jigsawpp.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.recipe.ElephantingRecipe;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;
import net.svisvi.jigsawpp.recipe.SpaceLiftRecipe;

import java.util.List;

@JeiPlugin
public class JEIJigsawPpModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(JigsawPpMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ElephantingCategory(registration.getJeiHelpers().getGuiHelper()), new PurgenCatalystCategory(registration.getJeiHelpers().getGuiHelper()),
                new PurgenFactoryCategory(registration.getJeiHelpers().getGuiHelper()), new SpaceLiftCategory(registration.getJeiHelpers().getGuiHelper()));
        //registration.addRecipeCategories(new PurgenCatalystCategory(registration.getJeiHelpers().getGuiHelper()));

    }



    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ElephantingRecipe> elephantingRecipes = recipeManager.getAllRecipesFor(ElephantingRecipe.Type.INSTANCE);
        List<PurgenCatalystRecipe> purgenCatalystRecipes = recipeManager.getAllRecipesFor(PurgenCatalystRecipe.Type.INSTANCE);
        List<PurgenFactoryRecipe> purgenFactoryRecipes = recipeManager.getAllRecipesFor(PurgenFactoryRecipe.Type.INSTANCE);
        List<SpaceLiftRecipe> spaceLiftRecipes = recipeManager.getAllRecipesFor(SpaceLiftRecipe.Type.INSTANCE);

        registration.addRecipes(ElephantingCategory.ELEPHANTING_TYPE, elephantingRecipes);

        registration.addRecipes(PurgenCatalystCategory.PURGEN_CATALYST_TYPE, purgenCatalystRecipes);

        registration.addRecipes(PurgenFactoryCategory.PURGEN_FACTORY_TYPE, purgenFactoryRecipes);

        registration.addRecipes(SpaceLiftCategory.SPACE_LIFT_TYPE, spaceLiftRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        IModPlugin.super.registerGuiHandlers(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
        registration.addRecipeCatalyst(new ItemStack(ModItems.SPACE_LIFT.get()), SpaceLiftCategory.SPACE_LIFT_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.PURGEN_FACTORY.get()), PurgenFactoryCategory.PURGEN_FACTORY_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModItems.PURGEN_FACTORY.get()), PurgenCatalystCategory.PURGEN_CATALYST_TYPE);
    }
}
