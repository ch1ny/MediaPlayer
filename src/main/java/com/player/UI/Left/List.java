package com.player.UI.Left;

import com.player.UI.Style.ScrollBarStyle;

import javax.swing.*;
import java.awt.*;

public class List extends JPanel {
    private ItemList itemList;
    private JScrollPane jScrollPane;

    public List() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();//得到宽
        double height = screenSize.getHeight();//得到高
        setLayout(null);
        setBounds(0,0,(int) (width * 0.8 * 0.2),(int) (height * 0.8 * 0.85));
        ListTop top = new ListTop();
        top.setBounds(0,0,(int) (width * 0.8 * 0.2), 50);
        itemList = new ItemList();
        jScrollPane = new JScrollPane();
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane.getVerticalScrollBar().setUI(new ScrollBarStyle());
        jScrollPane.setBounds(0, 50, (int) (width * 0.8 * 0.2), (int) (height * 0.8 * 0.85));
        jScrollPane.setViewportView(itemList);
        jScrollPane.setBorder(null);
        add(top);
        add(jScrollPane);
    }
}
