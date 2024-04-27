// ########################################
// ########################################
// ## ЕБИТЕСТЬ ВСЕ С КОНЁМ
// ########################################
// ########################################


package net.svisvi.jigsawpp.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.JigsawPpMod;

import java.util.Collections;
import java.util.List;


public class KegaBlock extends Block {
    public static final IntegerProperty ULTRA = IntegerProperty.create("ultra", 0, 1);
    private static final int RADIUS = 50;


    public KegaBlock() {
        super(BlockBehaviour.Properties.of().sound(SoundType.COPPER).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(ULTRA, 0));
    }


    // #####################################
    // ### Типо взрыв
    private static void replaceBlocksInSphere(ServerLevel world, BlockPos centerPos) {


        JigsawPpMod.queueServerWork(490, () -> { // Очередь работы на сервере
            int chunkRadius = (int) Math.ceil((double) RADIUS / 16); // Радиус в чанках
            int chunkX = centerPos.getX() >> 4;
            int chunkZ = centerPos.getZ() >> 4;
            world.setBlockAndUpdate(centerPos, Blocks.AIR.defaultBlockState());
            world.sendParticles(ParticleTypes.EXPLOSION, centerPos.getX() + 0.5, centerPos.getY() + 1, centerPos.getZ() + 0.5, 200, 50, 50, 50, 0);

            for (int chunkXOffset = -chunkRadius; chunkXOffset <= chunkRadius; chunkXOffset++) {
                for (int chunkZOffset = -chunkRadius; chunkZOffset <= chunkRadius; chunkZOffset++) {
                    int chunkPosX = chunkX + chunkXOffset;
                    int chunkPosZ = chunkZ + chunkZOffset;

                    for (int y = centerPos.getY() - RADIUS; y <= centerPos.getY() + RADIUS; y++) {
                        for (int x = (chunkPosX << 4); x < (chunkPosX << 4) + 16; x++) {
                            for (int z = (chunkPosZ << 4); z < (chunkPosZ << 4) + 16; z++) {
                                BlockPos mutablePos = new BlockPos(x, y, z);
                                if (centerPos.distSqr(mutablePos) <= RADIUS * RADIUS) {
                                    world.setBlockAndUpdate(mutablePos, Blocks.AIR.defaultBlockState()); // Заменяем блоки в сфере на воздух
                                }
                            }
                        }
                    }
                }
            }
        });
    }


    // #####################################
    // ### УИИИ УИИИ УИИИ  АКТИВИРОВАНА АКТИВАЦИЯ АЛЬФА БОЕГОЛОВОК...
    private static void playSound(ServerLevel world, BlockPos pos, BlockState pState) {
        // Защита от дурачков
        if ((pState.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _getip1 ? pState.getValue(_getip1) : -1) == 0) {
            ResourceLocation soundLocation = new ResourceLocation("jigsaw_pp:alpha_bomb");
            world.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(soundLocation), SoundSource.NEUTRAL, 2, 1); // Воспроизведение звука взрыва

        }
    }

    // #####################################
    // ### Отвечает за смену blockstate и включение 'взрыва'
    private static void active(BlockPos pPos, BlockState pState, Level pLevel) {
        // Если ultra == 0 то меняем на 1
        if ((pState.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _getip1 ? pState.getValue(_getip1) : -1) == 0) {
            playSound((ServerLevel) pLevel, pPos, pState);

            {
                int _value = 1;
                BlockState _bs = pLevel.getBlockState(pPos);
                if (_bs.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
                    pLevel.setBlock(pPos, _bs.setValue(_integerProp, _value), 3); // Устанавливаем состояние блока ultra на 1
            }
        }
        // Если ultra == 1 то запускаем 'взрыв'
        if ((pState.getBlock().getStateDefinition().getProperty("ultra") instanceof IntegerProperty _getip1 ? pState.getValue(_getip1) : -1) == 1) {
            ServerLevel serverLevel = (ServerLevel) pLevel;
            BlockPos pos = pPos;
            replaceBlocksInSphere((ServerLevel) pLevel, pPos); // Заменяем блоки в сфере
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag); // Передаем ховертекст от предмета
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true; // Пропускаем свет от неба сквозь блок
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player entity) {
        super.attack(pState, pLevel, pPos, entity); // Передаем урон от атаки по умолчанию


    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0; // Уровень светимости блока
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty(); // Визуальная форма блока
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return box(1, 0, 1, 15, 16, 15); // Форма для коллизий
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ULTRA); // Определение состояния блока
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return true; // Может ли соединяться с редстоуном
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder); // Получаем дроп блока по умолчанию
        if (!dropsOriginal.isEmpty())
            return dropsOriginal; // Возвращаем оригинальный дроп, если не пустой
        return Collections.singletonList(new ItemStack(this, 1)); // Возвращаем дроп нашего блока, если оригинальный пуст
    }

    // #####################################
    // ### Редстоун активация
    @Override
    public void neighborChanged(BlockState blockstate, net.minecraft.world.level.Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving); // Вызываем родительский метод
        if (world instanceof ServerLevel serverLevel && serverLevel.hasNeighborSignal(pos)) {
            //playSound(serverLevel, pos);
            //replaceBlocksInSphere(serverLevel, pos);
            active(pos, blockstate, serverLevel); // Активация блока при сигнале рядом


        }
    }
}
