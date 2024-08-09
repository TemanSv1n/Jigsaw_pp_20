package net.svisvi.jigsawpp.event.init;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.client.gui.BeaverSODOverlay;
import net.svisvi.jigsawpp.client.gui.PoopOverlay;
import net.svisvi.jigsawpp.entity.armor.GasMaskModel;
import net.svisvi.jigsawpp.entity.armor.JotaroHatModel;
import net.svisvi.jigsawpp.entity.armor.beaver.BeaverBodyModel;
import net.svisvi.jigsawpp.entity.armor.beaver.BeaverBootsModel;
import net.svisvi.jigsawpp.entity.armor.beaver.BeaverHatModel;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitModel;
import net.svisvi.jigsawpp.entity.init.ModModelLayers;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantModel;
import net.svisvi.jigsawpp.entity.plunger.PlungerModel;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileModel;

@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.MOSS_ELEPHANT_LAYER, MossElephantModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BLABBIT_LAYER, BlabbitModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.GAS_MASK_LAYER, GasMaskModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.JOTARO_HAT_LAYER, JotaroHatModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.FLOPPA_MISSILE_LAYER, FloppaMissileModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.PLUNGER, PlungerModel::createBodyLayer);

        event.registerLayerDefinition(ModModelLayers.BEAVER_BOOTS_LAYER, BeaverBootsModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BEAVER_BODY_LAYER, BeaverBodyModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.BEAVER_HAT_LAYER, BeaverHatModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("poop", PoopOverlay.HUD_POOP);
        event.registerAboveAll("bvsod", BeaverSODOverlay.HUD_POOP);
    }


}
