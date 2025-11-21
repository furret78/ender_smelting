package yuureiki.ender_smelting;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import yuureiki.ender_smelting.datagen.*;

public class EnderSmeltingDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(EnglishLanguageProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(BlockTagGenerator::new);
	}
}
