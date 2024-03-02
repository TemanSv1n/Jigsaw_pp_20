package net.svisvi.jigsawpp.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.svisvi.jigsawpp.block.crops.Beaweed;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Set;

public class ModBlocksLootTables extends BlockLootSubProvider {
    public ModBlocksLootTables(){super(Set.of(), FeatureFlags.REGISTRY.allFlags());}
    @Override
    protected void generate(){

        LootItemCondition.Builder lootitembuilder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.BEAWEED_PLANT.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(Beaweed.AGE,5));
        this.add(ModBlocks.BEAWEED_PLANT.get(),createCropDrops(ModBlocks.BEAWEED_PLANT.get(), ModItems.BEAWEED_SEEDS.get(),ModItems.BEAWEED_SEEDS.get(),lootitembuilder));
    }
}
