package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class NuclearShroom {
    public static void createNuclearMushroom(Level level, double x, double y, double z) {
        if (level instanceof ServerLevel serverLevel) {
            // Mushroom parameters
            int stemHeight = 20;
            double stemRadius = 2.0;
            int stemParticlesPerLayer = 30;
            int capHeight = 10;
            double capBaseRadius = 15.0;
            int capParticlesPerLayer = 100;

            // Create mushroom stem
            for (int yOffset = 0; yOffset < stemHeight; yOffset++) {
                for (int i = 0; i < stemParticlesPerLayer; i++) {
                    double angle = 2 * Math.PI * i / stemParticlesPerLayer;
                    double dx = stemRadius * Math.cos(angle);
                    double dz = stemRadius * Math.sin(angle);
                    sendFarParticles(serverLevel,
                            ParticleTypes.CAMPFIRE_COSY_SMOKE,
                            x + dx, y + yOffset, z + dz,
                            0.1, 0.1, 0.1);
                }
            }

            // Create mushroom cap
            for (int yOffset = 0; yOffset < capHeight; yOffset++) {
                double progress = (double) yOffset / capHeight;
                double currentRadius = capBaseRadius * Math.sqrt(1 - progress * progress);
                int particlesThisLayer = (int) (capParticlesPerLayer * (currentRadius / capBaseRadius));

                for (int i = 0; i < particlesThisLayer; i++) {
                    double angle = 2 * Math.PI * i / particlesThisLayer;
                    double dx = currentRadius * Math.cos(angle);
                    double dz = currentRadius * Math.sin(angle);

                    ParticleOptions type = ParticleTypes.CAMPFIRE_COSY_SMOKE;
                    double speed = 0.2;

                    if (yOffset < capHeight / 3) {
                        type = ParticleTypes.EXPLOSION_EMITTER;
                        speed = 0.3;
                    } else if (yOffset < 2 * capHeight / 3) {
                        type = ParticleTypes.LAVA;
                        speed = 0.25;
                    }

                    sendFarParticles(serverLevel, type,
                            x + dx, y + stemHeight + yOffset, z + dz,
                            speed, speed, speed);
                }
            }

            // Add fiery base
            sendFarParticles(serverLevel, ParticleTypes.FLAME, x, y, z, 5, 3, 5, 100, 0.2);
            sendFarParticles(serverLevel, ParticleTypes.LAVA, x, y, z, 5, 1, 5, 50, 0.1);

            // Add core
            sendFarParticles(serverLevel, ParticleTypes.GLOW, x, y + stemHeight, z, 2, 3, 2, 20, 0);
        }
    }

    public static void sendFarParticles(ServerLevel level, ParticleOptions type,
                                         double x, double y, double z,
                                         double xSpeed, double ySpeed, double zSpeed) {
        // Use the long-distance version for single particles
        for (ServerPlayer player : level.players()) {
            level.sendParticles(player, type, true, x, y, z, 1, 0, 0, 0, xSpeed);
        }
    }

    public static void sendFarParticles(ServerLevel level, ParticleOptions type,
                                         double x, double y, double z,
                                         double xd, double yd, double zd,
                                         int count, double speed) {
        // Use the broadcast version with long-distance flag
        ClientboundLevelParticlesPacket packet = new ClientboundLevelParticlesPacket(
                type, true, x, y, z,
                (float)xd, (float)yd, (float)zd,
                (float)speed, count
        );

        for (ServerPlayer player : level.players()) {
            if (player.level() == level) {
                player.connection.send(packet);
            }
        }
    }
}