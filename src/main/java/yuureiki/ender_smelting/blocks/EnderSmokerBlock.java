package yuureiki.ender_smelting.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.entity.EnderSmokerBlockEntity;

public class EnderSmokerBlock extends AbstractEnderFurnaceBlock{
    public EnderSmokerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnderSmokerBlockEntity(pos, state);
    }
}
