package net.svisvi.jigsawpp.block.yoba;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.ExtinguisherProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class YobaDispenserExtension {

    static DispenseItemBehavior dispenseitembehavior1 = new DefaultDispenseItemBehavior() {
        public ItemStack execute(BlockSource p_123437_, ItemStack p_123438_) {
            Level level = p_123437_.getLevel();
            BlockPos blockpos = p_123437_.getPos().relative(p_123437_.getBlockState().getValue(DispenserBlock.FACING));
            YobaBlock carvedpumpkinblock = (YobaBlock) ModBlocks.YOBA.get();
            if (p_123438_.getItem() == ModItems.YOBA_POOP.get()) {
                carvedpumpkinblock = (YobaPoopBlock) ModBlocks.YOBA_POOP.get();
            }
            if (level.isEmptyBlock(blockpos)) {
                if (!level.isClientSide) {
                    level.setBlock(blockpos, carvedpumpkinblock.defaultBlockState().setValue(YobaBlock.REAL_FACING, p_123437_.getBlockState().getValue(DispenserBlock.FACING)), 3);
                    level.gameEvent((Entity)null, GameEvent.BLOCK_PLACE, blockpos);
                }

                p_123438_.shrink(1);
                //this.setSuccess(true);
            }

            return p_123438_;
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
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.YOBA.get(), dispenseitembehavior1));
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.YOBA_POOP.get(), dispenseitembehavior1));


    }
}
