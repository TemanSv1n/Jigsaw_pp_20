package net.svisvi.jigsawpp.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.JigsawPpMod;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PurgenCatalystRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final float purityK; //K stands for K-oefficient
    private final float additionalTimeK;
    private final float malChanceK;
    private final ItemStack potionStack;


    public PurgenCatalystRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id,
                                float p_purityK, float p_additionalTimeK, float p_malChanceK, ItemStack p_potion) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.purityK = p_purityK;
        this.additionalTimeK = p_additionalTimeK;
        this.malChanceK = p_malChanceK;
        this.potionStack = p_potion;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        return inputItems.get(0).test(pContainer.getItem(0));
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
    public float getPurityK(RegistryAccess pRegistryAccess){
        return purityK;
    }
    public float getAdditionalTimeK(RegistryAccess pRegistryAccess){
        return additionalTimeK;
    }
    public float getMalChanceK(RegistryAccess pRegistryAccess){
        return malChanceK;
    }

    public ItemStack getPotionStack() {
        return potionStack;
    }

    public List<MobEffectInstance> getEffects(){
        return PotionUtils.getMobEffects(potionStack);
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

    public static class Type implements RecipeType<PurgenCatalystRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "purgen_catalyst";

    }
    public static class Serializer implements RecipeSerializer<PurgenCatalystRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(JigsawPpMod.MODID, "purgen_catalyst");

        @Override
        public PurgenCatalystRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            //primitives
            float purityK = pSerializedRecipe.get("purity").getAsFloat();
            float additionalTimeK = pSerializedRecipe.get("additionalTime").getAsFloat();;
            float malChanceK = pSerializedRecipe.get("malChance").getAsFloat();;





            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new PurgenCatalystRecipe(inputs, output, pRecipeId, purityK, additionalTimeK, malChanceK, ParsePotion.parseFromJson(pSerializedRecipe));
        }

        @Override
        public @Nullable PurgenCatalystRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();

            float purityK = pBuffer.readFloat();
            float additionalTimeK = pBuffer.readFloat();
            float malChanceK = pBuffer.readFloat();
            ItemStack potion = pBuffer.readItem();
            return new PurgenCatalystRecipe(inputs, output, pRecipeId, purityK, additionalTimeK, malChanceK, potion);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PurgenCatalystRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeFloat(pRecipe.getPurityK(null));
            pBuffer.writeFloat(pRecipe.getAdditionalTimeK(null));
            pBuffer.writeFloat(pRecipe.getMalChanceK(null));
            pBuffer.writeItemStack(pRecipe.getPotionStack(), false);

        }
    }
}
