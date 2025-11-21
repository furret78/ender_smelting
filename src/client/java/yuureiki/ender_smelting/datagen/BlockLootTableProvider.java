package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    public BlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addEnderDrop(BlocksRegistry.ENDER_FURNACE);
        addEnderDrop(BlocksRegistry.ENDER_SMOKER);
        addEnderDrop(BlocksRegistry.ENDER_BLAST_FURNACE);
    }

    private void addEnderDrop(Block block){
        addDrop(block, drops(block, Blocks.OBSIDIAN, ConstantLootNumberProvider.create(7)));
    }
}
