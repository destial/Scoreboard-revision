package rien.bijl.Scoreboard.r.Plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import rien.bijl.Scoreboard.r.Board.BoardPlayer;

import java.util.ArrayList;

public class WorldManager extends BukkitRunnable {

    private static ArrayList<String> disabled_worlds;
    private static boolean whitelist_mode;

    public WorldManager() {
        disabled_worlds = new ArrayList<>();
        whitelist_mode = ConfigControl.get().gc("settings").getBoolean("whitelist-world", false);
        for (String world : ConfigControl.get().gc("settings").getStringList("disabled-worlds")) {
            disabled_worlds.add(world.toLowerCase().trim());
        }
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            BoardPlayer player = BoardPlayer.getBoardPlayer(p);
            if (disabled_worlds.contains(p.getWorld().getName().toLowerCase().trim())) {
                if (whitelist_mode) {
                    if (player.worldLock) player.unlock();
                    continue;
                }
                if (!player.worldLock) player.lock();
            } else {
                if (whitelist_mode) {
                    if (!player.worldLock) player.lock();
                } else if (player.worldLock) player.unlock();
            }
        }
    }
}
