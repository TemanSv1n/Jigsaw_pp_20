package net.svisvi.jigsawpp.effect;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.procedures.ut.RegistriesGoon;

import java.util.Map;

public class PurgenManBlessingEffect extends MobEffect {
    //reging
    public int color = -12372212;

    public PurgenManBlessingEffect(MobEffectCategory mobEffectCategory, int color) {


        super(mobEffectCategory, color);
    }

    @Override
    public String getDescriptionId() {
        return "effect.jigsaw_pp.purgenman_blessing";
    }

    @Override
    public int getColor() {
        return this.color;
    }

//    public static final Map<String, String> purgen_replacements = Map.of(
//            "minecraft:bread", "jigsaw_pp:sweat_bread",
//            "minecraft_water_bucket", "jigsaw_pp:ponos_bucket"
//    );
//
//    // INDIAN CODE SECTION !!! TIN DIDIN DIDIN DIDIN STARTS
//
//    private void handlePlayerSlotReplacement(Player player, ItemStack original, ItemStack replacement) {
//        Inventory inventory = player.getInventory();
//
//        // Check main hand
//        if (original == player.getMainHandItem()) {
//            player.setItemInHand(InteractionHand.MAIN_HAND, replacement.copy());
//            return;
//        }
//
//        // Check offhand
//        if (original == player.getOffhandItem()) {
//            player.setItemInHand(InteractionHand.OFF_HAND, replacement.copy());
//            return;
//        }
//
//        // Check armor slots
//        for (int i = 0; i < 4; i++) {
//            if (original == inventory.getArmor(i)) {
//                inventory.armor.set(i, replacement.copy());
//                return;
//            }
//        }
//
//        // Regular inventory slots
//        for (int i = 0; i < inventory.items.size(); i++) {
//            if (inventory.items.get(i) == original) {
//                inventory.items.set(i, replacement.copy());
//                return;
//            }
//        }
//    }
//
//    private void handleMobSlotReplacement(LivingEntity entity, ItemStack original, ItemStack replacement) {
//        // For mobs, we need to use the capability system
//        entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
//            for (int i = 0; i < handler.getSlots(); i++) {
//                if (handler.getStackInSlot(i) == original) {
//                    handler.extractItem(i, original.getCount(), false);
//                    handler.insertItem(i, replacement.copy(), false);
//                    return;
//                }
//            }
//        });
//    }
//
//    // INDIAN CODE SECTION !!! TIN DIDIN DIDIN DIDIN ^^^^
//
//    public static ItemStack purgenify(ItemStack itemStack){
//        if (purgen_replacements.containsKey(RegistriesGoon.getItemRegistryName(itemStack.getItem()))) {
//            ItemStack newStack = new ItemStack(RegistriesGoon.getItemFromRegistryName(purgen_replacements.get(RegistriesGoon.getItemRegistryName(itemStack.getItem()))), itemStack.getCount());
//            if (itemStack.hasTag()) {
//                newStack.setTag(itemStack.getTag().copy());
//            }
//            return newStack;
//        } else {
//            return ItemStack.EMPTY;
//        }
//    }

//    @Override
//    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
//        if ((pLivingEntity.getEffect(this).getDuration() + 10) % 10 == 0) {
//            for (ItemStack stack : pLivingEntity.getAllSlots()) {
//                if (!stack.isEmpty()) {
//                    ItemStack repStack = purgenify(stack);
//                    if (!repStack.isEmpty()){
//                        stack.setCount(repStack.getCount());
//                        stack = repStack;
//                    }
//
//                }
//            }
//        }
//
//
//        super.applyEffectTick(pLivingEntity, pAmplifier);
//    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }


}

