package yuureiki.ender_smelting.models;

import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.EnderSmelting;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractEnderFurnaceBlockModel implements UnbakedModel, BakedModel, FabricBakedModel {
    private static final SpriteIdentifier[] SPRITE_IDS = getSpriteIds();
    private Sprite[] sprites = new Sprite[SPRITE_IDS.length];

    protected static final int SPRITE_FRONT = 0;
    protected static final int SPRITE_FRONT_ON = 1;
    protected static final int SPRITE_SIDE = 2;
    protected static final int SPRITE_TOP = 3;
    protected static final int SPRITE_BOTTOM = 4;

    protected Mesh mesh;

    private static SpriteIdentifier getSpriteIdentifier(String path){
        return new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EnderSmelting.newIdentifier(path));
    }

    public static SpriteIdentifier[] getSpriteIds(){
        return new SpriteIdentifier[]{
                getSpriteIdentifier("block/ender_furnace_front"),
                getSpriteIdentifier("block/ender_furnace_front_on"),
                getSpriteIdentifier("block/ender_furnace_side"),
                getSpriteIdentifier("block/ender_furnace_top")
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
        for (int i = 0; i < SPRITE_IDS.length; ++i){
            sprites[i] = textureGetter.apply(SPRITE_IDS[i]);
        }

        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder meshBuilder = renderer.meshBuilder();
        QuadEmitter quadEmitter = meshBuilder.getEmitter();

        for (Direction direction : Direction.values()){
            int spriteIdx;



        }

        return null;
    }
}
