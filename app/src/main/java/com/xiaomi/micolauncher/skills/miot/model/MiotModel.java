package com.xiaomi.micolauncher.skills.miot.model;

import android.content.Context;
import com.xiaomi.mico.base.utils.ActivityUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StartMiotCameraUiEvent;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.module.lockscreen.LockScreenSendBroadcast;
import com.xiaomi.micolauncher.module.setting.utils.Mic;
import com.xiaomi.micolauncher.module.setting.utils.Screen;
import com.xiaomi.miot.support.ICameraStatus;

/* loaded from: classes3.dex */
public class MiotModel {
    public static final String TAG = "[Camera:] ";
    private static volatile MiotModel a = null;
    private static boolean b = false;
    private static boolean c = false;
    private static ICameraStatus d = new ICameraStatus.Stub() { // from class: com.xiaomi.micolauncher.skills.miot.model.MiotModel.1
        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onPreviewStart(String str) {
            L.miot.d("%s [preview start]: %s", MiotModel.TAG, str);
        }

        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onPreviewEnd(String str) {
            L.miot.d("%s [preview stop]: %s", MiotModel.TAG, str);
        }

        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onCallStart(String str) {
            L.miot.d("%s [call start]: %s", MiotModel.TAG, str);
            boolean isMicMute = Mic.getInstance().isMicMute();
            boolean isRunning = SpeechManager.getInstance().isRunning();
            L.miot.i("%s isMicMute: %s, isRunning: %s", MiotModel.TAG, Boolean.valueOf(isMicMute), Boolean.valueOf(isRunning));
            L.miot.i("%s [Before]: isMicSet: %s, isQuerySet: %s", MiotModel.TAG, Boolean.valueOf(MiotModel.b), Boolean.valueOf(MiotModel.c));
            if (isMicMute) {
                boolean unused = MiotModel.b = true;
                L.miot.d("%s isMicSet: %s", MiotModel.TAG, true);
                L.mic.d("[Camera:] set mute false");
                Mic.getInstance().setMicMute(false);
            }
            if (!isRunning) {
                boolean unused2 = MiotModel.c = true;
                L.miot.d("%s isQuerySet: %s", MiotModel.TAG, true);
                SpeechManager.getInstance().start();
            }
            L.miot.i("%s [After]: isMicSet: %s, isQuerySet: %s", MiotModel.TAG, Boolean.valueOf(MiotModel.b), Boolean.valueOf(MiotModel.c));
        }

        @Override // com.xiaomi.miot.support.ICameraStatus
        public void onCallEnd(String str) {
            L.miot.d("%s [call stop]: %s", MiotModel.TAG, str);
            boolean isMicMute = Mic.getInstance().isMicMute();
            boolean isRunning = SpeechManager.getInstance().isRunning();
            L.miot.d("%s isMicMute: %s, isRunning: %s", MiotModel.TAG, Boolean.valueOf(isMicMute), Boolean.valueOf(isRunning));
            L.miot.i("%s [Before]: isMicSet: %s, isQuerySet: %s", MiotModel.TAG, Boolean.valueOf(MiotModel.b), Boolean.valueOf(MiotModel.c));
            if (!isMicMute && MiotModel.b) {
                boolean unused = MiotModel.b = false;
                L.miot.d("%s isMicSet: %s", MiotModel.TAG, false);
                L.mic.d("[Camera:] set mute true");
                Mic.getInstance().setMicMute(true);
            }
            if (isRunning && MiotModel.c) {
                boolean unused2 = MiotModel.c = false;
                L.miot.d("%s isQuerySet: %s", MiotModel.TAG, false);
                SpeechManager.getInstance().stop();
            }
            L.miot.i("%s [After]: isMicSet: %s, isQuerySet: %s", MiotModel.TAG, Boolean.valueOf(MiotModel.b), Boolean.valueOf(MiotModel.c));
        }
    };

    public static MiotModel getInstance() {
        if (a == null) {
            synchronized (MiotModel.class) {
                if (a == null) {
                    a = new MiotModel();
                }
            }
        }
        return a;
    }

    public static ICameraStatus getCameraStatus() {
        L.miot.i("%s get Camera Status", TAG);
        return d;
    }

    private boolean c() {
        LockScreenSendBroadcast.sendCloseLockScreenEventBroadcast(MicoApplication.getGlobalContext());
        Screen.getInstance().systemUiDismiss(0);
        Context globalContext = MicoApplication.getGlobalContext();
        if (globalContext == null) {
            return true;
        }
        ActivityUtil.stopQiyiVideo(globalContext);
        return true;
    }

    public void showCameraActivity(String str) {
        if (c()) {
            L.miot.i("%s miotModel start camera activity", TAG);
            EventBusRegistry.getEventBus().post(new StartMiotCameraUiEvent(d, str));
        }
    }
}
