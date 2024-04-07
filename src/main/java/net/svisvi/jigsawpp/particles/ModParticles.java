
package net.svisvi.jigsawpp.particles;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.api.distmarker.Dist;

import net.svisvi.jigsawpp.particles.FlyParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(ModParticleTypes.FLY.get(), FlyParticle::provider);
		event.registerSpriteSet(ModParticleTypes.POOP.get(), PoopParticle::provider);
		event.registerSpriteSet(ModParticleTypes.POOP_BUBBLE.get(), PoopBubbleParticle::provider);
	}
}
