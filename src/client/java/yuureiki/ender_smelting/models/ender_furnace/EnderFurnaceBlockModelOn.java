package yuureiki.ender_smelting.models.ender_furnace;

import net.minecraft.client.render.model.json.ModelOverrideList;
import yuureiki.ender_smelting.models.AbstractEnderFurnaceBlockModel;

public class EnderFurnaceBlockModelOn extends AbstractEnderFurnaceBlockModel {
    @Override
    public String getFurnaceFrontTextureName() {
        return "front_on";
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return null;
    }
}
