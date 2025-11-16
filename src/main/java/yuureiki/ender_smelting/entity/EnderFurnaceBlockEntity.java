package yuureiki.ender_smelting.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class EnderFurnaceBlockEntity extends AbstractEnderFurnaceBlockEntity{
    public EnderFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ENDER_FURNACE_BLOCK, pos, state);
    }
}
