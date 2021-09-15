package com.player.UI.Left;

import com.player.MainFrame;
import com.player.Player.MediaPlayer;
import com.player.UI.Bottom.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class Item extends JPanel {
    private JLabel title;
    private String path;
    private int isOdd; // 1是奇数，0是偶数

    public Item(String name, int num) {
        setBackground(new Color(255,255,255));
        setLayout(null);
        path = name;
        isOdd = num;
        title = new JLabel(name.substring(name.lastIndexOf("\\") + 1, name.lastIndexOf(".")), JLabel.LEFT);
        title.setVisible(true);
        title.setBounds(20, 0, (int) (MainFrame.getFrame().getWidth() * 0.2), 50);
        title.setForeground(Color.BLACK);
        title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(title);
        if (isOdd == 1) {
            setBackground(new Color(255,255,255));
        } else {
            setBackground(new Color(240,240,255));
        }
        JButton btn = new PlayerTextButton("");
        btn.setBounds(0, 0, (int) (MainFrame.getFrame().getWidth() * 0.2), 50);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(btn);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Function function = MainFrame.getBottom().getFunction();
                    function.playBegin();
                    try {
                        MediaPlayer player = MediaPlayer.getInstance();
                        if (player.isPlaying()) {
                            player.pause();
                        }
                        player.play(path);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else if (e.getButton() == MouseEvent.BUTTON3){
                    int confirm = JOptionPane.showConfirmDialog(null, "是否要将文件：" + title.getText() + "移出播放列表？", "移除文件", JOptionPane.YES_NO_OPTION);
                    if (confirm == 0) {
                        File file = new File("res/media");
                        try {
                            if (MediaPlayer.getInstance().getFilePath().equals(path)) {
                                try {
                                    MediaPlayer.getInstance().playNext();
                                } catch (ArrayIndexOutOfBoundsException e1) {

                                }
                                MediaPlayer.getInstance().pause();
                                MainFrame.getBottom().getFunction().playEnd();
                            }
                            FileInputStream input = new FileInputStream(file);
                            Scanner sc = new Scanner(input);
                            String rest = "";
                            while (sc.hasNextLine()) {
                                String line = sc.nextLine();
                                if (!line.equals(path)) {
                                    rest += line + "\n";
                                }
                            }
                            input.close();
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                            bw.write(rest);
                            bw.flush();
                            bw.close();
                            if (rest.equals("")) {
                                MediaPlayer.getInstance().init();
                            }
                            MainFrame.rebuildList();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                MainFrame.getFrame().requestFocus();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(155,155,155));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isOdd == 1) {
                    setBackground(new Color(255,255,255));
                } else {
                    setBackground(new Color(240,240,255));
                }
            }
        });
    }

    static class PlayerTextButton extends JButton {
        public PlayerTextButton(String text) {
            super(text);
            setBackground(null);
            setBorder(null);
            setVisible(true);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
}
