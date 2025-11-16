package yuureiki.ender_smelting;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

public class ItemGroupMod {
    public static void init(){
        modifyItemGroups();
    }

    private static void modifyItemGroups(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(BlocksRegistry.ENDER_FURNACE_ITEM);
        });
    }
}
