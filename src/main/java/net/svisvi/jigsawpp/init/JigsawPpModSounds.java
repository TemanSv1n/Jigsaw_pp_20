package net.svisvi.jigsawpp.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;

public class JigsawPpModSounds{
        public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, JigsawPpMod.MODID);
        public static final RegistryObject<SoundEvent> MOSS_ELEPHANT_PROOT = REGISTRY.register("moss_elephant_proot", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("jigsaw_pp", "moss_elephant_proot")));
}
