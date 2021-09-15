package com.player.UI.Component;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Volume extends JPanel {

    private JLabel image, volume;
    private Timer timer;

    public Volume() {
        setLayout(null);
        setBackground(new Color(100,100,100));
        image = new JLabel();
        add(image);
        volume = new JLabel("", JLabel.CENTER);
        volume.setForeground(new Color(255,255,255));
        volume.setVisible(true);
        volume.setFont(new Font(Font.DIALOG, 0, 25));
        add(volume);
        setVisible(false);
    }

    public void setVolume(int vol) {
        Volume.super.remove(image);
        int x = (int) (MainFrame.getMedia().getWidth() * 0.45 + MainFrame.getMedia().getX());
        int y = (int) (MainFrame.getMedia().getHeight() * 0.4);
        int width = (int) (MainFrame.getMedia().getWidth() * 0.1);
        int height = (int) (MainFrame.getMedia().getHeight() * 0.2);
        setBounds(x, y, width, height);
        if (vol <= 0) {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/mute.png").getImage().getScaledInstance((int) (width * 0.3),(int) (width * 0.3), Image.SCALE_SMOOTH)));
        } else if (vol < 70) {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/volume0.png").getImage().getScaledInstance((int) (width * 0.3),(int) (width * 0.3), Image.SCALE_SMOOTH)));
        } else if (vol < 100) {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/volume1.png").getImage().getScaledInstance((int) (width * 0.3),(int) (width * 0.3), Image.SCALE_SMOOTH)));
        } else {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/volume2.png").getImage().getScaledInstance((int) (width * 0.3),(int) (width * 0.3), Image.SCALE_SMOOTH)));
        }
        image.setBounds(0,-(int) (width * 0.2), width, height);
        Volume.super.repaint();
        Volume.super.add(image);
        volume.setBounds(0, (int) (width * 0.25), width, height);
        volume.setText(String.valueOf(vol));
        Volume.super.setVisible(true);
        ActionListener hide = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Volume.super.setVisible(false);
            }
        };
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(500, hide);
        timer.start();
    }

}
