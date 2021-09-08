package rien.bijl.Scoreboard.r.Plugin.Commands;

import org.bukkit.entity.Player;
import rien.bijl.Scoreboard.r.Board.BoardPlayer;
import rien.bijl.Scoreboard.r.Plugin.Utility.i18n;

public class Off {
    public Off(Player player) {
        if (!player.hasPermission("scoreboard.toggle")) {
            player.sendMessage(i18n.get("commands.lackspermission"));
            return;
        }
        if (BoardPlayer.getBoardPlayer(player).worldLock) {
            player.sendMessage(i18n.get("commands.worldlock"));
            return;
        }
        BoardPlayer.getBoardPlayer(player).setEnabled(false);
        player.sendMessage(i18n.get("commands.toggleoff"));
    }
}
