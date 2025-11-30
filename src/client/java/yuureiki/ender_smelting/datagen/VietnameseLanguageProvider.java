package yuureiki.ender_smelting.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import yuureiki.ender_smelting.EnderSmelting;

import java.util.List;

public class VietnameseLanguageProvider extends FabricLanguageProvider {
    private static final List<String> TRANSLATION_TABLE = List.of(
            "block." + EnderSmelting.MOD_ID + ".ender_furnace", "Lò nung Ender",
            "container." + EnderSmelting.MOD_ID + ".ender_furnace", "Lò nung Ender",
            "modmenu.nameTranslation." + EnderSmelting.MOD_ID, "Ender Smelting",
            "modmenu.descriptionTranslation." + EnderSmelting.MOD_ID, "Mod này thêm lò nung Ender. Chế tạo lò này tốn kha khá hơn so với lò nung thường, nhưng đặt đâu cũng xài được và nung nhanh gấp đôi so với lò nung thường.",
            "modmenu.summaryTranslation." + EnderSmelting.MOD_ID, "Thêm lò nung Ender."
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
