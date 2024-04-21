package net.svisvi.jigsawpp.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.init.ModTags;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.purgen_recipe_helpers.AbstractPurgenRecipeHelperItem;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;

public class PurgenFactoryCategory implements IRecipeCategory<PurgenFactoryRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(JigsawPpMod.MODID, "purgen_factory");
    public static final ResourceLocation TEXTURE = new ResourceLocation(JigsawPpMod.MODID,
            "textures/gui/purgen_factory_recipe_gui.png");
    public static final RecipeType<PurgenFactoryRecipe> PURGEN_FACTORY_TYPE =
            new RecipeType<>(UID, PurgenFactoryRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    public PurgenFactoryCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.PURGEN_FACTORY.get()));
    }

    @Override
    public RecipeType<PurgenFactoryRecipe> getRecipeType() {
        return PURGEN_FACTORY_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.jigsaw_pp.purgen_factory");
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
    public void setRecipe(IRecipeLayoutBuilder builder, PurgenFactoryRecipe recipe, IFocusGroup focuses) {
        //ingreds
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 6).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 26, 24).addIngredients(recipe.getIngredients().get(1));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 152, 60).addItemStack(recipe.getResultItem(null));

        builder.addSlot(RecipeIngredientRole.CATALYST, 134, 41).addItemStack(recipe.getPotionStack());
//        builder
//                .addSlot(RecipeIngredientRole.RENDER_ONLY, 80, 96)
//                .addItemStack(new ItemStack(ModItems.PURGEN_FACTORY_BIG_THUMB.get()));
        //empty pilule decorative
        builder.addSlot(RecipeIngredientRole.INPUT, 152, 29).addItemStack(new ItemStack(ModItems.EMPTY_PILULE.get()));

        ItemStack additionalTimeStack = new ItemStack(ModItems.PURGEN_RECIPE_HELPER_CLOCK.get());
        AbstractPurgenRecipeHelperItem.setToDisplay("int", additionalTimeStack);
        AbstractPurgenRecipeHelperItem.setInt(recipe.getAdditionalTime(null), additionalTimeStack);

        ItemStack purityStack = new ItemStack(ModItems.PURGEN_RECIPE_HELPER_PURITY.get());
        AbstractPurgenRecipeHelperItem.setToDisplay("int", purityStack);
        AbstractPurgenRecipeHelperItem.setInt(recipe.getPurity(null), purityStack);

        ItemStack tntStack = new ItemStack(ModItems.PURGEN_RECIPE_HELPER_TNT.get());
        AbstractPurgenRecipeHelperItem.setToDisplay("float", tntStack);
        AbstractPurgenRecipeHelperItem.setFloat(recipe.getMalChance(null), tntStack);

        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 152, 59)
                .addItemStack(additionalTimeStack);
        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 152, 41)
                .addItemStack(purityStack);
        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 152, 23)
                .addItemStack(tntStack);

    }
}
