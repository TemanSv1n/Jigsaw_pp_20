package net.svisvi.jigsawpp.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;

public class ModSounds {
        public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, JigsawPpMod.MODID);
        public static final RegistryObject<SoundEvent> WHISTLE = REGISTRY.register("whistle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "whistle")));
        public static final RegistryObject<SoundEvent> MOSS_ELEPHANT_PROOT = REGISTRY.register("moss_elephant_proot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "moss_elephant_proot")));
        public static final RegistryObject<SoundEvent> MOSS_ELEPHANT_ANGRY_PROOT = REGISTRY.register("moss_elephant_angry_proot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "moss_elephant_angry_proot")));
        public static final RegistryObject<SoundEvent> FACTORY_HEATER = REGISTRY.register("factory_heater", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "factory_heater")));
        public static final RegistryObject<SoundEvent> ALPHA_BOMB = REGISTRY.register("alpha_bomb", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "alpha_bomb")));
        public static final RegistryObject<SoundEvent> BLABBIT_LIVE_0 = REGISTRY.register("blabbit_live_0", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "blabbit_live_0")));
        public static final RegistryObject<SoundEvent> BLABBIT_LIVE_1 = REGISTRY.register("blabbit_live_1", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "blabbit_live_1")));
        public static final RegistryObject<SoundEvent> BLABBIT_LIVE_2 = REGISTRY.register("blabbit_live_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "blabbit_live_2")));
        public static final RegistryObject<SoundEvent> BLABBIT_LIVE_3 = REGISTRY.register("blabbit_live_3", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "blabbit_live_3")));
        public static final RegistryObject<SoundEvent> CLOWN_HORN = REGISTRY.register("clown_horn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "clown_horn")));
}
