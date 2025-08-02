package net.svisvi.jigsawpp.gamerules;

import net.minecraft.world.level.GameRules;
import net.svisvi.jigsawpp.JigsawPpMod;

public class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanValue> RULE_PURGEN_ENVIROMENT =
            register("purgen_enviroment", GameRules.Category.MISC, GameRules.BooleanValue.create(false));

    private static <T extends GameRules.Value<T>> GameRules.Key<T> register(
            String name, GameRules.Category category, GameRules.Type<T> type) {
        return GameRules.register(JigsawPpMod.MODID + "_" + name, category, type);
    }
    public static final GameRules.Key<GameRules.BooleanValue> REMOVE_POONAMI = GameRules.register("removePoonami", GameRules.Category.MISC, GameRules.BooleanValue.create(false));
    public static final GameRules.Key<GameRules.IntegerValue> POONAMI_SPEED = GameRules.register("poonamiSpeed", GameRules.Category.MISC, GameRules.IntegerValue.create(20));
    public static void register() {
        // Called during mod initialization
    }
}
