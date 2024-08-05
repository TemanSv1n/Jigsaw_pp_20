package net.svisvi.jigsawpp.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.item.armor.BeaverItem;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BeaverBombProjectile extends ThrowableItemProjectile {
    public boolean binding;
    public BeaverBombProjectile(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BeaverBombProjectile(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.BEAVER_BOMB.get(), pShooter, pLevel);
    }

    public BeaverBombProjectile(Level pLevel, double pX, double pY, double pZ) {
        super(ModEntities.BEAVER_BOMB.get(), pX, pY, pZ, pLevel);
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }
    public boolean isBinding(){
        return this.binding;
    }

    public BeaverBombProjectile(Level pLevel){
        super(ModEntities.BEAVER_BOMB.get(), pLevel);
    }

    /**
     * Handles an entity event received from a {@link net.minecraft.network.protocol.game.ClientboundEntityEventPacket}.
     */
    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
//        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.5F);
//        PoopEffect.addEffectLiquidWay(pResult.getEntity(),(new MobEffectInstance(ModEffects.POOP.get(), 60, 1, false, false)));
        //realHit(this.level(), new BlockPos((int)pResult.getLocation().x, (int)pResult.getLocation().y, (int)pResult.getLocation().z));
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    protected Item getDefaultItem() {
        if (!binding) {
            return ModItems.BEAVER_BOMB.get();
        }
        return ModItems.BEAVER_BOMB.get();
        // PLACE COOL BOMB here
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        realHit(this.level(), new BlockPos((int)pResult.getLocation().x, (int)pResult.getLocation().y, (int)pResult.getLocation().z));

    }
// SCRIPTING

    public static void beaverify(Level level, Entity entity, boolean binding){
        BlockPos pos = entity.getOnPos();
        if (entity == null)
            return;

        List<EquipmentSlot> slotNames = new ArrayList<EquipmentSlot>(Arrays.asList(EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD));
        ItemStack temp = ItemStack.EMPTY;
        List<Item> beaverArmor = new ArrayList<Item>(Arrays.asList(ModItems.BEAVER_BOOTS.get(), ModItems.SLAVE_LEGGINGS.get(), ModItems.BEAVER_CHESTPLATE.get(), ModItems.BEAVER_HELMET.get()));

        //checking for single items
        for (int i = 0; i < 4; i++) {

            if (!((entity instanceof LivingEntity _entGetArmor ? temp = _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).getItem() instanceof BeaverItem)) {
                if (i != 1) {
                    if (temp.getEnchantmentLevel(Enchantments.BINDING_CURSE) == 0) {
                        ItemEntity entityToSpawn = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), temp);
                        entityToSpawn.setPickUpDelay(10);
                        level.addFreshEntity(entityToSpawn);

                        ItemStack newStack = new ItemStack(beaverArmor.get(i));
                        if (binding) {
                            newStack.enchant(Enchantments.BINDING_CURSE, 1);
                        }
                        if (entity instanceof Player _player) {
                            _player.getInventory().armor.set(i, newStack);
                            _player.getInventory().setChanged();
                        } else if (entity instanceof LivingEntity _living) {
                            _living.setItemSlot(slotNames.get(i), newStack);
                        }

                    }
                }
        }

        }
//        if (!entity.level.isClientSide())
//            entity.discard();
    }

    public static void massBeaverify(Level level, BlockPos pos, boolean binding){

        final Vec3 _center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).collect(Collectors.toList());
        for (Entity entityiterator : _entfound) {
            beaverify(level, entityiterator, binding);
        }
    }

    public void realHit(Level level, BlockPos pos){
        if (!level.isClientSide()) {
            level.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.MASTER, 1, 1);
        } else {
            level.playLocalSound(pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.explode")), SoundSource.MASTER, 1, 1, false);
        }
        if (level instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.EXPLOSION, pos.getX(), pos.getY() + 1, pos.getZ(), 12, 0.5, 0.5, 0.5, 0);
            massBeaverify(level, pos, isBinding());
        }
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

}




