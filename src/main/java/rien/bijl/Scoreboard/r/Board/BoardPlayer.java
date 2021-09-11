package rien.bijl.Scoreboard.r.Board;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;

public class BoardPlayer {

    private final Player player;
    private ConfigBoard configBoard;
    private boolean enabled = true;
    public boolean worldLock = false;

    private BoardPlayer(Player player)
    {
        this.player = player;
        BoardPlayer.map.put(player, this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            configBoard.unhookPlayer(player);
        } else {
            configBoard.hookPlayer(player);
        }
    }

    public void lock() {
        configBoard.unhookPlayer(player);
        worldLock = true;
    }

    public void unlock() {
        worldLock = false;
        if (isEnabled()) {
            configBoard.hookPlayer(player);
        }
    }

    public void attachConfigBoard(ConfigBoard board) {
        if (configBoard != null) {
            configBoard.unhookPlayer(player);
        }
        configBoard = board;
        configBoard.hookPlayer(player);
    }

    public void kill() {
        configBoard.unhookPlayer(player);
        map.remove(player);
    }

    /**
     * Will always return a BoardPlayer, whether existing or newly created
     * @param player The base bukkit player
     * @return a BoardPlayer
     */
    public static @NotNull BoardPlayer getBoardPlayer(@NotNull Player player) {
        if (map.containsKey(player)) return map.get(player);
        return new BoardPlayer(player);
    }

    public static Collection<BoardPlayer> allBoardPlayers() {
        return map.values();
    }

    private static final HashMap<Player, BoardPlayer> map = new HashMap<>();

}
