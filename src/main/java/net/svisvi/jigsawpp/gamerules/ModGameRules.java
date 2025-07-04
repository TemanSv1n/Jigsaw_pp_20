package net.svisvi.jigsawpp.gamerules;

import net.minecraft.world.level.GameRules;
import net.svisvi.jigsawpp.JigsawPpMod;

public class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> RULE_PURGEN_ENVIROMENT =
            register("purgen_enviroment", GameRules.Category.MISC, GameRules.BooleanValue.create(false));

    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String name, GameRules.Category category, GameRules.Type<T> type) {
        return GameRules.register(JigsawPpMod.MODID + name, category, type);
    }

    public static void register() {
        // Called during mod initialization
    }
}
