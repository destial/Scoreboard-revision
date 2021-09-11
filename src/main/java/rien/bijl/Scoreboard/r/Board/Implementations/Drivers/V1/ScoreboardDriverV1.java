package rien.bijl.Scoreboard.r.Board.Implementations.Drivers.V1;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import rien.bijl.Scoreboard.r.Board.Implementations.IBoard;
import rien.bijl.Scoreboard.r.Plugin.Session;
import rien.bijl.Scoreboard.r.Plugin.Utility.LineLimits;
import rien.bijl.Scoreboard.r.Plugin.Utility.ScoreboardStrings;

import java.util.HashMap;
import java.util.Objects;

public class ScoreboardDriverV1 implements IBoard {

    private Player player;
    private Scoreboard board;
    private Scoreboard previousBoard;
    private Objective objective;
    private int lines;
    private final HashMap<Integer, String> cache = new HashMap<>();

    @Override
    public void setPlayer(Player player) {
        this.player = player;
        board = Objects.requireNonNull(Session.getSession().plugin.getServer().getScoreboardManager()).getNewScoreboard();
        objective = board.registerNewObjective("sb1", "sb2");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("");
        createTeams();
        setBoard();
        LineLimits.getLineLimit();
    }

    @Override
    public void setTitle(String title) {
        if (title == null) {
            title = "";
        }

        if (title.length() > LineLimits.getLineLimit() * 2) {
            title = title.substring(0, LineLimits.getLineLimit() * 2);
        }

        objective.setDisplayName(title);
    }

    @Override
    public void setLine(int line, String content) {
        if (content == null) content = "";
        if (!shouldUpdate(line, content)) return;

        Team team = board.getTeam(line + "");
        String[] split = split(content);

        assert team != null;

        team.setPrefix(split[0]);
        team.setSuffix(split[1]);
    }

    private String[] split(String line) {
        if (line.length() < LineLimits.getLineLimit()) return new String[]{line, ""};

        String prefix = line.substring(0, LineLimits.getLineLimit());
        String suffix = line.substring(LineLimits.getLineLimit());

        if (prefix.endsWith("§")) { // Check if we accidentally cut off a color
            prefix = ScoreboardStrings.removeLastCharacter(prefix);
            suffix = "§" + suffix;
        } else if (prefix.contains("§")) { // Are there any colors we need to continue?
            suffix = ChatColor.getLastColors(prefix) + suffix;
        } else { // Just make sure the team color doesn't mess up anything
            suffix = "§f" + suffix;
        }

        if (suffix.length() > LineLimits.getLineLimit()) {
            suffix = suffix.substring(0, LineLimits.getLineLimit());
        }

        return new String[]{prefix, suffix};
    }

    private boolean shouldUpdate(int line, String content) {
        if (!cache.containsKey(line)) {
            cache.put(line, content);
            return true;
        }
        if (cache.get(line).equals(content)) return false;
        cache.put(line, content);
        return true;
    }

    @Override
    public void setLineCount(int lines) {
        this.lines = lines;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Scoreboard getBukkitScoreboard() {
        return board;
    }

    @Override
    public Scoreboard getPreviousBukkitScoreboard() {
        return previousBoard;
    }

    private void createTeams() {
        int score = lines;
        for (int i = 0; i < lines; i++) {
            Team t = board.registerNewTeam(i + "");
            t.addEntry(ChatColor.values()[i] + "");
            objective.getScore(ChatColor.values()[i] + "").setScore(score--);
        }
    }

    private void setBoard() {
        previousBoard = player.getScoreboard();
        player.setScoreboard(board);
    }
}
