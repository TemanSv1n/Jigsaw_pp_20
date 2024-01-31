package net.svisvi.jigsawpp.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.ModEntities;
import net.svisvi.jigsawpp.entity.client.MossElephantModel;
import net.svisvi.jigsawpp.entity.custom.MossElephantEntity;

@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.MOSS_ELEPHANT.get(), MossElephantEntity.createAttributes().build());
    }
}
