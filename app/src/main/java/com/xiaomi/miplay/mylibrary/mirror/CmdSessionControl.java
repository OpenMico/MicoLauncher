package com.xiaomi.miplay.mylibrary.mirror;

import android.util.Log;
import com.xiaomi.miplay.mylibrary.Logger;
import com.xiaomi.miplay.mylibrary.MiDevice;
import com.xiaomi.mipush.sdk.Constants;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes4.dex */
public class CmdSessionControl {
    public static final int CHANNEL_CENTER = 0;
    public static final int CHANNEL_FRONT_LEFT = 1;
    public static final int CHANNEL_FRONT_RIGHT = 2;
    public static final int CMD_SESSION_ERROR_CONNECTION = -200002;
    public static final int CMD_SESSION_ERROR_HEARTBEAT = -200003;
    public static final int CMD_SESSION_ERROR_TIMEOUT = -200004;
    public static final int CMD_SESSION_ERROR_UNKNOWN = -200001;
    public static final int CMD_SESSION_INFO_CLOSE_ACK = 210002;
    public static final int CMD_SESSION_INFO_CONNECTED = 200001;
    public static final int CMD_SESSION_INFO_FLUSH_ACK = 210005;
    public static final int CMD_SESSION_INFO_GET_BT_FREQUENCY_ACK = 210019;
    public static final int CMD_SESSION_INFO_GET_CHANNEL_ACK = 210013;
    public static final int CMD_SESSION_INFO_GET_DEVICEINFO_ACK = 210015;
    public static final int CMD_SESSION_INFO_GET_MEDIA_INFO_ACK = 210011;
    public static final int CMD_SESSION_INFO_GET_MIRROR_MODE_ACK = 210017;
    public static final int CMD_SESSION_INFO_GET_POSITION_ACK = 210009;
    public static final int CMD_SESSION_INFO_GET_STATE_ACK = 210014;
    public static final int CMD_SESSION_INFO_GET_VOLUME_ACK = 210008;
    public static final int CMD_SESSION_INFO_MEDIAPLAYER_NEXT_ACK = 210026;
    public static final int CMD_SESSION_INFO_MEDIAPLAYER_PREV_ACK = 210025;
    public static final int CMD_SESSION_INFO_MUTE_AUDIO_ACK = 210016;
    public static final int CMD_SESSION_INFO_NOTIFY_BUFFERING_END = 211006;
    public static final int CMD_SESSION_INFO_NOTIFY_BUFFERING_START = 211005;
    public static final int CMD_SESSION_INFO_NOTIFY_CHANNEL = 211004;
    public static final int CMD_SESSION_INFO_NOTIFY_DEVICEINFO = 211007;
    public static final int CMD_SESSION_INFO_NOTIFY_FIRST_AUDIO_PCM = 211016;
    public static final int CMD_SESSION_INFO_NOTIFY_MEDIAINFO = 211003;
    public static final int CMD_SESSION_INFO_NOTIFY_MIRROR_MODE = 211014;
    public static final int CMD_SESSION_INFO_NOTIFY_NEXT = 211009;
    public static final int CMD_SESSION_INFO_NOTIFY_PAUSE = 211010;
    public static final int CMD_SESSION_INFO_NOTIFY_PLAY_POSTION = 211019;
    public static final int CMD_SESSION_INFO_NOTIFY_PREV = 211008;
    public static final int CMD_SESSION_INFO_NOTIFY_RESUME = 211011;
    public static final int CMD_SESSION_INFO_NOTIFY_SEEK = 211017;
    public static final int CMD_SESSION_INFO_NOTIFY_SEEK_DONE = 211020;
    public static final int CMD_SESSION_INFO_NOTIFY_SEIZE = 211015;
    public static final int CMD_SESSION_INFO_NOTIFY_STATE = 211001;
    public static final int CMD_SESSION_INFO_NOTIFY_STOP = 211012;
    public static final int CMD_SESSION_INFO_NOTIFY_STOP_ONE = 211018;
    public static final int CMD_SESSION_INFO_NOTIFY_SYNC_VOLUME = 211013;
    public static final int CMD_SESSION_INFO_NOTIFY_VOLUME = 211002;
    public static final int CMD_SESSION_INFO_OPEN_ACK = 210001;
    public static final int CMD_SESSION_INFO_PAUSE_ACK = 210003;
    public static final int CMD_SESSION_INFO_PAUSE_MEDIAPLAYER_ACK = 210022;
    public static final int CMD_SESSION_INFO_RESUME_ACK = 210004;
    public static final int CMD_SESSION_INFO_RESUME_MEDIAPLAYER_ACK = 210023;
    public static final int CMD_SESSION_INFO_SEEK_DONE_ACK = 210029;
    public static final int CMD_SESSION_INFO_SEEK_MEDIAPLAYER_ACK = 210024;
    public static final int CMD_SESSION_INFO_SET_BT_FREQUENCY_ACK = 210018;
    public static final int CMD_SESSION_INFO_SET_CHANNEL_ACK = 210012;
    public static final int CMD_SESSION_INFO_SET_DEVICEINFO_ACK = 210028;
    public static final int CMD_SESSION_INFO_SET_MEDIA_INFO_ACK = 210010;
    public static final int CMD_SESSION_INFO_SET_PLAYLIST_ACK = 210006;
    public static final int CMD_SESSION_INFO_SET_PLAY_SOURCE_ACK = 210020;
    public static final int CMD_SESSION_INFO_SET_POSITION_ACK = 210027;
    public static final int CMD_SESSION_INFO_SET_VLOUME_ACK = 210007;
    public static final int CMD_SESSION_INFO_START_MEDIAPLAYER_ACK = 210021;
    public static final int CMD_SESSION_NOTIFY_AUDIO_OPEN = 300003;
    public static final int CMD_SESSION_NOTIFY_PLAYFINISH = 300004;
    public static final int CMD_SESSION_NOTIFY_PRIVATE = 300999;
    public static final int CMD_SESSION_NOTIFY_VIDEO_PLAY = 300001;
    public static final int CMD_SESSION_NOTIFY_VIDEO_PLAY_ERROR = 300099;
    public static final int CMD_SESSION_NOTIFY_VIDEO_PLAY_SUCCESS = 300098;
    public static final int CMD_SESSION_NOTIFY_VIDEO_RESOLUTION = 300002;
    public static final int SESSION_TYPE_CLIENT = 2;
    public static final int SESSION_TYPE_NONE = 0;
    public static final int SESSION_TYPE_SERVER = 1;
    public static final int STATE_IDLED = 0;
    public static final int STATE_PAUSED = 3;
    public static final int STATE_PLAYED = 2;
    public static final int STATE_PREPARED = 1;
    private static String c = "CmdSessionControl-java";
    CmdClientCallback a;
    CmdServerCallback b;
    private String g;
    private MiDevice i;
    private long d = 0;
    private int e = 0;
    private volatile long f = 0;
    private Lock h = new ReentrantLock();

