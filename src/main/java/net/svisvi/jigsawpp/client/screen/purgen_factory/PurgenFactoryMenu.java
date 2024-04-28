package net.svisvi.jigsawpp.client.screen.purgen_factory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.SlotItemHandler;
import net.svisvi.jigsawpp.block.entity.PurgenFactoryBlockEntity;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.client.screen.ModMenuTypes;
import net.svisvi.jigsawpp.item.init.ModItems;

public class PurgenFactoryMenu extends AbstractContainerMenu {
    private final PurgenFactoryBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;
    private FluidStack fluidStack;

    public PurgenFactoryMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(8));
    }

    public PurgenFactoryMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data){
        super(ModMenuTypes.PURGEN_FACTORY_MENU.get(), pContainerId);
        checkContainerSize(inv, 8);
        blockEntity = (PurgenFactoryBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;
        this.fluidStack = blockEntity.getFluidStack();

        addPlayerHotbar(inv);
        addPlayerInventory(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 26, 6));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 26, 24));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 26, 42));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 26, 60));
            this.addSlot(new SlotItemHandler(iItemHandler, 4, 134, 11)
            {
                private final int slot = 4;

                @Override
                public boolean mayPlace(ItemStack itemstack) {


                    return itemstack.is(ItemTags.create(new ResourceLocation("jigsaw_pp:purgen_catalysts")));
                }
            });
            this.addSlot(new SlotItemHandler(iItemHandler, 5, 152, 29)
            {
                private final int slot = 5;

                @Override
                public boolean mayPlace(ItemStack itemstack) {
                    return itemstack.getItem() == ModItems.EMPTY_PILULE.get();
                }
            });
            this.addSlot(new SlotItemHandler(iItemHandler, 6, 152, 60)
            {
                private final int slot = 6;

                @Override
                public boolean mayPlace(ItemStack itemstack) {
                    return false;
                }
            });
            this.addSlot(new SlotItemHandler(iItemHandler, 7, 134, 60)
            {
                private final int slot = 7;

                @Override
                public boolean mayPlace(ItemStack itemstack) {
                    return itemstack.getItem() == ModItems.BATCH_SIZE_CARD.get();
                }
            });
        });

        addDataSlots(data);
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }
    public PurgenFactoryBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    public boolean isCrafting(){
        return data.get(0) > 0;
    }

    public int getScaledProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    private static final int TE_INVENTORY_SLOT_COUNT = 8;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            //specific items in specific slots
            //EMPTY PILULE SLOT
            if (sourceStack.getItem() == ModItems.EMPTY_PILULE.get()){
                if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT - 3, TE_INVENTORY_FIRST_SLOT_INDEX
                        + TE_INVENTORY_SLOT_COUNT - 2, false)) {
                    return ItemStack.EMPTY;  // EMPTY_ITEM
                }
                //CATALYSTS SLOT
            } else if (sourceStack.is(ItemTags.create(new ResourceLocation("jigsaw_pp:purgen_catalysts")))){
                if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT - 4, TE_INVENTORY_FIRST_SLOT_INDEX
                        + TE_INVENTORY_SLOT_COUNT - 3, false)) {
                    return ItemStack.EMPTY;  // EMPTY_ITEM
                }
                //CARD SLOT
            } else if (sourceStack.getItem() == ModItems.BATCH_SIZE_CARD.get()) {
                if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT - 1, TE_INVENTORY_FIRST_SLOT_INDEX
                        + TE_INVENTORY_SLOT_COUNT, false)) {
                    return ItemStack.EMPTY;  // EMPTY_ITEM
                }
            }
            else if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    //don't takers



    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, ModBlocks.PURGEN_FACTORY.get());
    }


    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
