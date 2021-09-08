package rien.bijl.Scoreboard.r;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ScoreboardRevision sr;
    @Override
    public void onEnable() {
        sr = new ScoreboardRevision(this, "board");
        sr.enable();
    }

    @Override
    public void onDisable() {
        sr = null;
    }
}
