package net.svisvi.jigsawpp.item.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.svisvi.jigsawpp.item.PurgenBundleItem;

public class ModItemProperties {
    public static void addCustomItemProperties(){
        makeBundle(ModItems.PURGEN_BUNDLE.get());

    }

    private static void makeBundle(Item item){
        ItemProperties.register(item, new ResourceLocation("filled"), (p_174625_, p_174626_, p_174627_, p_174628_) -> {
            return PurgenBundleItem.getFullnessDisplay(p_174625_);
        });
    }
}
