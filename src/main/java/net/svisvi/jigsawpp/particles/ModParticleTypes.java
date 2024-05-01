
/*
 *    Jigsawus note: This file is cursed and will be abolished
 */
package net.svisvi.jigsawpp.particles;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.svisvi.jigsawpp.JigsawPpMod;

public class ModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, JigsawPpMod.MODID);
	public static final RegistryObject<SimpleParticleType> FLY = REGISTRY.register("fly", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> POOP = REGISTRY.register("poop", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> POOP_BUBBLE = REGISTRY.register("poop_bubble", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> KEGA_BOOM = REGISTRY.register("kega_boom", () -> new SimpleParticleType(false));
}
