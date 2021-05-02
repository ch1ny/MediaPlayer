/**
 * 允许播放格式 Wav, Flac, Mp3, Ogg
 * 解码所需依赖：
 * 播放Flac：Maven --> jflac
 * 播放Mp3：Maven --> jlayer; tritonus_share; mp3spi
 * 播放Ogg：Maven --> jorbis; vorbisspi; tritonus_share
 * 暂时无法播放格式 wma m4a aac ape
 */

package com.player.Player;

import com.player.Main;
import com.player.MainFrame;
import com.player.UI.Bottom.Bottom;
import com.player.UI.Bottom.FileName;
import com.player.UI.View.ViewPanel;
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
    private static MusicThread music; // 音频播放线程
    private static VideoThread video; // 视频播放线程
    private static int totalTime; // 播放媒体的长度
    private static int MoV; // 判断当前播放的媒体类型

    private static final int MUSIC = 0;
    private static final int VIDEO = 1;

    private static MediaPlayer player = new MediaPlayer();

    public static MediaPlayer getInstance() {
        return player;
    }

    public static void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        disabledLogger();
        Vector v = getFileList();
        if (!v.isEmpty()) {
            String path = (String) v.get(0);
            prepare(path);
            MainFrame.getBottom().getFunction().haveSongs();
        } else {
            totalTime = 0;
            if (music != null) {
                music.stop();
            }
            MainFrame.getView().getCover().setCover(null);
            MainFrame.hideVideo();
            MainFrame.getBottom().getFunction().noSongs();
            MainFrame.getBottom().getFileName().setTitle("");
            Process.getInstance().setProcess(0);
        }
        Process.getInstance().init();
    }

    public static void play(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        if (isPlaying) {
            if (MoV == MUSIC) {
                music.pause();
            } else if (MoV == VIDEO) {
                video.pause();
            }
        }
        filePath = path;
        prepare(path);
        Process.getInstance().changeMedia(totalTime);
        switch (MoV) {
            case MUSIC:
                music.start();
                break;
            case VIDEO:
                video.start();
                break;
        }
        isPlaying = true;
    }

    public static void pause() {
        switch (MoV) {
            case MUSIC:
                music.pause();
                break;
            case VIDEO:
                video.pause();
                break;
        }
        isPlaying = false;
    }

    public void go_on() {
        switch (MoV) {
            case MUSIC:
                music.go_on();
                break;
            case VIDEO:
                video.go_on();
                break;
        }
        isPlaying = true;
    }

    public void jump(double percent) {
        music.jump(percent);
    }

    public void jump(long time) {
        if (time < 0) {
            time = 0;
        } else if (time > totalTime * 1000) {
            time = totalTime * 1000;
        }
        video.jump(time);
    }

    public static void playEnd() throws UnsupportedAudioFileException, IOException, LineUnavailableException, ReadOnlyFileException, TagException, InvalidAudioFrameException, CannotReadException {
        isPlaying = false;
        playNext();
    }

    public static void playNext() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        filePath = getMediaName(1);
        play(filePath);
        Bottom.getFunction().playBegin();
    }

    public void playPrev() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException, LineUnavailableException, UnsupportedAudioFileException {
        filePath = getMediaName(-1);
        play(filePath);
        Bottom.getFunction().playBegin();
    }

    private static Vector getFileList() {
        File audio = new File("res/media");
        try {
            FileInputStream stream = new FileInputStream(audio);
            Scanner sc = new Scanner(stream);
            Vector list = new Vector();
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
        Vector v = getFileList();
        int length = v.size();
        String now = filePath;
        if (flag < 0) {
            for (int i = 1; i < length; i++) {
                if (v.get(i).toString().equals(now))
                    return v.get(i - 1).toString();
            }
            return v.get(length - 1).toString();
        } else {
            for (int i = 0; i < length - 1; i++) {
                if (v.get(i).toString().equals(now))
                    return v.get(i + 1).toString();
            }
            return v.get(0).toString();
        }
    }

    // 播放歌曲之前预先做好：准备好播放流、设置左下方标题
    private static void prepare(String path) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException, LineUnavailableException, UnsupportedAudioFileException {
        filePath = path;
        if (checkMediaExists(path)) {
            String mediaName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
            FileName left = MainFrame.getBottom().getFileName();
            left.setTitle(mediaName);
            switch (JudgeMoV.judgeMoV(filePath)) {
                case MUSIC:
                    ViewPanel view = MainFrame.getView();
                    BufferedImage bufferedImage = getCover(filePath);
                    view.getCover().setCover(bufferedImage);
                    MainFrame.hideVideo();
                    music = new MusicThread(path);
                    MoV = MUSIC;
                    break;
                case VIDEO:
                    MainFrame.showVideo();
                    video = new VideoThread(path);
                    MoV = VIDEO;
                    break;
            }
        }
    }

    private static BufferedImage getCover(String path) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException {
        String type = path.substring(path.lastIndexOf("."));
        BufferedImage bufferedImage = null;
        AudioFileReader reader;
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
            default:
                throw new IllegalStateException("Unexpected value: " + type.toLowerCase(Locale.ROOT));
        }
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
        return bufferedImage;
    }

    private static boolean checkMediaExists(String path) {
        if (!new File(path).exists()) {
            File media = new File("res/media");
            try {
                FileInputStream input = new FileInputStream(media);
                Scanner sc = new Scanner(input);
                String rest = "";
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (!line.equals(path)) {
                        rest += line + "\n";
                    }
                }
                input.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(media));
                bw.write(rest);
                bw.flush();
                bw.close();
                if (filePath.equals(path)) {
                    try {
                        playNext();
                        pause();
                    } catch (ArrayIndexOutOfBoundsException e1) {}
                    MainFrame.getBottom().getFunction().playEnd();
                    if (rest.equals("")) {
                        init();
                        MainFrame.getBottom().getFunction().noSongs();
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

    // 禁用 jaudiotagger 的Logger
    private static void disabledLogger() {
        AudioFileIO.logger.setLevel(Level.OFF);
        AbstractID3v2Tag.logger.setLevel(Level.OFF);
        NumberFixedLength.logger.setLevel(Level.OFF);
        VorbisCommentReader.logger.setLevel(Level.OFF);
    }

    private static class MusicThread extends Thread {
        private static String filepath; // 当前播放的文件路径
        private static Clip clip;
        private static AudioInputStream stream;
        private static int mark; // 记录暂停位置

        public MusicThread(String file) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
            filepath = file;
            stream = AudioSystem.getAudioInputStream(new File(filepath));
            AudioFormat format = stream.getFormat();
            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), 16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(), false);
                stream = AudioSystem.getAudioInputStream(format, stream);
            }
            clip = AudioSystem.getClip();
            clip.open(stream);
            totalTime = (int) (clip.getFrameLength() / format.getFrameRate());
        }

        @Override
        public void run() {
            play(0);
        }

        private void play(int frame) {
            try {
                clip.setFramePosition(frame);
                clip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void pause() {
            mark = clip.getFramePosition();
            Process.getInstance().pause();
            clip.stop();
        }

        private void go_on() {
            play(mark);
            Process.getInstance().go_on();
        }

        private void jump(double percent) {
            clip.stop();
            play((int) (percent * clip.getFrameLength()));
            double time = percent * totalTime;
            Process.getInstance().setProcess((int) time);
        }
    }

    private static class VideoThread extends Thread {
        private static String filepath; // 当前播放的文件路径
        private static EmbeddedMediaPlayerComponent player;

        public VideoThread(String file) {
            filepath = file;
            player = MainFrame.getVideo();
            player.getMediaPlayer().playMedia(filepath);
            while (!player.getMediaPlayer().isPlaying());
            player.getMediaPlayer().pause();
            totalTime = (int) (player.getMediaPlayer().getLength() / 1000);
        }

        @Override
        public void run() {
            go_on();
        }

        private void pause() {
            player.getMediaPlayer().pause();
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

    public MusicThread getMusic() {
        return music;
    }

    public VideoThread getVideo() {
        return video;
    }

    public int getMoV() {
        return MoV;
    }
}
