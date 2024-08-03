package net.svisvi.jigsawpp.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.entity.decoration.PaintingVariant;

import net.svisvi.jigsawpp.JigsawPpMod;

public class ModPaintings {
    public static final DeferredRegister<PaintingVariant> REGISTRY = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, JigsawPpMod.MODID);
    public static final RegistryObject<PaintingVariant> AVEBREAD =
            REGISTRY.register("avebread", () -> new PaintingVariant(48, 64));
    public static final RegistryObject<PaintingVariant> BALLS =
            REGISTRY.register("balls", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> BEAVERNAZI =
            REGISTRY.register("beavernazi", () -> new PaintingVariant(64, 32));
    public static final RegistryObject<PaintingVariant> BLABBIT =
            REGISTRY.register("blabbit", () -> new PaintingVariant(48, 48));
    public static final RegistryObject<PaintingVariant> DED =
            REGISTRY.register("ded", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> DEDSAD =
            REGISTRY.register("dedsad", () -> new PaintingVariant(16, 16));
    public static final RegistryObject<PaintingVariant> DRISTPUNK2 =
            REGISTRY.register("dristpunk2", () -> new PaintingVariant(64, 32));
    public static final RegistryObject<PaintingVariant> PAG =
            REGISTRY.register("pag", () -> new PaintingVariant(64, 48));
    public static final RegistryObject<PaintingVariant> PUG =
            REGISTRY.register("pug", () -> new PaintingVariant(64, 48));
    public static final RegistryObject<PaintingVariant> POTSIGHT =
            REGISTRY.register("potsight", () -> new PaintingVariant(64, 48));
    public static final RegistryObject<PaintingVariant> SLONIK =
            REGISTRY.register("slonik", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> SVYATOSTRUN =
            REGISTRY.register("svyatostrun", () -> new PaintingVariant(64, 48));

}
