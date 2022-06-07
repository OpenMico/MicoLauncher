package com.xiaomi.micolauncher.module.setting.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import androidx.annotation.VisibleForTesting;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.UserBehaviorProxy;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.StatusBarEvent;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.stat.StatPoints;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Mic {
    private static final long a = TimeUnit.SECONDS.toMillis(2);
    private static final long b = TimeUnit.MILLISECONDS.toMillis(500);
    private static volatile Mic c;
    private final AudioManager d;
    private final Handler e = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.module.setting.utils.-$$Lambda$Mic$OcLjEiNE4sIZMFVsKBauqlBdjeM
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean a2;
            a2 = Mic.this.a(message);
            return a2;
        }
    });
    private long f;

    public void enterCallMode() {
    }

    public void exitCallMode() {
    }

    public static Mic getInstance() {
        if (c == null) {
            synchronized (Mic.class) {
                if (c == null) {
                    c = new Mic(MicoApplication.getGlobalContext());
                }
            }
        }
        return c;
    }

    @VisibleForTesting
    public Mic(Context context) {
        this.d = (AudioManager) context.getSystemService("audio");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.module.setting.utils.Mic.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.media.action.MICROPHONE_MUTE_CHANGED".equals(action)) {
                    boolean isMicMute = Mic.this.isMicMute();
                    L.mic.i("%s onReceive, action=%s, micMute=%s", "[Mic]: ", action, Boolean.valueOf(isMicMute));
                    UserBehaviorProxy.setUserBehavior(context2, "MicOnOff");
                    if (!isMicMute) {
                        Mic.this.e.removeMessages(1);
                    }
                    SystemSetting.setMicMute(isMicMute);
                    EventBusRegistry.getEventBus().post(new StatusBarEvent(StatusBarEvent.StatusBarType.STATUS_BAR_TYPE_MIC, isMicMute));
                    if (isMicMute) {
                        Mic.this.f = SystemClock.uptimeMillis();
                        StatPoints.recordPoint(StatPoints.Event.micolog_mic, StatPoints.MicKey.off_count);
                    } else {
                        StatPoints.recordPoint(StatPoints.Event.micolog_mic, StatPoints.MicKey.on_count);
                        StatPoints.Event event = StatPoints.Event.micolog_mic;
                        StatPoints.MicKey micKey = StatPoints.MicKey.off_duration;
                        StatPoints.recordPoint(event, micKey, "" + (((SystemClock.uptimeMillis() - Mic.this.f) / 1000) + 1));
                    }
                    Mic.this.e.removeMessages(2);
                    if (Hardware.isX10()) {
                        Mic.this.e.sendEmptyMessageDelayed(2, Mic.b);
                    } else {
                        Mic.this.e.sendEmptyMessageDelayed(2, Mic.a);
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.media.action.MICROPHONE_MUTE_CHANGED");
        context.registerReceiver(broadcastReceiver, intentFilter);
        if (this.d != null) {
            Logger logger = L.mic;
            logger.i("[Mic]: init.micMute=" + SystemSetting.getMicMute());
            if (SystemSetting.getRebootReason() != 1 || !SystemSetting.getMicMute()) {
                SystemSetting.setMicMute(isMicMute());
            } else {
                setMicMute(true);
            }
        }
    }

    public /* synthetic */ boolean a(Message message) {
        switch (message.what) {
            case 1:
                setMicMute(false);
                break;
            case 2:
                if (SpeechManager.peekInstance() != null) {
                    if (!isMicMute()) {
                        SpeechManager.getInstance().setVpmWorking(true);
                        break;
                    } else {
                        SpeechManager.getInstance().setVpmWorking(false);
                        break;
                    }
                }
                break;
        }
        return false;
    }

    public void setMicMute(boolean z) {
        AudioManager audioManager;
        Logger logger = L.mic;
        logger.d("[Mic]: isMute: " + z + " isMicMute(): " + isMicMute());
        if (z != isMicMute() && (audioManager = this.d) != null) {
            audioManager.setMicrophoneMute(z);
            Logger logger2 = L.mic;
            logger2.d("[Mic]: setMicMute.mute=" + z);
        }
    }

    public void muteMicrophone(int i) {
        L.mic.d("[Mic]: muteMicrophone.duration=%d", Integer.valueOf(i));
        if (this.d != null && i >= 0) {
            this.e.removeMessages(1);
            setMicMute(true);
            if (i > 0) {
                this.e.sendEmptyMessageDelayed(1, TimeUnit.SECONDS.toMillis(i));
            }
        }
    }

    public boolean isMicMute() {
        AudioManager audioManager = this.d;
        if (audioManager == null) {
            return false;
        }
        return audioManager.isMicrophoneMute();
    }
}
