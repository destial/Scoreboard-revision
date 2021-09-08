package rien.bijl.Scoreboard.r.Plugin.Utility;

public class LineLimits {
    private static int lineLimit = 0;

    public static int getLineLimit() {
        if (lineLimit != 0) {
            return lineLimit;
        }
        int minor = ServerVersion.minor();
        lineLimit = 16;
        if (minor >= 13) {
            lineLimit = 64;
        }
        return lineLimit;
    }
}
