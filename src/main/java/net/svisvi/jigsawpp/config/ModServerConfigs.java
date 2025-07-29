package net.svisvi.jigsawpp.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;

import java.util.Arrays;
import java.util.List;

import static net.svisvi.jigsawpp.procedures.ut.RegistriesGoon.getEntityRegistryName;

public class ModServerConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> JIGSAW;
    public static final ForgeConfigSpec.ConfigValue<List<String>> EMPREGNATION_MOBS_LIST;
    public static final ForgeConfigSpec.ConfigValue<List<String>> PURGEN_EFFECTS_BLACK_LIST;
    public static final ForgeConfigSpec.ConfigValue<Boolean> GIVE_PATCHOULI_BOOK;



    static {
        BUILDER.push("Configs for JIGSAW II mod");

        JIGSAW = BUILDER.comment("CHANGING THIS CAN CAUSE SERIOUS SHIT")
                .define("JIGSAW", "JIGSAW");

        EMPREGNATION_MOBS_LIST = BUILDER.comment("Mobs to be empregnated ex player")
                        .define("EMPREGNATION_MOBS_LIST", Arrays.asList(
                                //getEntityRegistryName(ModEntities.BEAVER_BOMB.get()),
                                "jigsaw_pp:beaver_spider",
                                "jigsaw_pp:stone_beaver",
                                "jigsaw_pp:beaver",
                                "minecraft:piglin",
                                "minecraft:piglin_brute",
                                "minecraft:zoglin",
                                "minecraft:hoglin"
                        ));

        PURGEN_EFFECTS_BLACK_LIST = BUILDER.comment("Effects that won't appear in purgen by random")
                .define("PURGEN_EFFECTS_BLACK_LIST", Arrays.asList(
                        "jigsaw_pp:purgative"
                ));

        GIVE_PATCHOULI_BOOK = BUILDER.comment("Should player be given patchouli book on join")
                .define("GIVE_PATCHOULI_BOOK", Boolean.TRUE
                );

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
