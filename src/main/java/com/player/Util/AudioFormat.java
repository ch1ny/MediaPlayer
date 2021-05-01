package com.player.Util;

import java.util.Locale;

public class AudioFormat {

    public enum PermittedFormat {MP3, FLAC, OGG, APE, WAV};

    public static boolean endWith(String str) {
        for (PermittedFormat format:
             PermittedFormat.values()) {
            if (str.toUpperCase(Locale.ROOT).endsWith("." + format)) {
                return true;
            }
        }
        return false;
    }

}
