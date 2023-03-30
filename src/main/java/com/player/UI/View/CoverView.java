package com.player.UI.View;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CoverView extends JPanel {
    private static BufferedImage image;

    public CoverView() {
        setLayout(null);
        setBounds(0,0,(int) (MainFrame.getFrame().getWidth() * 0.8), (int) (MainFrame.getFrame().getHeight() * 0.8));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth(), height = getHeight();

        g.setColor(Color.black);
        g.fillRect(0,0, width, height);
        if (image != null) {
            g.drawImage(image, (width - image.getWidth()) / 2, (height - image.getHeight()) / 2, null);
        }
    }

    public void setCover(BufferedImage bufferedImage) {
        image = bufferedImage;
        paintComponent(getGraphics());
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
    }

}
