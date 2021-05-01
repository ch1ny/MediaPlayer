package com.player.UI.View;

import javax.swing.*;

public class ViewPanel extends JPanel {
    private static CoverView coverView;

    public ViewPanel() {
        setLayout(null);
        setVisible(true);
        coverView = new CoverView();
        add(coverView);
    }

    public CoverView getCover() {
        return coverView;
    }
}
