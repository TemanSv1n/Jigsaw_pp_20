package net.svisvi.jigsawpp.event;

import com.google.common.collect.EvictingQueue;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.config.ModServerConfigs;
import net.svisvi.jigsawpp.procedures.Pair;

import java.util.function.Consumer;

// ACHTUNG!!! STOLEN INTELLECTUAL PROPERTY!!!
@Mod.EventBusSubscriber(modid = JigsawPpMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PatchouliGiving {

    private static final String PLAYED_BEFORE = "ap_played_before";
    private static final int CHAT_QUEUE_MAX_SIZE = 50;
    private static final int PLAYER_QUEUE_MAX_SIZE = 50;
    public static final EvictingQueue<Pair<Long, ChatMessageObject>> messageQueue = EvictingQueue.create(CHAT_QUEUE_MAX_SIZE);
    public static final EvictingQueue<Pair<Long, PlayerMessageObject>> playerMessageQueue = EvictingQueue.create(PLAYER_QUEUE_MAX_SIZE);
    private static long lastPlayerMessageID = 0;

    @SubscribeEvent
    public static void onWorldJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        // We could switch to the advancement way to give new players the book. However, that would not allow us to create
        // a config option for that. So we will stick with the custom solution here.
    if (ModList.get().isLoaded("patchouli")) {   // See https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/giving-new
        if (ModServerConfigs.GIVE_PATCHOULI_BOOK.get()) {
            if (!hasPlayedBefore(player)) {
                ItemStack book = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("patchouli", "guide_book")));
                CompoundTag nbt = new CompoundTag();
                nbt.putString("patchouli:book", "jigsaw_pp:kapital");
                book.setTag(nbt);
                player.addItem(book);
            }
        }}

        putPlayerMessage(Pair.of(getLastPlayerMessageID(), new PlayerMessageObject("playerJoin", player.getName().getString(), player.level().dimension().location().toString(), "")));
    }

    public static synchronized void putPlayerMessage(Pair<Long, PlayerMessageObject> message) {
        playerMessageQueue.add(message);
        lastPlayerMessageID++;
    }

    public static synchronized long getLastPlayerMessageID() {
        return lastPlayerMessageID;
    }

    private static boolean hasPlayedBefore(Player player) {
        CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        if (tag.getBoolean(PLAYED_BEFORE)) {
            return true;
        } else {
            tag.putBoolean(PLAYED_BEFORE, true);
            player.getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);
            return false;
        }
    }

    public record ChatMessageObject(String username, String message, String uuid, boolean isHidden) {}
    public record PlayerMessageObject(String eventName, String playerName, String fromDimension, String toDimension) {}
}