package yuureiki.ender_smelting;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import yuureiki.ender_smelting.inventory.EnderFurnaceInventory;
import yuureiki.ender_smelting.player_data.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class StateSaverAndLoader extends PersistentState {
    public EnderFurnaceInventory enderFurnaceInventory;

    public HashMap<UUID, PlayerData> players = new HashMap<>();

    public static PlayerData getPlayerState(LivingEntity player){
        StateSaverAndLoader serverState = getServerState(player.getWorld().getServer());
        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
        return playerState;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server){
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(
                StateSaverAndLoader::createFromNbt,
                StateSaverAndLoader::new,
                EnderSmelting.MOD_ID
        );

        state.markDirty();

        return state;
    }

    public static StateSaverAndLoader createFromNbt(NbtCompound tag){
        StateSaverAndLoader state = new StateSaverAndLoader();
        //load inventory NBT here

        NbtCompound playersNbt = tag.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            PlayerData playerData = new PlayerData();
            if (playersNbt.getCompound("key").contains("EnderFurnaceItems", NbtElement.LIST_TYPE)){
                playerData.enderFurnaceInventory.readNbtList(playersNbt.getList("EnderFurnaceItems", NbtElement.COMPOUND_TYPE));
            }
        });

        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();
        players.forEach(((uuid, playerData) -> {
            NbtCompound playerNbt = new NbtCompound();
            playerNbt.put("EnderFurnaceItems", playerData.enderFurnaceInventory.toNbtList());
            playersNbt.put(uuid.toString(), playerNbt);
        }));
        nbt.put("players", playersNbt);

        return nbt;
    }
}
