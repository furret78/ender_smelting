package yuureiki.ender_smelting.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.SmokerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import yuureiki.ender_smelting.EnderSmelting;

public class EnderSmokerBlockEntity extends AbstractEnderFurnaceBlockEntity{
    public EnderSmokerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.ENDER_FURNACE_BLOCK, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + EnderSmelting.MOD_ID + ".ender_smoker");
    }

    @Override
    public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        var enderFurnaceInventory = getInterface(player).enderSmelting$getEnderSmokerInventory();
        return new SmokerScreenHandler(syncId, playerInventory, enderFurnaceInventory, enderFurnaceInventory.propertyDelegate);
    }
}
