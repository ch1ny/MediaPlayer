/**
 * 允许播放格式 Wav, Flac, Mp3, Ogg
 * 解码所需依赖：
 * 播放Flac：Maven --> jflac
 * 播放Mp3：Maven --> jlayer; tritonus_share; mp3spi
 * 播放Ogg：Maven --> jorbis; vorbisspi; tritonus_share
 * 暂时无法播放格式 wma m4a aac ape
 */

package com.player.Player;

import com.player.MainFrame;
import com.player.UI.Bottom.Bottom;
import com.player.UI.Bottom.FileName;
import com.player.UI.View.ViewPanel;
import com.player.Util.AudioFormat;
import com.player.Util.JudgeMoV;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.generic.AudioFileReader;
import org.jaudiotagger.audio.mp3.MP3FileReader;
import org.jaudiotagger.audio.ogg.OggFileReader;
import org.jaudiotagger.audio.wav.WavFileReader;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;
import org.jaudiotagger.tag.datatype.NumberFixedLength;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentReader;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;

public class MediaPlayer {
    private static boolean isPlaying = false; // 正在播放
    private static String filePath; // 当前处理的文件路径
    private static MediaThread media; // 媒体播放线程
    private static int totalTime; // 播放媒体的长度

    private static final int MUSIC = 0;
    private static final int VIDEO = 1;

    private static final MediaPlayer player = new MediaPlayer();

    public static MediaPlayer getInstance() {
        return player;
    }

