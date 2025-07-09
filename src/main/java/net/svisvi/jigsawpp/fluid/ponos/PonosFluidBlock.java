package net.svisvi.jigsawpp.fluid.ponos;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.fluid.init.ModFluids;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

public class PonosFluidBlock extends LiquidBlock {
    public PonosFluidBlock() {
        super(() -> ModFluids.PONOS.get(), BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }
    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 1;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 1;
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getFluidState().isRandomlyTicking();
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        pState.getFluidState().randomTick(pLevel, pPos, pRandom);
    }


    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public void entityInside(BlockState blockstate, Level world, BlockPos pos, Entity entity) {
        super.entityInside(blockstate, world, pos, entity);
        if(PoopEffect.addEffectLiquidWay(entity, new MobEffectInstance(ModEffects.POOP.get(), 10, 0, false, false, false))){
            if (entity instanceof Player) {
                if (world instanceof ServerLevel _level) {
                    if (!_level.isClientSide()) {
                        _level.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.BLOCKS, 1, 1);
                    } else {
                        _level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk")), SoundSource.BLOCKS, 1, 1, false);
                    }
                }
            }

        }

        if (entity instanceof LivingEntity livingEntity){
            if (livingEntity.hasEffect(ModEffects.POOP_WALKING.get())){
                if (!blockstate.getFluidState().isSource()){
                    livingEntity.setDeltaMovement(new Vec3(livingEntity.getDeltaMovement().x,+ 0.01, livingEntity.getDeltaMovement().z));
                }
            }
        }

    }
}
