package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;

public class PiluleStyles {

    public static Style effectLogic(MobEffectInstance mobEffectInstance){
        String color = "";
        if (!mobEffectInstance.getEffect().isBeneficial()){
            color = "#FF5555";
        } else {
            color = "#5555FF";
        }
        return Style.EMPTY.withColor(TextColor.parseColor(color));
    }

    public static Style purityLogic(int purity){
        String color = "";
        if (purity >= 0 && purity < 20){
            color = "#452407";
        } else if (purity >= 20 && purity < 40){
            color = "#d4ba15";
        } else if (purity >= 40 && purity < 60){
            color = "#a3bf9f";
        } else if (purity >= 60 && purity < 80){
            color = "#13570a";
        } else if (purity >= 80 && purity <= 100){
            color = "#4bd4db";
        } else {
            color = "#000000";
            return Style.EMPTY.withColor(TextColor.parseColor(color)).withObfuscated(true);
        }

        return Style.EMPTY.withColor(TextColor.parseColor(color));
    }

    public static String numberToRoman(int n){//only for 1-6, like with effects
        n++;
        String ret = "Z";
        if (n >= 7){
            return Integer.toString(n);
        } else {
            switch (n) {
                case 1:
                    ret = "I";
                    break;
                case 2:
                    ret = "II";
                    break;
                case 3:
                    ret = "III";
                    break;
                case 4:
                    ret = "IV";
                    break;
                case 5:
                    ret = "V";
                    break;
                case 6:
                    ret = "VI";
                    break;
                default:
                    ret = "Z";
                    break;

            }
        }
        return ret;
    }
}
