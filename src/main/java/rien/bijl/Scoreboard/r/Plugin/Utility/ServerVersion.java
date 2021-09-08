package rien.bijl.Scoreboard.r.Plugin.Utility;

import rien.bijl.Scoreboard.r.Plugin.Session;

public class ServerVersion {

    private static int major = -1;
    private static int minor = -1;

    public static int major() {
        if (major != -1) {
           return major;
        }
        String pack = Session.getSession().plugin.getServer().getClass().getPackage().getName();
        String[] version = pack.substring(pack.lastIndexOf('.') + 1).substring(1).split("_");
        major = Integer.parseInt(version[0]);
        return major;
    }

    public static int minor() {
        if (minor != -1) {
            return minor;
        }
        String pack = Session.getSession().plugin.getServer().getClass().getPackage().getName();
        String[] version = pack.substring(pack.lastIndexOf('.') + 1).substring(1).split("_");
        minor = Integer.parseInt(version[1]);
        return minor;
    }
}
