package net.svisvi.jigsawpp.entity.emitters;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.gas.AbstractGasClass;
import net.svisvi.jigsawpp.gas.FartGasClass;
import net.svisvi.jigsawpp.gas.PoopGasClass;

public class FartGasEmitterEntity extends GasEmitterEntity{
    public static final AbstractGasClass DEFAULT_GAS = new FartGasClass();
    public AbstractGasClass gas = DEFAULT_GAS;
    private static final float DEFAULT_RADIUS = 2.5F;
    private static final int DEFAULT_DURATION = 20 * 60 * 5;

    public FartGasEmitterEntity(EntityType<? extends FartGasEmitterEntity> type, Level level, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy) {
        super(type, level);
        this.noPhysics = true;
        this.setParticle(gassy.getParticle());
        this.setRadius(radiuss);
        this.setDuration(durra);
        this.setGas(gassy);
        this.setDebugMode(false); // Default to normal mode
    }

    public FartGasEmitterEntity(EntityType<? extends FartGasEmitterEntity> type, Level level) {
        this(type, level, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);
    }

    public FartGasEmitterEntity(Level level, double x, double y, double z) {
        this(level, x, y, z, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);

    }

    public FartGasEmitterEntity(Level level, double x, double y, double z, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy){
        this(ModEntities.FART_GAS_EMITTER.get(), level, particleOptions, radiuss, durra, gassy);
        this.setPos(x, y, z);
    }

    public FartGasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra, AbstractGasClass gassy){
        this(level, x, y, z, gassy.getParticle(), radiuss, durra, gassy);
    }

    public FartGasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra){
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
}
