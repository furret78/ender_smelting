package yuureiki.ender_smelting.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.entity.EnderBlastFurnaceBlockEntity;

public class EnderBlastFurnaceBlock extends AbstractEnderFurnaceBlock{
    public EnderBlastFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnderBlastFurnaceBlockEntity(pos, state);
    }
}
