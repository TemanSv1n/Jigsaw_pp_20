package net.svisvi.jigsawpp.event;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.svisvi.jigsawpp.entity.emitters.AbstractEmitterEntity;

import java.util.List;
@Mod.EventBusSubscriber
public class OnBottleUseEvent {
    @SubscribeEvent
    public static void onBottleUse(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem() == Items.GLASS_BOTTLE) {
            Player player = event.getEntity();
            Level level = event.getLevel();

            List<AbstractEmitterEntity> emitters = level.getEntitiesOfClass(
                    AbstractEmitterEntity.class,
                    player.getBoundingBox().inflate(2.0),
                    emitter -> emitter != null && emitter.isAlive() && emitter.isSuckable()
            );

            if (!emitters.isEmpty()) {
                AbstractEmitterEntity emitter = emitters.get(0);
                if (emitter.getBottle() != null && emitter.isBottlePickable() && emitter.isSuckable()) {

                    level.playSound(
                            null,
                            player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BOTTLE_FILL_DRAGONBREATH,
                            SoundSource.NEUTRAL,
                            1.0F, 1.0F
                    );

                    level.gameEvent(player, GameEvent.FLUID_PICKUP, player.position());

//                    ItemStack filledBottle = new ItemStack(emitter.getBottle());
//                    ItemUtils.createFilledResult(
//                            event.getItemStack(),
//                            player,
//                            filledBottle
//                    );

                    ItemStack itemstack = event.getItemStack();
                    ItemStack itemstack1 = new ItemStack(emitter.getBottle(), 1);

                    itemstack.shrink(1);
                    event.getEntity().addItem(itemstack1);

                    emitter.suckOver();

                    event.setCanceled(true);
                    event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
                }
            }
        }
    }
}
