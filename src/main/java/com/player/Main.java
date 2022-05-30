/**
 * @author 山东大学 德布罗煜
 */

package com.player;

import com.player.Player.MediaPlayer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.File;
import java.util.Enumeration;

public class Main {

    public static void main(String[] args) {
        /**
         * 创建并显示GUI。
         * 出于线程安全的考虑，这个方法在事件调用线程中调用。
         */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/simkai.ttf")).deriveFont(20f);
                    InitGlobalFont(customFont);  //统一设置字体
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    new MainFrame();
                    new MediaPlayer().init();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
     */
    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }

}
