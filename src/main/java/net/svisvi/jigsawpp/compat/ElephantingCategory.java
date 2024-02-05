package net.svisvi.jigsawpp.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.recipe.ElephantingRecipe;

public class ElephantingCategory implements IRecipeCategory<ElephantingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(JigsawPpMod.MODID, "elephanting");
    public static final ResourceLocation TEXTURE = new ResourceLocation(JigsawPpMod.MODID,
            "textures/gui/elephanting_gui.png");
    public static final RecipeType<ElephantingRecipe> ELEPHANTING_TYPE =
            new RecipeType<>(UID, ElephantingRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public ElephantingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.MOSS_ELEPHANT_THUMB.get()));
    }

    @Override
    public RecipeType<ElephantingRecipe> getRecipeType() {
        return ELEPHANTING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.jigsaw_pp.elephanting");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ElephantingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 59).addItemStack(recipe.getResultItem(null));

    }
}
