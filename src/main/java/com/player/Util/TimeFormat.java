package com.player.Util;

public class TimeFormat {
    public static String timeFormat(int time) {
        String str;
        int minutes = time / 60;
        if (minutes < 10)
            str = "0" + minutes + ":";
        else
            str = minutes + ":";
        int seconds = time % 60;
        if (seconds < 10)
            str = str + "0" + seconds;
        else
            str = str + seconds;
        return str;
    }
}
