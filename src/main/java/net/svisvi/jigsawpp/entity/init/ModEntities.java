package net.svisvi.jigsawpp.entity.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.drist_tnt.PooclearTnt;
import net.svisvi.jigsawpp.entity.drist_tnt.PrimedDristTnt;
import net.svisvi.jigsawpp.entity.emitters.*;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairEntity;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;
import net.svisvi.jigsawpp.entity.plunger.ThrownPlungerEntity;
import net.svisvi.jigsawpp.entity.projectile.*;
import net.svisvi.jigsawpp.entity.beaverSpider.BeaverSpiderEntity;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieEntity;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;
import net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner.BeaverZombieSpawner;


import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileEntity;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.SlonGunGreenProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PurgenGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.GassyGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PonosGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.SmokeGrenadeProjectile;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverEntity;
import net.svisvi.jigsawpp.entity.teapod.blackTeapodSpider.BlackTeapodSpider;
import net.svisvi.jigsawpp.entity.teapod.rawTeapodSpider.RawTeapodSpider;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpider;
import net.svisvi.jigsawpp.entity.tntpot.PrimedNuclearTeapot;
import net.svisvi.jigsawpp.entity.tntpot.PrimedTntPot;
import net.svisvi.jigsawpp.entity.cursed_cow.CursedCowEntity;


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
    public static final RegistryObject<EntityType<NuclearTeapotMissileEntity>> NUCLEAR_TEAPOT_MISSILE_ENTITY =
            ENTITY_TYPES.register("nuclear_teapot_missile_entity", () -> EntityType.Builder.<NuclearTeapotMissileEntity>of(NuclearTeapotMissileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("nuclear_teapot_missile_entity"));
    public static final RegistryObject<EntityType<TeapotMissileEntity>> TEAPOT_MISSILE_ENTITY =
            ENTITY_TYPES.register("teapot_missile_entity", () -> EntityType.Builder.<TeapotMissileEntity>of(TeapotMissileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("teapot_missile_entity"));

    public static final RegistryObject<EntityType<PurgenGrenadeProjectile>> PURGEN_GRENADE_PROJECTILE =
            ENTITY_TYPES.register("purgen_grenade_projectile", () -> EntityType.Builder.<PurgenGrenadeProjectile>of(PurgenGrenadeProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("purgen_granade_projectile"));
    public static final RegistryObject<EntityType<GassyGrenadeProjectile>> GASSY_GRENADE_PROJECTILE =
            ENTITY_TYPES.register("gassy_grenade_projectile", () -> EntityType.Builder.<GassyGrenadeProjectile>of(GassyGrenadeProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("gassy_granade_projectile"));
    public static final RegistryObject<EntityType<PonosGrenadeProjectile>> PONOS_GRENADE_PROJECTILE =
            ENTITY_TYPES.register("ponos_grenade_projectile", () -> EntityType.Builder.<PonosGrenadeProjectile>of(PonosGrenadeProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("ponos_grenade_projectile"));
    public static final RegistryObject<EntityType<SmokeGrenadeProjectile>> SMOKE_GRENADE_PROJECTILE =
            ENTITY_TYPES.register("smoke_grenade_projectile", () -> EntityType.Builder.<SmokeGrenadeProjectile>of(SmokeGrenadeProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("smoke_grenade_projectile"));

    public static final RegistryObject<EntityType<TeapodSpider>> TEAPOD_SPIDER =
            ENTITY_TYPES.register("teapod_spider", () -> EntityType.Builder.of(TeapodSpider::new, MobCategory.MONSTER)
                    .sized(1f, 1f).build("teapod_spider"));
    public static final RegistryObject<EntityType<BlackTeapodSpider>> BLACK_TEAPOD_SPIDER =
            ENTITY_TYPES.register("black_teapod_spider", () -> EntityType.Builder.of(BlackTeapodSpider::new, MobCategory.MONSTER)
                    .sized(1f, 1f).build("black_teapod_spider"));
    public static final RegistryObject<EntityType<RawTeapodSpider>> RAW_TEAPOD_SPIDER =
            ENTITY_TYPES.register("raw_teapod_spider", () -> EntityType.Builder.of(RawTeapodSpider::new, MobCategory.MONSTER)
                    .sized(1f, 1f).build("raw_teapod_spider"));


  
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
    public static final RegistryObject<EntityType<PrimedTntPot>> TNTPOT =
            ENTITY_TYPES.register("tntpot",() -> EntityType.Builder.<PrimedTntPot>of(PrimedTntPot::new, MobCategory.MISC)
                    .fireImmune().sized(0.98f,0.98f).clientTrackingRange(10).updateInterval(10).build("tntpot"));
    public static final RegistryObject<EntityType<PrimedNuclearTeapot>> NUCLEAR_TEAPOT =
            ENTITY_TYPES.register("nuclear_teapot",() -> EntityType.Builder.<PrimedNuclearTeapot>of(PrimedNuclearTeapot::new, MobCategory.MISC)
                    .fireImmune().sized(0.98f,0.98f).clientTrackingRange(10).updateInterval(10).build("tntpot"));
    public static final RegistryObject<EntityType<DristTntStickProjectile>> DRIST_TNT_STICK =
            ENTITY_TYPES.register("drist_tnt_stick", () -> EntityType.Builder.<DristTntStickProjectile>of(DristTntStickProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("drist_tnt_stick"));
    public static final RegistryObject<EntityType<JetstreamChairEntity>> JETSTREAM_CHAIR =
            ENTITY_TYPES.register("jetstream_chair",() -> EntityType.Builder.<JetstreamChairEntity>of(JetstreamChairEntity::new, MobCategory.MISC)
                    .sized(0.7f,1.1f).setTrackingRange(64).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).build("jetstream_chair"));

    public static final RegistryObject<EntityType<StoneBeaverEntity>> STONE_BEAVER =
            ENTITY_TYPES.register("stone_beaver",() -> EntityType.Builder.<StoneBeaverEntity>of(StoneBeaverEntity::new, MobCategory.MONSTER)
                    .sized(1f,1f).fireImmune().setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(3).build("stone_beaver"));

    public static final RegistryObject<EntityType<BeaverZombieEntity>> ZOMBIE_BEAVER =
            ENTITY_TYPES.register("zombie_beaver", () -> EntityType.Builder.<BeaverZombieEntity>of(BeaverZombieEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 2f).build("zombie_beaver"));

    public static final RegistryObject<EntityType<BeaverZombieSpawner>> ZOMBIE_BEAVER_SPAWNER =
            ENTITY_TYPES.register("zombie_beaver_spawner", () -> EntityType.Builder.<BeaverZombieSpawner>of(BeaverZombieSpawner::new, MobCategory.MONSTER)
                    .sized(3f, 3f).build("zombie_beaver_spawner"));

    public static final RegistryObject<EntityType<PoopsEntity>> POOPS =
            ENTITY_TYPES.register("poops", () -> EntityType.Builder.<PoopsEntity>of(PoopsEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("poops"));

    public static final RegistryObject<EntityType<PoopisEntity>> POOPIS =
            ENTITY_TYPES.register("poopis", () -> EntityType.Builder.<PoopisEntity>of(PoopisEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("poopis"));

    //Emitters Zone
    public static final RegistryObject<EntityType<AbstractEmitterEntity>> ABSTRACT_EMITTER =
            ENTITY_TYPES.register("abstract_emitter", () -> EntityType.Builder.<AbstractEmitterEntity>of(AbstractEmitterEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).build("abstract_emitter"));

    public static final RegistryObject<EntityType<GasEmitterEntity>> GAS_EMITTER =
            ENTITY_TYPES.register("gas_emitter", () -> EntityType.Builder.<GasEmitterEntity>of(GasEmitterEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).build("gas_emitter"));

    public static final RegistryObject<EntityType<PoopGasEmitterEntity>> POOP_GAS_EMITTER =
            ENTITY_TYPES.register("poop_gas_emitter", () -> EntityType.Builder.<PoopGasEmitterEntity>of(PoopGasEmitterEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).build("poop_gas_emitter"));

    public static final RegistryObject<EntityType<PurgativeGasEmitterEntity>> PURGATIVE_GAS_EMITTER =
            ENTITY_TYPES.register("purgative_gas_emitter", () -> EntityType.Builder.<PurgativeGasEmitterEntity>of(PurgativeGasEmitterEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).build("purgative_gas_emitter"));

    public static final RegistryObject<EntityType<FartGasEmitterEntity>> FART_GAS_EMITTER =
            ENTITY_TYPES.register("fart_gas_emitter", () -> EntityType.Builder.<FartGasEmitterEntity>of(FartGasEmitterEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).build("fart_gas_emitter"));

    public static final RegistryObject<EntityType<SmokeGasEmitterEntity>> SMOKE_GAS_EMITTER =
            ENTITY_TYPES.register("smoke_gas_emitter", () -> EntityType.Builder.<SmokeGasEmitterEntity>of(SmokeGasEmitterEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).build("smoke_gas_emitter"));

    public static final RegistryObject<EntityType<CursedCowEntity>> CURSED_COW =
            ENTITY_TYPES.register("cursed_cow", () -> EntityType.Builder.<CursedCowEntity>of(CursedCowEntity::new, MobCategory.MISC)
                    .sized(0.9f, 1f)
                    .clientTrackingRange(10)
                    .build("cursed_cow"));

    public static final RegistryObject<EntityType<EmitterProjectile>> EMITTER_PROJECTILE =
            ENTITY_TYPES.register("emitter_projectile", () -> EntityType.Builder.<EmitterProjectile>of(EmitterProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("emitter_projectile"));

    public static final RegistryObject<EntityType<PooclearTnt>> POOCLEAR_TNT =
            ENTITY_TYPES.register("pooclear_tnt",() -> EntityType.Builder.<PooclearTnt>of(PooclearTnt::new, MobCategory.MISC)
                    .fireImmune().sized(0.98f,0.98f).clientTrackingRange(10).updateInterval(10).build("pooclear_tnt"));





    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
