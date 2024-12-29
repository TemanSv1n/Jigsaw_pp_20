package net.svisvi.jigsawpp.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
import net.svisvi.jigsawpp.block.factory_heater.FactoryHeatProducer;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.block.purgen_factory.PurgenCatalystRecipeReader;
import net.svisvi.jigsawpp.block.purgen_factory.PurgenPiluleBuilder;
import net.svisvi.jigsawpp.client.screen.space_lift.SpaceLiftMenu;
import net.svisvi.jigsawpp.client.screen.space_lift.SpaceLiftScreen;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.teapodSpider.TeapodSpider;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.networking.packet.FluidSyncS2CPacket;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;
import net.svisvi.jigsawpp.recipe.SpaceLiftRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

public class SpaceLiftBlockEntity extends BaseContainerBlockEntity implements MenuProvider, WorldlyContainer {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 780;

    private int PROGRESS_ACCELERATOR = 5;

//    public static void fillFluid(PurgenFactoryBlockEntity pEntity, int amount, FluidStack fluidStack){
//        pEntity.FLUID_TANK.fill()
//    }


    public SpaceLiftBlockEntity(BlockPos pPos, BlockState pBlockState){
        super(ModBlockEntities.SPACE_LIFT_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> SpaceLiftBlockEntity.this.progress;
                    case 1 -> SpaceLiftBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> SpaceLiftBlockEntity.this.progress = pValue;
                    case 1 -> SpaceLiftBlockEntity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {
                return 9;
            }
        };
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        //System.out.println("SPACE LIFT MENU");
        return new SpaceLiftMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        //System.out.println("NULL menu");
        return null;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
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
        return Component.translatable("block.jigsaw_pp.space_lift");
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());


        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    //RECIPES AND CRAFTING

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
    }

    public void craftItem(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<SpaceLiftRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()){
            return;
        }
        ItemStack result = recipe.get().getResultItem(null);

        if (canCraft(pLevel, pPos, pState, recipe.get())) {
            for (int i = 0; i < 8; i++) {
                this.itemHandler.extractItem(i, recipe.get().getIngredients().get(i).getItems()[0].getCount(), false);
            }
            ItemEntity eentityToSpawn = new ItemEntity(pLevel, pPos.getX()+2, pPos.getY(), pPos.getZ(), result);
            eentityToSpawn.setPickUpDelay(10);
            pLevel.addFreshEntity(eentityToSpawn);


        }

//        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
//                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }


    public boolean canCraft(Level pLevel, BlockPos pPos, BlockState pState, SpaceLiftRecipe recipe){
        if (!hasRecipe()){
            return false;
        }
        //SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            if(this.itemHandler.getStackInSlot(i) != ItemStack.EMPTY && recipe.getIngredients().get(i).getItems()[0] != ItemStack.EMPTY){
                System.out.println(this.itemHandler.getStackInSlot(i).getCount());
                System.out.println(recipe.getIngredients().get(i).getItems()[0].getCount());
                if (this.itemHandler.getStackInSlot(i).getCount() < recipe.getIngredients().get(i).getItems()[0].getCount()){
                    return false;
                }
            };
        }
        return true;
    }
    private boolean hasRecipe() {
        Optional<SpaceLiftRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return result!=null ? true : false;
    }

    private Optional<SpaceLiftRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < 8; i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }


        return getRecipeFor(SpaceLiftRecipe.Type.INSTANCE, inventory, level);
    }


    public static <C extends Container, T extends Recipe<C>> Optional<SpaceLiftRecipe> getRecipeFor(RecipeType<SpaceLiftRecipe> pRecipeType, SimpleContainer pInventory, Level pLevel) {

        return pLevel.getRecipeManager().getAllRecipesFor(pRecipeType).stream().filter((svo) -> {
            return svo.match(pInventory, pLevel);
        }).findFirst();
    }

//    private boolean canInsertItemIntoOutputSlot(Item item) {
//        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
//    }
//
//    private boolean canInsertAmountIntoOutputSlot(int count) {
//        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
//    }



    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress(float increaser) {
        progress += (int)(PROGRESS_ACCELERATOR * increaser);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < this.getContainerSize(); i++) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return this.itemHandler.getStackInSlot(pSlot);
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return this.itemHandler.extractItem(pSlot, pAmount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return this.itemHandler.extractItem(pSlot, 1, false);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        ItemStack itemstack = this.itemHandler.getStackInSlot(pSlot);
        boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
        this.itemHandler.setStackInSlot(pSlot, pStack);
        if (pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    //don't takers

    @Override
    public void clearContent() {
        for (int i = 0; i < this.getContainerSize(); i++){
            this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
    }
}
