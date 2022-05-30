# Update

更新说明：

## V1.3.1

* 移除屎山代码，音频视频播放均通过VLCJ实现，逻辑更简洁。
* 音频增加倍速调节功能。
* 支持大部分主流媒体格式。当前支持文件格式：[支持格式](#Format Supported)。

## V1.2.1

* 增加全屏播放功能（视频、音频均适用）。
* 在全屏状态下，部分UI交互界面无法使用，建议通过快捷键控制，[Usage-快捷键](#Usage) 。

## V1.1.3

* 增加速度调节功能（仅对视频有效）。具体操作方式请参考 [Usage-快捷键](#Usage) 。
* 修复切换音频文件时进度条进程无法启动的漏洞。

## V1.1.2

* 增加视频、音频音量调节功能。具体操作方式请参考 [Usage-快捷键](#Usage) 。

## V1.1.1

* 增加视频播放功能，支持大部分主流视频格式。
## V1.0.1
* 增加可供播放的音频格式，目前支持的音频格式有：
  * MP3
  * FLAC
  * OGG
  * WAV

# Background

山东大学2019级面向对象实验，所选课题：**媒体播放器**

课题要求：模拟实现一个多功能媒体播放器，它能播放音频（如mp3歌曲）、视频（选作，不属于基本要求）。媒体播放器界面，有进度条，可展示总播放时间、当前播放时间，进度条可拖动，有播放、暂停键，有快进、快退键。

# Setup

* **安装程序地址** ：https://github.com/AioliaRegulus/MediaPlayer/releases/download/V1.3.1/MediaPlayerSetup.exe

# Usage

使用Java Swing技术制作的可视化媒体播放器，用户可直接对操作交互式界面进行使用。

## 增加媒体

点击界面左上角  *添加至播放列表*  按钮，选择媒体文件即可。

## 删除媒体

**鼠标右击** 播放列表中的文件即可将其从播放列表中移除。

## 播放媒体

在播放列表中存在媒体文件的情况下，应用会自行读取媒体信息，用户只需点击播放按钮即可播放。

也可以通过 **鼠标左键** 点击左侧列表中的媒体文件进行播放。

## 快捷键

**SPACE** ：暂停/播放

**←** ：快退

**→** ：快进

**Ctrl + →** ：播放下一首

**Ctrl + ←** ：播放上一首

**Shift + ←**：降低一档速度

**Shift + →**：增加一档速度

**↑**：增加音量（10%）

**↓**：降低音量（10%）

**鼠标滚轮向上**：增加音量（`滚轮滚动行数`%）

**鼠标滚轮向下**：降低音量（`滚轮滚动行数`%）

**鼠标双击** ：进入/退出全屏模式

**ESC** ：退出全屏

# Format Supported

* **视频文件** ：*.3g2 *.3gp *.3gp2 *.3gpp *.amv *.asf *.avi *.bik *.bin *.divx *.drc *.dv *.f4v *.flv *.gvi *.gxf *.iso *.m1v *.m2v *.m2t *.m2ts *.m4v *.mkv *.mov *.mp2 *.mp4 *.mp4v *.mpe *.mpeg *.mpeg1 *.mpeg2 *.mpeg4 *.mpg *.mpv2 *.mts *.mxf *.mxg *.nsv *.nuv *.ogg *.ogm *.ogv *.ps *.rec *.rm *.rmvb *.rpl *.thp *.tod *.ts *.tts *.txd *.vob *.vro *.webm *.wm *.wmv *.wtv *.xesc
* **音频文件** ：*.3ga *.669 *.a52 *.acc *.ac3 *.adt *.adts *.aif *.aiff *.amr *.aob *.ape *.awb *.caf *.dts *.flac *.it *.kar *.m4a *.m4b *.m4p *.m5p *.mid *.mka *.mlp *.mod *.mpa *.mp1 *.mp2 *.mp3 *.mpc *.mpga *.mus *.oga *.ogg *.oma *.opus *.qcp *.ra *.rmi *.s3m *.sid *.spx *.thd *.tta *.voc *.vqf *.w64 *.wav *.wma *.wv *.xa *.xm

# Other

**VLCJ官网** ：https://capricasoftware.co.uk/#/projects/vlcj

**libvlc获取方式** ：

* 1.git clone vlc之后自行编译（该方法较为复杂，没有基础的同学可以参考方法二

* 2.进入VLCJ官网下载VLC播放器，进入其安装目录下，拷贝：

  * ./libvlc.dll
  * ./libvlccore.dll
  * ./plugins/*

  保存至项目文件夹内某一位置

