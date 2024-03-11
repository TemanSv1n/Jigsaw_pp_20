package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoopProtectionArmorConditions {
    //Checks armor and does logic
    public static boolean isProtectedFromLiquid(Entity entity){
        List<EquipmentSlot> slotNames = new ArrayList<EquipmentSlot>(Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
        List<ItemStack> items = new ArrayList<ItemStack>();
        ItemStack temp = ItemStack.EMPTY;

        //checking for single items
        for (int i = 0; i < 4; i++) {

            if ((entity instanceof LivingEntity _entGetArmor ? temp = _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("jigsaw_pp:poop_protective_liquid_one")))) {
                items.add(temp);
                action(entity, items, "liquid");
                return true;
            } else if ((entity instanceof LivingEntity _entGetArmor ? temp = _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("jigsaw_pp:poop_protective_liquid_full")))){
                //counting not single items
                items.add(temp);


            }
        }
        //false if it's not full complect
        if(items.size() < 4) {
            return false;
        } else{
            for (ItemStack item : items) {
                if (!(item.getItem().getClass().getSuperclass().getName().equals(temp.getItem().getClass().getSuperclass().getName()))) {
                    return false;
                }
            }
            action(entity, items, "liquid");
            return true;
        }

    }

    //ахтунг! скрипт аналогичен! При фиксах верхнего скрипта фиксить и нижний
    public static boolean isProtectedFromGas(Entity entity) {
        List<EquipmentSlot> slotNames = new ArrayList<EquipmentSlot>(Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
        List<ItemStack> items = new ArrayList<ItemStack>();
        ItemStack temp = ItemStack.EMPTY;

        //checking for single items
        for (int i = 0; i < 4; i++) {

            if ((entity instanceof LivingEntity _entGetArmor ? temp = _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("jigsaw_pp:poop_protective_gas_one")))) {
                items.add(temp);
                action(entity, items, "gas");
                return true;
            } else if ((entity instanceof LivingEntity _entGetArmor ? temp = _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("jigsaw_pp:poop_protective_gas_full")))){
                //counting not single items
                items.add(temp);


            }
        }
        //false if it's not full complect
        if(items.size() < 4) {
            return false;
        } else{
            for (ItemStack item : items) {
                if (!(item.getItem().getClass().getSuperclass().getName().equals(temp.getItem().getClass().getSuperclass().getName()))) {
                    return false;
                }
            }
            action(entity, items, "gas");
            return true;
        }
    }


    public static void action(Entity entity, List<ItemStack> items, String type){ //type is for liquid/gas
        for(ItemStack it : items) {
                if (type.equals("liquid")) {
                    if (it.getItem() instanceof IPoopProtective) {
                        ((IPoopProtective) it.getItem()).onLiquid(it, entity);
                    } else {
                        defaultAction(entity, it, "liquid");
                    }
                } else if (type.equals("gas")) {
                    if (it.getItem() instanceof IPoopProtective) {
                        ((IPoopProtective) it.getItem()).onGas(it, entity);
                    } else {
                        defaultAction(entity, it, "gas");
                    }
                }
        }

    }

    public static void defaultAction(Entity entity, ItemStack itemStack, String type){
        //break for example ://
    }

}
