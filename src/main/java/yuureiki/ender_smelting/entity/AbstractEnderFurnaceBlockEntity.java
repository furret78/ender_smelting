package yuureiki.ender_smelting.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;

public abstract class AbstractEnderFurnaceBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public AbstractEnderFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container." + EnderSmelting.MOD_ID + ".ender_furnace");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return getScreenHandler(syncId, playerInventory, player);
    }

    public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player){
        var enderFurnaceInventory = getInterface(player).enderSmelting$getEnderFurnaceInventory();
        return new FurnaceScreenHandler(syncId, playerInventory, enderFurnaceInventory, enderFurnaceInventory.propertyDelegate);
    }

    protected EnderFurnaceInterface getInterface(PlayerEntity player){
        return (EnderFurnaceInterface) player;
    }
}