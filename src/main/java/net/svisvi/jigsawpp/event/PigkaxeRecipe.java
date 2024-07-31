package net.svisvi.jigsawpp.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import net.svisvi.jigsawpp.item.init.ModItems;

import javax.annotation.Nullable;

import java.util.Iterator;
// ACHTUNG!!! MCREATOR CODE LEGACY
@Mod.EventBusSubscriber
public class PigkaxeRecipe {
    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(Entity entity, Entity sourceentity) {
        execute(null, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null)
            return;
        if (sourceentity instanceof Player && ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.LIGHTNING_ROD.asItem()
                || (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.LIGHTNING_ROD.asItem()) && entity instanceof Pig)
        {
            if (sourceentity instanceof Player _player) {
                ItemStack _stktoremove = new ItemStack(Blocks.LIGHTNING_ROD);
                _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
            }
            Level level = entity.level();
            BlockPos pos = entity.getOnPos();
            if (!level.isClientSide()) {
                level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 1, 1);
                level.playSound(null, pos, SoundEvents.PISTON_EXTEND, SoundSource.NEUTRAL, 1, 1);
                level.playSound(null, pos, SoundEvents.PIG_DEATH, SoundSource.NEUTRAL, 1, 1);
            } else {
                level.playLocalSound(pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 1, 1, false);
                level.playLocalSound(pos, SoundEvents.PISTON_EXTEND, SoundSource.NEUTRAL, 1, 1, false);
                level.playLocalSound(pos, SoundEvents.PIG_DEATH, SoundSource.NEUTRAL, 1, 1, false);
            }
            if (sourceentity.level().isClientSide()) {
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.PIGKAXE.get()));
            }
            if (level instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.HEART, pos.getX(), pos.getY()+1, pos.getZ(), 20, 0.5, 0.5, 0.5, 0);

            if (!entity.level().isClientSide())
                entity.discard();
            if (sourceentity instanceof Player _player) {
                ItemStack _setstack = new ItemStack(ModItems.PIGKAXE.get());
                _setstack.setCount(1);
                ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
            }
        }
//        if (sourceentity instanceof Player
//                && ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == Blocks.LIGHTNING_ROD.asItem()
//                || (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Blocks.LIGHTNING_ROD.asItem())
//                && entity instanceof Pig
//                && !(sourceentity instanceof ServerPlayer _plr && _plr.level() instanceof ServerLevel
//                ? _plr.getAdvancements().getOrStartProgress(_plr.server.getAdvancements().getAdvancement(new ResourceLocation("jigsaw:pigkaxeachievement"))).isDone()
//                : false)) {
//            if (sourceentity instanceof ServerPlayer _player) {
//                Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("jigsaw:pigkaxeachievement"));
//                AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
//                if (!_ap.isDone()) {
//                    Iterator _iterator = _ap.getRemainingCriteria().iterator();
//                    while (_iterator.hasNext())
//                        _player.getAdvancements().award(_adv, (String) _iterator.next());
//                }
//            }
//        }
    }
}