    private native void closeCmdSession(long j);

    private native int closeDevice(long j);

    private native long connectCmdSession(Object obj, String str, int i);

    private native long connectCmdSession2(Object obj, String str, int i, long j);

    private native long createCmdSession(Object obj, String str, int i);

    private native int flushDevice(long j);

    private native int getBtFrequency(long j);

    private native int getChannel(long j);

    private native int getDeviceInfo(long j);

    private native int getMediaInfo(long j);

    private native int getMirrorMode(long j);

    private native int getPosition(long j);

    private native int getState(long j);

    private native int getVolume(long j);

    private native int openAVDevice(long j, byte[] bArr, int i);

    private native int openDevice(long j, String str, int i);

    private native int openNotifyError(long j, byte[] bArr, int i);

    private native int pauseDevice(long j);

    private native int pauseMediaPlayer(long j);

    private native int playFinish(long j, int i);

    private native int playNext(long j);

    private native int playPrev(long j);

    private native void releaseMirrorLogInfo(long j);

    private native int resumeDevice(long j);

    private native int resumeMediaPlayer(long j);

    private native int seekMediaPlayer(long j, long j2);

    private native boolean sendCmdData(long j, byte[] bArr, int i);

    private native int setBtFrequency(long j, int i);

    private native int setBufferState(long j, int i);

    private native int setChannel(long j, int i);

    private native int setLocalDeviceInfo(long j, byte[] bArr, int i);

    private native int setMediaInfo(long j, byte[] bArr, int i);

    private native int setMediaState(long j, int i);

    private native int setPlayList(long j, byte[] bArr, int i);

    private native int setPlaySource(long j, byte[] bArr, int i);

    private native int setPosition(long j, long j2);

    private native int setSeekDone(long j);

    private native int setVolume(long j, int i);

    private native int startMediaPlayer(long j);

    public void recvCmdData(byte[] bArr) {
    }

    public void setDevice(MiDevice miDevice) {
        this.i = miDevice;
    }

    public CmdSessionControl(MiDevice miDevice) {
        String str = c;
        Logger.d(str, "CmdSessionControl dev:" + miDevice.toString(), new Object[0]);
        this.i = miDevice;
    }

    static {
        System.loadLibrary("audiomirror-jni");
    }

    public void addCmdClientCallback(CmdClientCallback cmdClientCallback) {
        this.a = cmdClientCallback;
    }

    public void addCmdServerCallback(CmdServerCallback cmdServerCallback) {
        this.b = cmdServerCallback;
    }

