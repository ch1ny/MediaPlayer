package com.player.UI.Bottom;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Bottom extends JPanel {
    private static FileName fileName;
    private static Function function;

    public Bottom() {
        setBackground(new Color(125,125,125));
        setLayout(null);
        fileName = new FileName();
        fileName.setBounds(0,0,(int) (MainFrame.getFrame().getWidth() * 0.2),(int) (MainFrame.getFrame().getHeight() * 0.2));
        fileName.setVisible(true);
        add(fileName);
        function = new Function();
        function.setBounds((int) (MainFrame.getFrame().getWidth() * 0.2),0,(int) (MainFrame.getFrame().getWidth() * 0.8),(int) (MainFrame.getFrame().getHeight() * 0.2));
        function.setVisible(true);
        add(function);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                int fileNameWidth = (int) (size.getWidth() * 0.2);
                fileName.setBounds(0, 0, fileNameWidth, size.height);
                function.setBounds(fileNameWidth, 0, size.width - fileNameWidth, size.height);
            }
        });
    }

    public static FileName getFileName() {
        return fileName;
    }

    public static Function getFunction() {
        return function;
    }

}
