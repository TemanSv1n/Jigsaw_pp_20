package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.Component;

import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class SwordOfBabushkaDustItem extends SwordItem {
    public SwordOfBabushkaDustItem() {
        super(new Tier() {
            public int getUses() {
                return 1000;
            }
            public float getSpeed() {
                return 3f;
            }
            public float getAttackDamageBonus() {
                return 50f;
            }
            public int getLevel() {
                return 0;
            }
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, 3, 6f, new Item.Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        this.attack(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity);
        return retval;
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
    }
    public static void attack(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;
        for (int index0 = 0; index0 < 5; index0++) {
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:metal_pipe_fall")), SoundSource.PLAYERS, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:metal_pipe_fall")), SoundSource.PLAYERS, 1, 1, false);
                }
            }
        }
        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
            _entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200, 1));
    }
}
