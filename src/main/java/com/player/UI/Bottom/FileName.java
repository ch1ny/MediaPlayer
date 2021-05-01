package com.player.UI.Bottom;

import javax.swing.*;
import java.awt.*;

public class FileName extends JPanel {

    private JLabel title;

    public FileName() {
        setLayout(null);
        setBackground(new Color(0,0,0));
        title = new JLabel("", JLabel.CENTER);
        title.setVisible(true);
        title.setBounds(0, 0, 170, 100);
        title.setForeground(new Color(255,255,255));
        add(title);
    }

    public void setTitle(String name) {
        this.title.setText(name);
    }

}
