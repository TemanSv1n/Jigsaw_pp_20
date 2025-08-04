package net.svisvi.jigsawpp.entity.emitters;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.gas.AbstractGasClass;
import net.svisvi.jigsawpp.gas.EffectGasClass;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class GasEmitterEntity extends AbstractEmitterEntity{
    public static final AbstractGasClass DEFAULT_GAS = new EffectGasClass(Arrays.asList(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60, 0)));
    public AbstractGasClass gas = DEFAULT_GAS;
    private static final float DEFAULT_RADIUS = 2.5F;
    private static final int DEFAULT_DURATION = 20 * 60 * 5;

    public GasEmitterEntity(EntityType<? extends GasEmitterEntity> type, Level level, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy) {
        super(type, level);
        this.noPhysics = true;
        this.setParticle(gassy.getParticle());
        this.setRadius(radiuss);
        this.setDuration(durra);
        this.setGas(gassy);
        this.setDebugMode(false); // Default to normal mode
    }

    public GasEmitterEntity(EntityType<? extends GasEmitterEntity> type, Level level) {
        this(type, level, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);
    }

    public GasEmitterEntity(Level level, double x, double y, double z) {
        this(level, x, y, z, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);

    }

    public GasEmitterEntity(Level level, double x, double y, double z, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy){
        this(ModEntities.GAS_EMITTER.get(), level, particleOptions, radiuss, durra, gassy);
        this.setPos(x, y, z);
    }

    public GasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra, AbstractGasClass gassy){
        this(level, x, y, z, gassy.getParticle(), radiuss, durra, gassy);
    }

    public GasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra){
        this(level, x, y, z, DEFAULT_GAS.getParticle(), radiuss, durra, DEFAULT_GAS);
    }


    public void setGas(AbstractGasClass gas) {
        this.gas = gas;
    }

    public AbstractGasClass getGas() {
        return gas;
    }

    @Override
    public ParticleOptions getParticle() {
        return getGas().getParticle();
    }



    //    @Override
//    public void applyEffects() {
//        super.applyEffects();
//        AbstractGasClass gass = this.getGas();
//
//    }

    @Override
    public void effectForEach(LivingEntity entity) {
        super.effectForEach(entity);
        AbstractGasClass gass = this.getGas();

        if (gass.gasApplyCondition(entity)){
            gass.gasApplyEffect(entity);
        }

//        if (entity instanceof Player player) {
//            if (player.level() instanceof ServerLevel _level) {
//                if (!_level.isClientSide()) {
//                    _level.playSound(null, player.getOnPos(), gass.sound, SoundSource.BLOCKS, 0.1f, 1);
//                } else {
//                    _level.playLocalSound(player.getX(), player.getY(), player.getZ(), gass.sound, SoundSource.BLOCKS, 0.1f, 1, false);
//                }
//            }
//        }
    }

    @Override
    public void setOwner(@Nullable LivingEntity owner) {
        this.getGas().setOwner(owner);
        super.setOwner(owner);
    }
}
