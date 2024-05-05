package net.svisvi.jigsawpp.entity.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.entity.projectile.ExtinguisherProjectile;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;

import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileEntity;
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
  
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
