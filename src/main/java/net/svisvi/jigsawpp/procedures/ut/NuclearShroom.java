package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class NuclearShroom {
    public static void createNuclearMushroom(Level leve, double x, double y, double z) {
        if (leve instanceof ServerLevel level) {
            // Mushroom stem parameters
            int stemHeight = 20;
            double stemRadius = 2.0;
            int stemParticlesPerLayer = 30;

            // Mushroom cap parameters
            int capHeight = 10;
            double capBaseRadius = 15.0;
            int capParticlesPerLayer = 100;

            // Create mushroom stem (vertical column)
            for (int yOffset = 0; yOffset < stemHeight; yOffset++) {
                for (int i = 0; i < stemParticlesPerLayer; i++) {
                    double angle = 2 * Math.PI * i / stemParticlesPerLayer;
                    double dx = stemRadius * Math.cos(angle);
                    double dz = stemRadius * Math.sin(angle);
                    level.sendParticles(ParticleTypes.LARGE_SMOKE,
                            x + dx, y + yOffset, z + dz,
                            2, 0.1, 0.1, 0.1, 0.05);
                }
            }

            // Create mushroom cap (hemisphere)
            for (int yOffset = 0; yOffset < capHeight; yOffset++) {
                double progress = (double) yOffset / capHeight;
                double currentRadius = capBaseRadius * Math.sqrt(1 - progress * progress);
                int particlesThisLayer = (int) (capParticlesPerLayer * (currentRadius / capBaseRadius));

                for (int i = 0; i < particlesThisLayer; i++) {
                    double angle = 2 * Math.PI * i / particlesThisLayer;
                    double dx = currentRadius * Math.cos(angle);
                    double dz = currentRadius * Math.sin(angle);

                    // Use different particles for different parts of the cap
                    ParticleOptions type = ParticleTypes.LARGE_SMOKE;
                    double speed = 0.05;

                    if (yOffset < capHeight / 3) {
                        type = ParticleTypes.EXPLOSION_EMITTER;
                        speed = 0.1;
                    } else if (yOffset < 2 * capHeight / 3) {
                        type = ParticleTypes.LAVA;
                        speed = 0.07;
                    }

                    level.sendParticles(type,
                            x + dx, y + stemHeight + yOffset, z + dz,
                            3, 0.2, 0.2, 0.2, speed);
                }
            }

            // Add fiery base effect
            level.sendParticles(ParticleTypes.FLAME, x, y, z,
                    100, 5, 3, 5, 0.2);
            level.sendParticles(ParticleTypes.LAVA, x, y, z,
                    50, 5, 1, 5, 0.1);

            // Add glowing core
            level.sendParticles(ParticleTypes.GLOW, x, y + stemHeight, z,
                    20, 2, 3, 2, 0);
        }
    }
}
