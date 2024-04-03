package net.svisvi.jigsawpp.item;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.network.chat.Component;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.pilule.AbstractPiluleItem;
import net.svisvi.jigsawpp.item.pilule.BasicPurgenPiluleItem;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MossElephantThumbItem extends Item {
    public MossElephantThumbItem() {
        super(new Item.Properties().stacksTo(14).rarity(Rarity.COMMON).food((new FoodProperties.Builder()).nutrition(-1).saturationMod(1f).alwaysEat().build()));
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.moss_elephant_thumb.desc"));
    }
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        //System.out.println(PoopProtectionArmorConditions.isProtectedFromLiquid(pContext.getPlayer()));
        //new MobEffectInstance(MobEffects.JUMP, 60, 1, false, false)
        List<MobEffectInstance> ar = new ArrayList<MobEffectInstance>();
        ar.add(new MobEffectInstance(MobEffects.JUMP, 800, 1, false, false));
        ar.add(new MobEffectInstance(MobEffects.BLINDNESS, 60, 1, false, false));
        if (pContext.getPlayer() instanceof ServerPlayer _player) {
            ItemStack _setstack = new ItemStack(ModItems.BASIC_PURGEN_PILULE.get());
            _setstack.setCount(1);
            AbstractPiluleItem.setDurationBuff(200, _setstack);
            PotionUtils.setCustomEffects(_setstack, ar);
            ItemHandlerHelper.giveItemToPlayer(_player, _setstack);


        }

        return super.useOn(pContext);
    }
}

