package com.player.Util;

import com.player.Main;
import com.player.MainFrame;
import com.player.Player.MediaPlayer;
import com.player.UI.Bottom.Bottom;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Author SDU德布罗煜
 * @Date 2021/5/7 15:23
 * @Version 1.0
 */
public class VideoClickListener extends MouseAdapter {
    private static  boolean flag = false;		//双击事件已执行时置为真
    private static int clickNum = 1;		//指示鼠标点击次数，默认为单击
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        final MouseEvent me = e;
        VideoClickListener.flag= false;
        if (VideoClickListener.clickNum==2) {
            //鼠标点击次数为2调用双击事件
            this.mouseClickedTwice(me);
            //调用完毕clickNum置为1
            VideoClickListener.clickNum=1;
            VideoClickListener.flag=true;
            return;
        }
        //新建定时器，双击检测间隔为300ms
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            //指示定时器执行次数
            int num = 0;
            @Override
            public void run() {
                // 双击事件已经执行，取消定时器任务
                if(VideoClickListener.flag) {
                    num=0;
                    VideoClickListener.clickNum=1;
                    this.cancel();
                    return;
                }
                //定时器再次执行，调用单击事件，然后取消定时器任务
                if (num==1) {
                    mouseClickedOnce(me);
                    VideoClickListener.flag=true;
                    VideoClickListener.clickNum=1;
                    num=0;
                    this.cancel();
                    return;
                }
                clickNum++;
                num++;
            }
        },new Date(), 300);
    }
    protected void mouseClickedOnce(MouseEvent me) {
        // 单击事件
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
        MainFrame.getFrame().requestFocus();
    }
    private void mouseClickedTwice(MouseEvent me) {
        // 双击事件
//        EmbeddedMediaPlayerComponent video = MainFrame.getVideo();
//        EmbeddedMediaPlayer player = video.getMediaPlayer();
//        player.setFullScreenStrategy(new Win32FullScreenStrategy(this));
//        player.to
//        MainFrame.getFrame().requestFocus();
    }
}
