package yuureiki.ender_smelting.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import yuureiki.ender_smelting.EnderSmelting;

public class BlocksRegistry {
    public static final Block ENDER_FURNACE = register("ender_furnace", new EnderFurnace(FabricBlockSettings.create()));
    public static final BlockItem ENDER_FURNACE_ITEM = registerItem("ender_furnace", new BlockItem(ENDER_FURNACE, new FabricItemSettings()));

    private static <T extends Block> T register(String path, T block){
        return Registry.register(Registries.BLOCK, EnderSmelting.newIdentifier(path), block);
    }

    private static <K extends BlockItem> K registerItem(String path, K item){
        return Registry.register(Registries.ITEM, EnderSmelting.newIdentifier(path), item);
    }

    public static void init(){}
}