    public static void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        disabledLogger();
        Vector<String> v = getFileList();
        assert v != null;
        if (!v.isEmpty()) {
            String path = (String) v.get(0);
            prepare(path);
            Bottom.getFunction().haveSongs();
        } else {
            totalTime = 0;
            MainFrame.getView().getCover().setCover(null);
            MainFrame.hideVideo();
            Bottom.getFunction().noSongs();
            Bottom.getFileName().setTitle("");
            Process.getInstance().setProcess(0);
        }
        Process.getInstance().init();
    }

    public static void play(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        if (isPlaying) {
            media.pause();
        }
        filePath = path;
        prepare(path);
        Process.getInstance().changeMedia(totalTime);
        media.start();
        isPlaying = true;
    }

    public static void pause() {
        media.pause();
        isPlaying = false;
    }

    public void go_on() {
        media.go_on();
        isPlaying = true;
    }

    public void jump(long time) {
        if (time < 0) {
            time = 0;
        } else if (time > totalTime * 1000L) {
            time = totalTime * 1000L;
        }
        media.jump(time);
        media.go_on();
    }

    public static void playEnd() throws UnsupportedAudioFileException, IOException, LineUnavailableException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException, InterruptedException {
        isPlaying = false;
        playNext();
    }

    public static void playNext() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        filePath = getMediaName(1);
        play(filePath);
        Bottom.getFunction().playBegin();
    }

    public static void playPrev() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        filePath = getMediaName(-1);
        play(filePath);
        Bottom.getFunction().playBegin();
    }

    private static Vector<String> getFileList() {
        File audio = new File("res/media");
        try {
            FileInputStream stream = new FileInputStream(audio);
            Scanner sc = new Scanner(stream);
            Vector<String> list = new Vector<String>();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                list.add(line);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMediaName(int flag) {
        Vector<String> v = getFileList();
        assert v != null;
        int length = v.size();
        String now = filePath;
        if (flag < 0) {
            for (int i = 1; i < length; i++) {
                if (v.get(i).equals(now))
                    return v.get(i - 1);
            }
            return v.get(length - 1);
        } else {
            for (int i = 0; i < length - 1; i++) {
                if (v.get(i).equals(now))
                    return v.get(i + 1);
            }
            return v.get(0);
        }
    }

    // 播放歌曲之前预先做好：准备好播放流、设置左下方标题
    private static void prepare(String path) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException, LineUnavailableException, UnsupportedAudioFileException {
        filePath = path;
        if (checkMediaExists(path)) {
            String mediaName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
            FileName left = Bottom.getFileName();
            left.setTitle(mediaName);
            switch (JudgeMoV.judgeMoV(filePath)) {
                case MUSIC:
                    ViewPanel view = MainFrame.getView();
                    BufferedImage bufferedImage = getCover(filePath);
                    view.getCover().setCover(bufferedImage);
                    MainFrame.hideVideo();
                    media = new MediaThread(path);
                    break;
                case VIDEO:
                    MainFrame.showVideo();
                    media = new MediaThread(path);
                    break;
            }
        }
    }

    private static BufferedImage getCover(String path) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        String type = path.substring(path.lastIndexOf("."));
        BufferedImage bufferedImage = null;
        if (AudioFormat.hasCover(path)) {
            AudioFileReader reader = null;
            switch (type.toLowerCase(Locale.ROOT)) {
                case ".mp3":
                    reader = new MP3FileReader();
                    break;
                case ".flac":
                    reader = new FlacFileReader();
                    break;
                case ".ogg":
                    reader = new OggFileReader();
                    break;
                case ".wav":
                    reader = new WavFileReader();
                    break;
            }
            assert reader != null;
            Artwork artwork = reader.read(new File(path)).getTag().getFirstArtwork();
            try {
                bufferedImage = artwork.getImage();
            } catch (NullPointerException e) {
                FileImageInputStream unknown = new FileImageInputStream(new File("res/icon/unknown.png"));
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int numBytesRead = 0;
                while ((numBytesRead = unknown.read(buf)) != -1) {
                    output.write(buf, 0, numBytesRead);
                }
                ByteArrayInputStream bais = new ByteArrayInputStream(output.toByteArray());
                bufferedImage = ImageIO.read(bais);
                output.close();
                unknown.close();
            }
        } else {
            FileImageInputStream unknown = new FileImageInputStream(new File("res/icon/unknown.png"));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = unknown.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(output.toByteArray());
            bufferedImage = ImageIO.read(bais);
            output.close();
            unknown.close();
        }
        return bufferedImage;
    }

    private static boolean checkMediaExists(String path) {
        if (!new File(path).exists()) {
            File media = new File("res/media");
            try {
                FileInputStream input = new FileInputStream(media);
                Scanner sc = new Scanner(input);
                StringBuilder rest = new StringBuilder();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (!line.equals(path)) {
                        rest.append(line).append("\n");
                    }
                }
                input.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(media));
                bw.write(rest.toString());
                bw.flush();
                bw.close();
                if (filePath.equals(path)) {
                    try {
                        playNext();
                        pause();
                    } catch (ArrayIndexOutOfBoundsException ignored) {}
                    Bottom.getFunction().playEnd();
                    if (rest.toString().equals("")) {
                        init();
                        Bottom.getFunction().noSongs();
                    }
                }
                MainFrame.rebuildList();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return false;
        }
        return true;
    }

    // 禁用 jaudiotagger 的 Logger
    private static void disabledLogger() {
        AudioFileIO.logger.setLevel(Level.OFF);
        AbstractID3v2Tag.logger.setLevel(Level.OFF);
        NumberFixedLength.logger.setLevel(Level.OFF);
        VorbisCommentReader.logger.setLevel(Level.OFF);
    }

    private static class MediaThread extends Thread {
        private static EmbeddedMediaPlayerComponent player;

        public MediaThread(String file) {
            // 当前播放的文件路径
            player = MainFrame.getMedia();
            player.getMediaPlayer().playMedia(file);
            while (!player.getMediaPlayer().isPlaying());
            player.getMediaPlayer().pause();
            totalTime = (int) (player.getMediaPlayer().getLength() / 1000);
        }

        @Override
        public void run() {
            go_on();
        }

        private void pause() {
            if (isPlaying) {
                player.getMediaPlayer().pause();
            }
            Process.getInstance().pause();
        }

        private void go_on() {
            player.getMediaPlayer().play();
            Process.getInstance().go_on();
        }

        private void jump(long time) {
            player.getMediaPlayer().setTime(time);
            Process.getInstance().setProcess((int) (time / 1000));
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getLength() {
        return totalTime;
    }

    public MediaThread getMedia() {
        return media;
    }
}
