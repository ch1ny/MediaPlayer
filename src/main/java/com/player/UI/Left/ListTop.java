package com.player.UI.Left;

import com.player.MainFrame;
import com.player.Player.MusicPlayer;
import com.player.Util.AudioFormat;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;

import static javax.swing.UIManager.*;

public class ListTop extends JPanel {

    private Add add;

    public ListTop() {
        setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        setBounds(0,0, (int) (width * 0.8 * 0.2), 50);
        setBackground(new Color(100,100,100));
        add = new Add();
        add.setBounds(0,0,300,50);
        add(add);
    }

    private class Add extends JPanel {
        public Add() {
            setBackground(new Color(100,100,100));
            setLayout(null);
            JLabel title = new JLabel("添加到播放列表...", JLabel.CENTER);
            title.setVisible(true);
            title.setBounds(0, 0, (int) (MainFrame.getFrame().getWidth() * 0.2), 50);
            title.setForeground(Color.WHITE);
            title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            add(title);
            JButton btn = new Item.PlayerTextButton("");
            btn.setBounds(0, 0, (int) (MainFrame.getFrame().getWidth() * 0.2), 50);
            btn.setContentAreaFilled(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        setBackground(new Color(100,100,100));
                        add.getParent().setBackground(new Color(100,100,100));
                        addSong();
                        MainFrame.rebuildList();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(new Color(155,155,155));
                    add.getParent().setBackground(new Color(155,155,155));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(new Color(100,100,100));
                    add.getParent().setBackground(new Color(100,100,100));
                }
            });
            add(btn);
        }

        private void addSong() throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, LineUnavailableException, UnsupportedAudioFileException {
            LookAndFeel myLookAndFeel = UIManager.getLookAndFeel(); // 保存我的外观设置
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName()); // 将JFileChooser外观设置成系统样式
            JFileChooser chooser = new JFileChooser(".");
            UIManager.setLookAndFeel(myLookAndFeel); // 将应用外观设置改回自定义的外观设置，防止frame重绘时改变其他组件样式
            chooser.setFileFilter(new Mp3Chooser());
            chooser.showOpenDialog(null);
            chooser.setName("选择Mp3文件");
            File file = chooser.getSelectedFile();
            if (file != null) {
                String path = file.getPath();
                if (!new Mp3Chooser().accept(file)) {
                    JOptionPane.showMessageDialog(null,"文件格式不支持！！", "文件不支持", 0);
                    return;
                }
                File audio = new File("res/audio");
                FileInputStream input = new FileInputStream(audio);
                Scanner sc = new Scanner(input);
                String had = "";
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (path.equals(line)) {
                        JOptionPane.showMessageDialog(null,"播放列表中已存在该曲目！", "请勿重复添加", 0);
                        return;
                    } else {
                        had += "\n" + line;
                    }
                }
                input.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(audio));
                bw.write(path + had);
                bw.flush();
                bw.close();
                if (had.equals("")) {
                    MusicPlayer.getInstance().init();
                }
                MainFrame.rebuildList();
            }
        }

        private class Mp3Chooser extends FileFilter {

            @Override
            public boolean accept(File f) {
                String name = f.getName();
                return f.isDirectory() || AudioFormat.endWith(name);
            }

            @Override
            public String getDescription() {
                String desc = "";
                int i = 0;
                for (AudioFormat.PermittedFormat format:
                     AudioFormat.PermittedFormat.values()) {
                    desc += "*." + format;
                    i++;
                    if (i < AudioFormat.PermittedFormat.values().length) {
                        desc += ",";
                    }
                }
                return desc;
            }
        }

    }

}
