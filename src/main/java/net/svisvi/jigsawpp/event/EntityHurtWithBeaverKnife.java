package net.svisvi.jigsawpp.event;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.svisvi.jigsawpp.item.init.ModItems;

import javax.annotation.Nullable;
//ACHTUNG MCREATOR LEGACY CODE
@Mod.EventBusSubscriber
public class EntityHurtWithBeaverKnife {
    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent event) {
        if (event != null && event.getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
        }
    }

    public static void execute(LevelAccessor world, Entity entity, Entity sourceentity) {
        execute(null, world, entity, sourceentity);
    }

    private static void execute(@Nullable Event event, LevelAccessor world, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null)
            return;
        if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == ModItems.BEAVER_KNIFE.get()) {
            if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrCreateTag().getBoolean("chance") == true) {
                (sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrCreateTag().putBoolean("chance", (false));
                DamageSource damagee = new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FLY_INTO_WALL), entity);
                //damagee.
                entity.getPersistentData().putDouble("health_d", (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1));
                entity.getPersistentData().putDouble("health_i", (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1));
                new Object() {
                    private int ticks = 0;
                    private float waitTicks;
                    private LevelAccessor world;

                    public void start(LevelAccessor world, int waitTicks) {
                        this.waitTicks = waitTicks;
                        MinecraftForge.EVENT_BUS.register(this);
                        this.world = world;
                    }

                    @SubscribeEvent
                    public void tick(TickEvent.ServerTickEvent event) {
                        if (event.phase == TickEvent.Phase.END) {
                            this.ticks += 1;
                            if (this.ticks >= this.waitTicks)
                                run();
                        }
                    }

                    private void run() {
                        entity.getPersistentData().putDouble("health_d", (entity.getPersistentData().getDouble("health_d") - (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)));
                        sourceentity.hurt((new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FLY_INTO_WALL))), (float) entity.getPersistentData().getDouble("health_d"));
                        if (entity instanceof LivingEntity _entity)
                            _entity.setHealth((float) entity.getPersistentData().getDouble("health_i"));
                        MinecraftForge.EVENT_BUS.unregister(this);
                    }
                }.start(world, 1);
            }
        }
    }
}