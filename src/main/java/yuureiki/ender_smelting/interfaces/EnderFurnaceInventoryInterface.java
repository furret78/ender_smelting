package yuureiki.ender_smelting.interfaces;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;

public interface EnderFurnaceInventoryInterface {
    ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory);
}
