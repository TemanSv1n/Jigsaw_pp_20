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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
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
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryMenu;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PurgativeGasEmitterEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpider;
import net.svisvi.jigsawpp.gas.PurgativeGasClass;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.networking.ModMessages;
import net.svisvi.jigsawpp.networking.packet.FluidSyncS2CPacket;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.DristExplosion;
import net.svisvi.jigsawpp.recipe.PurgenFactoryRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

public class PurgenFactoryBlockEntity extends BaseContainerBlockEntity implements MenuProvider, WorldlyContainer {
    private final ItemStackHandler itemHandler = new ItemStackHandler(8);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 780;

    private int PROGRESS_ACCELERATOR = 5;
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

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(!this.remove && side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            //return lazyItemHandler.cast();
            if (side == Direction.UP)
                return handlers[0].cast();
            else if (side == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
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
        for (int x = 0; x < handlers.length; x++)
            handlers[x].invalidate();
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
        BlockPos belowPos = pPos.below();
        BlockState belowState = pLevel.getBlockState(belowPos);
        if (belowState.getBlock() instanceof FactoryHeatProducer heater) {
            float koeff = 1;
            if ((koeff = heater.getHeat(belowState, pLevel, belowPos)) > 0) {
                spawnHeatedIndicatedParticles(pLevel, pPos); //particles show that it's working
                if (canCraft()) {
                    teapot_ticking(pLevel, pPos.getX(), pPos.getY(), pPos.getZ()); //teapot script
                    increaseCraftingProgress(koeff);
                    setChanged(pLevel, pPos, pState);

                    if (hasProgressFinished()) {
                        if (craftItem(pLevel, pPos, pState)){
                            spawnFinishedParticles(pLevel, pPos);
                        } else {

                        }
                        resetProgress();

                    }
                } else {
                    resetProgress();
                }
            }
        }
    }

    public static void spawnHeatedIndicatedParticles(Level pLevel, BlockPos pPos){
        if (pLevel instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.SMOKE, pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5, 5, 0.1, 0.2, 0.1, 0);
    }
    public static void spawnFinishedParticles(Level pLevel, BlockPos pPos){
        if (pLevel instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5, 5, 0.1, 0.2, 0.1, 0);
        pLevel.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.brewing_stand.brew")), SoundSource.BLOCKS, 1, 1);
    }
    public static void spawnCancelFinishedParticles(Level pLevel, BlockPos pPos){
        if (pLevel instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.ANGRY_VILLAGER, pPos.getX() + 0.5, pPos.getY() + 1.2, pPos.getZ() + 0.5, 5, 0.1, 0.2, 0.1, 0);
        pLevel.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.BLOCKS, 1, 1);
    }

    private void resetProgress() {
        progress = 0;
    }






    private boolean craftItem(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<PurgenFactoryRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);
        float malChance = recipe.get().getMalChance(null);


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
            //catalyst
            ItemStack catalyst_ = ItemStack.EMPTY;
            if (itemHandler.getStackInSlot(4) != ItemStack.EMPTY && itemHandler.getStackInSlot(4).is(ItemTags.create(new ResourceLocation("jigsaw_pp:purgen_catalysts")))) {
                ItemStack catalyst = itemHandler.getStackInSlot(4).copy();
                catalyst_ = catalyst.copy();
                //malchance affecting
                float mchmod = PurgenCatalystRecipeReader.getMalChanceK(PurgenCatalystRecipeReader.getCurrentRecipe(catalyst, pLevel).get());
                if (malChance < 0.7 && mchmod >=2){
                    mchmod*=10;
                }
                malChance *= mchmod > 0 ? mchmod : 1;
                this.itemHandler.extractItem(4, 1, false);
                //guanization
//                System.out.println("MAXSTACKSIZE");
//                System.out.println(catalyst.getMaxStackSize());
                if (catalyst.getMaxStackSize() == 1){
                    System.out.println(PurgenCatalystRecipeReader.getOutput(PurgenCatalystRecipeReader.getCurrentRecipe(catalyst, pLevel).get()).toString());
                    this.itemHandler.setStackInSlot(4, PurgenCatalystRecipeReader.getOutput(PurgenCatalystRecipeReader.getCurrentRecipe(catalyst, pLevel).get()));
                }


            }
            this.itemHandler.extractItem(5, partenSize(), false);
            this.FLUID_TANK.drain(recipe.get().getFluidStack().getAmount() * partenSize(), IFluidHandler.FluidAction.EXECUTE);

