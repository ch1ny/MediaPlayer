package com.player;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import com.player.Player.MediaPlayer;
import com.player.UI.Left.List;

import com.player.UI.Bottom.Bottom;
import com.player.UI.View.ViewPanel;
import com.player.Util.JudgeMoV;
import com.player.Util.Volume;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MainFrame extends JFrame {

    private static JFrame frame;
    private static Bottom bottom;
    private static ViewPanel view;
    private static EmbeddedMediaPlayerComponent video;
    private static List list;
    private static Volume vol;

    public static Bottom getBottom() {
        return bottom;
    }

    public static ViewPanel getView() {
        return view;
    }

    public MainFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();//得到宽
        double height = screenSize.getHeight();//得到高
        frame = new JFrame("Media Player");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize((int) (width * 0.9), (int) (height * 0.9));
        frame.setLocationRelativeTo(null);//窗体居中显示
        frame.setLayout(null);
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage("res/icon/music.png"));
        frame.setVisible(true);
        bottom = new Bottom();
        bottom.setBounds(0,(int) (height * 0.9 * 0.85),(int) (width * 0.9), (int) (height * 0.9 * 0.15));
        frame.add(bottom);
        list = new List();
        list.setBounds(0,0,(int) (width * 0.9 * 0.2),(int) (height * 0.9 * 0.85));
        frame.add(list);
        vol = new Volume();
        frame.add(vol);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "res/libvlc");
        video = new EmbeddedMediaPlayerComponent();
        video.setBounds((int) (width * 0.9 * 0.2), 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.85));
        frame.add(video);
        view = new ViewPanel();
        view.setBounds((int) (width * 0.9 * 0.2), 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.85));
        frame.add(view);

        FocusInit();
        FrameController();
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

    public static void showVideo() {
        video.setVisible(true);
    }

    public static void hideVideo() {
        video.setVisible(false);
    }

    private void FocusInit() {
        frame.setFocusable(true);
    }

    private void FrameController() {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (bottom.getFunction().isEnable) {
                    if (e.isControlDown()) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_RIGHT:
                                try {
                                    MediaPlayer.getInstance().playNext();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                try {
                                    MediaPlayer.getInstance().playPrev();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                        }
                    } else {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_SPACE:
                                MediaPlayer player = MediaPlayer.getInstance();
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
                                switch (MediaPlayer.getInstance().getMoV()) {
                                    case JudgeMoV.MUSIC:
                                        if (MediaPlayer.getInstance().getMusic() != null) {
                                            Bottom.getFunction().playBegin();
                                            double percent = (double) Bottom.getFunction().getSlider().getValue() / 100; // 相较于当前的进度
                                            double distance = 10.0; // 设置跳跃间隔
                                            double rate = distance / MediaPlayer.getInstance().getLength();
                                            percent += rate;
                                            if (percent > 1) {
                                                percent = 1;
                                            }
                                            MediaPlayer.getInstance().jump(percent);
                                        }
                                        break;
                                    case JudgeMoV.VIDEO:
                                        if (MediaPlayer.getInstance().getVideo() != null) {
                                            Bottom.getFunction().playBegin();
                                            long now = MainFrame.getVideo().getMediaPlayer().getTime();
                                            now += 3000;
                                            MediaPlayer.getInstance().jump(now);
                                        }
                                        break;
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                switch (MediaPlayer.getInstance().getMoV()) {
                                    case JudgeMoV.MUSIC:
                                        if (MediaPlayer.getInstance().getMusic() != null) {
                                            Bottom.getFunction().playBegin();
                                            double percent = (double) Bottom.getFunction().getSlider().getValue() / 100; // 相较于当前的进度
                                            double distance = 10.0; // 设置跳跃间隔
                                            double rate = distance / MediaPlayer.getInstance().getLength();
                                            percent -= rate;
                                            if (percent < 0) {
                                                percent = 0;
                                            }
                                            MediaPlayer.getInstance().jump(percent);
                                        }
                                        break;
                                    case JudgeMoV.VIDEO:
                                        if (MediaPlayer.getInstance().getVideo() != null) {
                                            Bottom.getFunction().playBegin();
                                            long now = MainFrame.getVideo().getMediaPlayer().getTime();
                                            now -= 3000;
                                            MediaPlayer.getInstance().jump(now);
                                        }
                                        break;
                                }
                                break;
                            case KeyEvent.VK_UP:
                                if (MediaPlayer.getInstance().getMoV() == JudgeMoV.VIDEO) {
                                    int volume = video.getMediaPlayer().getVolume();
                                    volume += 50;
                                    if (volume > 500) {
                                        volume = 500;
                                    }
                                    video.getMediaPlayer().setVolume(volume);
                                    vol.setVolume(volume / 5);
                                } else {
                                    FloatControl gainControl = (FloatControl) MediaPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                                    float max = gainControl.getMaximum();
                                    float min = gainControl.getMinimum();
                                    float distance = max - min;
                                    float now = gainControl.getValue();
                                    now += distance / 10;
                                    if (now > max) {
                                        now = max;
                                    }
                                    gainControl.setValue(now);
                                    int percent = (int) (((now - min) / distance) * 100);
                                    vol.setVolume(percent);
                                }
                                break;
                            case KeyEvent.VK_DOWN:
                                if (MediaPlayer.getInstance().getMoV() == JudgeMoV.VIDEO) {
                                    int volume = video.getMediaPlayer().getVolume();
                                    volume -= 50;
                                    if (volume < 0) {
                                        volume = 0;
                                    }
                                    video.getMediaPlayer().setVolume(volume);
                                    vol.setVolume(volume / 5);
                                } else {
                                    FloatControl gainControl = (FloatControl) MediaPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                                    float max = gainControl.getMaximum();
                                    float min = gainControl.getMinimum();
                                    float distance = max - min;
                                    float now = gainControl.getValue();
                                    now -= distance / 10;
                                    if (now < min) {
                                        now = min;
                                    }
                                    gainControl.setValue(now);
                                    int percent = (int) (((now - min) / distance) * 100);
                                    vol.setVolume(percent);
                                }
                                break;
                            case KeyEvent.VK_TAB:
                                frame.requestFocus();
                                break;
                        }
                    }
                }
            }
        });
        frame.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                switch (e.getWheelRotation()) {
                    case -1:
                        if (MediaPlayer.getInstance().getMoV() == JudgeMoV.VIDEO) {
                            int volume = video.getMediaPlayer().getVolume();
                            volume += e.getScrollAmount() * 5;
                            if (volume > 500) {
                                volume = 500;
                            }
                            video.getMediaPlayer().setVolume(volume);
                            vol.setVolume(volume / 5);
                        } else {
                            FloatControl gainControl = (FloatControl) MediaPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                            float max = gainControl.getMaximum();
                            float min = gainControl.getMinimum();
                            float distance = max - min;
                            float now = gainControl.getValue();
                            now += (distance / 100) * e.getScrollAmount();
                            if (now > max) {
                                now = max;
                            }
                            gainControl.setValue(now);
                            int percent = (int) (((now - min) / distance) * 100);
                            vol.setVolume(percent);
                        }
                        break;
                    case 1:
                        if (MediaPlayer.getInstance().getMoV() == JudgeMoV.VIDEO) {
                            int volume = video.getMediaPlayer().getVolume();
                            volume -= e.getScrollAmount() * 5;
                            if (volume < 0) {
                                volume = 0;
                            }
                            video.getMediaPlayer().setVolume(volume);
                            vol.setVolume(volume / 5);
                        } else {
                            FloatControl gainControl = (FloatControl) MediaPlayer.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
                            float max = gainControl.getMaximum();
                            float min = gainControl.getMinimum();
                            float distance = max - min;
                            float now = gainControl.getValue();
                            now -= (distance / 100) * e.getScrollAmount();
                            if (now < min) {
                                now = min;
                            }
                            gainControl.setValue(now);
                            int percent = (int) (((now - min) / distance) * 100);
                            vol.setVolume(percent);
                        }
                        break;
                }
            }
        });
    }

    public static EmbeddedMediaPlayerComponent getVideo() {
        return video;
    }
}
