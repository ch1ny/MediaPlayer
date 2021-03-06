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

    public ItemList() {
        setLayout(null);
        Vector media;
        media = getFileList();
        int num = media.size();
        for (int i = 0; i < num; i++) {
            Item item = new Item((String) media.get(i), i % 2);
            item.setBounds(0, 50 * i, (int) (MainFrame.getFrame().getWidth() * 0.2), 50);
            add(item);
        }
        setBackground(new Color(255,255,255));
        setVisible(true);
        setPreferredSize(new Dimension((int) (MainFrame.getFrame().getWidth() * 0.2), 50 * num));
    }

    private Vector getFileList() {
        File media = new File("res/media");
        if (!media.exists()) {
            try {
                media.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream stream = new FileInputStream(media);
            Scanner sc = new Scanner(stream);
            Vector list = new Vector();
            String str = "";
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (AudioFormat.endWith(line) || VideoFormat.endWith(line)) {
                    str += line + "\n";
                    list.add(line);
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(media));
            bw.write(str);
            bw.flush();
            bw.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
