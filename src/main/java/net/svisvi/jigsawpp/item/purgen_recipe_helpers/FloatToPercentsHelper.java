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

    public static String percentToString(int percent){
//        System.out.println("percentToString -> " + Integer.toString(percent));
        String str = Integer.toString(percent);
        if (percent > 0){
            str = "+" + str;
        }
        return str + "%";

    }

    public static String floatToStringPercent(float num){
        return percentToString(floatToPercent(num));
    }
}
