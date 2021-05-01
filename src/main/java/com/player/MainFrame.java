package com.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import com.player.Player.MusicPlayer;
import com.player.UI.Left.List;

import com.player.UI.Bottom.Bottom;
import com.player.UI.View.ViewPanel;

public class MainFrame extends JFrame {

    private static JFrame frame;
    private static Bottom bottom;
    private static ViewPanel view;
    private static List list;

    public static Bottom getBottom() {
        return bottom;
    }

    public static ViewPanel getView() {
        return view;
    }

    public MainFrame() throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();//得到宽
        double height = screenSize.getHeight();//得到高
        frame = new JFrame("Media Player");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) (width * 0.8), (int) (height * 0.8));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setLayout(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("res/icon/music.png"));
        frame.setVisible(true);
        bottom = new Bottom();
        bottom.setBounds(0,(int) (height * 0.8 * 0.85),(int) (width * 0.8), (int) (height * 0.8 * 0.15));
        frame.add(bottom);
        list = new List();
        list.setBounds(0,0,(int) (width * 0.8 * 0.2),(int) (height * 0.8 * 0.85));
        frame.add(list);
        view = new ViewPanel();
        view.setBounds((int) (width * 0.8 * 0.2), 0, (int) (width * 0.8 * 0.8), (int) (height * 0.8 * 0.85));
        frame.add(view);
        FocusInit();
        KeyController();
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void rebuildList() {
        frame.remove(list);
        list = new List();
        list.setBounds(0,0,(int) (frame.getWidth() * 0.2),(int) (frame.getHeight() * 0.85));
        frame.add(list);
        frame.revalidate(); // repaint()方法重绘，revalidate()方法对组件进行验证，重新排列组件
    }

    private void FocusInit() {
        frame.setFocusable(true);
    }

    private void KeyController() {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (bottom.getFunction().isEnable) {
                    if (e.isControlDown()) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_RIGHT:
                                try {
                                    MusicPlayer.getInstance().playNext();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                try {
                                    MusicPlayer.getInstance().playPrev();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                        }
                    } else {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_SPACE:
                                MusicPlayer player = MusicPlayer.getInstance();
                                if (player.isPlaying()) {
                                    player.pause();
                                    Bottom.getFunction().playEnd();
                                } else {
                                    try {
                                        player.go_on();
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                    Bottom.getFunction().playBegin();
                                }
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (MusicPlayer.getInstance().getPlayerThread() != null) {
                                    Bottom.getFunction().playBegin();
                                    double percent = (double) Bottom.getFunction().getSlider().getValue() / 100; // 相较于当前的进度
                                    double distance = 10.0; // 设置跳跃间隔
                                    double rate = distance / MusicPlayer.getInstance().getLength();
                                    percent += rate;
                                    if (percent > 1) {
                                        percent = 1;
                                    }
                                    MusicPlayer.getInstance().jump(percent);
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                if (MusicPlayer.getInstance().getPlayerThread() != null) {
                                    Bottom.getFunction().playBegin();
                                    double percent = (double) Bottom.getFunction().getSlider().getValue() / 100; // 相较于当前的进度
                                    double distance = 10.0; // 设置跳跃间隔
                                    double rate = distance / MusicPlayer.getInstance().getLength();
                                    percent -= rate;
                                    if (percent < 0) {
                                        percent = 0;
                                    }
                                    MusicPlayer.getInstance().jump(percent);
                                }
                                break;
                        }
                    }
                }
            }
        });
    }

}
