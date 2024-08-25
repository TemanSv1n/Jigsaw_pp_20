package net.svisvi.jigsawpp.item.poops;

import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.PoopisEntity;
import net.svisvi.jigsawpp.entity.projectile.PoopsEntity;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PoopisItemExtension {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.POOPIS.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position pos, ItemStack stack) {
                PoopisEntity arrow = new PoopisEntity(ModEntities.POOPIS.get(), pos.x(), pos.y(), pos.z(), level);
                arrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return arrow;
            }
        }));

    }
}
