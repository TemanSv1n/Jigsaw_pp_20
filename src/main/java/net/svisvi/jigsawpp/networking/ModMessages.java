package net.svisvi.jigsawpp.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.networking.packet.FluidSyncS2CPacket;
import net.svisvi.jigsawpp.networking.packet.SyncRocketDataPacket;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(JigsawPpMod.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(FluidSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncS2CPacket::new)
                .encoder(FluidSyncS2CPacket::toBytes)
                .consumerMainThread(FluidSyncS2CPacket::handle)
                .add();

//        net.messageBuilder(PurgenEnvironmentPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(PurgenEnvironmentPacket::new)
//                .encoder(PurgenEnvironmentPacket::toBytes)
//                .consumerMainThread(PurgenEnvironmentPacket::handle)
//                .add();

        net.messageBuilder(SyncRocketDataPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncRocketDataPacket::new)
                .encoder(SyncRocketDataPacket::toBytes)
                .consumerMainThread(SyncRocketDataPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToTrackingEntity(MSG message, Entity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
    }
}
