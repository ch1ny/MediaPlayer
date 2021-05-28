package com.player.UI.Component;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/7 17:09
 * @Version 1.0
 */

public class Rate extends JPanel {

    private JLabel rate;
    private Timer timer;

    public Rate() {
        setLayout(null);
        setBackground(new Color(100,100,100));
        rate = new JLabel("", JLabel.CENTER);
        rate.setForeground(new Color(255,255,255));
        rate.setVisible(true);
        rate.setFont(new Font(Font.DIALOG, 0, 25));
        add(rate);
        setVisible(false);
    }

    public void setRate(float rate) {
        this.rate.setText(rate + "x");
        int x = (int) (MainFrame.getVideo().getWidth() * 0.45 + MainFrame.getVideo().getX());
        int y = (int) (MainFrame.getVideo().getHeight() * 0.45);
        int width = (int) (MainFrame.getVideo().getWidth() * 0.1);
        int height = (int) (MainFrame.getVideo().getHeight() * 0.1);
        setBounds(x, y, width, height);
        this.rate.setBounds(0, 0, width, height);
        setVisible(true);
        ActionListener hide = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(500, hide);
        timer.start();
    }

}
