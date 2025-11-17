package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.TexturedModel;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(BlocksRegistry.ENDER_FURNACE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(BlocksRegistry.ENDER_BLAST_FURNACE);
        blockStateModelGenerator.registerNorthDefaultHorizontalRotation(BlocksRegistry.ENDER_SMOKER);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}
