package net.svisvi.jigsawpp.event.apoocalypse;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.gamerules.ModGameRules;

@Mod.EventBusSubscriber
public class LivingEntityTickUpdate {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (entity.level().isClientSide() || !entity.isAlive() || !isOverworld(level)) return;


        if (level.getLevelData().getGameRules().getBoolean(ModGameRules.APOOCALYPSE)) {
            entity.addEffect(new MobEffectInstance(ModEffects.PURGENMAN_BLESSING.get(), 120, 0, false, false));

            if (!isNightTime(entity.level()) && entity.level().canSeeSky(entity.blockPosition())) {
                entity.playSound(SoundEvents.COW_MILK, 0.6F, -1.0F);
                entity.addEffect(new MobEffectInstance(ModEffects.PURGATIVE.get(), 1200, 3, false, true));

            }
        }
    }

    // Helper method to check if it's nighttime
    private static boolean isNightTime(Level level) {
        long dayTime = level.getDayTime() % 24000;
        return dayTime > 13000 && dayTime < 23000; // Night period in Minecraft
    }

    private static boolean isOverworld(Level level) {
        return level.dimension() == Level.OVERWORLD;
    }
}
