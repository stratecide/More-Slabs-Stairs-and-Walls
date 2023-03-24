package games.twinhead.moreslabsstairsandwalls.block.terracotta;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;

public class GlazedTerracottaSlabBlock extends SlabBlock {

    public static final DirectionProperty FACING;

    public GlazedTerracottaSlabBlock(Settings settings) {
        super(settings);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @SuppressWarnings("deprecation")
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }

    static {
        FACING = HorizontalFacingBlock.FACING;
    }



}
