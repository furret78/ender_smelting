package yuureiki.ender_smelting.models.plugin;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.models.ender_furnace.EnderFurnaceReplacementModel;

public class EnderSmeltingModelPlugin implements ModelLoadingPlugin {
    public static final ModelIdentifier ENDER_FURNACE_MODEL = getModelId("ender_furnace", "");

    public static ModelIdentifier getModelId(String path, String variant){
        return new ModelIdentifier(new Identifier(EnderSmelting.MOD_ID, path), variant);
    }

    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        pluginContext.modifyModelOnLoad().register((original, context) -> {
            if (context.id().equals(ENDER_FURNACE_MODEL)) {
                return new EnderFurnaceReplacementModel();
            } else {
                return original;
            }
        });
    }
}
