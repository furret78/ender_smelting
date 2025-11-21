package yuureiki.ender_smelting.blocks;

import net.minecraft.entity.player.PlayerEntity;

public class EnderFurnaceBlock extends AbstractEnderFurnaceBlock {
    private static <T extends PlayerEntity> T getClientPlayer(){
        return null;
    }

    public EnderFurnaceBlock(Settings settings) {
        super(settings);
    }
}
