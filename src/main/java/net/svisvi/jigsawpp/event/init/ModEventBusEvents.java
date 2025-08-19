package net.svisvi.jigsawpp.event.init;

import net.minecraft.world.entity.animal.Cow;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieEntity;
import net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner.BeaverZombieSpawner;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;
import net.svisvi.jigsawpp.entity.cursed_cow.CursedCowEntity;
import net.svisvi.jigsawpp.entity.gentleman.GentleManEntity;
import net.svisvi.jigsawpp.entity.gentleman.GentleManWatchingEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverEntity;
import net.svisvi.jigsawpp.entity.teapod.blackTeapodSpider.BlackTeapodSpider;
import net.svisvi.jigsawpp.entity.teapod.rawTeapodSpider.RawTeapodSpider;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpider;

@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MOSS_ELEPHANT.get(), MossElephantEntity.createAttributes().build());
        event.put(ModEntities.BLABBIT.get(), BlabbitEntity.createAttributes().build());
        event.put(ModEntities.TEAPOD_SPIDER.get(), TeapodSpider.createAttributes().build());
        event.put(ModEntities.BLACK_TEAPOD_SPIDER.get(), BlackTeapodSpider.createAttributes().build());
        event.put(ModEntities.RAW_TEAPOD_SPIDER.get(), RawTeapodSpider.createAttributes().build());
        event.put(ModEntities.BEAVER_SPIDER.get(), TeapodSpider.createAttributes().build());
        event.put(ModEntities.STONE_BEAVER.get(), StoneBeaverEntity.createAttributes().build());
        event.put(ModEntities.ZOMBIE_BEAVER.get(), BeaverZombieEntity.createAttributes().build());
        event.put(ModEntities.ZOMBIE_BEAVER_SPAWNER.get(), BeaverZombieSpawner.createAttributes().build());
        event.put(ModEntities.CURSED_COW.get(), CursedCowEntity.createAttributes().build());
        event.put(ModEntities.GENTLEMAN.get(), GentleManEntity.createAttributes().build());
        event.put(ModEntities.GENTLEMAN_WATCHING.get(), GentleManWatchingEntity.createAttributes().build());
    }
}
