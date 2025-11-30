package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import yuureiki.ender_smelting.EnderSmelting;

import java.util.List;

public class VietnameseLanguageProvider extends FabricLanguageProvider {
    private static final List<String> TRANSLATION_TABLE = List.of(
            "block." + EnderSmelting.MOD_ID + ".ender_furnace", "Lò nung Ender",
            "container." + EnderSmelting.MOD_ID + ".ender_furnace", "Lò nung Ender"
    );

    public VietnameseLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "vi_vn");
    }

    @Override
    public void generateTranslations(FabricLanguageProvider.TranslationBuilder translationBuilder) {
        for (int i = 0; i < TRANSLATION_TABLE.size(); i = i + 2){
            translationBuilder.add(TRANSLATION_TABLE.get(i), TRANSLATION_TABLE.get(i + 1));
        }
    }
}
