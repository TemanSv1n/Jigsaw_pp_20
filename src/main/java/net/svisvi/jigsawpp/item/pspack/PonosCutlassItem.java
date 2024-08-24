package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.particles.ModParticleTypes;

public class PonosCutlassItem extends SwordItem {
    public PonosCutlassItem() {
        super(new Tier() {
            public int getUses() {
                return 100;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 0f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.BEDROCK));
            }
        }, 3, -2.4f, new Properties());
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        drainPoop(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, sourceentity);

        return retval;
    }

    public void drainPoop(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity){
        if (entity == null || sourceentity == null)
            return;
        double T = 0;
        double Zo = 0;
        double Yo = 0;
        double Za = 0;
        double Xo = 0;
        double Ya = 0;
        double Xa = 0;
        if (entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(ModEffects.PURGATIVE.get()) : false) {
            if (entity instanceof LivingEntity _entity)
                _entity.removeEffect(ModEffects.PURGATIVE.get());
            if (sourceentity instanceof LivingEntity _entity) {
                _entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1, (false), (true)));
                _entity.addEffect(new MobEffectInstance(ModEffects.GOOD_EFFECT.get(), 300, 1, (false), (true)));
            }
            if (world instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP_BUBBLE.get()), (entity.getX()), (entity.getY()), (entity.getZ()), 50, 0.7, 0.7, 0.7, 0.2);
            if (world instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP.get()), ((int)entity.getX()), ((int)entity.getY()), ((int)entity.getZ()), 20, 1, 1, 1, 0.1);
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1);
                } else {
                    _level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1, false);
                }
            }
        }
        else if (entity instanceof LivingEntity _livEnt ? _livEnt.hasEffect(ModEffects.POOP.get()) : false) {
            if (entity instanceof LivingEntity _entity)
                _entity.removeEffect(ModEffects.POOP.get());
            if (sourceentity instanceof LivingEntity _entity)
                _entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 21, 0, (false), (true)));
            if (world instanceof ServerLevel _level)
                _level.sendParticles((SimpleParticleType) (ModParticleTypes.POOP_BUBBLE.get()), (entity.getX()), (entity.getY()), (entity.getZ()), 20, 0.7, 0.7, 0.7, 0.2);
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1, false);
                }
            }
        }  else if (entity instanceof Player) {
            if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) >= 2) {
                if (entity instanceof Player _player)
                    _player.getFoodData().setFoodLevel((int) ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) - 1));
                if (world instanceof ServerLevel _level)
                    _level.sendParticles(ParticleTypes.ASH, (entity.getX()), (entity.getY()), (entity.getZ()), 15, 0.7, 0.7, 0.7, 0.2);
                if (world instanceof Level _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, new BlockPos((int)entity.getX(), (int)entity.getY(), (int)entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.PLAYERS, 1, -1);
                    } else {
                        _level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.player.burp")), SoundSource.PLAYERS, 1, -1, false);
                    }
                }
            }
        }
    }
}
