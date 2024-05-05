package net.svisvi.jigsawpp.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

public class RadiationEffect extends MobEffect {
    //reging
    public int color = -10040320;

    public RadiationEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.radiation";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //pDuration is just a random number))) Don't use it in the logic
        radPiska(pLivingEntity, new MobEffectInstance(ModEffects.RADIATION.get(), 21, pAmplifier), pLivingEntity.level());


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }



    //scripting
    //conditions
    public static boolean radiationAdditionConditionLiquidWay(Entity entity, MobEffectInstance mobEffectInstance) {
        boolean logic = true;
        if (PoopProtectionArmorConditions.isProtectedFromLiquid(entity)) {
            logic = false;
        }
        return logic;
    }

    public static boolean radiationAdditionConditionGasWay(Entity entity, MobEffectInstance mobEffectInstance) {
        boolean logic = true;
        if (PoopProtectionArmorConditions.isProtectedFromGas(entity)) {
            logic = false;
        }
        return logic;
    }

    public static boolean radiationAdditionConditionInnerWay(Entity entity, MobEffectInstance mobEffectInstance) {
        boolean logic = true;
        return logic;
    }

    //effect adders
    public static boolean addEffectLiquidWay(Entity entity, MobEffectInstance mobEffectInstance) {
        if (radiationAdditionConditionLiquidWay(entity, mobEffectInstance)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
                return true;
            }

        }
        return false;
    }

    public static boolean addEffectGasWay(Entity entity, MobEffectInstance mobEffectInstance) {
        if (radiationAdditionConditionGasWay(entity, mobEffectInstance)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
                return true;
            }

        }
        return false;
    }

    public static boolean addEffectInnerWay(Entity entity, MobEffectInstance mobEffectInstance) {
        if (radiationAdditionConditionInnerWay(entity, mobEffectInstance)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
                return true;
            }

        }
        return false;
    }
    //shitting actually
    public static boolean radPiskaConditions(Entity entity, MobEffectInstance mobEffectInstance) {
        //here are only effect amplifiers now, but can be more. Probably rearchitect this method if smth will bruh
//        Level level = entity.level();
          boolean logic = false;
//        if (mobEffectInstance.getAmplifier() == 0) {
//            if ((level instanceof ServerLevel _lvl ? _lvl.dimension() : Level.OVERWORLD) == Level.OVERWORLD) {
//                logic = true;
//            }
//
//        } else if (mobEffectInstance.getAmplifier() == 1) {
//            if ((level instanceof ServerLevel _lvl ? _lvl.dimension() : Level.OVERWORLD) == Level.OVERWORLD | (level instanceof ServerLevel _lvl ? _lvl.dimension() : Level.OVERWORLD) == Level.NETHER) {
//                logic = true;
//            }
//        } else if (mobEffectInstance.getAmplifier() == 2) {logic = true;}
        return logic;
    }

    public static void radPiska(Entity entity, MobEffectInstance mobEffectInstance, Level world){
        //pDuration is just a random number))) Don't use it in the logic
        float damageScale = 1F - 0.25F;
        if (entity instanceof Player player) {
           player.causeFoodExhaustion(0.4F);
        }
        if (entity.level().random.nextFloat() < damageScale + 0.1F) {
            //entity.hurt(ACDamageTypes.causeRadiationDamage(entity.level().registryAccess()), damageScale);
            entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FALL)), 2);
        }
    }

}

