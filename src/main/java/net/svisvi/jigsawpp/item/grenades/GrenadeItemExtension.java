package net.svisvi.jigsawpp.item.grenades;

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
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.GassyGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PonosGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PurgenGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.SmokeGrenadeProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;
//VERY INDIAN CODED!!!
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GrenadeItemExtension {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.PONOS_GRENADE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new PonosGrenadeProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    //p_123466_.setItem(new ItemStack(ModItems.SWEET_BREAD.get()));
                    p_123466_.setItem(new ItemStack(ModItems.PONOS_GRENADE_USED.get()));
                });
            }
        }));

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.GASSY_GRENADE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new GassyGrenadeProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    //p_123466_.setItem(new ItemStack(ModItems.SWEET_BREAD.get()));
                    p_123466_.setItem(new ItemStack(ModItems.GASSY_GRENADE_USED.get()));
                });
            }
        }));

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.PURGEN_GRENADE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new PurgenGrenadeProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    //p_123466_.setItem(new ItemStack(ModItems.SWEET_BREAD.get()));
                    p_123466_.setItem(new ItemStack(ModItems.PURGEN_GRENADE_USED.get()));
                    p_123466_.setAmplifier(PurgenGrenadeItem.getAmplifier(p_123470_));
                });
            }
        }));

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.SMOKE_GRENADE.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level p_123468_, Position p_123469_, ItemStack p_123470_) {
                return Util.make(new SmokeGrenadeProjectile(p_123468_, p_123469_.x(), p_123469_.y(), p_123469_.z()), (p_123466_) -> {
                    //p_123466_.setItem(new ItemStack(ModItems.SWEET_BREAD.get()));
                    p_123466_.setItem(new ItemStack(ModItems.SMOKE_GRENADE_USED.get()));
                });
            }
        }));


    }
}
