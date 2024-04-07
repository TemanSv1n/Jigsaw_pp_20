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
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.entity.projectile.floppa_missile.FloppaMissileEntity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, JigsawPpMod.MODID);

    public static final RegistryObject<EntityType<MossElephantEntity>> MOSS_ELEPHANT =
            ENTITY_TYPES.register("moss_elephant",() -> EntityType.Builder.of(MossElephantEntity::new, MobCategory.CREATURE)
                    .sized(2.4f,2.7f).build("moss_elephant"));
    public static final RegistryObject<EntityType<ThrownSweetBreadProjectile>> SWEET_BREAD =
            ENTITY_TYPES.register("sweet_bread", () -> EntityType.Builder.<ThrownSweetBreadProjectile>of(ThrownSweetBreadProjectile::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("sweet_bread"));
    public static final RegistryObject<EntityType<FloppaMissileEntity>> FLOPPA_MISSILE =
            ENTITY_TYPES.register("floppa_missile", () -> EntityType.Builder.<FloppaMissileEntity>of(FloppaMissileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("floppa_misile"));
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
