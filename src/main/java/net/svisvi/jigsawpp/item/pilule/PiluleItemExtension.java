package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PiluleItemExtension {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.BASIC_PURGEN_PILULE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new PurgenPiluleProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    p_123466_.setItem(p_123470_);
                });
            }
        }));

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.ADVANCED_PURGEN_PILULE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new PurgenPiluleProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    p_123466_.setItem(p_123470_);
                });
            }
        }));

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.CRYSTAL_PURGEN_PILULE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new PurgenPiluleProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    p_123466_.setItem(p_123470_);
                });
            }
        }));

    }
}
