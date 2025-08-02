package net.svisvi.jigsawpp.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS
            = DeferredRegister.create(ForgeRegistries.POTIONS, JigsawPpMod.MODID);

    public static final RegistryObject<Potion> RECTAL_THRUST_POTION = POTIONS.register("rectal_thrust_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.RECTAL_THRUST.get(), 200, 0)));

    public static final RegistryObject<Potion> POOP_POTION = POTIONS.register("poop_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.POOP.get(), 200, 0)));

    public static final RegistryObject<Potion> PURGATIVE_POTION = POTIONS.register("purgative_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.PURGATIVE.get(), 200, 0)));

    public static final RegistryObject<Potion> BVSOD_POTION = POTIONS.register("bvsod_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.BVSOD.get(), 200, 0)));

    public static final RegistryObject<Potion> EGGED_POTION = POTIONS.register("egged_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EGGED.get(), 200, 0)));

    public static final RegistryObject<Potion> UNLEVITATION_POTION = POTIONS.register("unlevitation_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.UNLEVITATION.get(), 200, 0)));

    public static final RegistryObject<Potion> EMPREGNATION_POTION = POTIONS.register("empregnation_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.EMPREGNATION.get(), 2000, 0)));

    public static final RegistryObject<Potion> PURGENMAN_BLESSING_POTION = POTIONS.register("purgenman_blessing_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.PURGENMAN_BLESSING.get(), 2000, 0)));

    public static final RegistryObject<Potion> POOP_WALKING_POTION = POTIONS.register("poop_walking_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.POOP_WALKING.get(), 8000, 0)));

    public static final RegistryObject<Potion> METEORISM_POTION = POTIONS.register("meteorism_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.METEORISM.get(), 2000, 0)));


    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
