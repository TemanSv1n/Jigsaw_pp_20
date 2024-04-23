package net.svisvi.jigsawpp.item.purgen_recipe_helpers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class AbstractPurgenRecipeHelperItem extends Item{
    public AbstractPurgenRecipeHelperItem() {
        super(new Item.Properties().stacksTo(14).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(-1).saturationMod(1f).alwaysEat().build()));
    }







    public static String toDisplay(ItemStack itemStack){return itemStack.getOrCreateTag().getString("to_display");} //float or //int
    public static void setToDisplay(String toDisplay, ItemStack itemStack){
        itemStack.getOrCreateTag().putString("to_display", toDisplay);

    }
    public static int Int(ItemStack itemStack){return itemStack.getOrCreateTag().getInt("int");}
    public static void setInt(int Int, ItemStack itemStack){
        itemStack.getOrCreateTag().putInt("int", Int);

    }
    public static float Float(ItemStack itemStack){return itemStack.getOrCreateTag().getFloat("float");}
    public static void setFloat(float Floatt, ItemStack itemStack){
        itemStack.getOrCreateTag().putFloat("float", Floatt);

    }
    public static float FloatAlt(ItemStack itemStack){return itemStack.getOrCreateTag().getFloat("float_alt");}
    public static void setFloatAlt(float Floatt, ItemStack itemStack){
        itemStack.getOrCreateTag().putFloat("float_alt", Floatt);

    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        String str = "";
        String toDisplay = toDisplay(itemstack);
        if (toDisplay.equals("int")){
            str = Integer.toString(Int(itemstack));
        } else if (toDisplay.equals("float")) {
            str = FloatToPercentsHelper.floatToStringPercent(Float(itemstack));
        } else if (toDisplay.equals("float_alt")) {
            str = FloatToPercentsHelper.floatToStringPercentAlt(FloatAlt(itemstack));
        }


        list.add(Component.literal(str).setStyle(Style.EMPTY.withColor(TextColor.parseColor("#8d8f94"))));

    }


}
