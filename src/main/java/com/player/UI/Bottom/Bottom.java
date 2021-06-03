package com.player.UI.Bottom;

import com.player.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Bottom extends JPanel {
    private static FileName fileName;
    private static Function function;

    public Bottom() {
        setBackground(new Color(125,125,125));
        setLayout(null);
        fileName = new FileName();
        fileName.setBounds(0,0,(int) (MainFrame.getFrame().getWidth() * 0.2),(int) (MainFrame.getFrame().getHeight() * 0.15));
        fileName.setVisible(true);
        add(fileName);
        function = new Function();
        function.setBounds((int) (MainFrame.getFrame().getWidth() * 0.2),0,(int) (MainFrame.getFrame().getWidth() * 0.8),(int) (MainFrame.getFrame().getHeight() * 0.15));
        function.setVisible(true);
        add(function);
    }

    public static FileName getFileName() {
        return fileName;
    }

    public static Function getFunction() {
        return function;
    }

}
