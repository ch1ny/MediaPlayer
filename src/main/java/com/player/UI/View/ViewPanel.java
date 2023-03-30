package com.player.UI.View;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ViewPanel extends JPanel {

    private static CoverView coverView;

    public ViewPanel() {
        setLayout(null);
        setVisible(true);
        coverView = new CoverView();
        add(coverView);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                coverView.setSize(e.getComponent().getSize());
            }
        });
    }

    public CoverView getCover() {
        return coverView;
    }
}
