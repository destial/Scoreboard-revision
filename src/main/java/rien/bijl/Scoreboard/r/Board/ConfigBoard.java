package rien.bijl.Scoreboard.r.Board;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import rien.bijl.Scoreboard.r.Board.Animations.Row;
import rien.bijl.Scoreboard.r.Board.Implementations.WrapperBoard;
import rien.bijl.Scoreboard.r.Plugin.ConfigControl;
import rien.bijl.Scoreboard.r.Plugin.Utility.ScoreboardStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ConfigBoard extends BukkitRunnable {

    public String board;
    private Row title;
    private final ArrayList<Row> rows = new ArrayList<>();
    private final HashMap<Player, WrapperBoard> playerToBoard = new HashMap<>();
    private boolean enabled;

    public ConfigBoard(String board)
    {
        this.board = board;
        initTitle();
        initRows();
    }

    private void initTitle()
    {
        List<String> lines = Objects.requireNonNull(ConfigControl.get().gc("settings").getConfigurationSection(board + ".title")).getStringList("lines");
        int interval = ConfigControl.get().gc("settings").getInt(board + ".title.interval");
        title = new Row(ScoreboardStrings.makeColoredStringList(lines), interval);
    }

    private void initRows()
    {
        for (int i = 1; i < 200; i++) {
            ConfigurationSection section = ConfigControl.get().gc("settings").getConfigurationSection(board + ".rows." + i);
            if (section != null) {
                Row row = new Row(ScoreboardStrings.makeColoredStringList(section.getStringList("lines")), section.getInt("interval"));
                rows.add(row);
            }
        }
    }

    public void hookPlayer(Player player) {
        try {
            WrapperBoard wrapperBoard = new WrapperBoard("SCOREBOARD_DRIVER_V1");
            wrapperBoard.setLineCount(rows.size());
            wrapperBoard.setPlayer(player);
            playerToBoard.put(player, wrapperBoard);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unhookPlayer(Player player) {
        playerToBoard.remove(player);
        player.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
    }

    @Override
    public void run() {
        if (!enabled) return;
        title.update();
        for (Row row : rows) {
            row.update();
        }
        for (Map.Entry<Player, WrapperBoard> pwb : playerToBoard.entrySet()) {
            pwb.getValue().setTitle(ScoreboardStrings.placeholders(pwb.getKey(), title.getLine()));

            int count = 0;
            for (Row row: rows) {
                pwb.getValue().setLine(count++, ScoreboardStrings.placeholders(pwb.getKey(), row.getLine()));
            }
        }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }
}
