package com.player.Player;

import com.player.MainFrame;
import com.player.UI.Bottom.Function;

public class Process {

    private int totalLength;
    private static Process process = new Process();
    private ProcessThread processThread;

    public static Process getInstance() {
        return process;
    }

    public int getTotalLength() {
        return totalLength;
    }

    //初始化进度条
    public void init() {
        totalLength = MediaPlayer.getInstance().getLength();
        processThread = new ProcessThread(0, totalLength);
        processThread.start();
        processThread.suspend();
    }

    //暂停播放
    public void pause() {
        processThread.suspend();
    }

    //继续播放
    public void go_on() {
        processThread.resume();
    }

    //调整进度，current是确定的时刻（秒）
    public void setProcess(int current) {
        try {
            processThread.stop();
        } catch (NullPointerException e) {}
        processThread = new ProcessThread(current, totalLength);
        processThread.start();
    }

    public void changeMedia(int length) {
        totalLength = length;
        processThread = new ProcessThread(0, this.totalLength);
        processThread.start();
        pause();
    }

    //进度条线程
    class ProcessThread extends Thread {
        private int current;

        public ProcessThread(int now, int time) {
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
                MediaPlayer.getInstance().playEnd();
            } catch (Exception e) {

            }
        }


    }

}
