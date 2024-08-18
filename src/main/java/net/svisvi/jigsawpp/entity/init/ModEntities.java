package net.svisvi.jigsawpp.entity.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.drist_tnt.PrimedDristTnt;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairEntity;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;
import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerEntity;
import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerRenderer;
import net.svisvi.jigsawpp.entity.projectile.*;
import net.svisvi.jigsawpp.entity.beaverSpider.BeaverSpiderEntity;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;

import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileEntity;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.SlonGunGreenProjectile;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverEntity;
import net.svisvi.jigsawpp.entity.teapodSpider.TeapodSpider;
import net.svisvi.jigsawpp.item.purgen_gun.PurgenGunItem;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JigsawPpMod.MODID);

    public static final RegistryObject<EntityType<MossElephantEntity>> MOSS_ELEPHANT =
            ENTITY_TYPES.register("moss_elephant",() -> EntityType.Builder.of(MossElephantEntity::new, MobCategory.CREATURE)
                    .sized(2.4f,2.7f).build("moss_elephant"));
    public static final RegistryObject<EntityType<ThrownSweetBreadProjectile>> SWEET_BREAD =
            ENTITY_TYPES.register("sweet_bread", () -> EntityType.Builder.<ThrownSweetBreadProjectile>of(ThrownSweetBreadProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("sweet_bread"));

    public static final RegistryObject<EntityType<ExtinguisherProjectile>> EXTINGUISHER_PROJECTILE =
            ENTITY_TYPES.register("exitnguisher_projectile", () -> EntityType.Builder.<ExtinguisherProjectile>of(ExtinguisherProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("extinguisher_projectile"));

    public static final RegistryObject<EntityType<FloppaMissileEntity>> FLOPPA_MISSILE =
            ENTITY_TYPES.register("floppa_missile", () -> EntityType.Builder.<FloppaMissileEntity>of(FloppaMissileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("floppa_misile"));

    public static final RegistryObject<EntityType<PurgenPiluleProjectile>> PURGEN_PILULE_PROJECTILE =
            ENTITY_TYPES.register("purgen_pilule_projectile", () -> EntityType.Builder.<PurgenPiluleProjectile>of(PurgenPiluleProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("purgen_pilule_projectile"));
    public static final RegistryObject<EntityType<BlabbitEntity>> BLABBIT =
            ENTITY_TYPES.register("blabbit",() -> EntityType.Builder.of(BlabbitEntity::new, MobCategory.MONSTER)
                    .sized(1f,1.8f).build("blabbit"));

    public static final RegistryObject<EntityType<SlonProjectile>> SLONGUN_PROJECTILE =
            ENTITY_TYPES.register("slon_gun_projectile", () -> EntityType.Builder.<SlonProjectile>of(SlonProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("slon_gun_projectile"));

    public static final RegistryObject<EntityType<SlonGunGreenProjectile>> SLONGUN_GREEN_PROJECTILE =
            ENTITY_TYPES.register("slon_gun_green_projectile", () -> EntityType.Builder.<SlonGunGreenProjectile>of(SlonGunGreenProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("slon_gun_green_projectile"));

    public static final RegistryObject<EntityType<BeaverBombProjectile>> BEAVER_BOMB =
            ENTITY_TYPES.register("beaver_bomb", () -> EntityType.Builder.<BeaverBombProjectile>of(BeaverBombProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("beaver_bomb"));

    public static final RegistryObject<EntityType<BeaverzookaEntity>> BEAVERZOOKA_ENTITY =
            ENTITY_TYPES.register("beaverzooka_entity", () -> EntityType.Builder.<BeaverzookaEntity>of(BeaverzookaEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("beaverzooka_entity"));

    public static final RegistryObject<EntityType<TeapodSpider>> TEAPOD_SPIDER =
            ENTITY_TYPES.register("teapod_spider", () -> EntityType.Builder.of(TeapodSpider::new, MobCategory.MONSTER)
                    .sized(1f, 1f).build("teapod_spider"));

  
    public static final RegistryObject<EntityType<TreeProjectile>> TREE_PROJECTILE =
            ENTITY_TYPES.register("tree_projectile", () -> EntityType.Builder.<TreeProjectile>of(TreeProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("tree_projectile"));

    public static final RegistryObject<EntityType<ThrownPlungerEntity>> THROWN_PLUNGER =
            ENTITY_TYPES.register("plunger", () -> EntityType.Builder.<ThrownPlungerEntity>of(ThrownPlungerEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("plunger"));
    public static final RegistryObject<EntityType<BeaverSpiderEntity>> BEAVER_SPIDER =
            ENTITY_TYPES.register("beaver_spider", () -> EntityType.Builder.<BeaverSpiderEntity>of(BeaverSpiderEntity::new, MobCategory.MONSTER)
                    .sized(1f, 1f).build("beaver_spider"));

    public static final RegistryObject<EntityType<PrimedDristTnt>> DRIST_TNT =
            ENTITY_TYPES.register("drist_tnt",() -> EntityType.Builder.<PrimedDristTnt>of(PrimedDristTnt::new, MobCategory.MISC)
                    .fireImmune().sized(0.98f,0.98f).clientTrackingRange(10).updateInterval(10).build("drist_tnt"));
    public static final RegistryObject<EntityType<DristTntStickProjectile>> DRIST_TNT_STICK =
            ENTITY_TYPES.register("drist_tnt_stick", () -> EntityType.Builder.<DristTntStickProjectile>of(DristTntStickProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("drist_tnt_stick"));
    public static final RegistryObject<EntityType<JetstreamChairEntity>> JETSTREAM_CHAIR =
            ENTITY_TYPES.register("jetstream_chair",() -> EntityType.Builder.<JetstreamChairEntity>of(JetstreamChairEntity::new, MobCategory.MISC)
                    .sized(0.7f,1.1f).setTrackingRange(64).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).build("jetstream_chair"));

    public static final RegistryObject<EntityType<StoneBeaverEntity>> STONE_BEAVER =
            ENTITY_TYPES.register("stone_beaver",() -> EntityType.Builder.<StoneBeaverEntity>of(StoneBeaverEntity::new, MobCategory.MONSTER)
                    .sized(1f,1f).fireImmune().setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build("stone_beaver"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
