package net.svisvi.jigsawpp.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.svisvi.jigsawpp.procedures.ut.RegistriesGoon.getEntityRegistryName;

public class ModServerConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Config values with proper typing
    public static final ForgeConfigSpec.ConfigValue<String> JIGSAW;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EMPREGNATION_MOBS_LIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> PURGEN_EFFECTS_BLACK_LIST;

    // Default values
    private static final List<String> DEFAULT_EMPREGNATION_MOBS = Arrays.asList(
            "jigsaw_pp:beaver_spider",
            "jigsaw_pp:stone_beaver",
            "jigsaw_pp:beaver",
            "minecraft:piglin",
            "minecraft:piglin_brute",
            "minecraft:zoglin",
            "minecraft:hoglin"
    );

    private static final List<String> DEFAULT_PURGEN_EFFECTS = Arrays.asList("jigsaw_pp:purgative");

    static {
        BUILDER.push("Configs for JIGSAW II mod");

        JIGSAW = BUILDER.comment("CHANGING THIS CAN CAUSE SERIOUS SHIT")
                .define("JIGSAW", "JIGSAW");

        EMPREGNATION_MOBS_LIST = BUILDER.comment("Mobs that can be empregnated")
                .defineList("EMPREGNATION_MOBS_LIST",
                        DEFAULT_EMPREGNATION_MOBS,
                        obj -> obj instanceof String
                );

        PURGEN_EFFECTS_BLACK_LIST = BUILDER.comment("Effects excluded from purgen randomization")
                .defineList("PURGEN_EFFECTS_BLACK_LIST",
                        DEFAULT_PURGEN_EFFECTS,
                        obj -> obj instanceof String
                );

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    // Helper methods to get the lists safely
    public static List<String> getImpregnationMobs() {
        return new ArrayList<>(EMPREGNATION_MOBS_LIST.get());
    }

    public static List<String> getPurgenEffectsBlacklist() {
        return new ArrayList<>(PURGEN_EFFECTS_BLACK_LIST.get());
    }
}
