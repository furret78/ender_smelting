package yuureiki.ender_smelting.screenhandlers;

import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers<T extends ScreenHandler> implements ToggleableFeature {
    private final FeatureSet requiredFeatures;
    private final ScreenHandlerType.Factory<T> factory;

    @Override
    public FeatureSet getRequiredFeatures() {
        return null;
    }

    public ModScreenHandlers(ScreenHandlerType.Factory<T> factory, FeatureSet requiredFeatures) {
        this.factory = factory;
        this.requiredFeatures = requiredFeatures;
    }
}
