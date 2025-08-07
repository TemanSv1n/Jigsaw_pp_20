package net.svisvi.jigsawpp.block.drist_tnt;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.entity.drist_tnt.PooclearTnt;
import net.svisvi.jigsawpp.entity.drist_tnt.PrimedDristTnt;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PooclearTntItemExtension {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModBlocks.POOCLEAR_TNT.get(), new DefaultDispenseItemBehavior() {
            protected ItemStack execute(BlockSource p_123425_, ItemStack p_123426_) {
                Level level = p_123425_.getLevel();
                BlockPos blockpos = p_123425_.getPos().relative((Direction)p_123425_.getBlockState().getValue(DispenserBlock.FACING));
                PooclearTnt primedtnt = new PooclearTnt(level, (double)blockpos.getX() + 0.5, (double)blockpos.getY(), (double)blockpos.getZ() + 0.5, (LivingEntity)null);
                level.addFreshEntity(primedtnt);
                level.playSound((Player)null, primedtnt.getX(), primedtnt.getY(), primedtnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent((Entity)null, GameEvent.ENTITY_PLACE, blockpos);
                p_123426_.shrink(1);
                return p_123426_;
            }
        }));

    }
}
