package com.player.Util;

public class JudgeMoV {

    public static final int MUSIC = 0;
    public static final int VIDEO = 1;

    public static int judgeMoV(String str) {
        if (AudioFormat.endWith(str))
            return MUSIC;
        else
            return VIDEO;
    }

}
