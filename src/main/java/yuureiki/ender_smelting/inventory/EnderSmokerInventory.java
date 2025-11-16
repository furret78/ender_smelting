package yuureiki.ender_smelting.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public class EnderSmokerInventory extends AbstractEnderFurnaceInventory{
    public EnderSmokerInventory() {
        super(RecipeType.SMOKING);
    }

    @Override
    public ScreenHandler getScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }
}
