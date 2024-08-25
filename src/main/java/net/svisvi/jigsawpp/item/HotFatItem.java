package net.svisvi.jigsawpp.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.RadiationEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class HotFatItem extends Item {
    private final float randomChanceOfFire;

    public HotFatItem(Properties properties, float randomChanceOfRadiation) {
        super(properties);

        this.randomChanceOfFire = randomChanceOfRadiation;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held) {
        super.inventoryTick(stack, level, entity, i, held);
        if (!level.isClientSide && entity instanceof LivingEntity living && !(living instanceof Player player && player.isCreative())) {
            float stackChance = stack.getCount() * randomChanceOfFire;
            entity.setSecondsOnFire(1);
        }
    }
}
