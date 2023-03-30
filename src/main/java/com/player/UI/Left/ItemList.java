package com.player.UI.Left;

import com.player.MainFrame;
import com.player.Util.AudioFormat;
import com.player.Util.VideoFormat;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class ItemList extends JPanel {
    private static final int ListItemHeight = 50;

    public ItemList() {
        setLayout(null);
        Vector<String> media = getFileList();
        assert media != null;
        int num = media.size();
        for (int i = 0; i < num; i++) {
            Item item = new Item(media.get(i), i % 2);
            item.setBounds(0, ItemList.ListItemHeight * i, (int) (MainFrame.getFrame().getWidth() * 0.2), ItemList.ListItemHeight);
            add(item);
        }
        setBackground(new Color(255,255,255));
        setVisible(true);
        setPreferredSize(new Dimension((int) (MainFrame.getFrame().getWidth() * 0.2), ItemList.ListItemHeight * num));
    }

    public void resetWidth(int width) {
        Component[] components = getComponents();
        int i = 0;
        for (Component component : components) {
            component.setBounds(0, ItemList.ListItemHeight * i++, width, ItemList.ListItemHeight);
        }
        setPreferredSize(new Dimension(width, ItemList.ListItemHeight * components.length));
        MainFrame.getFrame().revalidate();
    }

    private Vector<String> getFileList() {
        File media = new File("res/media");
        if (!media.exists()) {
            try {
                if (!media.createNewFile())
                    throw new IOException();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream stream = new FileInputStream(media);
            Scanner sc = new Scanner(stream);
            Vector<String> list = new Vector<>();
            StringBuilder str = new StringBuilder();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (AudioFormat.endWith(line) || VideoFormat.endWith(line)) {
                    str.append(line).append("\n");
                    list.add(line);
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(media));
            bw.write(str.toString());
            bw.flush();
            bw.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
