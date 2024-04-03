package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.List;

public class AbstractPiluleItem extends Item {
    static int duration = 1000;
    int duration_buff = 0;


    public AbstractPiluleItem(MobEffectInstance effect) {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(0).saturationMod(0f).alwaysEat().meat()
                        .effect(effect, 1F).build()));
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable("item.jigsaw_pp.pilule.duration"));
        list.add(Component.literal(Integer.toString(this.duration()/20))
                .append(Component.translatable("misc.jigsaw_pp.second"))
                .append(Component.literal("§7 + "))
                .append(Component.literal(Integer.toString(this.duration_buff()/20)))
                .append(Component.translatable("misc.jigsaw_pp.second")));
        super.appendHoverText(itemstack, world, list, flag);
    }

    public int duration(){return this.duration;}

    public int duration_buff(){
        return this.duration_buff;
    }

    public void setDurationBuff(int durationBuff){
        this.duration_buff = durationBuff;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        ItemStack retval = new ItemStack(ModItems.EMPTY_PILULE.get());
        super.finishUsingItem(itemstack, world, entity);
        if (itemstack.isEmpty()) {
            return retval;
        } else {
            if (entity instanceof Player player && !player.getAbilities().instabuild) {
                if (!player.getInventory().add(retval))
                    player.drop(retval, false);
            }
            return itemstack;
        }
    }

}
