package net.svisvi.jigsawpp.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.item.armor.SuicideVestItem;


@Mod.EventBusSubscriber
public class DamagedWithVestEvent {
    @SubscribeEvent
    public static void onEntityDeath(LivingDamageEvent event) {
        if (event != null && event.getEntity() != null) {
            //f (event.getEntity() instanceof Player player){
                if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SuicideVestItem vest){
                    if (event.getEntity().level().random.nextFloat() >= 0.95) {
                        vest.activate(event.getEntity().level(), event.getEntity().blockPosition(), event.getEntity(), event.getEntity(), event.getEntity(), event.getEntity().getItemBySlot(EquipmentSlot.CHEST));
                    }
//                    event.setResult(Event.Result.ALLOW);
                    return;
                }
            //} else if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST))
                
        }
    }

}