package net.svisvi.jigsawpp.event.init;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner.BeaverZombieSpawner;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverEntity;

@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
//    @SubscribeEvent
//    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
//        event.put(ModEntities.MOSS_ELEPHANT.get(), MossElephantEntity.createAttributes());
//    }
    @SubscribeEvent
    public static void entitySpawnRestriction(SpawnPlacementRegisterEvent event){
        event.register(ModEntities.MOSS_ELEPHANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.BLABBIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BlabbitEntity::init, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.STONE_BEAVER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                StoneBeaverEntity::init, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(ModEntities.ZOMBIE_BEAVER_SPAWNER.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BeaverZombieSpawner::init, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