    public boolean createCmdSession(String str, int i) {
        this.h.lock();
        this.d = createCmdSession(this, str, i);
        boolean z = true;
        if (this.d != 0) {
            this.e = 1;
        } else {
            z = false;
        }
        this.h.unlock();
        return z;
    }

    public long ipToLong(String str) {
        String[] split = str.split("\\.");
        return (Long.parseLong(split[0]) << 24) + (Long.parseLong(split[1]) << 16) + (Long.parseLong(split[2]) << 8) + Long.parseLong(split[3]);
    }

    public boolean connectCmdSession(String str, int i) {
        boolean z;
        this.h.lock();
        this.d = connectCmdSession(this, str, i);
        if (this.d != 0) {
            this.e = 2;
            z = true;
        } else {
            z = false;
        }
        this.h.unlock();
        return z;
    }

    public boolean connectCmdSession(String str, String str2, String str3, int i) {
        this.h.lock();
        String replace = str.replace(Constants.COLON_SEPARATOR, "");
        long parseLong = Long.parseLong(replace, 16);
        String str4 = c;
        boolean z = false;
        Logger.d(str4, replace + " lab:" + parseLong, new Object[0]);
        this.g = str;
        this.d = connectCmdSession2(this, str3, i, parseLong);
        if (this.d != 0) {
            this.e = 2;
            z = true;
        }
        this.h.unlock();
        return z;
    }

    public void closeCmdSession() {
        this.h.lock();
        long j = this.d;
        if (j != 0) {
            closeCmdSession(j);
            this.d = 0L;
        }
        this.h.unlock();
    }

    public void closeCmdSession(String str) {
        this.h.lock();
        long j = this.d;
        if (j != 0) {
            closeCmdSession(j);
            this.d = 0L;
        }
        long parseLong = Long.parseLong(str.replace(Constants.COLON_SEPARATOR, ""), 16);
        String str2 = c;
        Logger.d(str2, "mac:" + str + " lab:" + parseLong, new Object[0]);
        releaseMirrorLogInfo(parseLong);
        this.h.unlock();
    }

    public boolean sendCmdData(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        boolean sendCmdData = j != 0 ? sendCmdData(j, bArr, bArr.length) : false;
        this.h.unlock();
        return sendCmdData;
    }

    public void onCmdSessionMediaInfoAck(byte[] bArr) {
        String str = c;
        Logger.d(str, this.g + " onCmdSessionMediaInfoAck", new Object[0]);
        CmdClientCallback cmdClientCallback = this.a;
        if (cmdClientCallback != null) {
            cmdClientCallback.onMediaInfoAck(this.i, bArr);
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback != null) {
            cmdServerCallback.onMediaInfoAck(this.i, bArr);
        }
    }

    public void onCmdSessionMediaInfoNotify(byte[] bArr) {
        String str = c;
        Logger.d(str, this.g + " onCmdSessionMediaInfoNotify", new Object[0]);
        CmdClientCallback cmdClientCallback = this.a;
        if (cmdClientCallback != null) {
            cmdClientCallback.onMediaInfoNotify(this.i, bArr);
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback != null) {
            cmdServerCallback.onMediaInfoNotify(this.i, bArr);
        }
    }

    public void onCmdSessionDeviceInfoAck(byte[] bArr) {
        String str = c;
        Logger.d(str, this.g + " onCmdSessionDeviceInfoAck", new Object[0]);
        CmdClientCallback cmdClientCallback = this.a;
        if (cmdClientCallback != null) {
            cmdClientCallback.onDeviceInfo(this.i, bArr);
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback != null) {
            cmdServerCallback.onDeviceInfo(this.i, bArr);
        }
    }

    public void onCmdSessionDeviceInfoNotify(byte[] bArr) {
        String str = c;
        Logger.d(str, this.g + " onCmdSessionDeviceInfoNotify", new Object[0]);
        CmdClientCallback cmdClientCallback = this.a;
        if (cmdClientCallback != null) {
            cmdClientCallback.onDeviceNetworkChanged(this.i, bArr);
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback != null) {
            cmdServerCallback.onDeviceNetworkChanged(this.i, bArr);
        }
    }

    public void onCmdSessionDeviceInfoExNotify(byte[] bArr) {
        String str = c;
        Logger.d(str, this.g + " onCmdSessionDeviceInfoExNotify", new Object[0]);
        CmdClientCallback cmdClientCallback = this.a;
        if (cmdClientCallback != null) {
            cmdClientCallback.onDeviceInfoChanged(this.i, bArr);
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback != null) {
            cmdServerCallback.onDeviceInfoChanged(this.i, bArr);
        }
    }

