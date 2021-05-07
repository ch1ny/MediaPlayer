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
        setBounds((int) (MainFrame.getFrame().getWidth() * 0.55),
                (int) (MainFrame.getFrame().getHeight() * 0.35),
                (int) (MainFrame.getFrame().getWidth() * 0.1),
                (int) (MainFrame.getFrame().getHeight() * 0.15)
        );
        setBackground(new Color(100,100,100));
        image = new JLabel();
        add(image);
        volume = new JLabel("", JLabel.CENTER);
        volume.setForeground(new Color(255,255,255));
        volume.setVisible(true);
        volume.setFont(new Font(Font.DIALOG, 0, 25));
        volume.setBounds(0, (int) (MainFrame.getFrame().getWidth() * 0.025), (int) (MainFrame.getFrame().getWidth() * 0.1), (int) (MainFrame.getFrame().getHeight() * 0.15));
        add(volume);
        setVisible(false);
    }

    public void setVolume(int vol) {
        Volume.super.remove(image);
        if (vol <= 0) {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/mute.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getWidth() * 0.03),(int) (MainFrame.getFrame().getWidth() * 0.03), Image.SCALE_SMOOTH)));
            image.setBounds(0,-(int) (MainFrame.getFrame().getWidth() * 0.01),(int) (MainFrame.getFrame().getWidth() * 0.1),(int) (MainFrame.getFrame().getHeight() * 0.15));
        } else if (vol < 30) {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/volume0.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getWidth() * 0.03),(int) (MainFrame.getFrame().getWidth() * 0.03), Image.SCALE_SMOOTH)));
            image.setBounds(0,-(int) (MainFrame.getFrame().getWidth() * 0.01),(int) (MainFrame.getFrame().getWidth() * 0.1),(int) (MainFrame.getFrame().getHeight() * 0.15));
        } else if (vol < 70) {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/volume1.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getWidth() * 0.03),(int) (MainFrame.getFrame().getWidth() * 0.03), Image.SCALE_SMOOTH)));
            image.setBounds(0,-(int) (MainFrame.getFrame().getWidth() * 0.01),(int) (MainFrame.getFrame().getWidth() * 0.1),(int) (MainFrame.getFrame().getHeight() * 0.15));
        } else {
            image = new JLabel(new ImageIcon(new ImageIcon("res/icon/volume2.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getWidth() * 0.03),(int) (MainFrame.getFrame().getWidth() * 0.03), Image.SCALE_SMOOTH)));
            image.setBounds(0,-(int) (MainFrame.getFrame().getWidth() * 0.01),(int) (MainFrame.getFrame().getWidth() * 0.1),(int) (MainFrame.getFrame().getHeight() * 0.15));
        }
        Volume.super.repaint();
        Volume.super.add(image);
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
