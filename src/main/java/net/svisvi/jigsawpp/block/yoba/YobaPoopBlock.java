package net.svisvi.jigsawpp.block.yoba;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.effect.PoopEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;

public class YobaPoopBlock extends YobaBlock{
    @Override
    public String MOVE_SOUND(){
        return "entity.cow.milk";
    }

    @Override
    public Block getTrace(){
        return ModBlocks.PONOS_FLUID_BLOCK.get();
    }
    public Block getDied(){
        return ModBlocks.MOLD_BLOCK.get();
    }

    @Override
    public void entityInside(BlockState blockstate, Level world, BlockPos pos, Entity entity) {
        //super.entityInside(blockstate, world, pos, entity);
        if (entity instanceof Fox){
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            PoopEffect.addEffectLiquidWay(entity, new MobEffectInstance(ModEffects.PURGATIVE.get(), 400,0));
            if (!world.isClientSide()) {
                world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.eat")), SoundSource.HOSTILE, 1, 1);
            } else {
                world.playLocalSound(pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.fox.eat")), SoundSource.HOSTILE, 1, 1, false);
            }
        } else {
            PoopEffect.addEffectLiquidWay(entity, new MobEffectInstance(ModEffects.POOP.get(), 60,0));
        }
    }
}
