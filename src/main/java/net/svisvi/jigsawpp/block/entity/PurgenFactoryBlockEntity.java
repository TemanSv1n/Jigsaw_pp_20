package net.svisvi.jigsawpp.block.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.block.purgen_factory.PurgenPiluleBuilder;
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryMenu;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.networking.packet.FluidSyncS2CPacket;
import net.svisvi.jigsawpp.recipe.ModRecipes;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PurgenFactoryBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider, WorldlyContainer {
    private final ItemStackHandler itemHandler = new ItemStackHandler(8);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    public int partenSize(){
        int ret = 1;
        if (this.itemHandler.getStackInSlot(7) != ItemStack.EMPTY){
            ret = this.itemHandler.getStackInSlot(7).getCount() + 1;
        }
        return ret;
    }

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
                return 8;
            }
        };
    }

    //slots 0-1-2-3 INGREDIENT INPUT
    //slot 4 - CATALYST
    //slot 5 PILULE INPUT
    //slot 6 OUTPUT
    private static final int OUTPUT_SLOT = 6;


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
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
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
    protected Component getDefaultName() {
        return null;
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

    //RECIPES AND CRAFTING

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(canCraft()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem(pLevel, pPos, pState);
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }




    private void craftItem(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<PurgenFactoryRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        ItemStack built_purgen = PurgenPiluleBuilder.build_main(recipe, itemHandler.getStackInSlot(2), itemHandler.getStackInSlot(3),
                itemHandler.getStackInSlot(4), level, pPos, pState);
        int pu_count = this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + built_purgen.getCount() * partenSize();

        if (this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()
                || AbstractPiluleItem.comparePilules(built_purgen, this.itemHandler.getStackInSlot(OUTPUT_SLOT))

        ){
            this.itemHandler.extractItem(0, partenSize(), false);
            this.itemHandler.extractItem(1, partenSize(), false);
            this.itemHandler.extractItem(2, partenSize(), false);
            if (itemHandler.getStackInSlot(3) != ItemStack.EMPTY) {
                this.itemHandler.extractItem(3, partenSize(), false);
            }
            if (itemHandler.getStackInSlot(4) != ItemStack.EMPTY) {
                this.itemHandler.extractItem(4, 1, false);
            }
            this.itemHandler.extractItem(5, partenSize(), false);
            this.FLUID_TANK.drain(recipe.get().getFluidStack().getAmount() * partenSize(), IFluidHandler.FluidAction.EXECUTE);
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, built_purgen.copyWithCount(pu_count));
        } else {
            // KA BOOM
        }

        //REMOVE ITEMS FROM SLOTS & FLUID


        //


    }

    public boolean canCraft(){
        if (!hasRecipe()){
            return false;
        }
        if (itemHandler.getStackInSlot(0).getCount() >= partenSize()
                //ingredients
                && itemHandler.getStackInSlot(1).getCount() >= partenSize()
                //free ingredients
                && itemHandler.getStackInSlot(2).getCount() >= partenSize()
                && itemHandler.getStackInSlot(2).getItem().isEdible()
                //optional items
                && ((itemHandler.getStackInSlot(3).getCount() >= partenSize()
                && itemHandler.getStackInSlot(3).getItem().isEdible())
                || itemHandler.getStackInSlot(3) == ItemStack.EMPTY)
                //pilules
                && itemHandler.getStackInSlot(5).getCount() >= partenSize()
                && itemHandler.getStackInSlot(5).getItem() == ModItems.EMPTY_PILULE.get()){
            return true;
        }
        return false;
    }
    private boolean hasRecipe() {
        Optional<PurgenFactoryRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount() * partenSize()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<PurgenFactoryRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < 2; i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }
        FluidStack fstack = getFluidStack();


        return getRecipeFor(PurgenFactoryRecipe.Type.INSTANCE, inventory, level, fstack);
    }


    public static <C extends Container, T extends Recipe<C>> Optional<PurgenFactoryRecipe> getRecipeFor(RecipeType<PurgenFactoryRecipe> pRecipeType, SimpleContainer pInventory, Level pLevel, FluidStack fstack) {

        return pLevel.getRecipeManager().getAllRecipesFor(pRecipeType).stream().filter((svo) -> {
            return svo.match(pInventory, fstack, pLevel);
        }).findFirst();
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) {
            return new int[]{6};
        } else {
            return pSide == Direction.UP ? new int[]{4,5} : new int[]{0,1,2,3};
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if (pDirection == Direction.DOWN && pIndex == 6){
            return true;
        }return false;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return null;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return null;
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    //don't takers
    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex == 6) {
            return false;
        } else if (pIndex == 4 && !pStack.is(ItemTags.create(new ResourceLocation("jigsaw_pp:purgen_catalysts")))) {
            return false;
        } else if (pIndex == 5 && !(pStack.getItem() == ModItems.EMPTY_PILULE.get())){
            return false;
        } else if (pIndex == 7 && !(pStack.getItem() == ModItems.BATCH_SIZE_CARD.get())) {
            return false;
        }
        return true;
    }

    @Override
    public void clearContent() {

    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {

    }
}
