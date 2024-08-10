package net.svisvi.jigsawpp.block.kega;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import java.util.List;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.effect.RadiationEffect;
import net.svisvi.jigsawpp.effect.init.ModEffects;

import java.util.*;

public class KegaBlock extends Block {
    public static final IntegerProperty ULTRA = IntegerProperty.create("ultra", 0, 1);
    private static final float EXPLOSION_RADIUS = 100.0f;
    private static final int DELAY_TICKS = 490; // 24 секунды (20 ticks = 1 секунда)

    public KegaBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.COPPER).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(ULTRA, 0));
    }

    private static void playSoundOnce(ServerLevel world, BlockPos pos, BlockState pState) {
        if ((pState.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _getip1 ? pState.getValue(_getip1) : -1) == 0) {
            ResourceLocation soundLocation = new ResourceLocation("jigsaw_pp:alpha_bomb");
            world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(soundLocation), SoundSource.NEUTRAL, 2, 1);
        }
    }

    /*
    private static void explodeBoom(LevelAccessor world, double x, double y, double z) {
        boolean found = false;
        double sx = 0.0D;
        double sy = 0.0D;
        double sz = 0.0D;
        world.(BlockPos pos, Blocks.f_50016_.m_49966_(), 3);
        sx = -3.0D;
        found = false;
        for (int index0 = 0; index0 < 6; index0++) {
            sy = -3.0D;
            for (int index1 = 0; index1 < 6; index1++) {
                sz = -3.0D;
                for (int index2 = 0; index2 < 6; index2++) {
                    if (world instanceof Level) { Level _level = (Level)world; if (!_level.m_5776_())
                        _level.m_254849_(null, x + sx, y + sy, z + sz, 7.0F, Level.ExplosionInteraction.TNT);  }
                    sz++;
                    }
                sy++;
                }
            sx++;
            }
    }
    */

    private static void active(BlockPos pPos, BlockState pState, Level pLevel) {
        if ((pState.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _getip1 ? pState.getValue(_getip1) : -1) == 0) {
            // Проигрываем звук и изменяем состояние блока на "активное"
            playSoundOnce((ServerLevel) pLevel, pPos, pState);

            {
                int _value = 1;
                BlockState _bs = pLevel.getBlockState(pPos);
                if (_bs.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                    pLevel.setBlock(pPos, _bs.setValue(_integerProp, _value), 3);
            }

            // Запускаем задержку перед взрывом
            pLevel.scheduleTick(pPos, pState.getBlock(), DELAY_TICKS);
        }
    }



    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {
        if ((state.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _getip1 ? state.getValue(_getip1) : -1) == 1) {
            // Создаем взрыв
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            world.explode(null, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_RADIUS, Level.ExplosionInteraction.BLOCK);

            // Дополнительные эффекты, например, радиация
            List<Entity> entities = world.getEntities(null, new AABB(pos).inflate(EXPLOSION_RADIUS));
            for (Entity entity : entities) {
                double distance = entity.distanceToSqr(Vec3.atCenterOf(pos));
                if (distance <= EXPLOSION_RADIUS * EXPLOSION_RADIUS) {
                    RadiationEffect.addEffectLiquidWay(entity, new MobEffectInstance(ModEffects.RADIATION.get(), 14400));
                }
            }

            // Удаляем блок при взрыве без выпадения предметов

        }
    }



    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Custom Tooltip Text"));
        super.appendHoverText(itemstack, world, tooltip, flag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ULTRA);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return true;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void neighborChanged(BlockState blockstate, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
        if (world instanceof ServerLevel serverLevel && serverLevel.hasNeighborSignal(pos)) {
            active(pos, blockstate, serverLevel);
        }
    }
}