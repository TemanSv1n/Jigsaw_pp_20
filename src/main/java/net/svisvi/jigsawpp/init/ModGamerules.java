package net.svisvi.jigsawpp.init;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

public class ModGamerules {

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public class JigsawPpModGameRules {
        public static final GameRules.Key<GameRules.BooleanValue> REMOVE_POONAMI = GameRules.register("removePoonami", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
    }

}
