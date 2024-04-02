package net.svisvi.jigsawpp.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

import java.util.Random;

public class PurgativeEffect extends MobEffect {
    //reging
    public int color = -12372212;

    public PurgativeEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.purgative";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //pDuration is just a random number))) Don't use it in the logic
        shit(pLivingEntity, new MobEffectInstance(ModEffects.PURGATIVE.get(), 21, pAmplifier));


        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }



    //scripting
    //conditions
    public static boolean poopAdditionConditionLiquidWay(Entity entity, MobEffectInstance mobEffectInstance) {
        boolean logic = true;
        if (PoopProtectionArmorConditions.isProtectedFromLiquid(entity)) {
            logic = false;
        }
        return logic;
    }

    public static boolean poopAdditionConditionGasWay(Entity entity, MobEffectInstance mobEffectInstance) {
        boolean logic = true;
        if (PoopProtectionArmorConditions.isProtectedFromGas(entity)) {
            logic = false;
        }
        return logic;
    }

    public static boolean poopAdditionConditionInnerWay(Entity entity, MobEffectInstance mobEffectInstance) {
        boolean logic = true;
        return logic;
    }

    //effect adders
    public static void addEffectLiquidWay(Entity entity, MobEffectInstance mobEffectInstance) {
        if (poopAdditionConditionLiquidWay(entity, mobEffectInstance)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
            }

        }
    }

    public static void addEffectGasWay(Entity entity, MobEffectInstance mobEffectInstance) {
        if (poopAdditionConditionGasWay(entity, mobEffectInstance)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
            }

        }
    }

    public static void addEffectInnerWay(Entity entity, MobEffectInstance mobEffectInstance) {
        if (poopAdditionConditionInnerWay(entity, mobEffectInstance)) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                _entity.addEffect(mobEffectInstance);
            }

        }
    }
    //shitting actually
    public static boolean shitConditions(Entity entity, MobEffectInstance mobEffectInstance) {
        //here are only effect amplifiers now, but can be more. Probably rearchitect this method if smth will bruh
        Level level = entity.level();
        boolean logic = false;
        if (mobEffectInstance.getAmplifier() == 0) {
            if ((level instanceof ServerLevel _lvl ? _lvl.dimension() : Level.OVERWORLD) == Level.OVERWORLD) {
                logic = true;
            }

        } else if (mobEffectInstance.getAmplifier() == 1) {
            if ((level instanceof ServerLevel _lvl ? _lvl.dimension() : Level.OVERWORLD) == Level.OVERWORLD | (level instanceof ServerLevel _lvl ? _lvl.dimension() : Level.OVERWORLD) == Level.NETHER) {
                logic = true;
            }
        } else {logic = true;}
        return logic;
    }

    public static void shit(Entity entity, MobEffectInstance mobEffectInstance){
        //pDuration is just a random number))) Don't use it in the logic
        Level level = entity.level();
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();


        if (shitConditions(entity, mobEffectInstance)){

            if ((level.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.AIR |
                    (level.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.CAVE_AIR |
                    (level.getBlockState(BlockPos.containing(x, y, z))).getBlock() == Blocks.VOID_AIR) {

                level.setBlock(BlockPos.containing(x,y,z), ModBlocks.PONOS_FLUID_BLOCK.get().defaultBlockState(), 3);
                //pants "issue"
                if (mobEffectInstance.getAmplifier() == 2) {
                    ItemStack _ist = (entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(EquipmentSlot.LEGS) : ItemStack.EMPTY);
                    if (_ist.hurt(1, RandomSource.create(), null)) {
                        _ist.shrink(1);
                        _ist.setDamageValue(0);
                    }

                }
            }

        } else {
            PoopEffect.addEffectInnerWay(entity, new MobEffectInstance(ModEffects.POOP.get(), 21, 0));
        }
    }
}
