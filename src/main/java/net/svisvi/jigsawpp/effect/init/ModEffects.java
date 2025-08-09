package net.svisvi.jigsawpp.effect.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.effect.*;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, JigsawPpMod.MODID);

    public static final RegistryObject<MobEffect> POOP = MOB_EFFECTS.register("poop",
            () -> new PoopEffect(MobEffectCategory.HARMFUL, -12372212));

    public static final RegistryObject<MobEffect> PURGATIVE = MOB_EFFECTS.register("purgative",
            () -> new PurgativeEffect(MobEffectCategory.HARMFUL, -12372212));

    public static final RegistryObject<MobEffect> RADIATION = MOB_EFFECTS.register("radiation",
            () -> new RadiationEffect(MobEffectCategory.HARMFUL, -10040320));


    public static final RegistryObject<MobEffect> BAD_EFFECT = MOB_EFFECTS.register("random_bad_effect",
            () -> new RandomBadEffect(MobEffectCategory.HARMFUL));
    public static final RegistryObject<MobEffect> GOOD_EFFECT = MOB_EFFECTS.register("random_good_effect",
            () -> new RandomBadEffect(MobEffectCategory.HARMFUL));
    public static final RegistryObject<MobEffect> BVSOD = MOB_EFFECTS.register("bvsod",
            () -> new BeaverSODeffect());
    public static final RegistryObject<MobEffect> EGGED = MOB_EFFECTS.register("egged",
            () -> new EggedEffect());
    public static final RegistryObject<MobEffect> RECTAL_THRUST = MOB_EFFECTS.register("rectal_thrust",
            () -> new RectalThrustEffect(MobEffectCategory.HARMFUL, -12372212));
    public static final RegistryObject<MobEffect> UNLEVITATION = MOB_EFFECTS.register("unlevitation",
            () -> new UnlevitationEffect(MobEffectCategory.HARMFUL, 6987972));
    public static final RegistryObject<MobEffect> EMPREGNATION = MOB_EFFECTS.register("empregnation",
            () -> new EmpregnationEffect(MobEffectCategory.HARMFUL, 2037522));
    public static final RegistryObject<MobEffect> PURGENMAN_BLESSING = MOB_EFFECTS.register("purgenman_blessing",
            () -> new PurgenManBlessingEffect(MobEffectCategory.HARMFUL, -12372212));
    public static final RegistryObject<MobEffect> POOP_WALKING = MOB_EFFECTS.register("poop_walking",
            () -> new PoopWalkingEffect(MobEffectCategory.BENEFICIAL, -12372212));
    public static final RegistryObject<MobEffect> METEORISM = MOB_EFFECTS.register("meteorism",
            () -> new MeteorismEffect(MobEffectCategory.BENEFICIAL, 6987972));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
