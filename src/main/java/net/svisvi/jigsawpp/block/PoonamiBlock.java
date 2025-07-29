package net.svisvi.jigsawpp.block;


import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.block.init.ModBlocks;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.fluid.init.ModFluids;
import net.svisvi.jigsawpp.fluid.ponos.PonosFluid;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PoonamiBlock extends Block {

    public PoonamiBlock() {
        super(Properties.of().sound(SoundType.METAL).strength(-1f, 10f).noOcclusion().replaceable().noCollission());
        this.registerDefaultState(this.stateDefinition.any());
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().getItem() != this.asItem();
    }


    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void appendHoverText(ItemStack itemstack, BlockGetter world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.poonami.desc"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderLayer() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.POONAMI.get(), renderType -> renderType == RenderType.cutout());
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.isEmptyBlock(BlockPos.containing(x + 1, y, z))) {
            world.setBlock(BlockPos.containing(x + 1, y, z), ModBlocks.POONAMI.get().defaultBlockState(), 3);
        }
        if (world.isEmptyBlock(BlockPos.containing(x - 1, y, z))) {
            world.setBlock(BlockPos.containing(x - 1, y, z), ModBlocks.POONAMI.get().defaultBlockState(), 3);
        }
        if (world.isEmptyBlock(BlockPos.containing(x, y, z + 1))) {
            world.setBlock(BlockPos.containing(x, y, z + 1), ModBlocks.POONAMI.get().defaultBlockState(), 3);
        }
        if (world.isEmptyBlock(BlockPos.containing(x, y, z - 1))) {
            world.setBlock(BlockPos.containing(x, y, z - 1), ModBlocks.POONAMI.get().defaultBlockState(), 3);
        }
        world.setBlock(BlockPos.containing(x, y, z), ModBlocks.PONOS_FLUID_BLOCK.get().defaultBlockState(), 3);

        world.scheduleTick(pos, this, 10);
    }

}
