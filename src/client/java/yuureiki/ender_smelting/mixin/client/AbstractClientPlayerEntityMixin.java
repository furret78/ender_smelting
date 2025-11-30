package yuureiki.ender_smelting.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.mixin.PlayerEntityMixin;
import yuureiki.ender_smelting.mixin.client.accessor.ClientChunkManagerAccessor;
import yuureiki.ender_smelting.mixin.client.accessor.WorldRendererInvoker;

import java.util.concurrent.atomic.AtomicReferenceArray;

@Mixin(AbstractClientPlayerEntity.class)
public class AbstractClientPlayerEntityMixin extends PlayerEntityMixin implements EnderFurnaceInterface {
    @Shadow
    @Final
    public ClientWorld clientWorld;

    /// Furnace model rendering is reloaded here.
    /// This is exclusively client-sided.
    @Override
    public void enderSmelting$reloadModel() {
        if (MinecraftClient.getInstance() == null || clientWorld == null) return;
        var client = MinecraftClient.getInstance();

        ClientChunkManager.ClientChunkMap chunkMap = ((ClientChunkManagerAccessor) clientWorld.getChunkManager()).getChunks();
        AtomicReferenceArray<WorldChunk> chunks = ((ClientChunkManagerAccessor.ClientChunkMapAccessor) (Object) chunkMap).getChunks();
        ChunkPos[] chunkPositions = new ChunkPos[chunks.length()];
        for (int i = 0; i < chunks.length(); i++) {
            WorldChunk chunk = chunks.get(i);
            if (chunk != null) chunkPositions[i] = chunk.getPos();
        }
        for (ChunkPos chunkPos : chunkPositions) {
            if (chunkPos != null) {
                for (int y = clientWorld.getBottomSectionCoord(); y < clientWorld.getTopSectionCoord(); y++) {
                    ((WorldRendererInvoker) client.worldRenderer).invokeScheduleChunkRender(chunkPos.x, y, chunkPos.z, true);
                }
            }
        }
    }
}
