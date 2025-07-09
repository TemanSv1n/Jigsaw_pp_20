package net.svisvi.jigsawpp.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

public class RectalThrustEffect extends MobEffect {
    //reging
    public int color = -12372212;

    public RectalThrustEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.rectal_thrust";
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //pDuration is just a random number))) Don't use it in the logic
        if (workConditions(pLivingEntity)) {
            pLivingEntity.setDeltaMovement(new Vec3(pLivingEntity.getDeltaMovement().x, 1 * (pAmplifier + 1 * 0.4), pLivingEntity.getDeltaMovement().z));
            if (pLivingEntity.level() instanceof ServerLevel _level) {
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP.get()), pLivingEntity.getX(), (pLivingEntity.getY() - 0), pLivingEntity.getZ(), 40, 0.4, (-0.7), 0.4, 0.1);
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP_BUBBLE.get()), pLivingEntity.getX(), (pLivingEntity.getY() - 0), pLivingEntity.getZ(), 40, 0.6, (-0.3), 0.6, 0.1);
            }


                if (!pLivingEntity.level().isClientSide()) {
                    pLivingEntity.level().playSound(null, new BlockPos((int)pLivingEntity.getX(), (int)pLivingEntity.getY(), (int)pLivingEntity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ghast.shoot")), SoundSource.PLAYERS, 1, 1);
                } else {
                    pLivingEntity.level().playLocalSound(pLivingEntity.getX(), pLivingEntity.getY(), pLivingEntity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ghast.shoot")), SoundSource.PLAYERS, 1, 1, false);
                }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }



    //scripting

    //shitting actually
    public static boolean workConditions(Entity entity) {
        //here are only effect amplifiers now, but can be more. Probably rearchitect this method if smth will bruh
        Level level = entity.level();
        boolean logic = false;
        if (entity instanceof LivingEntity le){
            if (le.hasEffect(ModEffects.PURGATIVE.get())){
                if (PurgativeEffect.shitConditions(le, le.getEffect(ModEffects.PURGATIVE.get()))){
                    return true;
                }
            }
        }
        return logic;
    }
}
