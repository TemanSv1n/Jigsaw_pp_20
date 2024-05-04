package net.svisvi.jigsawpp.entity.moss_elephant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.blabbit.BlabbitEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.recipe.ElephantingRecipe;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class MossElephantEntity extends Animal {

    public MossElephantEntity(EntityType<? extends Animal> pEntityType, Level pLevel){
        super(pEntityType, pLevel);

    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    private final ItemStackHandler itemHandler = new ItemStackHandler(2);

    private static final Set<ResourceLocation> SPAWN_BIOMES = Set.of(new ResourceLocation("beach"), new ResourceLocation("swamp"));

    private int craftingCooldown = 0;
    public static void init() {
        SpawnPlacements.register(ModEntities.MOSS_ELEPHANT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }




    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        }else{
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        super.updateWalkAnimation(pPartialTick);
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        }else {
            f = 0f;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals(){

        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Cow.class, (float) 12, 1, 1.2)); //placeholder for beaver zomdbie
    }
    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 20D)
                .add(Attributes.MAX_HEALTH, 70)
                .add(Attributes.MOVEMENT_SPEED, 0.20D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK,0.5f)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1000)
                ;
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherPartner){
        return null;
    }


    //decorative stuff
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.moss.place"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.moss.break"));
    }

    @Override
    public boolean hurt(DamageSource damagesource, float amount) {
        if (damagesource.is(DamageTypes.IN_FIRE))
            return false;
        if (damagesource.getDirectEntity() instanceof AbstractArrow)
            return false;
        if (damagesource.getDirectEntity() instanceof ThrownPotion || damagesource.getDirectEntity() instanceof AreaEffectCloud)
            return false;
        if (damagesource.is(DamageTypes.FALL))
            return false;
        if (damagesource.is(DamageTypes.CACTUS))
            return false;
        if (damagesource.is(DamageTypes.DROWN))
            return false;
        if (damagesource.is(DamageTypes.LIGHTNING_BOLT))
            return false;
        if (damagesource.is(DamageTypes.EXPLOSION))
            return false;
        if (damagesource.is(DamageTypes.TRIDENT))
            return false;
        if (damagesource.is(DamageTypes.FALLING_ANVIL))
            return false;
        if (damagesource.is(DamageTypes.DRAGON_BREATH))
            return false;
        if (damagesource.is(DamageTypes.WITHER))
            return false;
        if (damagesource.is(DamageTypes.WITHER_SKULL))
            return false;
        return super.hurt(damagesource, amount);
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
    @Override
    protected void doPush(Entity entityIn) {
    }

    @Override
    protected void pushEntities() {
    }
    // SCRIPTING AND EDGING
    @Override
    public void baseTick() {
        super.baseTick();
        //fear
        this.fear(this.level(), this.getX(), this.getY(), this.getZ(), this);
        if (craftingCooldown < 80) {
            craftingCooldown += 1;
//            if (this.level() instanceof ServerLevel _level) {
//
//            }
        }

    }
    public static void fear(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        if (entity.getDeltaMovement().x() > 0.0001 | entity.getDeltaMovement().z() > 0.0001) {
            if (Math.random() <= 0.05) {
                if (world instanceof Level _level && !_level.isClientSide()) {
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(ModItems.SWEET_BREAD.get()));
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                }
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.NEUTRAL, 1, 1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.NEUTRAL, 1, 1, false);
                    }
                }
            }
        }
    }
    //right click
    @Override
    public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
        ItemStack itemstack = sourceentity.getItemInHand(hand);
        InteractionResult retval = InteractionResult.sidedSuccess(this.level().isClientSide());
        super.mobInteract(sourceentity, hand);
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        Entity entity = this;
        Level world = this.level();

        if (craftingCooldown == 80) {
            craftingCooldown = 0;
            if (hasRecipe(itemstack)) {
                if (world instanceof ServerLevel _level && !_level.isClientSide()) {
                    ItemStack res = craftItem(itemstack);
                    ItemEntity entityToSpawn = new ItemEntity(_level, x, y + 2.8f, z, res);
                    entityToSpawn.setPickUpDelay(10);
                    _level.addFreshEntity(entityToSpawn);
                    _level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 50, 2, 2, 2, 0);
                    itemstack.shrink(1);
                }
                if (world instanceof ServerLevel _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:moss_elephant_proot")), SoundSource.NEUTRAL, 1, -1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:moss_elephant_proot")), SoundSource.NEUTRAL, 1, -1, false);
                    }
                }
            } else {
                if (world instanceof ServerLevel _level && !_level.isClientSide()) {
                    _level.sendParticles(ParticleTypes.ANGRY_VILLAGER, x, y, z, 50, 2, 2, 2, 0);
                }
                if (world instanceof ServerLevel _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:moss_elephant_angry_proot")), SoundSource.NEUTRAL, 1, -1);
                    } else {
                        _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:moss_elephant_angry_proot")), SoundSource.NEUTRAL, 1, -1, false);
                    }
                }
            }
        }




        return retval;
    }
    //recepting. elephant side
    private Optional<ElephantingRecipe> getCurrentRecipe(ItemStack itemStack) {
        SimpleContainer inventory = new SimpleContainer(2);
//        for(int i = 0; i < itemHandler.getSlots(); i++) {
//            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
//        }
        inventory.setItem(0, itemStack);

        return this.level().getRecipeManager().getRecipeFor(ElephantingRecipe.Type.INSTANCE, inventory, this.level());
    }

    private ItemStack craftItem(ItemStack itemStack) {
        Optional<ElephantingRecipe> recipe = getCurrentRecipe(itemStack);
        ItemStack result = recipe.get().getResultItem(null);
        return result;

        //this.itemHandler.extractItem(INPUT_SLOT, 1, false);

//        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
//                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }
    private boolean hasRecipe(ItemStack itemStack) {
        Optional<ElephantingRecipe> recipe = getCurrentRecipe(itemStack);

        if(recipe.isEmpty()) {
            return false;
        }
        return true;
    }




}
