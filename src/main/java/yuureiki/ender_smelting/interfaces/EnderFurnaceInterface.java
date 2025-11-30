package yuureiki.ender_smelting.interfaces;

import yuureiki.ender_smelting.inventory.EnderBlastFurnaceInventory;
import yuureiki.ender_smelting.inventory.EnderFurnaceInventory;
import yuureiki.ender_smelting.inventory.EnderSmokerInventory;

public interface EnderFurnaceInterface {
    EnderFurnaceInventory enderSmelting$getEnderFurnaceInventory();
    EnderBlastFurnaceInventory enderSmelting$getEnderBlastFurnaceInventory();
    EnderSmokerInventory enderSmelting$getEnderSmokerInventory();
    void enderSmelting$reloadModel();
}
