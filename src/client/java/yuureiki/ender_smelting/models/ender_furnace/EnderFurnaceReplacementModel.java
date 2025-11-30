package yuureiki.ender_smelting.models.ender_furnace;

import net.minecraft.client.MinecraftClient;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.models.AbstractReplacementModel;

public class EnderFurnaceReplacementModel extends AbstractReplacementModel {
    public EnderFurnaceReplacementModel() {
        super(new EnderFurnaceBlockModelOn(), new EnderFurnaceBlockModelOff(), () -> {
            var clientGame = MinecraftClient.getInstance();
            if (clientGame == null)
            {
                EnderSmelting.LOGGER.info("NONE OF THIS SHIT IS VALID!!!");
                return false;
            }
            EnderSmelting.LOGGER.info("Client is valid.");

            var clientPlayer = clientGame.player;
            if (clientPlayer == null) return false;
            EnderSmelting.LOGGER.info("Player is valid.");

            var inventory = ((EnderFurnaceInterface) clientPlayer).enderSmelting$getEnderFurnaceInventory();
            if (inventory == null) return false;
            EnderSmelting.LOGGER.info("Inventory is valid.");

            return inventory.isBurning();
        });
    }
}