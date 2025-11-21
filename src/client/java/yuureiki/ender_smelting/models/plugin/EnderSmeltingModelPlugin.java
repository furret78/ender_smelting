package yuureiki.ender_smelting.models.plugin;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.models.EnderFurnaceBlockModel;

public class EnderSmeltingModelPlugin implements ModelLoadingPlugin {
    public static final ModelIdentifier ENDER_FURNACE_MODEL_ON = new ModelIdentifier(EnderSmelting.MOD_ID, "ender_furnace", "");

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        // We want to add our model when the models are loaded
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            if (context.id().equals(ENDER_FURNACE_MODEL_ON)) {
                return new EnderFurnaceBlockModel();
            } else {
                return original;
            }
        });
    }
}
