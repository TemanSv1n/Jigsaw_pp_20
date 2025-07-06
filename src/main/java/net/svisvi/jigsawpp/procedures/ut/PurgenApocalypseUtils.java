package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.gamerules.ModGameRules;

public class PurgenApocalypseUtils {
    
    public static boolean purgesEnvironmentCondition(Level level){
//        System.out.println("ln "+ (level != null));
//        System.out.println("pgr "+ level.getGameRules().getBoolean(ModGameRules.RULE_PURGEN_ENVIROMENT));
        return level != null && level.getGameRules().getBoolean(ModGameRules.RULE_PURGEN_ENVIROMENT);

    }

    public static boolean purgenPlayerRenderCondition(Player player){
        if (player == null) return false;
        return player.hasEffect(ModEffects.PURGENMAN_BLESSING.get());
    }
}
