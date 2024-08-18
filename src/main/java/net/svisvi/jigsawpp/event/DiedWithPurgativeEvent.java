package net.svisvi.jigsawpp.event;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;

import javax.annotation.Nullable;

//ACHTUNG!!! VERY FUCKING MCREATOR LEGACY CODE!!! VERY SHITTY!!!!

@Mod.EventBusSubscriber
public class DiedWithPurgativeEvent {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            if (event.getEntity().hasEffect(ModEffects.PURGATIVE.get())){
                int amp = 2;
                int effect_level = event.getEntity().getEffect(ModEffects.PURGATIVE.get()).getAmplifier() + 1;
                if (!event.getEntity().level().isClientSide()) {
                    DristExplosion.harmfulDristExplode(event.getEntity().level(), event.getEntity().getOnPos(), amp * effect_level, Level.ExplosionInteraction.NONE, event.getEntity());
                }
            }
        }
    }

}