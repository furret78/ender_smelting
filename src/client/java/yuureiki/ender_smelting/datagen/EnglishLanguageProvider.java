package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import yuureiki.ender_smelting.EnderSmelting;

import java.util.List;

public class EnglishLanguageProvider extends FabricLanguageProvider {
    private static final List<String> TRANSLATION_TABLE = List.of(
            "block." + EnderSmelting.MOD_ID + ".ender_furnace", "Ender Furnace",
            "container." + EnderSmelting.MOD_ID + ".ender_furnace", "Ender Furnace",
            "modmenu.nameTranslation." + EnderSmelting.MOD_ID, "Ender Smelting",
            "modmenu.summaryTranslation." + EnderSmelting.MOD_ID, "Adds an Ender Furnace."
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
