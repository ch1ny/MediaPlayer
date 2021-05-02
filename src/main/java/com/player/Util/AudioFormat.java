package com.player.Util;

import java.util.Locale;

public class AudioFormat {

    public static String[] permit = {"mp3","ogg","wav","flac"};

    public static boolean endWith(String str) {
        for (String format:
             permit) {
            if (str.toLowerCase(Locale.ROOT).endsWith("." + format)) {
                return true;
            }
        }
        return false;
    }

}
