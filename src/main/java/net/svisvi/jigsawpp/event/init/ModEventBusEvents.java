package net.svisvi.jigsawpp.event.init;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverEntity;
import net.svisvi.jigsawpp.entity.teapodSpider.TeapodSpider;

@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MOSS_ELEPHANT.get(), MossElephantEntity.createAttributes().build());
        event.put(ModEntities.BLABBIT.get(), BlabbitEntity.createAttributes().build());
        event.put(ModEntities.TEAPOD_SPIDER.get(), TeapodSpider.createAttributes().build());
        event.put(ModEntities.BEAVER_SPIDER.get(), TeapodSpider.createAttributes().build());
        event.put(ModEntities.STONE_BEAVER.get(), StoneBeaverEntity.createAttributes().build());
    }


}
