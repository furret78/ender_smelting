package yuureiki.ender_smelting.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInterface;
import yuureiki.ender_smelting.inventory.EnderBlastFurnaceInventory;
import yuureiki.ender_smelting.inventory.EnderFurnaceInventory;
import yuureiki.ender_smelting.inventory.EnderSmokerInventory;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements EnderFurnaceInterface {
    @Unique
    protected EnderFurnaceInventory enderSmelting$enderFurnaceInventory = new EnderFurnaceInventory();
    @Unique
    protected EnderBlastFurnaceInventory enderSmelting$enderBlastFurnaceInventory = new EnderBlastFurnaceInventory();
    @Unique
    protected EnderSmokerInventory enderSmelting$enderSmokerInventory = new EnderSmokerInventory();

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void enderSmelting$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        if (nbt.contains("EnderFurnaceItems", NbtElement.LIST_TYPE)){
            this.enderSmelting$enderFurnaceInventory.readNbtList(nbt.getList("EnderFurnaceItems", NbtElement.COMPOUND_TYPE));
        }
        if (nbt.contains("EnderBlastFurnaceItems", NbtElement.LIST_TYPE)){
            this.enderSmelting$enderBlastFurnaceInventory.readNbtList(nbt.getList("EnderBlastFurnaceItems", NbtElement.COMPOUND_TYPE));
        }
        if (nbt.contains("EnderSmokerItems", NbtElement.LIST_TYPE)){
            this.enderSmelting$enderSmokerInventory.readNbtList(nbt.getList("EnderSmokerItems", NbtElement.COMPOUND_TYPE));
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void enderSmelting$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.put("EnderFurnaceItems", this.enderSmelting$enderFurnaceInventory.toNbtList());
        nbt.put("EnderBlastFurnaceItems", this.enderSmelting$enderBlastFurnaceInventory.toNbtList());
        nbt.put("EnderSmokerItems", this.enderSmelting$enderSmokerInventory.toNbtList());
    }

    // When calling these methods, do ((EnderFurnaceInterface)instance).methodname
    @Override
    public EnderFurnaceInventory enderSmelting$getEnderFurnaceInventory() {
        EnderSmelting.LOGGER.info("Retrieved the inventory for the Ender Furnace!");
        return this.enderSmelting$enderFurnaceInventory;
    }

    @Override
    public EnderBlastFurnaceInventory enderSmelting$getEnderBlastFurnaceInventory() {
        EnderSmelting.LOGGER.info("Retrieved the inventory for the Ender Blast Furnace!");
        return this.enderSmelting$enderBlastFurnaceInventory;
    }

    @Override
    public EnderSmokerInventory enderSmelting$getEnderSmokerInventory() {
        EnderSmelting.LOGGER.info("Retrieved the inventory for the Ender Smoker!");
        return this.enderSmelting$enderSmokerInventory;
    }
}
