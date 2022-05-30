package com.player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import com.player.Player.MediaPlayer;
import com.player.Player.Process;
import com.player.UI.Left.List;

import com.player.UI.Bottom.Bottom;
import com.player.UI.Menu.MenuBar;
import com.player.UI.View.ViewPanel;
import com.player.UI.Component.Rate;
import com.player.Util.VideoClickListener;
import com.player.UI.Component.Volume;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class MainFrame {

    private static JFrame frame;
    private static MenuBar menuBar;
    private static Bottom bottom;
    private static ViewPanel view;
    private static EmbeddedMediaPlayerComponent media;
    private static List list;
    private static Volume vol;
    private static Rate r;
    private static Vector<Float> Rates = new Vector<Float>();

    public static MenuBar getMenu() {
        return menuBar;
    }

    public static Bottom getBottom() {
        return bottom;
    }

    public static List getList() {
        return list;
    }

    public static ViewPanel getView() {
        return view;
    }

    public MainFrame() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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

        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);

        bottom = new Bottom();
        bottom.setBounds(0,(int) (height * 0.9 * 0.8),(int) (width * 0.9), (int) (height * 0.9 * 0.2));
        frame.add(bottom);
        list = new List();
        list.setBounds(0,0,(int) (width * 0.9 * 0.2),(int) (height * 0.9 * 0.8));
        frame.add(list);
        vol = new Volume();
        frame.add(vol);
        r = new Rate();
        frame.add(r);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "libvlc");
        media = new EmbeddedMediaPlayerComponent();
        media.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        media.setBounds((int) (width * 0.9 * 0.2), 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.8));
        media.getMediaPlayer().setFullScreenStrategy(new DefaultAdaptiveRuntimeFullScreenStrategy(frame));
        frame.add(media);
        view = new ViewPanel();
        view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        view.setBounds((int) (width * 0.9 * 0.2), 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.8));
        frame.add(view);
        RateInit();
        FocusInit();
        FrameController();
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void rebuildList() {
        frame.remove(list);
        list = new List();
        list.setBounds(0,0,(int) (frame.getWidth() * 0.2),(int) (frame.getHeight() * 0.8));
        frame.add(list);
        frame.revalidate(); // repaint()方法重绘，revalidate()方法对组件进行验证，重新排列组件
    }

    public static void setAss(File assFile) {
        media.getMediaPlayer().setSubTitleFile(assFile);
    }

    public static void clearAss() {
        media.getMediaPlayer().setSubTitleFile("");
    }

    public static void showVideo() {
        media.setVisible(true);
    }

    public static void hideVideo() {
        media.setVisible(false);
    }

    private void FocusInit() {
        frame.setFocusable(true);
    }

    private void RateInit() {
        Rates.add(0.5f);
        Rates.add(0.75f);
        Rates.add(1.0f);
        Rates.add(1.25f);
        Rates.add(1.5f);
        Rates.add(2.0f);
        Rates.add(2.5f);
        Rates.add(3.0f);
    }

    private static void FrameController() {
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
                            case KeyEvent.VK_S:
                                try {
                                    ScreenShot();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                        }
                    } else if (e.isShiftDown()) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_RIGHT:
                                try {
                                    float rate = media.getMediaPlayer().getRate();
                                    int index = Rates.indexOf(rate);
                                    if (index != Rates.size() - 1) {
                                        media.getMediaPlayer().setRate(Rates.get(index + 1));
                                        Process.getInstance().setSleep(Rates.get(index + 1));
                                    }
                                    r.setRate(media.getMediaPlayer().getRate());
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                try {
                                    float rate = media.getMediaPlayer().getRate();
                                    int index = Rates.indexOf(rate);
                                    if (index != 0) {
                                        media.getMediaPlayer().setRate(Rates.get(index - 1));
                                        Process.getInstance().setSleep(Rates.get(index - 1));
                                    }
                                    r.setRate(media.getMediaPlayer().getRate());
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                        }
                    } else {
                        int volume;
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
                                if (MediaPlayer.getInstance().getMedia() != null) {
                                    Bottom.getFunction().playBegin();
                                    long now = MainFrame.getMedia().getMediaPlayer().getTime();
                                    now += 3000;
                                    MediaPlayer.getInstance().jump(now);
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                if (MediaPlayer.getInstance().getMedia() != null) {
                                    Bottom.getFunction().playBegin();
                                    long now = MainFrame.getMedia().getMediaPlayer().getTime();
                                    now -= 3000;
                                    MediaPlayer.getInstance().jump(now);
                                }
                                break;
                            case KeyEvent.VK_UP:
                                volume = media.getMediaPlayer().getVolume();
                                volume += 10;
                                if (volume > 150) {
                                    volume = 150;
                                }
                                media.getMediaPlayer().setVolume(volume);
                                vol.setVolume(volume);
                                break;
                            case KeyEvent.VK_DOWN:
                                volume = media.getMediaPlayer().getVolume();
                                volume -= 10;
                                if (volume < 0) {
                                    volume = 0;
                                }
                                media.getMediaPlayer().setVolume(volume);
                                vol.setVolume(volume);
                                break;
                            case KeyEvent.VK_TAB:
                                frame.requestFocus();
                                break;
                            case KeyEvent.VK_ESCAPE:
                                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                                double width = screen.getWidth();
                                double height = screen.getHeight();
                                bottom.setVisible(true);
                                list.setVisible(true);
                                media.setBounds((int) (width * 0.9 * 0.2), 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.8));
                                view.setBounds((int) (width * 0.9 * 0.2), 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.8));
                                view.getCover().setBounds(0, 0, (int) (width * 0.9 * 0.8), (int) (height * 0.9 * 0.8));
                                menuBar.setVisible(true);
                                media.getMediaPlayer().setFullScreen(false);
                                break;
                            case KeyEvent.VK_OPEN_BRACKET:
                                try {
                                    MediaPlayer.getInstance().playPrev();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case KeyEvent.VK_CLOSE_BRACKET:
                                try {
                                    MediaPlayer.getInstance().playNext();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                        }
                    }
                }
            }
        });
        frame.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int volume;
                switch (e.getWheelRotation()) {
                    case -1:
                        volume = media.getMediaPlayer().getVolume();
                        volume += e.getScrollAmount();
                        if (volume > 150) {
                            volume = 150;
                        }
                        media.getMediaPlayer().setVolume(volume);
                        vol.setVolume(volume);
                        break;
                    case 1:
                        volume = media.getMediaPlayer().getVolume();
                        volume -= e.getScrollAmount();
                        if (volume < 0) {
                            volume = 0;
                        }
                        media.getMediaPlayer().setVolume(volume);
                        vol.setVolume(volume);
                        break;
                }
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeiconified(WindowEvent e) {
                frame.requestFocus();
            }
            @Override
            public void windowActivated(WindowEvent e) {
                frame.requestFocus();
            }
        });
        media.getVideoSurface().addMouseListener(new VideoClickListener());
        view.addMouseListener(new VideoClickListener());
    }

    private static void ScreenShot() throws IOException {
        BufferedImage bi = media.getMediaPlayer().getSnapshot();
        File snapshot = new File("C:\\Users\\10563\\Desktop\\snapshot.jpg");
        ImageIO.write(bi, "jpg", snapshot);
    }

    public static EmbeddedMediaPlayerComponent getMedia() {
        return media;
    }
}
