package com.player.Player;

import com.player.MainFrame;
import com.player.UI.Bottom.Function;

public class Process {

    private int totalLength;
    private static Process process = new Process();
    private MusicProcessThread musicProcessThread;

    public static Process getInstance() {
        return process;
    }

    public int getTotalLength() {
        return totalLength;
    }

    //初始化进度条
    public void init() {
        totalLength = MusicPlayer.getInstance().getLength();
        musicProcessThread = new MusicProcessThread(0, totalLength);
        musicProcessThread.start();
        musicProcessThread.suspend();
    }

    //暂停播放
    public void pause() {
        musicProcessThread.suspend();
    }

    //继续播放
    public void go_on() {
        musicProcessThread.resume();
    }

    //调整进度，current是确定的时刻（秒）
    public void setProcess(int current) {
        try {
            musicProcessThread.stop();
        } catch (NullPointerException e) {}
        musicProcessThread = new MusicProcessThread(current, totalLength);
        musicProcessThread.start();
    }

    public void changeMusic(int length) {
        totalLength = length;
        musicProcessThread = new MusicProcessThread(0, this.totalLength);
        musicProcessThread.start();
    }

    //进度条线程
    class MusicProcessThread extends Thread {
        private int current;

        public MusicProcessThread(int now, int time) {
            Function function = MainFrame.getBottom().getFunction();
            function.setTotalLength(time);
            current = now;
        }

        //执行时，控制当前播放进度的显示条
        @Override
        public void run() {
            Function function = MainFrame.getBottom().getFunction();
            try {
                while (current <= totalLength) {
                    function.setCurrent(current);
                    if (totalLength == 0) {
                        break;
                    }
                    function.setPlayProcessSlider(current * 100 / totalLength);
                    current += 1;
                    Thread.sleep(1000);
                }
                function.setCurrent(0);
                function.setPlayProcessSlider(0);
                MusicPlayer.getInstance().playEnd();
            } catch (Exception e) {

            }
        }


    }

}
