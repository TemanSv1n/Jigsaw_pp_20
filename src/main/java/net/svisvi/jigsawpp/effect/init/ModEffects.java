package net.svisvi.jigsawpp.effect.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.RandomBadEffect;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, JigsawPpMod.MODID);

    public static final RegistryObject<MobEffect> POOP = MOB_EFFECTS.register("poop",
            () -> new PoopEffect(MobEffectCategory.HARMFUL, -12372212));

    public static final RegistryObject<MobEffect> PURGATIVE = MOB_EFFECTS.register("purgative",
            () -> new PurgativeEffect(MobEffectCategory.HARMFUL, -12372212));

    public static final RegistryObject<MobEffect> BAD_EFFECT = MOB_EFFECTS.register("random_bad_effect",
            () -> new RandomBadEffect(MobEffectCategory.HARMFUL));
    public static final RegistryObject<MobEffect> GOOD_EFFECT = MOB_EFFECTS.register("random_good_effect",
            () -> new RandomBadEffect(MobEffectCategory.HARMFUL));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
