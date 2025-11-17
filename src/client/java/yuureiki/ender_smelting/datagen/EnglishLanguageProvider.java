package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import yuureiki.ender_smelting.EnderSmelting;

import java.util.List;

public class EnglishLanguageProvider extends FabricLanguageProvider {
    private static final List<String> TRANSLATION_TABLE = List.of(
            "block." + EnderSmelting.MOD_ID + ".ender_furnace", "Ender Furnace",
            "container." + EnderSmelting.MOD_ID + ".ender_furnace", "Ender Furnace",
            "block." + EnderSmelting.MOD_ID + ".ender_smoker", "Ender Smoker",
            "container." + EnderSmelting.MOD_ID + ".ender_smoker", "Ender Smoker",
            "block." + EnderSmelting.MOD_ID + ".ender_blast_furnace", "Ender Blast Furnace",
            "container." + EnderSmelting.MOD_ID + ".ender_blast_furnace", "Ender Blast Furnace"
    );

    public EnglishLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (int i = 0; i < TRANSLATION_TABLE.size(); i = i + 2){
            translationBuilder.add(TRANSLATION_TABLE.get(i), TRANSLATION_TABLE.get(i + 1));
        }
    }
}