    public void onCmdSessionCommonNotify(int i, byte[] bArr) {
        String str = c;
        Log.d(str, this.g + "onCmdSessionCommonNotify" + i);
        CmdClientCallback cmdClientCallback = this.a;
        if (!(cmdClientCallback == null || i == 300001 || i == 300002)) {
            if (i == 300003) {
                this.a.onAudioOpenNotify(this.i, Integer.parseInt(new String(bArr)));
            } else if (i == 300004) {
                cmdClientCallback.onPlayFinish(this.i);
            } else if (i == 300098) {
                cmdClientCallback.onPlayed(this.i);
            } else if (i == 300099) {
                cmdClientCallback.onCirculateFail(this.i, bArr);
            } else if (i == 300999) {
                cmdClientCallback.onNotifyPropertiesInfo(this.i, bArr);
            }
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback == null) {
            return;
        }
        if (i == 300001) {
            cmdServerCallback.onStartPlater(this.i, bArr);
        } else if (i != 300002 && i != 300003 && i == 300999 && cmdServerCallback != null) {
            cmdServerCallback.onNotifyPropertiesInfo(this.i, bArr);
        }
    }

    public void onCmdSessionInfo(int i, int i2) {
        if (i != 200001) {
            switch (i) {
                case CMD_SESSION_INFO_OPEN_ACK /* 210001 */:
                    String str = c;
                    Logger.d(str, this.g + " CMD_SESSION_INFO_OPEN_ACK", new Object[0]);
                    CmdClientCallback cmdClientCallback = this.a;
                    if (cmdClientCallback != null) {
                        cmdClientCallback.onOpenAck(this.i, i2);
                    }
                    CmdServerCallback cmdServerCallback = this.b;
                    if (cmdServerCallback != null) {
                        cmdServerCallback.onOpenAck(this.i, i2);
                        return;
                    }
                    return;
                case CMD_SESSION_INFO_CLOSE_ACK /* 210002 */:
                case CMD_SESSION_INFO_PAUSE_ACK /* 210003 */:
                case CMD_SESSION_INFO_RESUME_ACK /* 210004 */:
                case CMD_SESSION_INFO_FLUSH_ACK /* 210005 */:
                case CMD_SESSION_INFO_SET_PLAYLIST_ACK /* 210006 */:
                case CMD_SESSION_INFO_SET_VLOUME_ACK /* 210007 */:
                case CMD_SESSION_INFO_SET_MEDIA_INFO_ACK /* 210010 */:
                    return;
                case CMD_SESSION_INFO_GET_VOLUME_ACK /* 210008 */:
                    String str2 = c;
                    Logger.d(str2, this.g + " CMD_SESSION_INFO_GET_VOLUME_ACK:" + i2, new Object[0]);
                    CmdClientCallback cmdClientCallback2 = this.a;
                    if (cmdClientCallback2 != null) {
                        cmdClientCallback2.onVolumeAck(this.i, i2);
                    }
                    CmdServerCallback cmdServerCallback2 = this.b;
                    if (cmdServerCallback2 != null) {
                        cmdServerCallback2.onVolumeAck(this.i, i2);
                        return;
                    }
                    return;
                case CMD_SESSION_INFO_GET_POSITION_ACK /* 210009 */:
                    String str3 = c;
                    Log.d(str3, this.g + " CMD_SESSION_INFO_GET_POSITION_ACK:" + i2);
                    CmdClientCallback cmdClientCallback3 = this.a;
                    if (cmdClientCallback3 != null) {
                        cmdClientCallback3.onPositionAck(this.i, i2);
                    }
                    CmdServerCallback cmdServerCallback3 = this.b;
                    if (cmdServerCallback3 != null) {
                        cmdServerCallback3.onPositionAck(this.i, i2);
                        return;
                    }
                    return;
                default:
                    switch (i) {
                        case CMD_SESSION_INFO_SET_CHANNEL_ACK /* 210012 */:
                            return;
                        case CMD_SESSION_INFO_GET_CHANNEL_ACK /* 210013 */:
                            String str4 = c;
                            Logger.d(str4, this.g + " CMD_SESSION_INFO_GET_CHANNEL_ACK:" + i2, new Object[0]);
                            CmdClientCallback cmdClientCallback4 = this.a;
                            if (cmdClientCallback4 != null) {
                                cmdClientCallback4.onChannelsAck(this.i, i2);
                            }
                            CmdServerCallback cmdServerCallback4 = this.b;
                            if (cmdServerCallback4 != null) {
                                cmdServerCallback4.onChannelsAck(this.i, i2);
                                return;
                            }
                            return;
                        case CMD_SESSION_INFO_GET_STATE_ACK /* 210014 */:
                            CmdClientCallback cmdClientCallback5 = this.a;
                            if (cmdClientCallback5 != null) {
                                cmdClientCallback5.onPlayStateAck(this.i, i2);
                            }
                            CmdServerCallback cmdServerCallback5 = this.b;
                            if (cmdServerCallback5 != null) {
                                cmdServerCallback5.onPlayStateAck(this.i, i2);
                                return;
                            }
                            return;
                        default:
                            switch (i) {
                                case CMD_SESSION_INFO_MUTE_AUDIO_ACK /* 210016 */:
                                case CMD_SESSION_INFO_SET_BT_FREQUENCY_ACK /* 210018 */:
                                case CMD_SESSION_INFO_SET_PLAY_SOURCE_ACK /* 210020 */:
                                case CMD_SESSION_INFO_START_MEDIAPLAYER_ACK /* 210021 */:
                                case CMD_SESSION_INFO_PAUSE_MEDIAPLAYER_ACK /* 210022 */:
                                case CMD_SESSION_INFO_RESUME_MEDIAPLAYER_ACK /* 210023 */:
                                case CMD_SESSION_INFO_SEEK_MEDIAPLAYER_ACK /* 210024 */:
                                case CMD_SESSION_INFO_MEDIAPLAYER_PREV_ACK /* 210025 */:
                                case CMD_SESSION_INFO_MEDIAPLAYER_NEXT_ACK /* 210026 */:
                                case CMD_SESSION_INFO_SET_POSITION_ACK /* 210027 */:
                                case CMD_SESSION_INFO_SET_DEVICEINFO_ACK /* 210028 */:
                                    return;
                                case CMD_SESSION_INFO_GET_MIRROR_MODE_ACK /* 210017 */:
                                    String str5 = c;
                                    Logger.d(str5, this.g + " CMD_SESSION_INFO_GET_MIRROR_MODE_ACK:" + i2, new Object[0]);
                                    CmdClientCallback cmdClientCallback6 = this.a;
                                    if (cmdClientCallback6 != null) {
                                        cmdClientCallback6.onMirrorModeAck(this.i, i2);
                                    }
                                    CmdServerCallback cmdServerCallback6 = this.b;
                                    if (cmdServerCallback6 != null) {
                                        cmdServerCallback6.onMirrorModeAck(this.i, i2);
                                        return;
                                    }
                                    return;
                                case CMD_SESSION_INFO_GET_BT_FREQUENCY_ACK /* 210019 */:
                                    String str6 = c;
                                    Logger.d(str6, this.g + " CMD_SESSION_INFO_GET_BT_FREQUENCY_ACK:" + i2, new Object[0]);
                                    CmdClientCallback cmdClientCallback7 = this.a;
                                    if (cmdClientCallback7 != null) {
                                        cmdClientCallback7.onBtFrequencyACK(this.i, i2);
                                    }
                                    CmdServerCallback cmdServerCallback7 = this.b;
                                    if (cmdServerCallback7 != null) {
                                        cmdServerCallback7.onBtFrequencyACK(this.i, i2);
                                        return;
                                    }
                                    return;
                                default:
                                    switch (i) {
                                        case CMD_SESSION_INFO_NOTIFY_STATE /* 211001 */:
                                            String str7 = c;
                                            Logger.d(str7, this.g + " CMD_SESSION_INFO_NOTIFY_STATE:" + i2, new Object[0]);
                                            CmdClientCallback cmdClientCallback8 = this.a;
                                            if (cmdClientCallback8 != null) {
                                                cmdClientCallback8.onPlayStateNotify(this.i, i2);
                                            }
                                            CmdServerCallback cmdServerCallback8 = this.b;
                                            if (cmdServerCallback8 != null) {
                                                cmdServerCallback8.onPlayStateNotify(this.i, i2);
                                                return;
                                            }
                                            return;
                                        case CMD_SESSION_INFO_NOTIFY_VOLUME /* 211002 */:
                                            String str8 = c;
                                            Logger.d(str8, this.g + " CMD_SESSION_INFO_NOTIFY_VOLUME:" + i2, new Object[0]);
                                            CmdClientCallback cmdClientCallback9 = this.a;
                                            if (cmdClientCallback9 != null) {
                                                cmdClientCallback9.onVolumeNotify(this.i, i2);
                                            }
                                            CmdServerCallback cmdServerCallback9 = this.b;
                                            if (cmdServerCallback9 != null) {
                                                cmdServerCallback9.onVolumeNotify(this.i, i2);
                                                return;
                                            }
                                            return;
                                        default:
                                            switch (i) {
                                                case CMD_SESSION_INFO_NOTIFY_CHANNEL /* 211004 */:
                                                    String str9 = c;
                                                    Logger.d(str9, this.g + " CMD_SESSION_INFO_NOTIFY_CHANNEL:" + i2, new Object[0]);
                                                    return;
                                                case CMD_SESSION_INFO_NOTIFY_BUFFERING_START /* 211005 */:
                                                    String str10 = c;
                                                    Logger.d(str10, this.g + " CMD_SESSION_INFO_NOTIFY_BUFFERING_START", new Object[0]);
                                                    CmdClientCallback cmdClientCallback10 = this.a;
                                                    if (cmdClientCallback10 != null) {
                                                        cmdClientCallback10.onBufferStateNotify(this.i, i2);
                                                    }
                                                    CmdServerCallback cmdServerCallback10 = this.b;
                                                    if (cmdServerCallback10 != null) {
                                                        cmdServerCallback10.onBufferStateNotify(this.i, i2);
                                                        return;
                                                    }
                                                    return;
                                                case CMD_SESSION_INFO_NOTIFY_BUFFERING_END /* 211006 */:
                                                    String str11 = c;
                                                    Logger.d(str11, this.g + " CMD_SESSION_INFO_NOTIFY_BUFFERING_END", new Object[0]);
                                                    CmdClientCallback cmdClientCallback11 = this.a;
                                                    if (cmdClientCallback11 != null) {
                                                        cmdClientCallback11.onBufferStateNotify(this.i, i2);
                                                    }
                                                    CmdServerCallback cmdServerCallback11 = this.b;
                                                    if (cmdServerCallback11 != null) {
                                                        cmdServerCallback11.onBufferStateNotify(this.i, i2);
                                                        return;
                                                    }
                                                    return;
                                                default:
                                                    switch (i) {
                                                        case CMD_SESSION_INFO_NOTIFY_PREV /* 211008 */:
                                                            String str12 = c;
                                                            Logger.d(str12, this.g + " CMD_SESSION_INFO_NOTIFY_PREV", new Object[0]);
                                                            CmdClientCallback cmdClientCallback12 = this.a;
                                                            if (cmdClientCallback12 != null) {
                                                                cmdClientCallback12.onPrevNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback12 = this.b;
                                                            if (cmdServerCallback12 != null) {
                                                                cmdServerCallback12.onPrevNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_NEXT /* 211009 */:
                                                            String str13 = c;
                                                            Logger.d(str13, this.g + " CMD_SESSION_INFO_NOTIFY_NEXT", new Object[0]);
                                                            CmdClientCallback cmdClientCallback13 = this.a;
                                                            if (cmdClientCallback13 != null) {
                                                                cmdClientCallback13.onNextNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback13 = this.b;
                                                            if (cmdServerCallback13 != null) {
                                                                cmdServerCallback13.onNextNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_PAUSE /* 211010 */:
                                                            String str14 = c;
                                                            Logger.d(str14, this.g + " CMD_SESSION_INFO_NOTIFY_PAUSE", new Object[0]);
                                                            CmdClientCallback cmdClientCallback14 = this.a;
                                                            if (cmdClientCallback14 != null) {
                                                                cmdClientCallback14.onPauseNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback14 = this.b;
                                                            if (cmdServerCallback14 != null) {
                                                                cmdServerCallback14.onPauseNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_RESUME /* 211011 */:
                                                            String str15 = c;
                                                            Logger.d(str15, this.g + " CMD_SESSION_INFO_NOTIFY_RESUME", new Object[0]);
                                                            CmdClientCallback cmdClientCallback15 = this.a;
                                                            if (cmdClientCallback15 != null) {
                                                                cmdClientCallback15.onResumeNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback15 = this.b;
                                                            if (cmdServerCallback15 != null) {
                                                                cmdServerCallback15.onResumeNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_STOP /* 211012 */:
                                                            String str16 = c;
                                                            Logger.d(str16, this.g + " CMD_SESSION_INFO_NOTIFY_STOP", new Object[0]);
                                                            CmdClientCallback cmdClientCallback16 = this.a;
                                                            if (cmdClientCallback16 != null) {
                                                                cmdClientCallback16.onDisconnectNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback16 = this.b;
                                                            if (cmdServerCallback16 != null) {
                                                                cmdServerCallback16.onDisconnectNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_SYNC_VOLUME /* 211013 */:
                                                            String str17 = c;
                                                            Logger.d(str17, this.g + " CMD_SESSION_INFO_NOTIFY_SYNC_VOLUME:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback17 = this.a;
                                                            if (cmdClientCallback17 != null) {
                                                                cmdClientCallback17.onPhysicalBtVolumeNotify(this.i, i2);
                                                            }
                                                            CmdServerCallback cmdServerCallback17 = this.b;
                                                            if (cmdServerCallback17 != null) {
                                                                cmdServerCallback17.onPhysicalBtVolumeNotify(this.i, i2);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_MIRROR_MODE /* 211014 */:
                                                            String str18 = c;
                                                            Logger.d(str18, this.g + " CMD_SESSION_INFO_NOTIFY_MIRROR_MODE:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback18 = this.a;
                                                            if (cmdClientCallback18 != null) {
                                                                cmdClientCallback18.onMirrorModeNotify(this.i, i2);
                                                            }
                                                            CmdServerCallback cmdServerCallback18 = this.b;
                                                            if (cmdServerCallback18 != null) {
                                                                cmdServerCallback18.onMirrorModeNotify(this.i, i2);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_SEIZE /* 211015 */:
                                                            String str19 = c;
                                                            Logger.d(str19, this.g + " CMD_SESSION_INFO_NOTIFY_SEIZE:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback19 = this.a;
                                                            if (cmdClientCallback19 != null) {
                                                                cmdClientCallback19.onBeSeized(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback19 = this.b;
                                                            if (cmdServerCallback19 != null) {
                                                                cmdServerCallback19.onBeSeized(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_FIRST_AUDIO_PCM /* 211016 */:
                                                            String str20 = c;
                                                            Logger.d(str20, this.g + " CMD_SESSION_INFO_NOTIFY_FIRST_AUDIO_PCM:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback20 = this.a;
                                                            if (cmdClientCallback20 != null) {
                                                                cmdClientCallback20.onDeviceStartPlaying(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback20 = this.b;
                                                            if (cmdServerCallback20 != null) {
                                                                cmdServerCallback20.onDeviceStartPlaying(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_SEEK /* 211017 */:
                                                            String str21 = c;
                                                            Logger.d(str21, this.g + " CMD_SESSION_INFO_NOTIFY_SEEK:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback21 = this.a;
                                                            if (cmdClientCallback21 != null) {
                                                                cmdClientCallback21.onSeekNotify(this.i, i2);
                                                            }
                                                            CmdServerCallback cmdServerCallback21 = this.b;
                                                            if (cmdServerCallback21 != null) {
                                                                cmdServerCallback21.onSeekNotify(this.i, i2);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_STOP_ONE /* 211018 */:
                                                            String str22 = c;
                                                            Logger.d(str22, this.g + " CMD_SESSION_INFO_NOTIFY_STOP_ONE:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback22 = this.a;
                                                            if (!(cmdClientCallback22 == null || cmdClientCallback22 == null)) {
                                                                cmdClientCallback22.onDisConnectOneNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback22 = this.b;
                                                            if (cmdServerCallback22 != null && cmdServerCallback22 != null) {
                                                                cmdServerCallback22.onDisConnectOneNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_PLAY_POSTION /* 211019 */:
                                                            String str23 = c;
                                                            Logger.d(str23, this.g + " CMD_SESSION_INFO_NOTIFY_PLAY_POSTION:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback23 = this.a;
                                                            if (cmdClientCallback23 != null) {
                                                                cmdClientCallback23.onDurationUpdated(this.i, i2);
                                                            }
                                                            CmdServerCallback cmdServerCallback23 = this.b;
                                                            if (cmdServerCallback23 != null) {
                                                                cmdServerCallback23.onDurationUpdated(this.i, i2);
                                                                return;
                                                            }
                                                            return;
                                                        case CMD_SESSION_INFO_NOTIFY_SEEK_DONE /* 211020 */:
                                                            String str24 = c;
                                                            Logger.d(str24, this.g + " CMD_SESSION_INFO_NOTIFY_SEEK_DONE:" + i2, new Object[0]);
                                                            CmdClientCallback cmdClientCallback24 = this.a;
                                                            if (cmdClientCallback24 != null) {
                                                                cmdClientCallback24.onSeekDoneNotify(this.i);
                                                            }
                                                            CmdServerCallback cmdServerCallback24 = this.b;
                                                            if (cmdServerCallback24 != null) {
                                                                cmdServerCallback24.onSeekDoneNotify(this.i);
                                                                return;
                                                            }
                                                            return;
                                                        default:
                                                            String str25 = c;
                                                            Logger.d(str25, this.g + " onCmdSessionInfo what:" + i + " extra:" + i2, new Object[0]);
                                                            return;
                                                    }
                                            }
                                    }
                            }
                    }
            }
        } else {
            String str26 = c;
            Logger.d(str26, this.g + " CMD_SESSION_INFO_CONNECTED", new Object[0]);
            CmdClientCallback cmdClientCallback25 = this.a;
            if (cmdClientCallback25 != null) {
                cmdClientCallback25.onSuccess();
            }
            CmdServerCallback cmdServerCallback25 = this.b;
            if (cmdServerCallback25 != null) {
                cmdServerCallback25.onSuccess();
            }
        }
    }

    public void onCmdSessionError(int i, int i2) {
        String str = c;
        Logger.d(str, this.g + " onCmdSessionError what:" + i + " extra:" + i2, new Object[0]);
        CmdClientCallback cmdClientCallback = this.a;
        if (cmdClientCallback != null) {
            cmdClientCallback.onError();
        }
        CmdServerCallback cmdServerCallback = this.b;
        if (cmdServerCallback != null) {
            cmdServerCallback.onError();
        }
    }

    public int openDevice(String str, int i) {
        this.h.lock();
        if (this.d == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        String str2 = c;
        Logger.d(str2, this.g + " openDevice.", new Object[0]);
        int openDevice = openDevice(this.d, str, i);
        this.h.unlock();
        return openDevice;
    }

    public int closeDevice() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return 0;
        }
        int closeDevice = closeDevice(j);
        this.h.unlock();
        return closeDevice;
    }

    public int pauseDevice() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int pauseDevice = pauseDevice(j);
        this.h.unlock();
        return pauseDevice;
    }

    public int resumeDevice() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int resumeDevice = resumeDevice(j);
        this.h.unlock();
        return resumeDevice;
    }

    public int playFinish(int i) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int playFinish = playFinish(j, i);
        this.h.unlock();
        return playFinish;
    }

    public int seek(long j) {
        this.h.lock();
        long j2 = this.d;
        if (j2 == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        this.f = j;
        int flushDevice = flushDevice(j2);
        this.h.unlock();
        return flushDevice;
    }

    public int setBufferState(int i) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int bufferState = setBufferState(j, i);
        this.h.unlock();
        return bufferState;
    }

    public int setVolume(int i) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int volume = setVolume(j, i);
        this.h.unlock();
        return volume;
    }

    public int getVolume() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int volume = getVolume(j);
        this.h.unlock();
        return volume;
    }

    public int setPosition(long j) {
        this.h.lock();
        long j2 = this.d;
        if (j2 == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int position = setPosition(j2, j);
        this.h.unlock();
        return position;
    }

    public int getPosition() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int position = getPosition(j);
        this.h.unlock();
        return position;
    }

    public int setPlayList(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int playList = setPlayList(j, bArr, bArr.length);
        this.h.unlock();
        return playList;
    }

    public int setMediaState(int i) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int mediaState = setMediaState(j, i);
        this.h.unlock();
        return mediaState;
    }

    public int setMediaInfo(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int mediaInfo = setMediaInfo(j, bArr, bArr.length);
        this.h.unlock();
        return mediaInfo;
    }

    public int getMediaInfo() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int mediaInfo = getMediaInfo(j);
        this.h.unlock();
        return mediaInfo;
    }

    public int setLocalDeviceInfo(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int localDeviceInfo = setLocalDeviceInfo(j, bArr, bArr.length);
        this.h.unlock();
        return localDeviceInfo;
    }

    public int getDeviceInfo() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int deviceInfo = getDeviceInfo(j);
        this.h.unlock();
        return deviceInfo;
    }

    public int setChannel(int i) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int channel = setChannel(j, i);
        this.h.unlock();
        return channel;
    }

    public int getChannel() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int channel = getChannel(j);
        this.h.unlock();
        return channel;
    }

    public int getState() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int state = getState(j);
        this.h.unlock();
        return state;
    }

    public int getMirrorMode() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int mirrorMode = getMirrorMode(j);
        this.h.unlock();
        return mirrorMode;
    }

    public int setBtFrequency(int i) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int btFrequency = setBtFrequency(j, i);
        this.h.unlock();
        return btFrequency;
    }

    public int getBtFrequency() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int btFrequency = getBtFrequency(j);
        this.h.unlock();
        return btFrequency;
    }

    public int setPlaySource(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int playSource = setPlaySource(j, bArr, bArr.length);
        this.h.unlock();
        return playSource;
    }

    public int startMediaPlayer() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int startMediaPlayer = startMediaPlayer(j);
        this.h.unlock();
        return startMediaPlayer;
    }

    public int pauseMediaPlayer() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int pauseMediaPlayer = pauseMediaPlayer(j);
        this.h.unlock();
        return pauseMediaPlayer;
    }

    public int resumeMediaPlayer() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int resumeMediaPlayer = resumeMediaPlayer(j);
        this.h.unlock();
        return resumeMediaPlayer;
    }

    public int seekMediaPlayer(long j) {
        this.h.lock();
        long j2 = this.d;
        if (j2 == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int seekMediaPlayer = seekMediaPlayer(j2, j);
        this.h.unlock();
        return seekMediaPlayer;
    }

    public int playPrev() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int playPrev = playPrev(j);
        this.h.unlock();
        return playPrev;
    }

    public int playNext() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int playNext = playNext(j);
        this.h.unlock();
        return playNext;
    }

    public int openAVDevice(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int openAVDevice = openAVDevice(j, bArr, bArr.length);
        this.h.unlock();
        return openAVDevice;
    }

    public int openNotifyError(byte[] bArr) {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int openNotifyError = openNotifyError(j, bArr, bArr.length);
        this.h.unlock();
        return openNotifyError;
    }

    public int setSeekDone() {
        this.h.lock();
        long j = this.d;
        if (j == 0 || this.e != 2) {
            this.h.unlock();
            return -1;
        }
        int seekDone = setSeekDone(j);
        this.h.unlock();
        return seekDone;
    }
}
