package yuureiki.ender_smelting.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.entity.EnderFurnaceBlockEntity;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.inventory.AbstractEnderFurnaceInventory;

public abstract class AbstractEnderFurnaceBlock extends BlockWithEntity {
    public AbstractEnderFurnaceBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(Properties.HORIZONTAL_FACING);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (int i = 0; i < 3; i++) {
            int j = random.nextInt(2) * 2 - 1;
            int k = random.nextInt(2) * 2 - 1;
            double d = pos.getX() + 0.5 + 0.25 * j;
            double e = pos.getY() + random.nextFloat();
            double f = pos.getZ() + 0.5 + 0.25 * k;
            double g = random.nextFloat() * j;
            double h = (random.nextFloat() - 0.5) * 0.125;
            double l = random.nextFloat() * k;
            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, l);
        }
    }
}
