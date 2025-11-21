package com.endercrest.voidspawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeleportManager {
    private static TeleportManager instance = new TeleportManager();
    private VoidSpawn plugin;
    private List<UUID> playerToggle;

    public static TeleportManager getInstance() {
        return instance;
    }

    public void setUp(VoidSpawn plugin) {
        this.plugin = plugin;
        playerToggle = new ArrayList<>();
    }

    /**
     * Teleport the player to the selected world.
     *
     * @param player    The Player that will be teleported.
     * @param worldName Name of the world to get coordinates from.
     * @return Whether the teleport was successful.
     */
    public TeleportResult teleportSpawn(Player player, String worldName) {
        double x = ConfigManager.getInstance().getDouble(worldName + ".spawn.x", 0);
        double y = ConfigManager.getInstance().getDouble(worldName + ".spawn.y", 0);
        double z = ConfigManager.getInstance().getDouble(worldName + ".spawn.z", 0);
        float pitch = ConfigManager.getInstance().getFloat(worldName + ".spawn.pitch", 0);
        float yaw = ConfigManager.getInstance().getFloat(worldName + ".spawn.yaw", 0);
        World world = plugin.getServer().getWorld(ConfigManager.getInstance().getString(worldName + ".spawn.world", "world"));
        if (world == null) {
            return TeleportResult.INVALID_WORLD;
        }
        Location location = new Location(world, x, y, z, yaw, pitch);

        player.teleportAsync(location).thenAccept(result -> {
            if (result)
                player.setFallDistance(0f);
        });

        return TeleportResult.SUCCESS;
    }

    /**
     * Checks whether the player is toggled disabled.
     *
     * @param uuid The players UUID.
     * @return Returns true if player has teleportation toggled.
     */
    public boolean isPlayerToggled(UUID uuid) {
        return playerToggle.contains(uuid);
    }

    /**
     * Toggle the current status of the player.
     *
     * @param uuid The uuid of the player.
     * @return Returns true if the player has just been toggled to disable teleportation.
     */
    public boolean togglePlayer(UUID uuid) {
        if (playerToggle.contains(uuid)) {
            playerToggle.remove(uuid);
            return false;
        } else {
            playerToggle.add(uuid);
            return true;
        }
    }

    /**
     * Disables toggle and re-enables the player to be teleported.
     *
     * @param uuid The UUID of the player.
     */
    public void disableToggle(UUID uuid) {
        playerToggle.remove(uuid);
    }

    /**
     * Enables toggle and disables the player to be teleported.
     *
     * @param uuid The UUID of the player.
     */
    public void enableToggle(UUID uuid) {
        playerToggle.add(uuid);
    }

    /**
     * Remove the player from maps to clean up resources.
     *
     * @param uuid Player UUID
     */
    public void removePlayer(UUID uuid) {
        playerToggle.remove(uuid);
    }

}