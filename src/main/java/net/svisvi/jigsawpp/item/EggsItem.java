package net.svisvi.jigsawpp.item;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.particles.ModParticles;

public class EggsItem extends Item {

    private static final float BASE_KINETIC_MULTIPLIER = 2.0F;
    private static final float MAX_KINETIC_MULTIPLIER = 15.0F;
    private static final float VELOCITY_THRESHOLD = 0.3F; // Minimum velocity to apply bonus
    private static final float FALL_DAMAGE_MULTIPLIER = 4.7F; // Extra multiplier for fall damage conversion

    public EggsItem() {
        super(new Item.Properties().durability(128));
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState blockstate) {
        return List.of().contains(blockstate.getBlock()) ? 0f : 1;
    }

    @Override
    public boolean mineBlock(ItemStack itemstack, Level world, BlockState blockstate, BlockPos pos, LivingEntity entity) {
        itemstack.hurtAndBreak(1, entity, i -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        double speed = 0;
        double Yaw = 0;
        double xRadius = 0;
        double loop = 0;
        double zRadius = 0;
        double particleAmount = 0;
        double loop_big = 0;
        double speed2 = 0;

        itemstack.hurtAndBreak(2, entity, i -> i.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        //if (entity.level() instanceof ServerLevel _level) {
            DamageSource egged = new DamageSource(entity.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), sourceentity);
        //}

        Vec3 velocity = sourceentity.getDeltaMovement();
        double horizontalVelocity = Math.sqrt(velocity.x * velocity.x + velocity.z * velocity.z);
        double verticalVelocity = Math.abs(velocity.y);
        double totalVelocity = velocity.length();
        float coeff = 0F;

        float fallBonus = (float) (Math.max(verticalVelocity - 0.5, 0) * FALL_DAMAGE_MULTIPLIER);
        coeff = 1.0F + Math.min(fallBonus, MAX_KINETIC_MULTIPLIER - 1.0F);
        if (totalVelocity < VELOCITY_THRESHOLD) {
            coeff =  0F; // No bonus
        }

        if (coeff != 0) {
            loop = 0;
            particleAmount = 64;
            xRadius = 2;
            zRadius = 2;
            Level world = entity.level();
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();
            while (loop < particleAmount) {
                world.addParticle((SimpleParticleType) (ModParticleTypes.POOP.get()), (x + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRadius), (entity.getY() + 0.02), (z + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRadius),
                        0, 0.15, 0);
                loop = loop + 1;
            }
            if (!world.isClientSide()) {
                world.playSound(null, new BlockPos((int) x, (int) y, (int) z), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 2, -1);
            } else {
                world.playLocalSound(x, y, z, SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 2, -1, false);
            }
        }

        float damagee = 6;
        damagee = (damagee * coeff);
        entity.hurt(egged, damagee);
        applyKineticKnockback(entity, sourceentity);
        return true;
    }

    private static void applyKineticKnockback(LivingEntity target, LivingEntity attacker) {
        Vec3 attackerVelocity = attacker.getDeltaMovement();

        // Scale knockback based on velocity
        double knockbackStrength = Math.min(attackerVelocity.length() * 0.5, 2.0);

        if (knockbackStrength > 0.1) {
            // Apply knockback in the direction of attacker's velocity
            target.knockback(
                    knockbackStrength,
                    -attackerVelocity.x,
                    -attackerVelocity.z
            );
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, ar.getObject());
        return ar;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 2f, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -2.2, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
        return InteractionResult.SUCCESS;
    }


    public static void execute(Level world, double x, double y, double z, Player entity, ItemStack itemstack) {
        if (entity == null)
            return;
        DamageSource egged = new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.CRAMMING), entity);
        boolean holder123 = false;
        double speed = 0;
        double Yaw = 0;
        double xRadius = 0;
        double loop = 0;
        double zRadius = 0;
        double particleAmount = 0;
        double loop_big = 0;
        double speed2 = 0;
        //egged = egged.bypassArmor();
        speed = 2;
        speed2 = 1.3;
        Yaw = entity.getYRot();
        if (!entity.isShiftKeyDown()) {
            new Object() {
                private int ticks = 0;
                private float waitTicks;
                private Level world;

                public void start(Level world, int waitTicks) {
                    this.waitTicks = waitTicks;
                    MinecraftForge.EVENT_BUS.register(this);
                    this.world = world;
                }

                @SubscribeEvent
                public void tick(TickEvent.ServerTickEvent event) {
                    if (event.phase == TickEvent.Phase.END) {
                        this.ticks += 1;
                        if (this.ticks >= this.waitTicks)
                            run();
                    }
                }

                private void run() {
                    entity.addEffect(new MobEffectInstance(ModEffects.EGGED.get(), 600, 0, (true), (true)));
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }.start(world, 5);
            {
                ItemStack _ist = itemstack;
                if (_ist.hurt(2, entity.level().random, null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
            }
            entity.getCooldowns().addCooldown(itemstack.getItem(), 80);
            entity.setDeltaMovement(new Vec3((speed * Math.cos((Yaw + 90) * (Math.PI / 180))), (speed * 0.7), (speed * Math.sin((Yaw + 90) * (Math.PI / 180)))));
            loop = 0;
            particleAmount = 64;
            xRadius = 1.5;
            zRadius = 1.5;
            while (loop < particleAmount) {
                world.addParticle((SimpleParticleType) (ModParticleTypes.POOP.get()), (entity.getX() + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRadius), (entity.getY() + 0.02),
                        (entity.getZ() + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRadius), 0, 0.05, 0);
                loop = loop + 1;
            }
                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")), SoundSource.PLAYERS, 2, -1);
                } else {
                    world.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")), SoundSource.PLAYERS, 2, -1, false);
                }
            {
                final Vec3 _center = new Vec3((entity.getX()), (entity.getY()), (entity.getZ()));
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                        .collect(Collectors.toList());
                for (Entity entityiterator : _entfound) {
                    if (!(entityiterator == entity)) {
                        entityiterator.hurt(egged, 5);
                    }
                }
            }
        } else if (entity.isShiftKeyDown()) {
            {
                ItemStack _ist = itemstack;
                if (_ist.hurt(1, entity.level().random, null)) {
                    _ist.shrink(1);
                    _ist.setDamageValue(0);
                }
            }
            entity.getCooldowns().addCooldown(itemstack.getItem(), 40);
            loop = 0;
            particleAmount = 64;
            xRadius = 2;
            zRadius = 2;
            while (loop < particleAmount) {
                world.addParticle((SimpleParticleType) (ModParticleTypes.POOP.get()), (x + Math.cos(((Math.PI * 2) / particleAmount) * loop) * xRadius), (entity.getY() + 0.02), (z + Math.sin(((Math.PI * 2) / particleAmount) * loop) * zRadius),
                        0, 0.05, 0);
                loop = loop + 1;
            }
                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.PLAYERS, 2, -1);
                } else {
                    world.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.PLAYERS, 2, -1, false);
                }
            {
                final Vec3 _center = new Vec3(x, y, z);
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                        .collect(Collectors.toList());
                for (Entity entityiterator : _entfound) {
                    if (entityiterator instanceof LivingEntity _entity)
                        _entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3, (false), (false)));
                    if (!(entityiterator == entity)) {
                        entityiterator.hurt(egged, 7);
                        entityiterator.setDeltaMovement(new Vec3((speed2 * Math.cos((Yaw + 90) * (Math.PI / 180))), (speed2 * 0.5), (speed2 * Math.sin((Yaw + 90) * (Math.PI / 180)))));
                    }
                }
            }
        }
    }
}
