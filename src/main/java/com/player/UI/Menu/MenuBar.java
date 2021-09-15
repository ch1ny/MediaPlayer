package com.player.UI.Menu;

import com.player.MainFrame;
import com.player.Player.MediaPlayer;
import com.player.Util.AudioFormat;
import com.player.Util.VideoFormat;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import static javax.swing.UIManager.getSystemLookAndFeelClassName;

/**
 * @Author SDU德布罗煜
 * @Date 2021/6/7 18:37
 * @Version 1.0
 */

public class MenuBar extends JMenuBar {

    public MenuBar() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        LookAndFeel myLookAndFeel = UIManager.getLookAndFeel();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JMenu file = new JMenu("文件");
        JMenuItem addFiles = new JMenuItem("添加多个媒体至播放列表");
        addFiles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addFiles();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        JMenuItem addDirectory = new JMenuItem("添加文件夹至播放列表");
        addDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addDirectory();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        JMenuItem addAss = new JMenuItem("添加字幕");
        addAss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookAndFeel myLookAndFeel = UIManager.getLookAndFeel(); // 保存我的外观设置
                    UIManager.setLookAndFeel(getSystemLookAndFeelClassName()); // 将JFileChooser外观设置成系统样式
                    JFileChooser chooser = new JFileChooser(".");
                    UIManager.setLookAndFeel(myLookAndFeel);
                    chooser.setFileFilter(new FileFilter() {
                        @Override
                        public boolean accept(File f) {
                            if (f.isDirectory() || (f.getName().indexOf(".") != -1 && f.getName().substring(f.getName().lastIndexOf(".")).equals(".ass"))) {
                                return true;
                            }
                            return false;
                        }
                        @Override
                        public String getDescription() {
                            return "字幕文件(*.ass)";
                        }
                    });
                    chooser.setMultiSelectionEnabled(false);
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooser.setPreferredSize(new Dimension((int) (MainFrame.getFrame().getWidth() * 0.6),(int) (MainFrame.getFrame().getHeight() * 0.6)));
                    chooser.setDialogTitle("选择媒体文件");
                    chooser.showOpenDialog(null);
                    File file = chooser.getSelectedFile();
                    if (file == null) {
                        return;
                    }
                    if (!file.getName().substring(file.getName().lastIndexOf(".")).equals(".ass")) {
                        JOptionPane.showMessageDialog(null,file.getName() + "不是字幕文件！", "文件不支持", 0);
                        return;
                    }
                    MainFrame.setAss(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        file.add(addFiles);
//        file.addSeparator();
        file.add(addDirectory);
//        file.addSeparator();
        file.add(addAss);
        file.setBackground(new Color(245, 245, 255));
        add(file);
        addNotify();
        setPreferredSize(new Dimension(MainFrame.getFrame().getWidth(), (int) (MainFrame.getFrame().getHeight() * 0.03)));
        UIManager.setLookAndFeel(myLookAndFeel);
    }

    private void addFiles() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedAudioFileException, TagException, CannotReadException, LineUnavailableException, InvalidAudioFrameException, ReadOnlyFileException {
        LookAndFeel myLookAndFeel = UIManager.getLookAndFeel(); // 保存我的外观设置
        UIManager.setLookAndFeel(getSystemLookAndFeelClassName()); // 将JFileChooser外观设置成系统样式
        JFileChooser chooser = new JFileChooser(".");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        UIManager.setLookAndFeel(myLookAndFeel); // 将应用外观设置改回自定义的外观设置，防止frame重绘时改变其他组件样式
        chooser.setPreferredSize(new Dimension((int) (MainFrame.getFrame().getWidth() * 0.6),(int) (MainFrame.getFrame().getHeight() * 0.6)));
        chooser.setDialogTitle("选择媒体文件");
        chooser.showOpenDialog(null);
        File[] files = chooser.getSelectedFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (!AudioFormat.endWith(file.getName()) && !VideoFormat.endWith(file.getName())) {
                JOptionPane.showMessageDialog(null,file.getName() + "文件格式不支持！！", "文件不支持", 0);
                continue;
            }
            String path = file.getPath();
            File media = new File("res/media");
            FileInputStream input = new FileInputStream(media);
            Scanner sc = new Scanner(input);
            String had = "";
            boolean isExist = false;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (path.equals(line)) {
                    JOptionPane.showMessageDialog(null,"播放列表中已存在" + file.getName() + "！", "请勿重复添加", 0);
                    isExist = true;
                    break;
                } else {
                    had += "\n" + line;
                }
            }
            input.close();
            if (isExist) {
                continue;
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(media));
            bw.write(path + had);
            bw.flush();
            bw.close();
            if (had.equals("")) {
                MediaPlayer.getInstance().init();
            }
        }
        if (files.length > 0) {
            MainFrame.rebuildList();
        }
    }

    private void addDirectory() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, UnsupportedAudioFileException, TagException, CannotReadException, LineUnavailableException, InvalidAudioFrameException, ReadOnlyFileException {
        LookAndFeel myLookAndFeel = UIManager.getLookAndFeel(); // 保存我的外观设置
        UIManager.setLookAndFeel(getSystemLookAndFeelClassName()); // 将JFileChooser外观设置成系统样式
        JFileChooser chooser = new JFileChooser(".");
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        UIManager.setLookAndFeel(myLookAndFeel); // 将应用外观设置改回自定义的外观设置，防止frame重绘时改变其他组件样式
        chooser.setPreferredSize(new Dimension((int) (MainFrame.getFrame().getWidth() * 0.6),(int) (MainFrame.getFrame().getHeight() * 0.6)));
        chooser.setDialogTitle("选择存有媒体文件的文件夹");
        chooser.showOpenDialog(null);
        File directory = chooser.getSelectedFile();
        if (directory != null && directory.isDirectory()) {
            int add = JOptionPane.showConfirmDialog(null, "是否要将文件夹：" + directory.getName() + "中的所有媒体文件加入到播放列表中？", "添加整个文件夹", JOptionPane.YES_NO_OPTION);
            if (add == 1) {
                return;
            }
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!AudioFormat.endWith(file.getName()) && !VideoFormat.endWith(file.getName())) {
                    continue;
                }
                String path = file.getPath();
                File media = new File("res/media");
                FileInputStream input = new FileInputStream(media);
                Scanner sc = new Scanner(input);
                String had = "";
                boolean isExist = false;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (path.equals(line)) {
//                        JOptionPane.showMessageDialog(null,"播放列表中已存在" + file.getName() + "！", "请勿重复添加", 0);
                        isExist = true;
                        break;
                    } else {
                        had += "\n" + line;
                    }
                }
                input.close();
                if (isExist) {
                    continue;
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter(media));
                bw.write(path + had);
                bw.flush();
                bw.close();
                if (had.equals("")) {
                    MediaPlayer.getInstance().init();
                }
            }
            MainFrame.rebuildList();
        }
    }

}
