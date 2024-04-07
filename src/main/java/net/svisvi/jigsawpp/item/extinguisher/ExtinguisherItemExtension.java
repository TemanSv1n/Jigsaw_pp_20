package net.svisvi.jigsawpp.item.extinguisher;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.ExtinguisherProjectile;
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ExtinguisherItemExtension {

    static DispenseItemBehavior dispenseitembehavior1 = new DefaultDispenseItemBehavior() {
        public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
            Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
            Position position = DispenserBlock.getDispensePosition(blockSource);

            Level level = blockSource.getLevel();
//                Projectile _SpawnEntity = new Object(){
//                    public Projectile getArrow(Level level, float damage, int knockback ){
//                        AbstractArrow SpawnEntity = new ExtinguisherProjectile(ModEntities.EXTINGUISHER_PROJECTILE.get(), level);
//                        SpawnEntity.setBaseDamage(damage);
//                        SpawnEntity.setKnockback(knockback);
//                        SpawnEntity.setSilent(true);
//                        return SpawnEntity;
//                    }
//                }.getArrow(level, 0, 0);
//                _SpawnEntity.setPos(blockSource.getPos().getX(), blockSource.getPos().getY() - 0.1, blockSource.getPos().getZ());
            for (int i = 0; i <= 5; i++) {
                Projectile _SpawnEntity = new ExtinguisherProjectile(ModEntities.EXTINGUISHER_PROJECTILE.get(), position.x(), position.y(), position.z(), level);

                _SpawnEntity.shoot((double) direction.getStepX(), (double) ((float) direction.getStepY() + 0.1F), (double) direction.getStepZ(), 0.7f, 5);
                level.addFreshEntity(_SpawnEntity);


            }
            ItemStack _ist = itemStack;
            if (_ist.hurt(1, RandomSource.create(), null)) {
                _ist.shrink(1);
                _ist.setDamageValue(0);
            }

            return itemStack;
        }

        /**
         * Play the dispense sound from the specified block.
         */
        protected void playSound(BlockSource p_123554_) {
            p_123554_.getLevel().levelEvent(1018, p_123554_.getPos(), 0);
        }
    };


        /**
         * Dispense the specified stack, play the dispense sound, and spawn particles.
         */
        
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.EXTINGUISHER.get(), dispenseitembehavior1));

    }
}
