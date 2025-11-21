package com.endercrest.voidspawn;

import com.endercrest.voidspawn.detectors.Detector;
import com.endercrest.voidspawn.modes.Mode;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.function.Consumer;

public class VoidCheckTask implements Consumer<ScheduledTask> {

    private final UUID playerId;

    public VoidCheckTask(UUID playerId) {
        this.playerId = playerId;
    }

    @Override
    public void accept(ScheduledTask task) {
        Player player = Bukkit.getPlayer(playerId);
        if (player == null) {
            task.cancel();
            return;
        }

        String worldName = player.getWorld().getName();
        if (!ConfigManager.getInstance().isModeSet(worldName)) return;

        Mode mode = ModeManager.getInstance().getWorldMode(worldName);
        Detector detector = DetectorManager.getInstance().getWorldDetector(worldName);
        if (!detector.isDetected(mode, player, player.getWorld())) return;

        mode.onActivate(player, worldName);
    }

}
