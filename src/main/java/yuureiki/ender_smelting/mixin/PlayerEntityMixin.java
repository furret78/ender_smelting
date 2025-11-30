package yuureiki.ender_smelting.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.inventory.EnderFurnaceInventory;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements EnderFurnaceInterface {
    @Unique
    protected EnderFurnaceInventory enderSmelting$enderFurnaceInventory = new EnderFurnaceInventory();

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void enderSmelting$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("EnderFurnaceItems", NbtElement.LIST_TYPE)){
            this.enderSmelting$enderFurnaceInventory.readNbtList(nbt.getList("EnderFurnaceItems", NbtElement.COMPOUND_TYPE));
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void enderSmelting$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.put("EnderFurnaceItems", this.enderSmelting$enderFurnaceInventory.toNbtList());
    }

    @Override
    public EnderFurnaceInventory enderSmelting$getEnderFurnaceInventory() {
        return this.enderSmelting$enderFurnaceInventory;
    }

    @Override
    public void enderSmelting$reloadModel() {

    }
}
