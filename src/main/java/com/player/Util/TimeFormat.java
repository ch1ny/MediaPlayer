package com.player.Util;

public class TimeFormat {
    public static String timeFormat(int time) {
        String string;
        int minutes = time / 60;
        if (minutes < 10)
            string = "0" + minutes + ":";
        else
            string = "" + minutes + ":";
        int seconds = time % 60;
        if (seconds < 10)
            string = string + "0" + seconds;
        else
            string = string + seconds;
        return string;
    }
}
