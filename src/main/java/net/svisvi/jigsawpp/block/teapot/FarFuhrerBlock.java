
package net.svisvi.jigsawpp.block.teapot;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.PurgativeEffect;
import net.svisvi.jigsawpp.procedures.ut.PoopProtectionArmorConditions;

import java.util.Comparator;
import java.util.List;

public class FarFuhrerBlock extends TeapotBlock {

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);
        list.add(Component.translatable("block.jigsaw_pp.farfuhrer.desc"));
    }

    @Override
    public void fume(LevelAccessor world, double x, double y, double z){
        if (world instanceof Level level) {
            if (!level.isClientSide()) {
                level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:whistle")), SoundSource.BLOCKS, 1, 1);
            } else {
                level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:whistle")), SoundSource.BLOCKS, 1, 1, false);
            }
        }

        if (world instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.SNEEZE, x, y, z, 100, 4, 2, 4 , 0.1);

        final Vec3 _center = new Vec3(x, y, z);
        List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
        for (Entity entityiterator : _entfound) {
            if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
                PurgativeEffect.addEffectGasWay(_entity, new MobEffectInstance(MobEffects.POISON, 120, 2, false, false));
                PurgativeEffect.addEffectGasWay(_entity, new MobEffectInstance(MobEffects.WITHER, 120, 0, false, false));
//                _entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 1, false, false));
            }

        }


    }
}
