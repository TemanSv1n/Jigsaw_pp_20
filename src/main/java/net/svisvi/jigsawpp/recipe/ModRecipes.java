package net.svisvi.jigsawpp.recipe;

import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, JigsawPpMod.MODID);

    public static final RegistryObject<RecipeSerializer<ElephantingRecipe>> ELEPHANTING_SERIALIZER =
            SERIALIZERS.register("elephanting", () -> ElephantingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PurgenCatalystRecipe>> PURGEN_CATALYST_SERIALIZER =
            SERIALIZERS.register("purgen_catalyst", () -> PurgenCatalystRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
