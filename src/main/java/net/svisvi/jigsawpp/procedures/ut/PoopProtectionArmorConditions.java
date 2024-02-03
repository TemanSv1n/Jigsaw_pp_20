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

            if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("jigsaw_pp:poop_protective_liquid_one")))) {
                //System.out.println("True bi du ba dubi");
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
                //System.out.println(item.getItem().getClass().getName());
                //System.out.println(item.getItem().getClass().getSuperclass().getName());
                if (!(item.getItem().getClass().getSuperclass().getName().equals(temp.getItem().getClass().getSuperclass().getName()))) {
                    //System.out.println("ABAS here!");
                    System.out.println(item.getItem().getClass().getSuperclass().getName());
                    return false;
                }
            }
            //System.out.println("True here!");
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

            if ((entity instanceof LivingEntity _entGetArmor ? _entGetArmor.getItemBySlot(slotNames.get(i)) : ItemStack.EMPTY).is(ItemTags.create(new ResourceLocation("jigsaw_pp:poop_protective_gas_one")))) {
                //System.out.println("True bi du ba dubi");
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
                //System.out.println(item.getItem().getClass().getName());
                //System.out.println(item.getItem().getClass().getSuperclass().getName());
                if (!(item.getItem().getClass().getSuperclass().getName().equals(temp.getItem().getClass().getSuperclass().getName()))) {
                    //System.out.println("ABAS here!");
                    System.out.println(item.getItem().getClass().getSuperclass().getName());
                    return false;
                }
            }
            //System.out.println("True here!");
            return true;
        }
    }
}
