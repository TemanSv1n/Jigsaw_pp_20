package net.svisvi.jigsawpp.recipe;

import com.google.gson.JsonArray;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class ParsePotion {

    public static ItemStack parseFromJson(JsonObject pSerializedRecipe){
        JsonArray potionStats = GsonHelper.getAsJsonArray(pSerializedRecipe, "potionStats");

        List<MobEffectInstance> efs = new ArrayList<MobEffectInstance>();

        for (int i = 0; i < potionStats.size(); i++){
            //System.out.println(PotionUtils.getMobEffects());
            Potion pot = Potion.byName(potionStats.get(i).getAsJsonObject().get("potion").getAsString());
            if (pot.getEffects().size() !=0) {
                MobEffect me = pot.getEffects().get(0).getEffect();
                System.out.println(pot.getEffects().toString());
                efs.add(new MobEffectInstance(me, potionStats.get(i).getAsJsonObject().get("duration").getAsInt(),
                        potionStats.get(i).getAsJsonObject().get("amplifier").getAsInt()));
            }
        }

        ItemStack potionstack = new ItemStack(Items.POTION);
        potionstack = PotionUtils.setCustomEffects(potionstack, efs);
        return potionstack;
    }
}
