package yuureiki.ender_smelting.inventory;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;

public class EnderSmokerInventory extends AbstractEnderFurnaceInventory{
    public EnderSmokerInventory() {
        super(RecipeType.SMOKING);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 2;
    }

    @Override
    public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }
}
