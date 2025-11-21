package yuureiki.ender_smelting.models;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.EnderSmelting;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractEnderFurnaceBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final SpriteIdentifier[] SPRITE_IDS = getSpriteIds();
    private Sprite[] sprites = new Sprite[SPRITE_IDS.length];

    protected static final int SPRITE_FRONT = 0;
    protected static final int SPRITE_SIDE = 1;
    protected static final int SPRITE_TOP = 2;
    protected static final int SPRITE_BOTTOM = 3;

    protected Mesh mesh;

    private static SpriteIdentifier getSpriteIdentifier(String path) {
        return new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EnderSmelting.newIdentifier(path));
    }

    public static String getBlockName() {
        return "ender_furnace";
    }

    public static String getFurnaceFrontTextureName() {
        return "front";
    }

    public static String getFurnaceBottomTextureName() {
        return "top";
    }

    public static SpriteIdentifier[] getSpriteIds() {
        return new SpriteIdentifier[]{
                getSpriteIdentifier("block/" + getBlockName() + "_" + getFurnaceFrontTextureName()),
                getSpriteIdentifier("block/" + getBlockName() + "_side"),
                getSpriteIdentifier("block/" + getBlockName() + "_top"),
                getSpriteIdentifier("block/" + getBlockName() + "_" + getFurnaceBottomTextureName())
        };
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
        for (int i = 0; i < SPRITE_IDS.length; ++i) {
            sprites[i] = textureGetter.apply(SPRITE_IDS[i]);
        }

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder meshBuilder = renderer.meshBuilder();
        QuadEmitter quadEmitter = meshBuilder.getEmitter();

        for (Direction direction : Direction.values()) {
            int spriteIdx = switch (direction) {
                case UP -> SPRITE_TOP;
                case DOWN -> SPRITE_BOTTOM;
                case NORTH -> SPRITE_FRONT;
                default -> SPRITE_SIDE;
            };
            quadEmitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f);
            quadEmitter.spriteBake(sprites[spriteIdx], MutableQuadView.BAKE_LOCK_UV);
            quadEmitter.color(-1, -1, -1, -1);
            quadEmitter.emit();
        }
        mesh = meshBuilder.build();
        return this;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public Sprite getParticleSprite() {
        return sprites[SPRITE_TOP];
    }

    @Override
    public ModelTransformation getTransformation() {
        return null;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        mesh.outputTo(context.getEmitter());
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

    }
}
