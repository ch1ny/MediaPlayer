package com.player.Util;

import java.util.Locale;

public class VideoFormat {

    public static String[] permit = {"3g2","3gp","3gp2","3gpp","amv","asf","avi","bik","bin","divx","drc","dv","f4v","flv","gvi","gxf","iso","m1v","m2v","m2t","m2ts","m4v","mkv","mov","mp2","mp4","mp4v","mpe","mpeg","mpeg1","mpeg2","mpeg4","mpg","mpv2","mts","mxf","mxg","nsv","nuv","ogg","ogm","ogv","ps","rec","rm","rmvb","rpl","thp","tod","ts","tts","txd","vob","vro","webm","wm","wmv","wtv","xesc"};

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
