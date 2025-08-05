package net.svisvi.jigsawpp.entity.emitters;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.gas.AbstractGasClass;
import net.svisvi.jigsawpp.gas.PoopGasClass;
import net.svisvi.jigsawpp.gas.SmokeGasClass;
import net.svisvi.jigsawpp.item.init.ModItems;

public class SmokeGasEmitterEntity extends GasEmitterEntity{
    public static final AbstractGasClass DEFAULT_GAS = new SmokeGasClass();
    public AbstractGasClass gas = DEFAULT_GAS;
    private static final float DEFAULT_RADIUS = 2.5F;
    private static final int DEFAULT_DURATION = 20 * 60 * 5;

    public SmokeGasEmitterEntity(EntityType<? extends SmokeGasEmitterEntity> type, Level level, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy) {
        super(type, level);
        this.noPhysics = true;
        this.setParticle(gassy.getParticle());
        this.setRadius(radiuss);
        this.setDuration(durra);
        this.setGas(gassy);
        //this.setBottle(ModItems.POOP_BOTTLE.get());
        this.setDebugMode(false); // Default to normal mode
    }

    public SmokeGasEmitterEntity(EntityType<? extends SmokeGasEmitterEntity> type, Level level) {
        this(type, level, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);
    }

    public SmokeGasEmitterEntity(Level level, double x, double y, double z) {
        this(level, x, y, z, DEFAULT_GAS.particle, DEFAULT_RADIUS, DEFAULT_DURATION, DEFAULT_GAS);

    }

    public SmokeGasEmitterEntity(Level level, double x, double y, double z, ParticleOptions particleOptions, float radiuss, int durra, AbstractGasClass gassy){
        this(ModEntities.SMOKE_GAS_EMITTER.get(), level, particleOptions, radiuss, durra, gassy);
        this.setPos(x, y, z);
    }

    public SmokeGasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra, AbstractGasClass gassy){
        this(level, x, y, z, gassy.getParticle(), radiuss, durra, gassy);
    }

    public SmokeGasEmitterEntity(Level level, double x, double y, double z, float radiuss, int durra){
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
