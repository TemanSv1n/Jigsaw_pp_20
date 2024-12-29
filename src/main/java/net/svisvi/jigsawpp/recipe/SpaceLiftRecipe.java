package net.svisvi.jigsawpp.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.svisvi.jigsawpp.JigsawPpMod;
import org.jetbrains.annotations.Nullable;

public class SpaceLiftRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public SpaceLiftRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        return inputItems.get(0).test(pContainer.getItem(0))
                && inputItems.get(1).test(pContainer.getItem(1))
                && inputItems.get(2).test(pContainer.getItem(2))
                && inputItems.get(3).test(pContainer.getItem(3))
                && inputItems.get(4).test(pContainer.getItem(4))
                && inputItems.get(5).test(pContainer.getItem(5))
                && inputItems.get(6).test(pContainer.getItem(6))
                && inputItems.get(7).test(pContainer.getItem(7));
    }
    public boolean match(SimpleContainer pContainer, Level pLevel){
        if(pLevel.isClientSide()){
            return false;
        }
        return inputItems.get(0).test(pContainer.getItem(0))
                && inputItems.get(1).test(pContainer.getItem(1))
                && inputItems.get(2).test(pContainer.getItem(2))
                && inputItems.get(3).test(pContainer.getItem(3))
                && inputItems.get(4).test(pContainer.getItem(4))
                && inputItems.get(5).test(pContainer.getItem(5))
                && inputItems.get(6).test(pContainer.getItem(6))
                && inputItems.get(7).test(pContainer.getItem(7));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SpaceLiftRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "space_lift";

    }
    public static class Serializer implements RecipeSerializer<SpaceLiftRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(JigsawPpMod.MODID, "space_lift");

        @Override
        public SpaceLiftRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
//            System.out.println(ingredients);
//            System.out.println("INGREDIONS!!!");
            NonNullList<Ingredient> inputs = NonNullList.withSize(8, Ingredient.EMPTY);
//            System.out.println(Ingredient.fromJson(ingredients.get(0)).getItems()[0].toString());
//            System.out.println("INGREDIO 0 in recipe parser");

            for(int i = 0; i < inputs.size(); i++) {
                Ingredient ingredo = Ingredient.fromJson(ingredients.get(i));
                if (ingredients.get(i).getAsJsonObject().get("count") != null) {
                    ingredo.getItems()[0].setCount(ingredients.get(i).getAsJsonObject().get("count").getAsInt());
                }
//                System.out.println(ingredo.getItems()[0].toString());
//                System.out.println("INGREDO WITH COUNT");
                inputs.set(i, ingredo);
            }
            return new SpaceLiftRecipe(inputs, output, pRecipeId);
        }

        @Override
        public @Nullable SpaceLiftRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();
            return new SpaceLiftRecipe(inputs, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SpaceLiftRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
