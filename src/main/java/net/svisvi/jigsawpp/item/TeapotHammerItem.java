package net.svisvi.jigsawpp.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.item.pspack.RoadSignItem;

public class TeapotHammerItem extends RoadSignItem {
    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        entity.level().playSound(null, entity.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:whistle")), SoundSource.NEUTRAL, 1, 1);
        return super.hurtEnemy(itemstack, entity, sourceentity);
    }
}
