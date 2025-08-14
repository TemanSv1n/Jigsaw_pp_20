package net.svisvi.jigsawpp.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.svisvi.jigsawpp.entity.rocket.RocketEntity;

import java.util.function.Supplier;

public class SyncRocketDataPacket {
    private final int entityId;
    private final CompoundTag data;

    public SyncRocketDataPacket(int entityId, CompoundTag data) {
        this.entityId = entityId;
        this.data = data;
    }

    public SyncRocketDataPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.data = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarInt(entityId);
        buf.writeNbt(data);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            // Client-side handling
            if (net.minecraft.client.Minecraft.getInstance().level != null) {
                Entity entity = net.minecraft.client.Minecraft.getInstance().level.getEntity(entityId);
                if (entity instanceof RocketEntity rocket) {
                    rocket.getPersistentData().merge(data);
                }
            }
        });
        return true;
    }
}