package yuureiki.ender_smelting;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

public class EnderSmelting implements ModInitializer {
	public static final String MOD_ID = "ender_smelting";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
        BlocksRegistry.init();
        ItemGroupMod.init();
	}

    public static Identifier newIdentifier(String path){
        return Identifier.of(MOD_ID, path);
    }
}