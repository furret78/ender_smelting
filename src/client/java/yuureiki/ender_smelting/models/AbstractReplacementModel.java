package yuureiki.ender_smelting.models;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class AbstractReplacementModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private final AbstractEnderFurnaceBlockModel on_model;
    private final AbstractEnderFurnaceBlockModel off_model;
    private final Supplier<Boolean> conditionGetter;

    public AbstractReplacementModel(AbstractEnderFurnaceBlockModel onModel, AbstractEnderFurnaceBlockModel offModel, Supplier<Boolean> conditionGetter) {
        on_model = onModel;
        off_model = offModel;
        this.conditionGetter = conditionGetter;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return conditionGetter.get() ? on_model.useAmbientOcclusion() : off_model.useAmbientOcclusion();
    }

    @Override
    public boolean hasDepth() {
        return conditionGetter.get() ? on_model.hasDepth() : off_model.hasDepth();
    }

    @Override
    public boolean isSideLit() {
        return conditionGetter.get() ? on_model.isSideLit() : off_model.isSideLit();
    }

    @Override
    public boolean isBuiltin() {
        return conditionGetter.get() ? on_model.isBuiltin() : off_model.isBuiltin();
    }

    @Override
    public Sprite getParticleSprite() {
        return conditionGetter.get() ? on_model.getParticleSprite() : off_model.getParticleSprite();
    }

    @Override
    public ModelTransformation getTransformation() {
        return conditionGetter.get() ? on_model.getTransformation() : off_model.getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return conditionGetter.get() ? on_model.getOverrides() : off_model.getOverrides();
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

    }

    @Override
    public @Nullable BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return conditionGetter.get() ? on_model.bake(baker, textureGetter, rotationContainer, modelId) : off_model.bake(baker, textureGetter, rotationContainer, modelId);
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (conditionGetter.get()) {
            on_model.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        } else {
            off_model.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        if (conditionGetter.get()) {
            on_model.emitItemQuads(stack, randomSupplier, context);
        } else {
            off_model.emitItemQuads(stack, randomSupplier, context);
        }
    }
}
