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

import javax.annotation.Nullable;

//ACHTUNG!!! VERY FUCKING MCREATOR LEGACY CODE!!! VERY SHITTY!!!!

@Mod.EventBusSubscriber
public class TotemOfShitEvent {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
        }
    }

    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        execute(null, world, x, y, z, entity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (entity instanceof Player) {
            if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.TOTEM_OF_SHIT.get()) {
                if (world.isClientSide())
                    Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.TOTEM_OF_SHIT.get()));
                if (entity instanceof LivingEntity _entity) {
                    ItemStack _setstack = new ItemStack(Blocks.AIR);
                    _setstack.setCount(1);
                    _entity.setItemInHand(InteractionHand.MAIN_HAND, _setstack);
                    if (_entity instanceof Player _player)
                        _player.getInventory().setChanged();
                }
                if (entity instanceof LivingEntity _entity) {
                    _entity.addEffect(new MobEffectInstance(ModEffects.POOP.get(), 120, 1, (false), (false)));
                    _entity.addEffect(new MobEffectInstance(ModEffects.PURGATIVE.get(), 120, 1, (false), (false)));
                }
                if (world instanceof ServerLevel _level) {
                    LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                    entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)x, (int)y, (int)z)));
                    entityToSpawn.setVisualOnly(false);
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 2, -1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 2, -1, false);
                    }
                }
            } else if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == ModItems.TOTEM_OF_SHIT.get()) {
                if (world.isClientSide())
                    Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.TOTEM_OF_SHIT.get()));
                if (entity instanceof LivingEntity _entity) {
                    ItemStack _setstack = new ItemStack(Blocks.AIR);
                    _setstack.setCount(1);
                    _entity.setItemInHand(InteractionHand.OFF_HAND, _setstack);
                    if (_entity instanceof Player _player)
                        _player.getInventory().setChanged();
                }
                if (entity instanceof LivingEntity _entity) {
                    _entity.addEffect(new MobEffectInstance(ModEffects.POOP.get(), 120, 1, (false), (false)));
                    _entity.addEffect(new MobEffectInstance(ModEffects.PURGATIVE.get(), 120, 1, (false), (false)));
                }
                if (world instanceof ServerLevel _level) {
                    LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
                    entityToSpawn.moveTo(Vec3.atBottomCenterOf(new BlockPos((int)x, (int)y, (int)z)));
                    entityToSpawn.setVisualOnly(false);
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 2, -1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 2, -1, false);
                    }
                }
            }
        }
    }
}
