package yuureiki.ender_smelting.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.Nullable;

public class EnderFurnaceInventory extends AbstractEnderFurnaceInventory {
    public EnderFurnaceInventory() {
        super(RecipeType.SMELTING);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }
}
