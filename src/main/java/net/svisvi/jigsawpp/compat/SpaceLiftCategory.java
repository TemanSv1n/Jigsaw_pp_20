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
import net.svisvi.jigsawpp.recipe.SpaceLiftRecipe;

public class SpaceLiftCategory implements IRecipeCategory<SpaceLiftRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(JigsawPpMod.MODID, "space_lift");
    public static final ResourceLocation TEXTURE = new ResourceLocation(JigsawPpMod.MODID,
            "textures/gui/space_lift_recipe_gui.png");
    public static final RecipeType<SpaceLiftRecipe> SPACE_LIFT_TYPE =
            new RecipeType<>(UID, SpaceLiftRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public SpaceLiftCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.SPACE_LIFT.get()));
    }

    @Override
    public RecipeType<SpaceLiftRecipe> getRecipeType() {
        return SPACE_LIFT_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.jigsaw_pp.space_lift");
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
    public void setRecipe(IRecipeLayoutBuilder builder, SpaceLiftRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 7).addIngredients(recipe.getIngredients().get(0));
//        System.out.println(recipe.getIngredients().get(0).getItems()[0].toString());
//        System.out.println("INGREDIO 1 in JEI");
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 7).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 7).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 7).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 7).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 98, 7).addIngredients(recipe.getIngredients().get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 116, 7).addIngredients(recipe.getIngredients().get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 134, 7).addIngredients(recipe.getIngredients().get(7));
//        builder.addSlot(RecipeIngredientRole.INPUT, 152, 7).addIngredients(recipe.getIngredients().get(8));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 62).addItemStack(recipe.getResultItem(null));

    }
}
