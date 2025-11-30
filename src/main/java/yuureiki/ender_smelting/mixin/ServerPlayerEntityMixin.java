package yuureiki.ender_smelting.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin extends PlayerEntityMixin implements EnderFurnaceInterface {
    @Inject(method = "copyFrom", at = @At("RETURN"))
    private void enderSmelting$copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci){
        var enderSmelting$oldPlayer = (EnderFurnaceInterface) oldPlayer;
        this.enderSmelting$enderFurnaceInventory = enderSmelting$oldPlayer.enderSmelting$getEnderFurnaceInventory();
        //this.enderSmelting$enderBlastFurnaceInventory = enderSmelting$oldPlayer.enderSmelting$getEnderBlastFurnaceInventory();
        //this.enderSmelting$enderSmokerInventory = enderSmelting$oldPlayer.enderSmelting$getEnderSmokerInventory();
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void enderSmelting$tick(CallbackInfo ci){
        this.enderSmelting$enderFurnaceInventory.tick();
        //this.enderSmelting$enderBlastFurnaceInventory.tick();
        //this.enderSmelting$enderSmokerInventory.tick();
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void enderSmelting$constructorEnd(MinecraftServer server, ServerWorld world, GameProfile profile, CallbackInfo ci){
        this.enderSmelting$enderFurnaceInventory.setCurrentWorld(world);
        //this.enderSmelting$enderBlastFurnaceInventory.setCurrentWorld(world);
        //this.enderSmelting$enderSmokerInventory.setCurrentWorld(world);
    }

    @Override
    public void enderSmelting$reloadModel() {

    }
}
