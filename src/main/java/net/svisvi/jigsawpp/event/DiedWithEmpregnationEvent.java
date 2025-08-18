package net.svisvi.jigsawpp.event;

import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.effect.EmpregnationEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;

//ACHTUNG!!! VERY FUCKING MCREATOR LEGACY CODE!!! VERY SHITTY!!!!

@Mod.EventBusSubscriber
public class DiedWithEmpregnationEvent {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            if (event.getEntity().hasEffect(ModEffects.EMPREGNATION.get())){
                int effect_level = event.getEntity().getEffect(ModEffects.EMPREGNATION.get()).getAmplifier();
                if (!event.getEntity().level().isClientSide()) {
                    for (int i = 0; i < effect_level + 1; i++){
                        EmpregnationEffect.born(event.getEntity().level(), event.getEntity().blockPosition());
                    }
                }
            }
        }
    }

}