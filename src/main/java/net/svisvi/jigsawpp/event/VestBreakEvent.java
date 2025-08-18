package net.svisvi.jigsawpp.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.item.armor.SuicideVestItem;


@Mod.EventBusSubscriber
public class VestBreakEvent {
    @SubscribeEvent
    public static void onEntityDeath(LivingEquipmentChangeEvent event) {
        if (event != null && event.getEntity() != null) {
            if (event.getSlot().getType() == EquipmentSlot.Type.ARMOR && event.getFrom().getItem() instanceof SuicideVestItem) {
                ItemStack brokenVest = event.getFrom();
                //System.out.println("HTIEL");
                if (brokenVest.getDamageValue() + 3 >= brokenVest.getMaxDamage()) {
                    //System.out.println("ADLOF");
                    SuicideVestItem vest = (SuicideVestItem) brokenVest.getItem();
                    vest.activate(
                            event.getEntity().level(),
                            event.getEntity().blockPosition(),
                            event.getEntity(),
                            event.getEntity(),
                            event.getEntity(),
                            brokenVest
                    );
                }
            }
                
        }
    }

}