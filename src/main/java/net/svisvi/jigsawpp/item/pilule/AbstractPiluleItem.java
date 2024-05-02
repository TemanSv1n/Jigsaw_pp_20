package net.svisvi.jigsawpp.item.pilule;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.procedures.ut.PiluleStyles;
import org.apache.commons.lang3.ObjectUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AbstractPiluleItem extends Item {
    static int duration = 1000;
    int duration_buff = 0;
    static int amplifier = 0;
    public static int EFFECTS_REVEAL = 60; //>= this value ---- effects are shown under pilule

    MobEffectInstance effect;


    public AbstractPiluleItem(MobEffectInstance _effect) {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(0).saturationMod(0f).alwaysEat().meat()
                        .effect(_effect, 1F).build()));
    }
//    public AbstractPiluleItem(MobEffectInstance effect, MobEffectInstance second_effect) {
//        super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)
//                .food((new FoodProperties.Builder()).nutrition(0).saturationMod(0f).alwaysEat().meat()
//                        .effect(effect, 1F)
//                        .effect(second_effect, 1F)
//                        .build()));
//        secondary_effect = second_effect;
//    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 16;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {

        //List<MobEffectInstance> efs = PotionUtils.getMobEffects(itemstack);

        list.add(Component.translatable("item.jigsaw_pp.pilule.duration"));
        list.add(Component.literal(Integer.toString(this.duration()/20))
                .append(Component.translatable("misc.jigsaw_pp.second"))
                .append(Component.literal("§7 + "))
                .append(Component.literal(Integer.toString(itemstack.getOrCreateTag().getInt("duration_buff")/20)))
                .append(Component.translatable("misc.jigsaw_pp.second")));

        list.add(Component.translatable("item.jigsaw_pp.pilule.purity"));
        list.add(Component.literal(Integer.toString(purity(itemstack)))     .setStyle(PiluleStyles.purityLogic(purity(itemstack)))
                .append(Component.literal("%")));

        if (purity(itemstack) >= EFFECTS_REVEAL) {
            if (PotionUtils.getMobEffects(itemstack) != null) {
                list.add(Component.translatable("item.jigsaw_pp.pilule.sec_effect"));
                for (MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(itemstack)) {
                    list.add(Component.literal("§7")
                            .append(Component.translatable(mobeffectinstance.getDescriptionId())    .setStyle(PiluleStyles.effectLogic(mobeffectinstance)))
                            .append(Component.literal(" "))
                            .append(Integer.toString(mobeffectinstance.getAmplifier() + 1))
                            .append(Component.literal(" "))
                            .append(Integer.toString(mobeffectinstance.getDuration() / 20))
                            .append(Component.translatable("misc.jigsaw_pp.second")));
                }
                //list.add(Component.literal());
            }
        } else {
            list.add(Component.translatable("item.jigsaw_pp.pilule.sec_effect"));
            list.add(Component.translatable("item.jigsaw_pp.pilule.effects_hidden"));
        }


        super.appendHoverText(itemstack, world, list, flag);
    }

    public int duration(){return this.duration;}

    public int duration_buff(){
        return this.duration_buff;
    }
    public int amplifier(){return this.amplifier;}
    public MobEffectInstance effect(){return this.effect;}
    public int purity(ItemStack itemStack){return itemStack.getOrCreateTag().getInt("purity");}

    public static void setDurationBuff(int durationBuff, ItemStack itemStack){
        itemStack.getOrCreateTag().putInt("duration_buff", durationBuff);

    }
    public static void setPurity(int purity, ItemStack itemStack){
        itemStack.getOrCreateTag().putInt("purity", purity);

    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        PurgativeEffect.addEffectInnerWay(entity, new MobEffectInstance(this.effect().getEffect(), this.duration()+this.duration_buff(), amplifier()));
        //probably do разводка for effects


        Player player = entity instanceof Player ? (Player)entity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemstack);
        }

        if (!world.isClientSide) {
            for(MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(itemstack)) {
                if (mobeffectinstance.getEffect().isInstantenous()) {
                    if (mobeffectinstance.getEffect().equals(ModEffects.BAD_EFFECT.get())){
                        mobeffectinstance.getEffect().applyInstantenousEffect(player, player, entity, mobeffectinstance.getAmplifier(), mobeffectinstance.getDuration());
                    } else {
                        mobeffectinstance.getEffect().applyInstantenousEffect(player, player, entity, mobeffectinstance.getAmplifier(), 1.0D);
                    }
                } else {
                    entity.addEffect(new MobEffectInstance(mobeffectinstance));
                }
            }
        }



        ItemStack retval = new ItemStack(ModItems.EMPTY_PILULE.get());
        super.finishUsingItem(itemstack, world, entity);
        if (itemstack.isEmpty()) {
            return retval;
        } else {
            if (entity instanceof Player _player && !_player.getAbilities().instabuild) {
                if (!_player.getInventory().add(retval))
                    _player.drop(retval, false);
            }
            return itemstack;
        }
    }

    public static boolean comparePilules(ItemStack first, ItemStack second){
        boolean logic = true;
        if (first.getOrCreateTag().getInt("duration_buff") != second.getOrCreateTag().getInt("duration_buff")){
            logic = false;
            return logic;
        }
        if (first.getOrCreateTag().getInt("purity") != second.getOrCreateTag().getInt("purity")){
            logic = false;
            return logic;
        }
        if (!PotionUtils.getMobEffects(first).equals(PotionUtils.getMobEffects(second))){
            logic = false;
            return logic;
        }
        return logic;
    }

}
