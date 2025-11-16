package yuureiki.ender_smelting.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.entity.EnderFurnaceBlockEntity;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.inventory.AbstractEnderFurnaceInventory;

public abstract class AbstractEnderFurnaceBlock extends BlockWithEntity {
    public AbstractEnderFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory namedScreenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (namedScreenHandlerFactory != null) {
                player.openHandledScreen(namedScreenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // Might cause compatibility issues at some point.
        // The block currently has exactly 1 state so any change should mean that it's broken.
        world.getPlayers().forEach((playerEntity -> {
            AbstractEnderFurnaceInventory inventory = GetFurnaceInventory((EnderFurnaceInterface) playerEntity);
            inventory.currentBlockBroken(world, pos);
        }));
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    /// Override using the arg provided to get a different inventory.
    public AbstractEnderFurnaceInventory GetFurnaceInventory(EnderFurnaceInterface enderFurnaceInterface){
        return enderFurnaceInterface.enderSmelting$getEnderFurnaceInventory();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnderFurnaceBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return false;
    }
}
