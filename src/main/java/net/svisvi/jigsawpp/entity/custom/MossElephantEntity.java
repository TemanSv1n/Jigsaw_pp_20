package net.svisvi.jigsawpp.entity.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class MossElephantEntity extends Animal {
    public MossElephantEntity(EntityType<? extends Animal> pEntityType, Level pLevel){
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals(){

        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //For future //this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, ZombieBeaverEntity.class, (float) 12, 1, 1.2));
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


}
