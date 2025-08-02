package net.svisvi.jigsawpp.entity.cursed_cow;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CowTransformationHandler {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();
        player.swing(event.getHand());
        // Only transform VANILLA cows (not blue cows)
        if (target.getType() == EntityType.COW && !(target instanceof CursedCowEntity) &&
                stack.getItem() == ModItems.TEETH.get()) {

            Level level = event.getLevel();
            if (!level.isClientSide) {
                Cow vanillaCow = (Cow) target;

                CursedCowEntity blueCow = ModEntities.CURSED_COW.get().create(level);
                if (blueCow == null) return;


                // Create blue cow entity
                CursedCowEntity cursedCow = ModEntities.CURSED_COW.get().create(level);
                if (cursedCow == null) return;

                // Transfer properties from vanilla cow
                cursedCow.copyPosition(vanillaCow);
                cursedCow.setYHeadRot(vanillaCow.getYHeadRot());
                cursedCow.setXRot(vanillaCow.getXRot());






                // Remove vanilla cow and spawn blue cow
                vanillaCow.discard();
                level.addFreshEntity(cursedCow);

                // Consume item and play sound
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                level.playSound(null, cursedCow.getX(), cursedCow.getY(), cursedCow.getZ(),
                        Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn"))), SoundSource.PLAYERS, 1.0F, 1.5F);


                // Cancel original interaction
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.sidedSuccess(false));
            }
        }
    }
}