package net.svisvi.jigsawpp.procedures.ut;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
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
}
