package games.twinhead.moreslabsstairsandwalls.block.log;

import games.twinhead.moreslabsstairsandwalls.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StrippableStairsBlock extends StairsBlock {

    private ModBlocks strippedBlock;

    public StrippableStairsBlock(ModBlocks strippedBlock, Settings settings) {
        super(Blocks.OAK_LOG.getDefaultState(), settings);
        this.strippedBlock = strippedBlock;
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!(player.getMainHandStack().getItem() instanceof AxeItem)) return ActionResult.PASS;

        if (!world.isClient) {
            world.setBlockState(pos, strippedBlock.getStairsBlock().getStateWithProperties(state));
            player.getMainHandStack().damage(1, player, p -> p .sendToolBreakStatus(hand));
        } else {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }

        return ActionResult.SUCCESS;
    }
}
