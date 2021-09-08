package rien.bijl.Scoreboard.r.Plugin.Commands;

import org.bukkit.entity.Player;
import rien.bijl.Scoreboard.r.Plugin.ConfigControl;
import rien.bijl.Scoreboard.r.Plugin.Utility.i18n;

import java.util.List;
import java.util.Objects;

public class Help {
    public Help(Player player) {
        for (String line : Objects.requireNonNull(ConfigControl.get().gc("language").getConfigurationSection("commands")).getStringList("help")) {
            player.sendMessage(i18n.parse(line));
        }
    }
}
