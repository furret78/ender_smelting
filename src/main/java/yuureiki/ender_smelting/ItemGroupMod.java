package yuureiki.ender_smelting;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

public class ItemGroupMod {
    public static void init(){
        modifyItemGroups();
    }

    private static void modifyItemGroups(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.addAfter(Items.BLAST_FURNACE, BlocksRegistry.ENDER_BLAST_FURNACE_ITEM);
            content.addAfter(Items.BLAST_FURNACE, BlocksRegistry.ENDER_SMOKER_ITEM);
            content.addAfter(Items.BLAST_FURNACE, BlocksRegistry.ENDER_FURNACE_ITEM);
        });
    }
}
