package yuureiki.ender_smelting.models.ender_furnace;

import net.minecraft.client.MinecraftClient;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.models.AbstractReplacementModel;

public class EnderFurnaceReplacementModel extends AbstractReplacementModel {
    public EnderFurnaceReplacementModel() {
        super(new EnderFurnaceBlockModelOn(), new EnderFurnaceBlockModelOff(), () -> {
            var clientGame = MinecraftClient.getInstance();
            if (clientGame == null) return false;
            var clientPlayer = clientGame.player;
            if (clientPlayer == null) return false;
            var inventory = ((EnderFurnaceInterface) clientPlayer).enderSmelting$getEnderFurnaceInventory();
            if (inventory == null) return false;
            return inventory.isBurning();
        });
    }
}