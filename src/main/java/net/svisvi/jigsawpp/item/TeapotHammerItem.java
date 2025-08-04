package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.init.ModDatas;
import net.svisvi.jigsawpp.item.pspack.RoadSignItem;

import java.util.List;

import static net.svisvi.jigsawpp.procedures.ut.TeapotExplosion.getTeapot;

public class TeapotHammerItem extends RoadSignItem {
    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        entity.level().playSound(null, entity.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:whistle")), SoundSource.NEUTRAL, 1, 1);
        return super.hurtEnemy(itemstack, entity, sourceentity);
    }
//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        BlockState pState = pContext.getLevel().getBlockState(pContext.getClickedPos());
//        Level world = pContext.getLevel();
//        if (ModDatas.DEFAULT_TEAPOTS.contains(pState.getBlock())){
//            FallingBlockEntity falling = FallingBlockEntity.fall(world, pContext.getClickedPos(), pState);
//            falling.setDeltaMovement(0,1,0);
//            world.addFreshEntity(falling);
//        }
//        return super.useOn(pContext);
//    }
}
