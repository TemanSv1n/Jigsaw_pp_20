package net.svisvi.jigsawpp.entity.init;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.svisvi.jigsawpp.entity.beaverSpider.BeaverSpiderRenderer;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieRenderer;
import net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner.BeaverZombieSpawnerRenderer;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitRenderer;
import net.svisvi.jigsawpp.entity.drist_tnt.DristTntRenderer;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairRenderer;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantRenderer;
import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerRenderer;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileRenderer;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverRenderer;
import net.svisvi.jigsawpp.entity.teapod.blackTeapodSpider.BlackTeapodSpiderRender;
import net.svisvi.jigsawpp.entity.teapod.rawTeapodSpider.RawTeapodSpiderRender;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpiderRender;
import net.svisvi.jigsawpp.entity.tntpot.NuclearTeapotRenderer;
import net.svisvi.jigsawpp.entity.tntpot.TntPotRenderer;

public class EntityRenders {
    public static void registerEntityRenders(){

        EntityRenderers.register(ModEntities.MOSS_ELEPHANT.get(), MossElephantRenderer::new);
        EntityRenderers.register(ModEntities.SWEET_BREAD.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.POOPS.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.POOPIS.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.FLOPPA_MISSILE.get(), FloppaMissileRenderer::new);
        EntityRenderers.register(ModEntities.PURGEN_PILULE_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.EXTINGUISHER_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BLABBIT.get(), BlabbitRenderer::new);
        EntityRenderers.register(ModEntities.SLONGUN_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.SLONGUN_GREEN_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BEAVER_BOMB.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BEAVERZOOKA_ENTITY.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.NUCLEAR_TEAPOT_MISSILE_ENTITY.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.TEAPOT_MISSILE_ENTITY.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModEntities.PURGEN_GRENADE_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.PONOS_GRENADE_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.GASSY_GRENADE_PROJECTILE.get(), ThrownItemRenderer::new);

        EntityRenderers.register(ModEntities.TEAPOD_SPIDER.get(), TeapodSpiderRender::new);
        EntityRenderers.register(ModEntities.BLACK_TEAPOD_SPIDER.get(), BlackTeapodSpiderRender::new);
        EntityRenderers.register(ModEntities.RAW_TEAPOD_SPIDER.get(), RawTeapodSpiderRender::new);

        EntityRenderers.register(ModEntities.STONE_BEAVER.get(), StoneBeaverRenderer::new);
        EntityRenderers.register(ModEntities.ZOMBIE_BEAVER.get(), BeaverZombieRenderer::new);
        EntityRenderers.register(ModEntities.ZOMBIE_BEAVER_SPAWNER.get(), BeaverZombieSpawnerRenderer::new);

        EntityRenderers.register(ModEntities.TREE_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.THROWN_PLUNGER.get(), ThrownPlungerRenderer::new);
        EntityRenderers.register(ModEntities.DRIST_TNT.get(), DristTntRenderer::new);
        EntityRenderers.register(ModEntities.TNTPOT.get(), TntPotRenderer::new);
        EntityRenderers.register(ModEntities.NUCLEAR_TEAPOT.get(), NuclearTeapotRenderer::new);
        EntityRenderers.register(ModEntities.DRIST_TNT_STICK.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.BEAVER_SPIDER.get(), BeaverSpiderRenderer::new);
        EntityRenderers.register(ModEntities.JETSTREAM_CHAIR.get(), (p_174090_) -> {
            return new JetstreamChairRenderer(p_174090_, ModModelLayers.JETSTREAM_CHAIR_LAYER);
        });

        //Emitters
        EntityRenderers.register(ModEntities.ABSTRACT_EMITTER.get(), NoopRenderer::new);
        EntityRenderers.register(ModEntities.GAS_EMITTER.get(), NoopRenderer::new);
        EntityRenderers.register(ModEntities.POOP_GAS_EMITTER.get(), NoopRenderer::new);
        EntityRenderers.register(ModEntities.PURGATIVE_GAS_EMITTER.get(), NoopRenderer::new);
        EntityRenderers.register(ModEntities.FART_GAS_EMITTER.get(), NoopRenderer::new);

    }
}
