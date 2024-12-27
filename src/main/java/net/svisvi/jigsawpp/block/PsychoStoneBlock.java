package net.svisvi.jigsawpp.block;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;


import java.awt.*;
import java.util.Collections;
import java.util.List;

public class PsychoStoneBlock extends Block {
    public PsychoStoneBlock() {
        super(Properties.of().sound(SoundType.DEEPSLATE).strength(50.2f, 0.1f));
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    public static void execute(Level level, BlockPos pos){
        String cmd = "summon jigsaw_pp:beaver_spider ~ ~1 ~";
        if (level instanceof ServerLevel _level)
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(pos.getX(), pos.getY(), pos.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), cmd);

        level.setBlock(pos, Blocks.DEEPSLATE.defaultBlockState(), 3);
        if (level instanceof ServerLevel _level)
            _level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 10, 0.5, 0.5, 0.5, 0);

            if (!level.isClientSide()) {
                level.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("event.raid.horn")), SoundSource.BLOCKS, 3, 1);
            } else {
                level.playLocalSound(pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("event.raid.horn")), SoundSource.BLOCKS, 3, 1, false);

            }
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        execute(pLevel, pPos);
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        execute(pLevel, pPos);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        execute(level, pos);
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity) {
        execute(pLevel, pPos);
        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    @Override
    public void wasExploded(Level pLevel, BlockPos pPos, Explosion pExplosion) {
        execute(pLevel, pPos);
        super.wasExploded(pLevel, pPos, pExplosion);
    }
}
