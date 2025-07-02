package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistriesGoon {

    //ENTITIES
    public static String getEntityRegistryName(EntityType<?> entityType) {
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(entityType);
        return key != null ? key.toString() : null; // Returns "modid:entity_name"
    }

    public static EntityType<?> getEntityFromRegistryName(String registryName) {
        ResourceLocation key = new ResourceLocation(registryName);
        EntityType<?> ret = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("minecraft_pig"));
        final EntityType<?> rett = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation("minecraft_pig"));
        try {
            ret = ForgeRegistries.ENTITY_TYPES.getValue(key);
            if (ret == null){
                return rett;
            }
        } catch (Exception e){
            return rett;
        }
        return ret;
    }
    //ITEMS
    public static String getItemRegistryName(Item item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
        return key != null ? key.toString() : null; // Returns "modid:item_name"
    }

    public static Item getItemFromRegistryName(String registryName) {
        ResourceLocation key = new ResourceLocation(registryName);
        // Default fallback to minecraft:potato
        final Item fallbackItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "potato"));

        if (key == null) {
            return fallbackItem;
        }

        try {
            Item item = ForgeRegistries.ITEMS.getValue(key);
            return item != null ? item : fallbackItem;
        } catch (Exception e) {
            return fallbackItem;
        }
    }

}


