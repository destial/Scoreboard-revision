package rien.bijl.Scoreboard.r.Board.Animations;

import java.util.List;

public class Row {

    private final List<String> lines;
    private final int interval;
    private int count = 0;
    private int current = 1;
    private boolean is_static = false;

    private String line;

    public Row(List<String> lines, int interval) {
        this.lines = lines;
        this.interval = interval;

        if (lines.size() == 1) {
            is_static = true;
        } else {
            for (String line: lines) {
                if (line.contains("%")) {
                    is_static = false;
                    break;
                }
            }
        }

        if (lines.size() < 1) {
            line = "";
        } else {
            line = lines.get(0);
        }
    }

    public String getLine()
    {
        return line;
    }

    public void update() {
        if (is_static) return;
        if (count >= interval) {
            count = 0;
            ++current;
            if (current >= lines.size()) current = 0;
            line = lines.get(current);
        } else {
            ++count;
        }

    }

}
