package com.player.UI.View;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class CoverView extends JPanel {
    private static BufferedImage image;

    public CoverView() {
        setLayout(null);
        setBounds(0,0,(int) (MainFrame.getFrame().getWidth() * 0.8), (int) (MainFrame.getFrame().getHeight() * 0.85));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        if (image != null) {
            g.drawImage(image, (this.getWidth() - image.getWidth()) / 2, (this.getHeight() - image.getHeight()) / 2, null);
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
