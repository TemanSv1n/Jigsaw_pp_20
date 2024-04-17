package net.svisvi.jigsawpp.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.recipe.PurgenCatalystRecipe;

public class PurgenCatalystCategory implements IRecipeCategory<PurgenCatalystRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(JigsawPpMod.MODID, "purgen_catalyst");
    public static final ResourceLocation TEXTURE = new ResourceLocation(JigsawPpMod.MODID,
            "textures/gui/purgen_catalyst_gui.png");
    public static final RecipeType<PurgenCatalystRecipe> PURGEN_CATALYST_TYPE =
            new RecipeType<>(UID, PurgenCatalystRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public PurgenCatalystCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.DIAMOND));
    }

    @Override
    public RecipeType<PurgenCatalystRecipe> getRecipeType() {
        return PURGEN_CATALYST_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.jigsaw_pp.purgen_catalyst");
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
    public void setRecipe(IRecipeLayoutBuilder builder, PurgenCatalystRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 11).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 26, 59).addItemStack(recipe.getResultItem(null));

        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 80, 96)
                .addItemStack(new ItemStack(ModItems.PURGEN_FACTORY_BIG_THUMB.get()));

    }
}
