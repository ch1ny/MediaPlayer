package com.player.Util;

import java.util.Locale;

public class AudioFormat {

    public static String[] permit = {"3ga","669","a52","acc","ac3","adt","adts","aif","aiff","amr","aob","ape","awb","caf","dts","flac","it","kar","m4a","m4b","m4p","m5p","mid","mka","mlp","mod","mpa","mp1","mp2","mp3","mpc","mpga","mus","oga","ogg","oma","opus","qcp","ra","rmi","s3m","sid","spx","thd","tta","voc","vqf","w64","wav","wma","wv","xa","xm"};
    public static String[] hasCover = {"mp3","ogg","wav","flac"};

    public static boolean endWith(String str) {
        for (String format: permit) {
            if (str.toLowerCase(Locale.ROOT).endsWith("." + format)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCover(String str) {
        for (String type: hasCover) {
            if (str.toLowerCase(Locale.ROOT).endsWith("." + type)) {
                return true;
            }
        }
        return false;
    }

}
