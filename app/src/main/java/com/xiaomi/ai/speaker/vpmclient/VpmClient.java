package com.xiaomi.ai.speaker.vpmclient;

import android.os.Handler;
import android.os.Parcel;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public final class VpmClient {
    private static final String LOG_TAG = "[VpmClientJava]:";
    public static final int VPM_CLIENT_ASR_CANCEL = 4;
    public static final int VPM_CLIENT_ASR_END = 3;
    public static final int VPM_CLIENT_ASR_START = 2;
    public static final int VPM_CLIENT_FOCUS_LOSS = 12;
    public static final int VPM_CLIENT_MULTI_CHS_WUW = 13;
    public static final int VPM_CLIENT_NEED_RESUME = 14;
    public static final int VPM_CLIENT_REFRESH_TOKEN = 10;
    public static final int VPM_CLIENT_STAT_POINTS = 7;
    public static final int VPM_CLIENT_VERSION = 5;
    public static final int VPM_CLIENT_WAKEUP = 0;
    public static final int VPM_CLIENT_WAKEUP_CANCEL = 9;
    public static final int VPM_CLIENT_WAKEUP_LOCAL = 8;
    public static final int VPM_CLIENT_WAKEUP_TIMEOUT = 1;
    public static final int VPM_CLIENT_WUW = 6;
    private static final int VPM_ST_IDLE = 0;
    private static final int VPM_ST_INIT = 1;
    private static final int VPM_ST_START = 2;
    private static final int VPM_ST_STOP = 3;
    private final int MSG_VPM_START;
    private final String mAppId;
    private Handler mHandler;
    private ILogHook mLog;
    private int mStatus;

    /* loaded from: classes3.dex */
    public interface ILogHook {
        void onLog(String str, Object... objArr);
    }

    private static native int create(Object obj, String str, int i, int i2, int i3);

    private static native int destroy();

    private static native int dialogMode(int i);

    private static native int disableOneShot();

    private static native int disableVad();

    private static native int disableVoipData();

    private static native int disableWakeupData();

    private static native int enableOneShot();

    private static native int enableVad();

    private static native int enableVoipData();

    private static native int enableWakeupData();

    private static native int notifyAsrLength(int i);

    private static native int pauseAsrData();

    private static native int read(byte[] bArr, int i, int i2, boolean z);

    private static native int readMultiChsAsr(byte[] bArr, int i, int i2, boolean z);

    private static native int readMultiChsWuw(byte[] bArr, int i, int i2);

    private static native int resumeAsrData();

    private static native int setInfo(int i, int i2, int i3, Parcel parcel);

    private static native int setMultiAsr(int i);

    private static native int setUnWakeup();

    private static native int setWaitAsrTimeout(int i);

    private static native int setWakeup();

    private static native int setWakeupSense(int i);

    private static native int setWorking(int i);

    private static native int start();

    private static native int startUpload();

    private static native int stop();

    private static native int stopUpload();

    private static native int updateDeviceInfo(String str, String str2, String str3, String str4, String str5, String str6);

    private static native int updateWifiInfo(String str, String str2, int i);

    private static native int uploadLog();

    private static native int voiceWakeupControl(int i);

    static {
        System.loadLibrary("mivpmclient");
    }

    public VpmClient(Handler handler, String str, String str2, int i) {
        this.mStatus = 0;
        vpmCreate(str2);
        this.mAppId = str;
        this.mHandler = handler;
        this.MSG_VPM_START = i;
    }

    public VpmClient(String str, Handler handler, int i) {
        this(handler, null, str, i);
    }

    public void setLogHook(ILogHook iLogHook) {
        this.mLog = iLogHook;
    }

    private void vpmCreate(String str) {
        create(new WeakReference(this), str, 3, 16000, 2);
        this.mStatus = 1;
    }

    public int vpmDestroy() {
        int destroy = destroy();
        this.mStatus = 0;
        return destroy;
    }

    public static void postEventFromNative(Object obj, int i, Object obj2) {
        VpmClient vpmClient = (VpmClient) ((WeakReference) obj).get();
        if (vpmClient != null && vpmClient.mStatus == 2) {
            vpmClient.mHandler.obtainMessage(vpmClient.MSG_VPM_START + i, obj2).sendToTarget();
            ILogHook iLogHook = vpmClient.mLog;
            if (iLogHook != null) {
                iLogHook.onLog("%s postEventFromNative.msg=%s", LOG_TAG, Integer.valueOf(vpmClient.MSG_VPM_START + i));
            }
        }
    }

    @Deprecated
    public static void postAudioDataFromNative(Object obj, byte[] bArr, int i) {
        VpmClient vpmClient = (VpmClient) ((WeakReference) obj).get();
    }

    public int vpmRead(byte[] bArr, int i) {
        return read(bArr, 0, i, true);
    }

    public int vpmReadMultiChsAsr(byte[] bArr, int i, int i2) {
        return readMultiChsAsr(bArr, i, i2, true);
    }

    public int vpmReadMultiChsWuw(byte[] bArr, int i, int i2) {
        return readMultiChsWuw(bArr, i, i2);
    }

    public int vpmStart() {
        ILogHook iLogHook = this.mLog;
        if (iLogHook != null) {
            iLogHook.onLog("%s vpmStart.status=%d", LOG_TAG, Integer.valueOf(this.mStatus));
        }
        int i = this.mStatus;
        if (i == 2) {
            ILogHook iLogHook2 = this.mLog;
            if (iLogHook2 == null) {
                return 0;
            }
            iLogHook2.onLog("%s vpmStart has started, mStatus=%d", LOG_TAG, Integer.valueOf(i));
            return 0;
        } else if (i != 0) {
            this.mStatus = 2;
            return start();
        } else {
            throw new IllegalStateException("vpmStart.error: need to call vpmCreate first!!");
        }
    }

    public boolean vpmIsRunning() {
        return this.mStatus == 2;
    }

    public int vpmStop() {
        ILogHook iLogHook = this.mLog;
        if (iLogHook != null) {
            iLogHook.onLog("%s vpmStop.status=%d", LOG_TAG, Integer.valueOf(this.mStatus));
        }
        int i = this.mStatus;
        if (i == 3) {
            ILogHook iLogHook2 = this.mLog;
            if (iLogHook2 != null) {
                iLogHook2.onLog("%s vpmStop has stop!", LOG_TAG);
            }
        } else if (i == 2) {
            this.mStatus = 3;
            ILogHook iLogHook3 = this.mLog;
            if (iLogHook3 != null) {
                iLogHook3.onLog("%s vpmStop stopped, status=%d!", LOG_TAG, Integer.valueOf(this.mStatus));
            }
        }
        return 0;
    }

    @Deprecated
    public void vpmSetWaitAsrTimeout(int i) {
        setWaitAsrTimeout(i);
    }

    public int vpmSetWakeup() {
        return setWakeup();
    }

    public int vpmSetUnWakeup() {
        return setUnWakeup();
    }

    @Deprecated
    public int vpmEnableVad() {
        return enableVad();
    }

    @Deprecated
    public int vpmDisableVad() {
        return disableVad();
    }

    @Deprecated
    public int vpmPause() {
        return pauseAsrData();
    }

    @Deprecated
    public int vpmResume() {
        return resumeAsrData();
    }

    public int vpmEnableWuw() {
        return enableWakeupData();
    }

    public int vpmDisableWuw() {
        return disableWakeupData();
    }

    @Deprecated
    public int vpmEnableVoip() {
        return enableVoipData();
    }

    @Deprecated
    int vpmDisableVoip() {
        return disableVoipData();
    }

    @Deprecated
    public int vpmEnableOneShot() {
        return enableOneShot();
    }

    @Deprecated
    public int vpmDisableOneShot() {
        return disableOneShot();
    }

    public int vpmUploadLog() {
        return uploadLog();
    }

    public int vpmStartUpload() {
        return startUpload();
    }

    public int vpmStopUpload() {
        return stopUpload();
    }

    public int vpmUpdateDeviceInfo(String str, String str2, String str3, String str4, String str5) {
        return updateDeviceInfo(str, str2, str3, str4, this.mAppId, str5);
    }

    public int vpmUpdateWifiInfo(String str, String str2, int i) {
        return updateWifiInfo(str, str2, i);
    }

    public void vpmNotifyAsrLength(int i) {
        notifyAsrLength(i);
    }

    public void vpmSetWakeupSense(int i) {
        if (i >= 0 && i <= 100) {
            setWakeupSense(i);
        }
    }

    public void vpmSetWorking(boolean z) {
        if (z) {
            setWorking(1);
        } else {
            setWorking(0);
        }
    }

    public void disableVoiceWakeup() {
        voiceWakeupControl(0);
    }

    public void enableVoiceWakeup() {
        voiceWakeupControl(1);
    }

    public void vpmEnterDialog() {
        dialogMode(1);
    }

    public void vpmExitDialog() {
        dialogMode(0);
    }

    public int vpmEnableMultiAsr() {
        return setMultiAsr(1);
    }

    public int vpmDisableMultiAsr() {
        return setMultiAsr(0);
    }

    public int privacyEnable() {
        return setInfo(28, 1, 0, null);
    }

    public int privacyDisable() {
        return setInfo(28, 0, 0, null);
    }

    public int setWakeupWord(int i, boolean z) {
        return setInfo(30, i, z ? 1 : 0, null);
    }

    public int vpmMultiWuwEnable() {
        return setInfo(31, 1, 0, null);
    }

    public int VpmMultiWuwDisable() {
        return setInfo(31, 0, 0, null);
    }

    public int vpmSleepModeEnter() {
        return setInfo(32, 1, 0, null);
    }

    public int vpmSleepModeExit() {
        return setInfo(32, 0, 0, null);
    }

    public int vpmPlayWakeup(int i, int i2) {
        return setInfo(33, i, i2, null);
    }

    /* loaded from: classes3.dex */
    private static final class SetInfoMsg {
        private static final int MSG_MULTI_WUW_SWITCHER = 31;
        private static final int MSG_PLAY_WAKEUP = 33;
        private static final int MSG_PRIVACY_SWITCHER = 28;
        private static final int MSG_SET_WAKEUP_WORD = 30;
        private static final int MSG_SLEEP_MODE = 32;
        private static final int MSG_WIFI_INFO = 29;

        private SetInfoMsg() {
        }
    }
}
