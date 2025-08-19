package net.svisvi.jigsawpp.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;

//LEGACY AND PAJEETY CODE! DO NOT INVESTIGATE!
public class Honk {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;

        // Only execute logic on the server side
        if (!world.isClientSide()) {
            boolean enable = false;
            if (itemstack.getOrCreateTag().getDouble("penis") < 120) {
                itemstack.getOrCreateTag().putDouble("penis", (itemstack.getOrCreateTag().getDouble("penis") + 1));
            }

            if (!entity.isShiftKeyDown()) {
                itemstack.getOrCreateTag().putBoolean("sus", (false));
            } else if (itemstack.getOrCreateTag().getDouble("penis") >= 60
                    && !itemstack.getOrCreateTag().getBoolean("sus")
                    && entity.isShiftKeyDown()) {

                // Play sound only once (server will broadcast to clients)
                Level level = (Level) world;
                level.playSound(null, BlockPos.containing(x, y, z),
                        ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")),
                        SoundSource.PLAYERS, 1, 1);

                itemstack.getOrCreateTag().putBoolean("sus", (true));
                if (entity instanceof Player _player)
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
                itemstack.getOrCreateTag().putDouble("penis", 0);
            }
        }
    }
}