package com.player.UI.Left;

import com.player.MainFrame;
import com.player.UI.Style.ScrollBarStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class List extends JPanel {
    private ItemList itemList;
    private JScrollPane jScrollPane;

    public List() {
        setLayout(null);
        setBounds(0,0,(int) (MainFrame.getFrame().getWidth() * 0.2),(int) (MainFrame.getFrame().getHeight() * 0.8));
        itemList = new ItemList();
        jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.getVerticalScrollBar().setUI(new ScrollBarStyle());
        jScrollPane.setBounds(0, 0, (int) (MainFrame.getFrame().getWidth() * 0.2), (int) (MainFrame.getFrame().getHeight() * 0.8));
        jScrollPane.setViewportView(itemList);
        add(jScrollPane);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                jScrollPane.setSize(size);
                itemList.resetWidth(size.width);
                MainFrame.getFrame().revalidate();
            }
        });
    }
}
