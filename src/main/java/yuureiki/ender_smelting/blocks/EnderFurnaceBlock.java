package yuureiki.ender_smelting.blocks;

import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.inventory.AbstractEnderFurnaceInventory;

public class EnderFurnaceBlock extends AbstractEnderFurnaceBlock {
    public EnderFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public AbstractEnderFurnaceInventory furnaceInventory(EnderFurnaceInterface enderFurnaceInterface) {
        return enderFurnaceInterface.enderSmelting$getEnderFurnaceInventory();
    }
}
