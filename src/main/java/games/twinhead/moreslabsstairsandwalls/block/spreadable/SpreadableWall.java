package games.twinhead.moreslabsstairsandwalls.block.spreadable;

import games.twinhead.moreslabsstairsandwalls.block.ModBlocks;
import games.twinhead.moreslabsstairsandwalls.block.dirt.DirtWall;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public class SpreadableWall extends DirtWall implements Waterloggable {

    public static final BooleanProperty SNOWY;

    public SpreadableWall(Settings settings) {
        super(settings);
    }


    private static boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isIn(BlockTags.WALLS)){
            return false;
        } else if (!blockState.isOpaqueFullCube(world, pos)) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            if(state.isOf(ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.WALL))) {
                world.setBlockState(pos, ModBlocks.DIRT.getBlock(ModBlocks.BlockType.WALL).getStateWithProperties(world.getBlockState(pos)), Block.NOTIFY_LISTENERS);
            }else if(state.isOf(ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.WALL))) {
                world.setBlockState(pos, ModBlocks.DIRT.getBlock(ModBlocks.BlockType.WALL).getStateWithProperties(world.getBlockState(pos)), Block.NOTIFY_LISTENERS);
            }else if(state.isOf(ModBlocks.WARPED_NYLIUM.getBlock(ModBlocks.BlockType.WALL))) {
                //world.setBlockState(pos, ModBlocks.NETHERRACK.getBlock(ModBlocks.BlockType.WALL).getStateWithProperties(world.getBlockState(pos)), Block.NOTIFY_LISTENERS);
            }else if(state.isOf(ModBlocks.CRIMSON_NYLIUM.getBlock(ModBlocks.BlockType.WALL))) {
                //world.setBlockState(pos, ModBlocks.NETHERRACK.getBlock(ModBlocks.BlockType.WALL).getStateWithProperties(world.getBlockState(pos)), Block.NOTIFY_LISTENERS);
            } else {
                world.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
        } else {
            if (world.getLightLevel(pos.up()) >= 9) {
                BlockState blockState = this.getDefaultState();

                for(int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (canSpread(blockState, world, blockPos)) {
                        if(world.getBlockState(blockPos).isOf(Blocks.DIRT))
                            if(state.isOf(ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.WALL))){
                                world.setBlockState(blockPos, Blocks.GRASS.getDefaultState().with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                            } else if(state.isOf(ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.WALL))){
                                world.setBlockState(blockPos, Blocks.MYCELIUM.getDefaultState().with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                            }

                        if(world.getBlockState(blockPos).isOf(ModBlocks.DIRT.getBlock(ModBlocks.BlockType.SLAB)))
                            if(state.isOf(ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.WALL))){
                                world.setBlockState(blockPos, ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.SLAB).getDefaultState().with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)).with(SlabBlock.WATERLOGGED, world.getBlockState(blockPos).get(SlabBlock.WATERLOGGED)).with(SlabBlock.TYPE, world.getBlockState(blockPos).get(SlabBlock.TYPE)));
                            } else if(state.isOf(ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.WALL))){
                                world.setBlockState(blockPos, ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.SLAB).getDefaultState().with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)).with(SlabBlock.WATERLOGGED, world.getBlockState(blockPos).get(SlabBlock.WATERLOGGED)).with(SlabBlock.TYPE, world.getBlockState(blockPos).get(SlabBlock.TYPE)));
                            }

                        if(world.getBlockState(blockPos).isOf(ModBlocks.DIRT.getBlock(ModBlocks.BlockType.STAIRS)))
                            if(state.isOf(ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.WALL))){
                                world.setBlockState(blockPos, ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.STAIRS).getStateWithProperties(world.getBlockState(blockPos)));
                            } else if(state.isOf(ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.WALL))) {
                                world.setBlockState(blockPos, ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.STAIRS).getStateWithProperties(world.getBlockState(blockPos)));
                            }

                        if(world.getBlockState(blockPos).isOf(ModBlocks.DIRT.getBlock(ModBlocks.BlockType.WALL))  && !world.getBlockState(blockPos.up()).isIn(BlockTags.WALLS))
                            if(state.isOf(ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.WALL))){
                                world.setBlockState(blockPos, ModBlocks.GRASS_BLOCK.getBlock(ModBlocks.BlockType.WALL).getStateWithProperties(world.getBlockState(blockPos)));
                            } else if(state.isOf(ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.WALL))) {
                                world.setBlockState(blockPos, ModBlocks.MYCELIUM.getBlock(ModBlocks.BlockType.WALL).getStateWithProperties(world.getBlockState(blockPos)));
                            }
                    }
                }
            }

        }
    }

    private static boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    static {
        SNOWY = Properties.SNOWY;
    }
}
