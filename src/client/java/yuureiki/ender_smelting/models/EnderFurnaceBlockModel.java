package yuureiki.ender_smelting.models;

import net.minecraft.client.render.model.json.ModelOverrideList;

public class EnderFurnaceBlockModel extends AbstractEnderFurnaceBlockModel{
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
