package net.svisvi.jigsawpp.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryMenu;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.networking.packet.FluidSyncS2CPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PurgenFactoryBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(7);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public final FluidTank FLUID_TANK = new FluidTank(8000){
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return super.isFluidValid(stack);
        }
    };

    public void setFluid(FluidStack stack){
        this.FLUID_TANK.setFluid(stack);
    }
    public FluidStack getFluidStack(){
        return this.FLUID_TANK.getFluid();
    }

    public static void drainFluid(PurgenFactoryBlockEntity pEntity, int amount){

    }

//    public static void fillFluid(PurgenFactoryBlockEntity pEntity, int amount, FluidStack fluidStack){
//        pEntity.FLUID_TANK.fill()
//    }


    public PurgenFactoryBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(ModBlockEntities.PURGEN_FACTORY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> PurgenFactoryBlockEntity.this.progress;
                    case 1 -> PurgenFactoryBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> PurgenFactoryBlockEntity.this.progress = pValue;
                    case 1 -> PurgenFactoryBlockEntity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {
                return 7;
            }
        };
    }

    //slots 0-1-2-3 INGREDIENT INPUT
    //slot 5 - CATALYST
    //slot 6 PILULE INPUT
    //slot 7 OUTPUT


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new PurgenFactoryMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.jigsaw_pp.purgen_factory");
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("purgen_factory.progress", progress);
        pTag.put("fluidTank", FLUID_TANK.writeToNBT(new CompoundTag()));


        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("purgen_factory.progress");
        if (pTag.get("fluidTank") instanceof CompoundTag compoundTag)
            FLUID_TANK.readFromNBT(compoundTag);


    }

}
