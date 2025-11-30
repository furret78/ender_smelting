package yuureiki.ender_smelting.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

public class BlockEntityRegistry {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, EnderSmelting.newIdentifier(path), blockEntityType);
    }

    public static final BlockEntityType<EnderFurnaceBlockEntity> ENDER_FURNACE_BLOCK = register(
            "ender_furnace_block", FabricBlockEntityTypeBuilder.create(
                    EnderFurnaceBlockEntity::new, BlocksRegistry.ENDER_FURNACE
            ).build()
    );

    //public static final BlockEntityType<EnderBlastFurnaceBlockEntity> ENDER_BLAST_FURNACE_BLOCK = register(
    //        "ender_blast_furnace_block", FabricBlockEntityTypeBuilder.create(
    //                EnderBlastFurnaceBlockEntity::new, BlocksRegistry.ENDER_BLAST_FURNACE
    //        ).build()
    //);

    //public static final BlockEntityType<EnderSmokerBlockEntity> ENDER_SMOKER_BLOCK = register(
    //        "ender_smoker_block", FabricBlockEntityTypeBuilder.create(
    //                EnderSmokerBlockEntity::new, BlocksRegistry.ENDER_SMOKER
    //        ).build()
    //);

    public static void init(){}
}
