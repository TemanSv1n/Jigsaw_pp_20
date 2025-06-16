package net.svisvi.jigsawpp.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.block.entity.init.ModBlockEntities;
import net.svisvi.jigsawpp.block.factory_heater.FactoryHeatProducer;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.block.purgen_factory.PurgenCatalystRecipeReader;
import net.svisvi.jigsawpp.block.purgen_factory.PurgenPiluleBuilder;
import net.svisvi.jigsawpp.client.screen.space_lift.SpaceLiftMenu;
import net.svisvi.jigsawpp.client.screen.space_lift.SpaceLiftScreen;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairEntity;
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

    public int dropX = this.getBlockPos().getX();
    public int dropZ = this.getBlockPos().getZ();

    private int PROGRESS_ACCELERATOR = 5;

    List<BeaconBlockEntity.BeaconBeamSection> beamSections = Lists.newArrayList();
    private List<BeaconBlockEntity.BeaconBeamSection> checkingBeamSections = Lists.newArrayList();

    int levels;
    private int lastCheckY;

//    public static void fillFluid(PurgenFactoryBlockEntity pEntity, int amount, FluidStack fluidStack){
//        pEntity.FLUID_TANK.fill()
//    }
    public List<BeaconBlockEntity.BeaconBeamSection> getBeamSections() {
        return (List<BeaconBlockEntity.BeaconBeamSection>)(this.levels == 0 ? ImmutableList.of() : this.beamSections);
    }

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

