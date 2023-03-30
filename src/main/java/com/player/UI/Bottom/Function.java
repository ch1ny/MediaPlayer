package com.player.UI.Bottom;

import com.player.MainFrame;
import com.player.Player.MediaPlayer;
import com.player.UI.Style.SliderStyle;
import com.player.Util.TimeFormat;
import com.player.Player.Process;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Function extends JPanel {
    private final FunctionButton play, next, prev, forward, backward;
    private final JLabel current;
    private final JLabel total;
    private final JSlider playProcessSlider;
    public boolean isEnable = true;
    private boolean isAss = false;

    public Function() {
        setBackground(new Color(125,125,125));
        setLayout(null);
        play = new FunctionButton("res/icon/play.png");
        play.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getHeight() * 0.1 - 40)) / 2), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.25), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40));
        add(play);
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (play.isEnabled()) {
                    MediaPlayer player = MediaPlayer.getInstance();
                    if (!player.isPlaying()) { // 没在播放 -> 播放
                        play.setIconPath("res/icon/pause.png");
                        try {
                            player.go_on();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else { // 播放中 -> 暂停
                        play.setIconPath("res/icon/play.png");
                        MediaPlayer.pause();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });

        next = new FunctionButton("res/icon/next.png");
        next.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getHeight() * 0.1 - 40)) / 2 + 75), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.25), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40));
        add(next);
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (next.isEnabled()) {
                    try {
                        MediaPlayer.playNext();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });
        prev = new FunctionButton("res/icon/prev.png");
        prev.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getHeight() * 0.1 - 40)) / 2 - 75), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.25), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40));
        add(prev);
        prev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (prev.isEnabled()) {
                    try {
                        MediaPlayer.playPrev();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });

        forward = new FunctionButton("res/icon/forward.png");
        forward.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getHeight() * 0.1 - 40)) / 2 + 150), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.25), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40));
        add(forward);
        forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (forward.isEnabled()) {
                    if (MediaPlayer.getInstance().getMedia() != null) {
                        play.setIconPath("res/icon/pause.png");
                        long now = MainFrame.getMedia().getMediaPlayer().getTime();
                        now += 3000;
                        MediaPlayer.getInstance().jump(now);
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });
        backward = new FunctionButton("res/icon/backward.png");
        backward.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getHeight() * 0.1 - 40)) / 2 - 150), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.25), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40), (int) (MainFrame.getFrame().getHeight() * 0.1 - 40));
        add(backward);
        backward.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (backward.isEnabled()) {
                    if (MediaPlayer.getInstance().getMedia() != null) {
                        play.setIconPath("res/icon/pause.png");
                        long now = MainFrame.getMedia().getMediaPlayer().getTime();
                        now -= 3000;
                        MediaPlayer.getInstance().jump(now);
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });

        playProcessSlider = new JSlider(0,100,0);
        playProcessSlider.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getWidth() * 0.8 * 0.5)) / 2), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.1),(int) (MainFrame.getFrame().getWidth() * 0.8 * 0.5),24);
        playProcessSlider.setUI(new SliderStyle(playProcessSlider));
        playProcessSlider.setBackground(new Color(125,125,125));
        playProcessSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(playProcessSlider);
        playProcessSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                playProcessSlider.setUI(new SliderStyle(playProcessSlider));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (playProcessSlider.isEnabled()) {
                    if (MediaPlayer.getInstance().getMedia() != null) {
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (MainFrame.getFrame().getHeight() * 0.1 - 40),(int) (MainFrame.getFrame().getHeight() * 0.1 - 40), Image.SCALE_SMOOTH)));
                        double percent = e.getX() / (double) playProcessSlider.getWidth(); // 相较于当前的进度
                        if (percent > 1) {
                            percent = 1;
                        } else if (percent < 0) {
                            percent = 0;
                        }
                        long total = MainFrame.getMedia().getMediaPlayer().getLength();
                        long time = (long) (total * percent);
                        MediaPlayer.getInstance().jump(time);
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });
        playProcessSlider.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (playProcessSlider.isEnabled()) {
                    double percent = e.getX() / (double) playProcessSlider.getWidth();
                    double time = MediaPlayer.getInstance().getLength() * percent;
                    if (time > Process.getInstance().getTotalLength()) {
                        time = Process.getInstance().getTotalLength();
                    } else if (time < 0) {
                        time = 0;
                    }
                    Process.getInstance().setProcess((int) (time));
                }
            }
        });

        current = new JLabel("00:00");
        current.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) - (MainFrame.getFrame().getWidth() * 0.8 * 0.5)) / 2 - 60), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.1), 50, 30);
        current.setForeground(new Color(255,255,255));
        add(current);

        total = new JLabel("00:00");
        total.setBounds((int) (((MainFrame.getFrame().getWidth() * 0.8) + (MainFrame.getFrame().getWidth() * 0.8 * 0.5)) / 2 + 10), (int) (MainFrame.getFrame().getHeight() * 0.2 * 0.1), 50, 30);
        total.setForeground(new Color(255,255,255));
        add(total);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = e.getComponent().getSize();
                play.setBounds((int) ((size.width - (size.height * 0.5 - 40)) / 2), (int) (size.height * 0.25), (int) (size.height * 0.5 - 40), (int) (size.height * 0.5 - 40));
                next.setBounds((int) ((size.width - (size.height * 0.5 - 40)) / 2 + 75), (int) (size.height * 0.25), (int) (size.height * 0.5 - 40), (int) (size.height * 0.5 - 40));
                prev.setBounds((int) ((size.width - (size.height * 0.5 - 40)) / 2 - 75), (int) (size.height * 0.25), (int) (size.height * 0.5 - 40), (int) (size.height * 0.5 - 40));
                forward.setBounds((int) ((size.width - (size.height * 0.5 - 40)) / 2 + 150), (int) (size.height * 0.25), (int) (size.height * 0.5 - 40), (int) (size.height * 0.5 - 40));
                backward.setBounds((int) ((size.width - (size.height * 0.5 - 40)) / 2 - 150), (int) (size.height * 0.25), (int) (size.height * 0.5 - 40), (int) (size.height * 0.5 - 40));
                playProcessSlider.setBounds((int) ((size.width - (size.width * 0.5)) / 2), (int) (size.height * 0.1),(int) (size.width * 0.5),24);
                current.setBounds((int) ((size.width - (size.width * 0.5)) / 2 - 60), (int) (size.height * 0.1), 50, 30);
                total.setBounds((int) ((size.width + (size.width * 0.5)) / 2 + 10), (int) (size.height * 0.1), 50, 30);
            }
        });
    }

    public void playBegin() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (screen.getHeight() * 0.9 * 0.1 - 40),(int) (screen.getHeight() * 0.9 * 0.1 - 40), Image.SCALE_SMOOTH)));
    }

    public void playEnd() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.play.setIcon(new ImageIcon(new ImageIcon("res/icon/play.png").getImage().getScaledInstance((int) (screen.getHeight() * 0.9 * 0.1 - 40),(int) (screen.getHeight() * 0.9 * 0.1 - 40), Image.SCALE_SMOOTH)));
    }

    // 设置总时间，单位秒
    public void setTotalLength(int t) {
        String str = TimeFormat.timeFormat(t);
        this.total.setText(str);
    }

    // 当前时间，单位秒
    public void setCurrent(int t) {
        String str = TimeFormat.timeFormat(t);
        this.current.setText(str);
    }

    // 设置播放进度条
    public void setPlayProcessSlider(int p) {
        this.playProcessSlider.setValue(p);
    }

    public JSlider getSlider() {
        return playProcessSlider;
    }

    public void noSongs() {
        prev.setEnabled(false);
        backward.setEnabled(false);
        play.setEnabled(false);
        forward.setEnabled(false);
        next.setEnabled(false);
        playProcessSlider.setEnabled(false);
        isEnable = false;
    }

    public void haveSongs() {
        prev.setEnabled(true);
        backward.setEnabled(true);
        play.setEnabled(true);
        forward.setEnabled(true);
        next.setEnabled(true);
        playProcessSlider.setEnabled(true);
        isEnable = true;
    }

    private static class FunctionButton extends JButton {
        private ImageIcon imageIcon;

        public FunctionButton(String iconFilename) {
            super("");
            setIconPath(iconFilename);
            setBackground(null);
            setBorder(null);
            setVisible(true);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    Dimension size = e.getComponent().getSize();
                    resizeIcon(size.width, size.height);
                }
            });
        }

        void setIconPath(String iconFilename) {
            imageIcon = new ImageIcon(iconFilename);
            int width = getWidth(), height = getHeight();
            if (width == 0 || height == 0) return;
            resizeIcon(width, height);
        }

        void resizeIcon(int width, int height) {
            setIcon(new ImageIcon(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        }
    }
}
