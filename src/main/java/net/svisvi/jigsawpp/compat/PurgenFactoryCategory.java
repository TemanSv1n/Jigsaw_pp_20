package net.svisvi.jigsawpp.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.data.ForgeItemTagsProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.client.screen.FluidTankRenderer;
import net.svisvi.jigsawpp.init.ModTags;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.item.purgen_recipe_helpers.AbstractPurgenRecipeHelperItem;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;

import java.util.List;
import java.util.Optional;

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

        ItemStack output_stack = recipe.getResultItem(null);
        AbstractPiluleItem.setDurationBuff(recipe.getAdditionalTime(null), output_stack);
        AbstractPiluleItem.setPurity(recipe.getPurity(null)/4, output_stack);
        PotionUtils.setCustomEffects(output_stack, recipe.getEffects());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 152, 60).addItemStack(output_stack);

        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 80, 96)
                .addItemStack(new ItemStack(ModItems.PURGEN_FACTORY_BIG_THUMB.get()));
        //empty pilule decorative
        builder.addSlot(RecipeIngredientRole.INPUT, 152, 29).addItemStack(new ItemStack(ModItems.EMPTY_PILULE.get()));

        ItemStack additionalTimeStack = new ItemStack(ModItems.PURGEN_RECIPE_HELPER_CLOCK.get());
        AbstractPurgenRecipeHelperItem.setToDisplay("int", additionalTimeStack);
        AbstractPurgenRecipeHelperItem.setInt(recipe.getAdditionalTime(null), additionalTimeStack);

        ItemStack purityStack = new ItemStack(ModItems.PURGEN_RECIPE_HELPER_PURITY.get());
        AbstractPurgenRecipeHelperItem.setToDisplay("int", purityStack);
        AbstractPurgenRecipeHelperItem.setInt(recipe.getPurity(null), purityStack);

        ItemStack tntStack = new ItemStack(ModItems.PURGEN_RECIPE_HELPER_TNT.get());
        AbstractPurgenRecipeHelperItem.setToDisplay("float_alt", tntStack);
        AbstractPurgenRecipeHelperItem.setFloatAlt(recipe.getMalChance(null), tntStack);

        builder
                .addSlot(RecipeIngredientRole.RENDER_ONLY, 134, 60)
                .addItemStack(tntStack);

        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 52, 11)
                .addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidStack())
                .addTooltipCallback(addFluidTooltip(recipe.getFluidStack().getAmount()));

    }

    public static IRecipeSlotTooltipCallback addFluidTooltip(int mbAmount) {
        return (view, tooltip) -> {
            Optional<FluidStack> displayed = view.getDisplayedIngredient(ForgeTypes.FLUID_STACK);
            if (displayed.isEmpty())
                return;

            FluidStack fluidStack = displayed.get();

            int amount = mbAmount == -1 ? fluidStack.getAmount() : mbAmount;
            Component text = Component.literal(String.valueOf(amount)).append(Component.translatable("jigsaw_pp.tooltip.liquid.mb")).withStyle(ChatFormatting.GOLD);
            if (tooltip.isEmpty())
                tooltip.add(0, text);
            else {
                List<Component> siblings = tooltip.get(0).getSiblings();
                siblings.add(Component.literal(" "));
                siblings.add(text);
            }
        };
    }
}
