package net.svisvi.jigsawpp.item.purgen_recipe_helpers;

public class FloatToPercentsHelper {
    public static int floatToPercent(float num){
//        System.out.println("floatToPercent -> " + Float.toString(num));
        int ret_num = 0;
        if (num != 0) {
            ret_num = (int) ((num - 1) * 100);

        }
        return ret_num;
    }

    public static float floatToPercentFloat(float num){
//        System.out.println("floatToPercent -> " + Float.toString(num));
        float ret_num = 0;
        if (num != 0) {
            ret_num = ((num - 1) * 100);

        }
        return ret_num;
    }

    public static String percentToString(int percent, boolean hasPlus){
//        System.out.println("percentToString -> " + Integer.toString(percent));
        String str = Integer.toString(percent);
        if (hasPlus) {
            if (percent > 0) {
                str = "+" + str;
            }
        }
        return str + "%";

    }
    public static String percentToString(float percent, boolean hasPlus){
//        System.out.println("percentToString -> " + Integer.toString(percent));
        String str = Float.toString(percent);
        if (hasPlus) {
            if (percent > 0) {
                str = "+" + str;
            }
        }
        return str + "%";

    }
    public static int reversePercent(int percent){
        return 100 + percent;
    }
    public static float reversePercent(float percent){
        return 100 + percent;
    }

    public static String floatToStringPercent(float num){
        return percentToString(floatToPercent(num), true);
    }
    public static String floatToStringPercentAlt(float num){
        return percentToString(reversePercent(floatToPercentFloat(num)), false);
    }

}
