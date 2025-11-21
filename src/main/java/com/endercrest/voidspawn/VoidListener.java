package com.endercrest.voidspawn;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles detecting if a player has entered the void & calls
 * all the necessary executors.
 */
public class VoidListener implements Listener {

    private final VoidSpawn plugin;
    private final Map<UUID, ScheduledTask> taskMap = new HashMap<>();

    public VoidListener(VoidSpawn plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        VoidCheckTask task = new VoidCheckTask(event.getPlayer().getUniqueId());
        ScheduledTask scheduledTask = Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, task, 20, 20); // Runs every second (20 ticks)
        taskMap.put(event.getPlayer().getUniqueId(), scheduledTask);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        ScheduledTask task = taskMap.remove(playerId);
        if (task != null)
            task.cancel();
    }

}
