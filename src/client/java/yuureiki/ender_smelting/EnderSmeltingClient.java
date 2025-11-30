package yuureiki.ender_smelting;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import yuureiki.ender_smelting.models.plugin.EnderSmeltingModelPlugin;

public class EnderSmeltingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        //ModelLoadingPlugin.register(new EnderSmeltingModelPlugin());
	}
}