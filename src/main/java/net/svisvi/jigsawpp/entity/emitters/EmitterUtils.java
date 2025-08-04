package net.svisvi.jigsawpp.entity.emitters;

import net.minecraft.world.level.Level;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EmitterUtils {
    public static GasEmitterEntity createEmitter(Class<? extends GasEmitterEntity> emitterClass, Level level, double x, double y, double z, float radiuss, int durra) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        if (emitterClass == null) {
            throw new IllegalStateException("Emitter class not set!");
        }
        // Use getConstructor() if you need to pass arguments
        Constructor<? extends GasEmitterEntity> constructor = emitterClass.getConstructor(Level.class, double.class, double.class, double.class, float.class, int.class);
        return constructor.newInstance(level, x, y, z, radiuss, durra);
    }
}
