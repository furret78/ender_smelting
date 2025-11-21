package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider {
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        addToBlockTag(BlockTags.PICKAXE_MINEABLE);
        addToBlockTag(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    private void addToBlockTag(TagKey<Block> blockTag){
        getOrCreateTagBuilder(blockTag)
                .add(BlocksRegistry.ENDER_SMOKER)
                .add(BlocksRegistry.ENDER_FURNACE)
                .add(BlocksRegistry.ENDER_BLAST_FURNACE)
                .setReplace(false);
    }
}