            ItemStack retStack = built_purgen.copyWithCount(pu_count);
            Random random = new Random();
            if (random.nextFloat() < malChance){
                retStack = badEventHandler(pLevel, pPos, malChance, catalyst_, built_purgen, recipe);
            }
            this.itemHandler.setStackInSlot(OUTPUT_SLOT, retStack.copyWithCount(pu_count));

            return true;
        } else {
            // KA BOOM
        }
        return false;

        //REMOVE ITEMS FROM SLOTS & FLUID


        //


    }

    public static int sumMapValues(Map<String, Integer> map) {
        if (map == null || map.isEmpty()) {
            return 0; // Return 0 if the map is null or empty
        }

        int sum = 0;
        for (Integer value : map.values()) {
            sum += value;
        }
        return sum;
    }
    public static <K, V> K getKeyByIndex(LinkedHashMap<K, V> map, int index) {
        if (map == null || map.isEmpty()) {
            return null; // or throw an exception
        }
        if (index < 0 || index >= map.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of range"); // throw an exception if the index is invalid
        }

        // Convert the key set to a list
        List<K> keys = new ArrayList<>(map.keySet());

        // Get the key at the index position
        return keys.get(index);
    }

    public static final Map<String, Integer> badEvents = new HashMap<String, Integer>(); //Name of event, weight
    static {
        badEvents.put("poop_leak", 90);
        badEvents.put("big_poop_leak", 30);
        badEvents.put("teapodification", 3);
        badEvents.put("teapotification", 6);
        badEvents.put("poop_gas", 45); //45
        badEvents.put("purgen_gas", 31); //26
        badEvents.put("explosion", 1);
    }
    public static final Map<Item, String> catalystEvents = new HashMap<Item, String>(); //Name of event, weight
    static {
        catalystEvents.put(Items.GUNPOWDER, "explosion");
    }

    public ItemStack badEventHandler(Level world, BlockPos pPos, float malChance, ItemStack catalyst, ItemStack builtPurgen, Optional<PurgenFactoryRecipe> recipe){
        if (world instanceof ServerLevel _level && !_level.isClientSide()) {
            String event = "";

            // Check for catalyst events first
            if (!catalyst.isEmpty() && catalystEvents.containsKey(catalyst.getItem())) {
                event = catalystEvents.get(catalyst.getItem());
            } else {
                // Do weighted random selection
                int totalSum = sumMapValues(badEvents);
                int random = world.random.nextInt(totalSum);
                int cumulativeWeight = 0;

                for (Map.Entry<String, Integer> entry : badEvents.entrySet()) {
                    cumulativeWeight += entry.getValue();
                    if (random < cumulativeWeight) {
                        event = entry.getKey();
                        break;  // Important: break once we find our event
                    }
                }
            }

            switch (event) {
                case "poop_leak":
                    DristExplosion.harmfulDristExplode(world, pPos, 6, Level.ExplosionInteraction.NONE, null);
                    break;
                case "big_poop_leak":
                    DristExplosion.harmfulDristExplode(world, pPos, 10, Level.ExplosionInteraction.NONE, null);
                    break;
                case "teapodification":
                    world.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                    TeapodSpider entityToSpawn = new TeapodSpider(ModEntities.TEAPOD_SPIDER.get(), _level);
                    _level.addFreshEntity(entityToSpawn);
                    _level.sendParticles(ParticleTypes.DRIPPING_WATER, pPos.getX(), pPos.getY(), pPos.getZ(), 20, 0.5, 0.5, 0.5, 0);

                        if (!_level.isClientSide()) {
                            _level.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.death")), SoundSource.BLOCKS, 1, 1);
                        } else {
                            _level.playLocalSound(pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.death")), SoundSource.BLOCKS, 1, 1, false);

                        }
                    break;
                case "teapotification":
                    world.setBlock(pPos, ModBlocks.TEAPOT.get().defaultBlockState(), 3);
                    _level.sendParticles(ParticleTypes.DRIPPING_WATER, pPos.getX(), pPos.getY(), pPos.getZ(), 20, 0.5, 0.5, 0.5, 0);

                    if (!_level.isClientSide()) {
                        _level.playSound(null, pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.extend")), SoundSource.BLOCKS, 1, 1);
                    } else {
                        _level.playLocalSound(pPos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.piston.extend")), SoundSource.BLOCKS, 1, 1, false);

                    }
                    break;
                case "explosion":
                    _level.explode(null, pPos.getX(), pPos.getY(), pPos.getZ(), 8, Level.ExplosionInteraction.TNT);
                    ItemEntity eentityToSpawn = new ItemEntity(_level, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(ModBlocks.FACTORY_HEATER.get()));
                    eentityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(eentityToSpawn);
                    break;
                case "poop_gas":
                    PoopGasEmitterEntity emitter = new PoopGasEmitterEntity(_level, pPos.getX(), pPos.getY(), pPos.getZ(), 5f, 700);
                    _level.addFreshEntity(emitter);
                    break;
                case "purgen_gas":
                    List<MobEffectInstance> eflist = PotionUtils.getMobEffects(builtPurgen);
                    eflist.add(new MobEffectInstance(ModEffects.PURGATIVE.get(), 200, 2));
                    PurgativeGasClass gassy = new PurgativeGasClass(eflist);
                    PurgativeGasEmitterEntity purgen_emitter = new PurgativeGasEmitterEntity(_level, pPos.getX(), pPos.getY(), pPos.getZ(), 5f, 700, gassy);
                    _level.addFreshEntity(purgen_emitter);
                    break;
                default:
                    System.out.println("Invalid purgen factory malfunction.");
                    break;
            }
            ItemStack ret = builtPurgen.copy();
            Random rand = new Random();
            //if (rand.nextFloat(malChance < 1f ? 1f : malChance + 0.2f) > malChance){
                ret = PurgenPiluleBuilder.build_main(recipe, new ItemStack(ModItems.SWEET_BREAD.get()), new ItemStack(ModItems.SWEET_BREAD.get()), ItemStack.EMPTY, world, pPos, world.getBlockState(pPos));
                ret.setCount(builtPurgen.getCount());
            //}
            return ret;
        }
        return ItemStack.EMPTY;
        //FOR NOW IT's ONLY AN EXPLOSION. LATER... LATER THERE WILL BE... OH FCK....
//        if (world instanceof ServerLevel _level && !_level.isClientSide()) {
//            float rand = world.random.nextFloat();
//            if (rand < 0.2) {
//                _level.explode(null, pPos.getX(), pPos.getY(), pPos.getZ(), 8, Level.ExplosionInteraction.TNT);
//                ItemEntity entityToSpawn = new ItemEntity(_level, pPos.getX(), pPos.getY(), pPos.getZ(), new ItemStack(ModBlocks.FACTORY_HEATER.get()));
//                entityToSpawn.setPickUpDelay(10);
//                _level.addFreshEntity(entityToSpawn);
//            } else if (rand > 0.2 && rand < 0.7){
//                DristExplosion.harmfulDristExplode(_level, pPos, 6, Level.ExplosionInteraction.NONE, null);
//            } else {
//                BeaverSpiderEntity entityToSpawn = new BeaverSpiderEntity(ModEntities.BEAVER_SPIDER.get(), _level);
//                _level.addFreshEntity(entityToSpawn);
//            }
//        }


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

    private void increaseCraftingProgress(float increaser) {
        progress += (int)(PROGRESS_ACCELERATOR * increaser);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if (pSide == Direction.DOWN) {
            return new int[]{6};
        } else {
            return pSide == Direction.UP ? new int[]{4,5} : new int[]{0,1,2,3,6};
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(pIndex, pItemStack) && IntStream.of(this.getSlotsForFace(pDirection)).anyMatch(x -> x == pIndex);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if (pDirection != Direction.UP && pIndex == 6){
            return true;
        }return false;
    }

    @Override
    public int getContainerSize() {
        return 8;
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
        for (int i = 0; i < this.getContainerSize(); i++){
            this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public static void teapot_ticking(LevelAccessor world, double x, double y, double z) {
        double xRadius = 0;
        double loop = 0;
        double zRadius = 0;
        double particleAmount = 0;
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:whistle")), SoundSource.BLOCKS, 0.1f, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:whistle")), SoundSource.BLOCKS, 0.1f, 1, false);
                }
            }
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ModParticleTypes.POOP_BUBBLE.get(), x + 0.5,y + 0.5,z + 0.5, 5, 1, 1, 1, 0.5);
            loop = 0;
            particleAmount = 64;
            xRadius = 0.5;
            zRadius = 0.5;
            while (loop < particleAmount) {
                world.addParticle(ParticleTypes.SMOKE, (x + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRadius), (y + 0.02 + 0.5), (z + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRadius), 0, 0.01, 0);
                loop = loop + 1;
            }
        }
}
