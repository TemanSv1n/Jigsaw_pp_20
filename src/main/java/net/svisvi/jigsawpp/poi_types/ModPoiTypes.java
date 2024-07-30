package net.svisvi.jigsawpp.poi_types;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.core.Registry;

import java.util.Set;
import java.util.stream.Collectors;
import java.lang.reflect.Constructor;

import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;



public final class ModPoiTypes {
    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, JigsawPpMod.MODID);

    public static final RegistryObject<PoiType> LENIN_BUST = POI_TYPES.register("lenin_bust", () -> new PoiType(getPoiHiveStates(), 0, 1));

    private static Set<BlockState> getPoiHiveStates() {
        final Set<BlockState> HIVES = Set.of(ModBlocks.LENIN_BUST.get()).stream().flatMap((block) -> {
            return block.getStateDefinition().getPossibleStates().stream();
        }).collect(ImmutableSet.toImmutableSet());
//    	LOGGER.debug(HIVES);
        return HIVES;
    }
}