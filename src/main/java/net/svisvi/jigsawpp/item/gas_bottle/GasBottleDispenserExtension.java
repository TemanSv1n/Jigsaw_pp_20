package net.svisvi.jigsawpp.item.gas_bottle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.svisvi.jigsawpp.item.init.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GasBottleDispenserExtension {

    private static final DispenseItemBehavior GAS_BOTTLE_DISPENSE_BEHAVIOR = new OptionalDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior defaultBehavior = new DefaultDispenseItemBehavior();

        private ItemStack takeGasBottle(BlockSource source, ItemStack inputStack, ItemStack outputStack) {
            inputStack.shrink(1); // Consume 1 gas bottle
            if (inputStack.isEmpty()) {
                source.getLevel().gameEvent(null, GameEvent.FLUID_PICKUP, source.getPos());
                return outputStack.copy(); // Return glass bottle if stack is now empty
            } else {
                // Try to add the glass bottle to the dispenser
                if (source.<DispenserBlockEntity>getEntity().addItem(outputStack.copy()) < 0) {
                    this.defaultBehavior.dispense(source, outputStack.copy()); // Drop if no space
                }
                return inputStack; // Return remaining gas bottles
            }
        }

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {
            this.setSuccess(false);
            Level level = source.getLevel();
            BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));

            if (level.isEmptyBlock(blockpos) && stack.getItem() instanceof AbstractGasBottleItem bottle) {
                bottle.emptyContents(null, level, blockpos, null, null);
                this.setSuccess(true);
                return this.takeGasBottle(source, stack, new ItemStack(Items.GLASS_BOTTLE));
            }
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {
            source.getLevel().levelEvent(1018, source.getPos(), 0);
        }
    };

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(ModItems.POOP_BOTTLE.get(), GAS_BOTTLE_DISPENSE_BEHAVIOR);
            DispenserBlock.registerBehavior(ModItems.FART_BOTTLE.get(), GAS_BOTTLE_DISPENSE_BEHAVIOR);
        });
    }
}