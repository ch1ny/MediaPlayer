# Update

更新说明：

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

**↑**：增加音量（10%）

**↓**：降低音量（10%）

**鼠标滚轮向上**：增加音量（`滚轮滚动行数`%）

**鼠标滚轮向下**：降低音量（`滚轮滚动行数`%）

# Q&A

**Q**：为什么播放列表中的某些歌曲不见了？

**A**：播放器在读取播放列表时会判断文件是否存在，如果文件不存在会自行将文件从播放列表中移除。



**Q**：为什么新版本加载音乐比老版本慢？

**A**：新版本为了增加可供播放的音频类型，修改了部分底层实现逻辑。

老版本是通过设置缓冲区，使音频文件以流的形式边读边写，增加I/O读写次数，但是用户不会感受到明显延迟。

新版本目前的实现方式是使用Clip类将音频资源一次性载入到进程中去，因此在音频初始化的过程用户可能会感受到有部分延迟的存在，有待后续更新。