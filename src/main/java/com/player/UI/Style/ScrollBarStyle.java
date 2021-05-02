package com.player.UI.Style;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class ScrollBarStyle extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        trackColor = new Color(255,255,255);
        setThumbBounds(0, 0, 10, 10);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        c.setPreferredSize(new Dimension(5, 0));
        return super.getPreferredSize(c);
    }

    @Override
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = null;
        if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL)
            gp = new GradientPaint(0, 0, new Color(255,255,255), trackBounds.width, 0, new Color(255,255,255));
        g2.setPaint(gp);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);//填充Track
        if (trackHighlight == BasicScrollBarUI.DECREASE_HIGHLIGHT)
            this.paintDecreaseHighlight(g);
        if (trackHighlight == BasicScrollBarUI.INCREASE_HIGHLIGHT)
            this.paintIncreaseHighlight(g);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);// 这句一定要加上，不然拖动就失效了
        g.setColor(new Color(155,155,155));// 设置把手颜色
        g.drawRoundRect(0, 0, 5, thumbBounds.height, 5, 5);
        // 消除锯齿
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);
        // 半透明
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRoundRect(0, 0, 40, thumbBounds.height, 5, 5);
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        return button;

    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorder(null);
        return button;
    }

}