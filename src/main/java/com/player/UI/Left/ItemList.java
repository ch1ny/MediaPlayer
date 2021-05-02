package com.player.UI.Left;

import com.player.MainFrame;
import com.player.Util.AudioFormat;
import com.player.Util.VideoFormat;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class ItemList extends JPanel {

    public ItemList() {
        setLayout(null);
        setBackground(new Color(255,255,255));
        Vector media;
        media = getFileList();
        int num = media.size();
        for (int i = 0; i < num; i++) {
            Item item = new Item((String) media.get(i));
            item.setBounds(0, 50 * i, 300, 50);
            add(item);
        }
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
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (AudioFormat.endWith(line) || VideoFormat.endWith(line))
                    list.add(line);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
