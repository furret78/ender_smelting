package yuureiki.ender_smelting.models.plugin;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.models.ender_furnace.EnderFurnaceReplacementModel;

public class EnderSmeltingModelPlugin implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            if (enderFurnaceCheck(context.id(), EnderSmelting.MOD_ID + ":ender_furnace") && !enderFurnaceCheck(context.id(), "#inventory")) {
                return new EnderFurnaceReplacementModel();
            } else {
                return original;
            }
        });
    }

    private boolean enderFurnaceCheck(Identifier input, String keyword){
        return String.valueOf(input).toLowerCase().contains(keyword.toLowerCase());
    }
}
