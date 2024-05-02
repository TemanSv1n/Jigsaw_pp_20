package net.svisvi.jigsawpp.item.ut;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.effect.RadiationEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;


public class RadiationItem extends Item {

    private final float randomChanceOfRadiation;

    public RadiationItem(Properties properties, float randomChanceOfRadiation) {
        super(properties);

        this.randomChanceOfRadiation = randomChanceOfRadiation;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held) {
        super.inventoryTick(stack, level, entity, i, held);
        if (!level.isClientSide && entity instanceof LivingEntity living && !(living instanceof Player player && player.isCreative())) {
            float stackChance = stack.getCount() * randomChanceOfRadiation;
            if (!living.hasEffect(ModEffects.RADIATION.get()) && level.random.nextFloat() < stackChance) {
                MobEffectInstance instance = new MobEffectInstance(ModEffects.RADIATION.get(), 1800);
                RadiationEffect.addEffectLiquidWay(entity, instance);
            }
        }
    }
}
