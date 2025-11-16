package yuureiki.ender_smelting.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.inventory.AbstractEnderFurnaceInventory;

public abstract class AbstractEnderFurnaceBlock extends Block {
    private static final Text CONTAINER_NAME = Text.translatable("container." + EnderSmelting.MOD_ID + ".ender_furnace");

    public AbstractEnderFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        AbstractEnderFurnaceInventory enderFurnaceInventory = furnaceInventory((EnderFurnaceInterface) player);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (enderFurnaceInventory != null && blockEntity instanceof EnderChestBlockEntity) {
            BlockPos blockPos = pos.up();
            if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                return ActionResult.success(world.isClient);
            } else if (world.isClient) {
                return ActionResult.SUCCESS;
            } else {
                enderFurnaceInventory.setActiveBlock(world, pos);
                player.openHandledScreen(
                        new SimpleNamedScreenHandlerFactory(
                                (syncId, inventory, playerx) -> GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, enderFurnaceInventory), CONTAINER_NAME
                        )
                );
                player.incrementStat(Stats.INTERACT_WITH_FURNACE);
                return ActionResult.CONSUME;
            }
        } else {
            return ActionResult.success(world.isClient);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        // Might cause compatibility issues at some point.
        // The block currently has exactly 1 state so any change should mean that it's broken.
        world.getPlayers().forEach((playerEntity -> {
            AbstractEnderFurnaceInventory inventory = furnaceInventory((EnderFurnaceInterface) playerEntity);
            inventory.currentBlockBroken(world, pos);
        }));
    }

    /// Override using the arg provided to get a different inventory.
    public AbstractEnderFurnaceInventory furnaceInventory(EnderFurnaceInterface enderFurnaceInterface){
        return enderFurnaceInterface.enderSmelting$getEnderFurnaceInventory();
    }
}
