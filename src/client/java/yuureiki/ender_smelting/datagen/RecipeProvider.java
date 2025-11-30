package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import yuureiki.ender_smelting.blocks.BlocksRegistry;

import java.util.function.Consumer;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        generateEnderRecipe(BlocksRegistry.ENDER_FURNACE_ITEM, Items.FURNACE, consumer);
        //generateEnderRecipe(BlocksRegistry.ENDER_FURNACE_ITEM, Items.SMOKER, consumer);
        //generateEnderRecipe(BlocksRegistry.ENDER_FURNACE_ITEM, Items.BLAST_FURNACE, consumer);
        //generateEnderRecipe(BlocksRegistry.ENDER_SMOKER_ITEM, Items.SMOKER, consumer);
        //generateEnderRecipe(BlocksRegistry.ENDER_BLAST_FURNACE_ITEM, Items.BLAST_FURNACE, consumer);
    }

    private static void generateEnderRecipe(Item result, Item special_input, Consumer<RecipeJsonProvider> consumer){
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, result)
                .pattern("DDD")
                .pattern("DBD")
                .pattern("DCD")
                .input('D', Items.OBSIDIAN)
                .input('B', Items.ENDER_EYE)
                .input('C', special_input)
                //.input('D', Items.END_STONE)
                //.criterion(FabricRecipeProvider.hasItem(Items.OBSIDIAN), FabricRecipeProvider.conditionsFromItem(Items.OBSIDIAN))
                .criterion(FabricRecipeProvider.hasItem(Items.ENDER_EYE), FabricRecipeProvider.conditionsFromItem(Items.ENDER_EYE))
                .criterion(FabricRecipeProvider.hasItem(special_input), FabricRecipeProvider.conditionsFromItem(special_input))
                .criterion(FabricRecipeProvider.hasItem(Items.END_STONE), FabricRecipeProvider.conditionsFromItem(Items.END_STONE))
                .offerTo(consumer);
    }
}
