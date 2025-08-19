package net.svisvi.jigsawpp.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.item.armor.SuicideVestItem;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;


@Mod.EventBusSubscriber
public class DiedWithVestEvent {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            //f (event.getEntity() instanceof Player player){
                if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SuicideVestItem vest){
                    vest.activate(event.getEntity().level(), event.getEntity().blockPosition(), event.getEntity(), event.getEntity(), event.getEntity(), event.getEntity().getItemBySlot(EquipmentSlot.CHEST));
//                    event.setResult(Event.Result.ALLOW);
                    return;
                }
            //} else if (event.getEntity().getItemBySlot(EquipmentSlot.CHEST))

        }
    }

}