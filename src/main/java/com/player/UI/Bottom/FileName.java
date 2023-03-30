package com.player.UI.Bottom;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FileName extends JPanel {

    private final JLabel title;

    public FileName() {
        setLayout(null);
        setBackground(new Color(0,0,0));
        title = new JLabel("", JLabel.CENTER);
        title.setVisible(true);
        Dimension size = getSize();
        title.setBounds(0, 0, size.width, (int) (size.getHeight() * 0.75));
        title.setForeground(new Color(255,255,255));
        add(title);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                title.setSize(size.width, (int) (size.getHeight() * 0.75));
            }
        });
    }

    public void setTitle(String name) {
        this.title.setText(name);
    }

}
