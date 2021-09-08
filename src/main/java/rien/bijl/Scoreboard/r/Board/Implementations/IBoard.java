package rien.bijl.Scoreboard.r.Board.Implementations;

import org.bukkit.entity.Player;

public interface IBoard {
    /**
     * Set the player this board should publish to
     * @param player The player this scoreboard is assigned to
     */
    void setPlayer(Player player);

    /**
     * Set the title of the board
     * @param title What should the title display
     */
    void setTitle(String title);

    /**
     * Set the line of the board
     * @param line The line number (1-16)
     * @param content What should the line display
     */
    void setLine(int line, String content);

    /**
     * Set the amount of lines of the scoreboard
     * @param lines The amount of the lines the scoreboard should have
     */
    void setLineCount(int lines);

    /**
     * Get the player that this scoreboard is hooked onto
     * @return The player of this hooked scoreboard
     */
    Player getPlayer();
}
