package net.svisvi.jigsawpp.entity.emitters;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.gas.AbstractGasClass;
import net.svisvi.jigsawpp.gas.BrownHoleGasClass;
import net.svisvi.jigsawpp.gas.SmokeGasClass;
import net.svisvi.jigsawpp.particles.ModParticleTypes;
import net.svisvi.jigsawpp.procedures.ut.NuclearShroom;

public class BrownHoleGasEmitterEntity extends GasEmitterEntity{
    public static final AbstractGasClass DEFAULT_GAS = new BrownHoleGasClass();
    public AbstractGasClass gas = DEFAULT_GAS;
    private static final float DEFAULT_RADIUS = 2.5F;
    private static final int DEFAULT_DURATION = 20 * 60 * 5;

    public BrownHoleGasEmitterEntity(EntityType<? extends BrownHoleGasEmitterEntity> type, Level level, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy) {
        super(type, level);
        this.noPhysics = true;
        this.setParticle(gassy.getParticle());
        this.setRadius(radiuss);
        this.setDuration(durra);
        this.setGas(gassy);
        //this.setBottle(ModItems.POOP_BOTTLE.get());
        this.setDebugMode(false); // Default to normal mode
    }

    @Override
    public float getDensity() {
        return 50f;
    }

    public BrownHoleGasEmitterEntity(EntityType<? extends BrownHoleGasEmitterEntity> type, Level level) {
        this(type, level, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);
    }

    public BrownHoleGasEmitterEntity(Level level, double x, double y, double z) {
        this(level, x, y, z, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);

    }

    public BrownHoleGasEmitterEntity(Level level, double x, double y, double z, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy){
        this(ModEntities.BROWNHOLE_GAS_EMITTER.get(), level, particleOptions, radiuss, durra, gassy);
        this.setPos(x, y, z);
    }

    public BrownHoleGasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra, AbstractGasClass gassy){
        this(level, x, y, z, gassy.getParticle(), radiuss, durra, gassy);
    }

    public BrownHoleGasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra){
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
        return this.getGas().getParticle();
    }

    //    @Override
//    public void applyEffects() {
//        super.applyEffects();
//        AbstractGasClass gass = this.getGas();
//
//    }


    @Override
    public void spawnParticles(int count, float speed) {
        super.spawnParticles(count, speed);
        if (this.level() instanceof ServerLevel serverLevel) {
            NuclearShroom.sendFarParticles(serverLevel,
                    ModParticleTypes.POOP_CLOUD.get(),
                    this.getX(), this.getY(), this.getZ(),
                    1,
                    1,
                    1,
                    200, 0);
            NuclearShroom.sendFarParticles(serverLevel,
                    ModParticleTypes.POOP_BUBBLE.get(),
                    this.getX(), this.getY(), this.getZ(),
                    1.5,
                    1.5,
                    1.5,
                    200, 0);
        }
    }

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