//        int i = pPos.getX();
//        int j = pPos.getY();
//        int k = pPos.getZ();
//        BlockPos blockpos;
//        if (this.lastCheckY < j) {
//            blockpos = pPos;
//            this.checkingBeamSections = Lists.newArrayList();
//            this.lastCheckY = pPos.getY() - 1;
//        } else {
//            blockpos = new BlockPos(i, this.lastCheckY + 1, k);
//        }
//
//        BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = this.checkingBeamSections.isEmpty() ? null : (BeaconBlockEntity.BeaconBeamSection)this.checkingBeamSections.get(this.checkingBeamSections.size() - 1);
//        int l = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, k);
//
//        for(int i1 = 0; i1 < 10 && blockpos.getY() <= l; ++i1) {
//            BlockState blockstate = pLevel.getBlockState(blockpos);
//            Block block = blockstate.getBlock();
//            float[] afloat = blockstate.getBeaconColorMultiplier(pLevel, blockpos, pPos);
//            if (afloat != null) {
//                if (this.checkingBeamSections.size() <= 1) {
//                    beaconblockentity$beaconbeamsection = new BeaconBlockEntity.BeaconBeamSection(afloat);
//                    this.checkingBeamSections.add(beaconblockentity$beaconbeamsection);
//                } else if (beaconblockentity$beaconbeamsection != null) {
//                    if (Arrays.equals(afloat, beaconblockentity$beaconbeamsection.color)) {
//                        beaconblockentity$beaconbeamsection.increaseHeight();
//                    } else {
//                        beaconblockentity$beaconbeamsection = new BeaconBlockEntity.BeaconBeamSection(new float[]{(beaconblockentity$beaconbeamsection.color[0] + afloat[0]) / 2.0F, (beaconblockentity$beaconbeamsection.color[1] + afloat[1]) / 2.0F, (beaconblockentity$beaconbeamsection.color[2] + afloat[2]) / 2.0F});
//                        this.checkingBeamSections.add(beaconblockentity$beaconbeamsection);
//                    }
//                }
//            } else {
//                if (beaconblockentity$beaconbeamsection == null || blockstate.getLightBlock(pLevel, blockpos) >= 15 && !blockstate.is(Blocks.BEDROCK)) {
//                    this.checkingBeamSections.clear();
//                    this.lastCheckY = l;
//                    break;
//                }
//
//                beaconblockentity$beaconbeamsection.increaseHeight();
//            }
//
//            blockpos = blockpos.above();
//            ++this.lastCheckY;
//        }
//    }
    public void regenDropPos(Level pLevel, BlockPos pPos, BlockState pState){
        boolean logic = false;
        final int radius = 100;
        int kX = 0;
        int kZ = 0;
        if (dropX == pPos.getX() && dropZ == pPos.getZ()){
            logic = true;
        }
        if (!logic){
            if (pLevel.random.nextFloat() > 0.4){
                logic = true;
            }
        }
        if (logic){
            kX = pLevel.random.nextInt(radius*-1, radius);
            kZ = pLevel.random.nextInt(radius*-1, radius);

            dropX = pPos.getX()+ kX;
            dropZ = pPos.getZ() +kZ;
        }
    }

    public void spawnDropPod(Level pLevel, BlockPos pPos, BlockState pState, ItemStack result){
        regenDropPos(pLevel, pPos, pState);
        final int dropAddition = 30;
        int dropY = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, dropX, dropZ);
        if (!pLevel.isOutsideBuildHeight(dropY + dropAddition)){
            dropY += dropAddition;
        }
        BlockPos dropPos = new BlockPos(dropX, dropY, dropZ);
        if (pLevel instanceof ServerLevel _level && !_level.isClientSide()) {
                ItemStack coal = new ItemStack(ModItems.COAL_FOSSIL.get(), 1);
                coal.setHoverName(Component.literal(String.format("%d, %d", dropX, dropZ)));
                ItemEntity eentityToSpawn = new ItemEntity(_level, pPos.getX() + 2, pPos.getY(), pPos.getZ(), coal);
                eentityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(eentityToSpawn);


            LightningBolt bolt = (LightningBolt) EntityType.LIGHTNING_BOLT.create(_level);
            bolt.moveTo(dropPos, 0.0F, 0.0F);
            bolt.setVisualOnly(true);
            level.addFreshEntity(bolt);

            MinecartChest pod = (MinecartChest) EntityType.CHEST_MINECART.create(_level);
            pod.moveTo(dropPos, 0.0F, 0.0F);
            pod.setItem(0, result);
            level.addFreshEntity(pod);
        }

    }

    public void craftItem(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<SpaceLiftRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()){
            return;
        }
        ItemStack result = recipe.get().getResultItem(null);
        BlockPos upPos = pPos.above(2);

        if (canCraft(pLevel, pPos, pState, recipe.get())) {
            for (int i = 0; i < 8; i++) {
                this.itemHandler.extractItem(i, recipe.get().getIngredients().get(i).getItems()[0].getCount(), false);
            }

            if (pLevel instanceof ServerLevel _level && !_level.isClientSide()) {
//                ItemEntity eentityToSpawn = new ItemEntity(_level, pPos.getX() + 2, pPos.getY(), pPos.getZ(), result);
//                eentityToSpawn.setPickUpDelay(10);
//                _level.addFreshEntity(eentityToSpawn);

                //Spawning "rocket"
                Skeleton skeletonToSpawn = (Skeleton) EntityType.SKELETON.create(_level);
                skeletonToSpawn.moveTo(upPos, 0.0F, 0.0F);
                skeletonToSpawn.setInvulnerable(true);
                skeletonToSpawn.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.BEAWEED_DUST.get(), 64));
                skeletonToSpawn.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(ModItems.BEAWEED_DUST.get(), 64));


                JetstreamChairEntity chair = (JetstreamChairEntity) ModEntities.JETSTREAM_CHAIR.get().create(_level);
                chair.moveTo(upPos, 0.0F, 0.0F);
                chair.setInvulnerable(true);
                skeletonToSpawn.startRiding(chair);
                level.addFreshEntity(chair);
                level.addFreshEntity(skeletonToSpawn);

                JigsawPpMod.queueServerWork(300, ()->{
                    BlockPos entPos = skeletonToSpawn.getOnPos();
                    if (!level.isClientSide()) {
                        level.playSound(null, entPos, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.NEUTRAL, 1, 1);
                        level.playSound(null, entPos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1);
                    } else {
                        level.playLocalSound(entPos, SoundEvents.ENDER_DRAGON_FLAP, SoundSource.NEUTRAL, 1, 1, false);
                        level.playLocalSound(entPos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1, false);
                    }
                    _level.sendParticles(ParticleTypes.EXPLOSION, entPos.getX(), entPos.getY()+1, entPos.getZ(), 12, 0.5, 0.5, 0.5, 0);
                    skeletonToSpawn.discard();
                    chair.discard();

                    spawnDropPod(pLevel, pPos, pState, result);
                });


            }
            if (!level.isClientSide()) {
                level.playSound(null, upPos, SoundEvents.BLAZE_HURT, SoundSource.NEUTRAL, 1, 1);
                level.playSound(null, upPos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1);
            } else {
                level.playLocalSound(upPos, SoundEvents.BLAZE_HURT, SoundSource.NEUTRAL, 1, 1, false);
                level.playLocalSound(upPos, SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, SoundSource.NEUTRAL, 1, 1, false);
            }
            if (level instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, upPos.getX(), upPos.getY()+1, upPos.getZ(), 12, 0.5, 0.5, 0.5, 0);



        }

//        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
//                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }


    public boolean canCraft(Level pLevel, BlockPos pPos, BlockState pState, SpaceLiftRecipe recipe){
        if (!hasRecipe()){
            return false;
        }
        if (!pLevel.canSeeSky(pPos)){
            return false;
        }
        //SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            if(this.itemHandler.getStackInSlot(i) != ItemStack.EMPTY && recipe.getIngredients().get(i).getItems()[0] != ItemStack.EMPTY){
//                System.out.println(this.itemHandler.getStackInSlot(i).getCount());
//                System.out.println(recipe.getIngredients().get(i).getItems()[0].getCount());
                if (this.itemHandler.getStackInSlot(i).getCount() != recipe.getIngredients().get(i).getItems()[0].getCount()){
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
