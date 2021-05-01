package com.player.UI.Bottom;

import javax.swing.*;
import java.awt.*;

public class Bottom extends JPanel{
    private static FileName fileName;
    private static Function function;

    public Bottom() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();//得到宽
        double height = screenSize.getHeight();//得到高
        setBackground(new Color(125,125,125));
        setLayout(null);
        fileName = new FileName();
        fileName.setBounds(0,0,(int) (width * 0.8 * 0.2),(int) (height * 0.8 * 0.15));
        fileName.setVisible(true);
        add(fileName);
        function = new Function();
        function.setBounds((int) (width * 0.8 * 0.2),0,(int) (width * 0.8 * 0.8),(int) (height * 0.8 * 0.15));
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
