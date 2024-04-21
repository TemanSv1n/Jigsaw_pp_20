package net.svisvi.jigsawpp.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PurgenFactoryRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int purity; //K stands for K-oefficient
    private final int additionalTime;
    private final float malChance;
    private final ItemStack potionStack;
    private final FluidStack fluidStack;


    public PurgenFactoryRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id,
                               int p_purity, int p_additionalTime, float p_malChance, ItemStack p_potion, FluidStack p_fluidStack) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.purity = p_purity;
        this.additionalTime = p_additionalTime;
        this.malChance = p_malChance;
        this.potionStack = p_potion;
        this.fluidStack = p_fluidStack;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }
        return inputItems.get(0).test(pContainer.getItem(0))
                && inputItems.get(1).test(pContainer.getItem(1));
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
    public int getPurity(RegistryAccess pRegistryAccess){
        return purity;
    }
    public int getAdditionalTime(RegistryAccess pRegistryAccess){
        return additionalTime;
    }
    public float getMalChance(RegistryAccess pRegistryAccess){
        return malChance;
    }

    public ItemStack getPotionStack() {
        return potionStack;
    }
    public FluidStack getFluidStack(){
        return fluidStack;
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

    public static class Type implements RecipeType<PurgenFactoryRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "purgen_factory";

    }
    public static class Serializer implements RecipeSerializer<PurgenFactoryRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(JigsawPpMod.MODID, "purgen_factory");

        @Override
        public PurgenFactoryRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            //primitives
            int purity = pSerializedRecipe.get("purity").getAsInt();
            int additionalTime = pSerializedRecipe.get("additionalTime").getAsInt();;
            float malChance = pSerializedRecipe.get("malChance").getAsFloat();;





            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            JsonObject fluidObj = GsonHelper.getAsJsonObject(pSerializedRecipe, "fluid");
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidObj.get("fluid_name").getAsString())); //fluidObj.get("fluid_name").getAsString()
            FluidStack fstack = new FluidStack(fluid, fluidObj.get("amount").getAsInt());

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new PurgenFactoryRecipe(inputs, output, pRecipeId, purity, additionalTime, malChance, ParsePotion.parseFromJson(pSerializedRecipe), fstack);
        }

        @Override
        public @Nullable PurgenFactoryRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack output = pBuffer.readItem();

            int purity = pBuffer.readInt();
            int additionalTime = pBuffer.readInt();
            float malChance = pBuffer.readFloat();
            ItemStack potion = pBuffer.readItem();
            FluidStack fstack = pBuffer.readFluidStack();
            return new PurgenFactoryRecipe(inputs, output, pRecipeId, purity, additionalTime, malChance, potion, fstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, PurgenFactoryRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for(Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
            pBuffer.writeFloat(pRecipe.getPurity(null));
            pBuffer.writeFloat(pRecipe.getAdditionalTime(null));
            pBuffer.writeFloat(pRecipe.getMalChance(null));
            pBuffer.writeItemStack(pRecipe.getPotionStack(), false);
            pBuffer.writeFluidStack(pRecipe.getFluidStack());


        }
    }
}
