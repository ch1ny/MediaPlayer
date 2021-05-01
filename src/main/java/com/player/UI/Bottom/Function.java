package com.player.UI.Bottom;

import com.player.MainFrame;
import com.player.Player.MusicPlayer;
import com.player.UI.Style.SliderStyle;
import com.player.Util.TimeFormat;
import com.player.Player.Process;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Function extends JPanel {
    private JButton play, next, prev, forward, backward;
    private JLabel current;
    private JLabel total;
    private JSlider playProcessSlider;
    private int width, height;
    public boolean isEnable = true;

    public Function() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth();//得到宽
        height = (int)screenSize.getHeight();//得到高
        setBackground(new Color(125,125,125));
        setLayout(null);
        play = new functionButton(new ImageIcon(new ImageIcon("res/icon/play.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
        play.setBounds((int) (((width * 0.8 * 0.8) - (height * 0.08 - 40)) / 2), 30, (int) (height * 0.08 - 40), (int) (height * 0.08 - 40));
        add(play);
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (play.isEnabled()) {
                    MusicPlayer player = MusicPlayer.getInstance();
                    if (!player.isPlaying()) { // 没在播放 -> 播放
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
                        try {
                            player.go_on();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    } else { // 播放中 -> 暂停
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/play.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
                        player.pause();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });

        next = new functionButton(new ImageIcon(new ImageIcon("res/icon/next.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
        next.setBounds((int) (((width * 0.8 * 0.8) - (height * 0.08 - 40)) / 2 + 50), 30, (int) (height * 0.08 - 40), (int) (height * 0.08 - 40));
        add(next);
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (next.isEnabled()) {
                    try {
                        MusicPlayer.getInstance().playNext();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });
        prev = new functionButton(new ImageIcon(new ImageIcon("res/icon/prev.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
        prev.setBounds((int) (((width * 0.8 * 0.8) - (height * 0.08 - 40)) / 2 - 50), 30, (int) (height * 0.08 - 40), (int) (height * 0.08 - 40));
        add(prev);
        prev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (prev.isEnabled()) {
                    try {
                        MusicPlayer.getInstance().playPrev();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });

        forward = new functionButton(new ImageIcon(new ImageIcon("res/icon/forward.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
        forward.setBounds((int) (((width * 0.8 * 0.8) - (height * 0.08 - 40)) / 2 + 100), 30, (int) (height * 0.08 - 40), (int) (height * 0.08 - 40));
        add(forward);
        forward.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (forward.isEnabled()) {
                    if (MusicPlayer.getInstance().getPlayerThread() != null) {
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
                        double percent = (double) playProcessSlider.getValue() / 100; // 相较于当前的进度
                        double distance = 10.0; // 设置跳跃间隔
                        double rate = distance / MusicPlayer.getInstance().getLength();
                        percent += rate;
                        if (percent > 1) {
                            percent = 1;
                        }
                        MusicPlayer.getInstance().jump(percent);
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });
        backward = new  functionButton(new ImageIcon(new ImageIcon("res/icon/backward.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
        backward.setBounds((int) (((width * 0.8 * 0.8) - (height * 0.08 - 40)) / 2 - 100), 30, (int) (height * 0.08 - 40), (int) (height * 0.08 - 40));
        add(backward);
        backward.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (backward.isEnabled()) {
                    if (MusicPlayer.getInstance().getPlayerThread() != null) {
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
                        double percent = (double) Bottom.getFunction().getSlider().getValue() / 100; // 相较于当前的进度
                        double distance = 10.0; // 设置跳跃间隔
                        double rate = distance / MusicPlayer.getInstance().getLength();
                        percent -= rate;
                        if (percent < 0) {
                            percent = 0;
                        }
                        MusicPlayer.getInstance().jump(percent);
                    }
                    MainFrame.getFrame().requestFocus();
                }
            }
        });

        playProcessSlider = new JSlider(0,100,0);
        playProcessSlider.setBounds((int) (((width * 0.8 * 0.8) - (width * 0.8 * 0.8 * 0.5)) / 2), 5,(int) (width * 0.8 * 0.8 * 0.5),24);
        playProcessSlider.setUI(new SliderStyle(playProcessSlider));
        playProcessSlider.setBackground(new Color(125,125,125));
        playProcessSlider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(playProcessSlider);
        playProcessSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (playProcessSlider.isEnabled()) {
                    if (MusicPlayer.getInstance().getPlayerThread() != null) {
                        play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
                        double percent = e.getX() / (double) playProcessSlider.getWidth(); // 相较于当前的进度
                        if (percent > 1) {
                            percent = 1;
                        } else if (percent < 0) {
                            percent = 0;
                        }
                        MusicPlayer.getInstance().jump(percent);
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
                    double time = MusicPlayer.getInstance().getLength() * percent;
                    if (time > Process.getInstance().getTotalLength()) {
                        time = Process.getInstance().getTotalLength();
                    } else if (time < 0) {
                        time = 0;
                    }
                    setCurrent((int) time);
                }
            }
        });

        current = new JLabel("00:00");
        current.setBounds((int) (((width * 0.8 * 0.8) - (width * 0.8 * 0.8 * 0.5)) / 2 - 50), 0, 50, 30);
        current.setForeground(new Color(255,255,255));
        add(current);

        total = new JLabel("00:00");
        total.setBounds((int) (((width * 0.8 * 0.8) + (width * 0.8 * 0.8 * 0.5)) / 2 + 10), 0, 50, 30);
        total.setForeground(new Color(255,255,255));
        add(total);
    }

    public void playBegin() {
        this.play.setIcon(new ImageIcon(new ImageIcon("res/icon/pause.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
    }

    public void playEnd() {
        this.play.setIcon(new ImageIcon(new ImageIcon("res/icon/play.png").getImage().getScaledInstance((int) (height * 0.08 - 40),(int) (height * 0.08 - 40), Image.SCALE_SMOOTH)));
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

    class functionButton extends JButton {
        public functionButton(Icon icon) {
            super("", icon);
            setBackground(null);
            setBorder(null);
            setVisible(true);
            setHorizontalTextPosition(SwingConstants.CENTER);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }
}
