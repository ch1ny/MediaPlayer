package com.player.UI.FullScreen;

import com.player.MainFrame;
import com.player.Player.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @Author SDU德布罗煜
 * @Date 2021/7/2 16:14
 * @Description
 * @Version 1.0
 */

public class Controller extends JPanel {

    private JButton play, next, prev, forward, backward;
    private JLabel current;
    private JLabel total;
    private JSlider playProcessSlider;

    public Controller() {
        setBackground(new Color(125,125,125));
        setLayout(null);
        play = new functionButton(new ImageIcon(new ImageIcon("res/icon/play.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getHeight() * 0.1 - 40),(int) (MainFrame.getFrame().getHeight() * 0.1 - 40), Image.SCALE_SMOOTH)));
        play.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getHeight() * 0.1 - 40)) / 2), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.25), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40));
        add(play);
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (play.isEnabled()) {
                    MediaPlayer player = MediaPlayer.getInstance();
                    if (!player.isPlaying()) { // 没在播放 -> 播放
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getHeight() * 0.1 - 40),(int) (MainFrame.getFrame().getHeight() * 0.1 - 40), Image.SCALE_SMOOTH)));
                        try {
                            player.go_on();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else { // 播放中 -> 暂停
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/play.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getHeight() * 0.1 - 40),(int) (MainFrame.getFrame().getHeight() * 0.1 - 40), Image.SCALE_SMOOTH)));
                        player.pause();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });
    }

    private class functionButton extends JButton {
        public functionButton(Icon icon) {
            super("", icon);
            setBackground(null);
            setBorder(null);
            setVisible(true);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

}
