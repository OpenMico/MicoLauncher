package com.xiaomi.micolauncher.common.stat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.application.MicoApplication;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.module.setting.utils.FullDuplexSpeechHelper;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;

/* loaded from: classes3.dex */
public class StatPoints {
    private static final String ACTION_QS_PANEL_CLOSE = "com.android.systemui.actions.ACTION_QS_PANEL_CLOSE";
    private static final String ACTION_QS_PANEL_OPEN = "com.android.systemui.actions.ACTION_QS_PANEL_OPEN";
    private static final int DELAYED_MAX_SECONDS = 43200;
    private static final int DELAYED_MIN_SECONDS = 5;
    private static final int DELAYED_NORMAL_SECONDS = 60;
    private static final String LOG_TAG = "[StatPoints]: ";
    public static final int MSG_SCREEN_CLICK = 1;
    public static final int MSG_SCREEN_MOTION = 2;
    public static final String STAT_TIME = "1";
    private static volatile StatPoints sInstance;
    private Handler mHandler = new Handler(ThreadUtil.getWorkHandler().getLooper(), new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.stat.StatPoints.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    StatPoints.recordPoint(Event.micolog_screen, ScreenKey.click);
                    return false;
                case 2:
                    StatPoints.recordPoint(Event.micolog_screen, ScreenKey.motion);
                    return false;
                default:
                    return false;
            }
        }
    });
    private BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.xiaomi.micolauncher.common.stat.StatPoints.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!TextUtils.isEmpty(intent.getAction())) {
                String action = intent.getAction();
                char c = 65535;
                int hashCode = action.hashCode();
                if (hashCode != -1788194007) {
                    if (hashCode == 389369049 && action.equals(StatPoints.ACTION_QS_PANEL_CLOSE)) {
                        c = 1;
                    }
                } else if (action.equals(StatPoints.ACTION_QS_PANEL_OPEN)) {
                    c = 0;
                }
                if (c == 0) {
                    StatPoints.recordPointDelayed(1);
                }
            }
        }
    };

    /* loaded from: classes3.dex */
    public enum DialogSpeechKey {
        wakeup,
        no_asr,
        chat_count,
        exit_timeout,
        exit_cmd,
        switcher_open,
        switcher_close,
        use_duration,
        switcher_status
    }

    /* loaded from: classes3.dex */
    public enum Event {
        micolog_wakeup,
        micolog_speech,
        micolog_mic,
        spk,
        miio,
        voip,
        micolog_screen,
        micolog_lock_screen,
        micolog_page,
        player_bt,
        mediaplayer,
        micolog_speech_dialog,
        micolog_feature,
        power
    }

    /* loaded from: classes3.dex */
    public enum FeatureKey {
        kid_mode_action
    }

    /* loaded from: classes3.dex */
    public enum LockScreenKey {
        type
    }

    /* loaded from: classes3.dex */
    public enum MediaplayerKey {
        action,
        play,
        pause,
        stop,
        toggle,
        prev,
        next,
        app_content,
        ch,
        app,
        mibrain,
        duration,
        volume_noon,
        volume_night
    }

    /* loaded from: classes3.dex */
    public enum MicKey {
        on_count,
        off_count,
        off_duration
    }

    /* loaded from: classes3.dex */
    public enum MiotPluginAutoMation {
        play_fm,
        play_music,
        play_pause,
        play_prev,
        play_next,
        play_tts,
        alarm_stop,
        mic_on,
        mic_off
    }

    /* loaded from: classes3.dex */
    public enum PowerKey {
        disconnect,
        connect
    }

    /* loaded from: classes3.dex */
    public enum ScreenKey {
        click,
        motion
    }

    /* loaded from: classes3.dex */
    public enum SpeechKey {
        success_count,
        asr_no_query_count,
        asr_no_query_reason,
        nlp_no_response_count,
        wait_speaking_timeout_count,
        invalid_doamin_action_count,
        network_probe_err_count,
        sdk_network_err_count,
        sdk_handshake_err_count,
        sdk_connect_err_count,
        sdk_rw_err_count,
        sdk_authfail_err_count,
        sdk_other_err_count,
        app_network_detect,
        query_latency,
        wakeup_tone_switch,
        aivs_asr_timeout_rssi
    }

    /* loaded from: classes3.dex */
    public enum WakeupKey {
        xatx_count,
        dup_count,
        reopen_mic
    }

    private StatPoints() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_QS_PANEL_OPEN);
        intentFilter.addAction(ACTION_QS_PANEL_CLOSE);
        MicoApplication.getGlobalContext().registerReceiver(this.mReceiver, intentFilter, null, this.mHandler);
    }

    public static void recordPoint(Event event, Enum r2) {
        recordPoint(event, r2, "1");
    }

    public static void recordPoint(Event event, Enum r4, String str) {
        Logger logger = L.base;
        logger.d(LOG_TAG + event.name() + ".key=" + r4.name() + ", .val=" + str);
        MicoStatUtils.recordEventMultiDimensions(event.name(), r4.name(), str);
    }

    public static void recordPoint(String str, String str2, String str3) {
        Logger logger = L.base;
        logger.d(LOG_TAG + str + ".key=" + str2 + ", .val=" + str3);
        MicoStatUtils.recordEventMultiDimensions(str, str2, str3);
    }

    public static void recordPointDelayed(int i, int i2) {
        if (i2 <= 5) {
            i2 = 5;
        } else if (i2 >= DELAYED_MAX_SECONDS) {
            i2 = DELAYED_MAX_SECONDS;
        }
        if (sInstance == null) {
            sInstance = new StatPoints();
        }
        sInstance.mHandler.removeMessages(i);
        sInstance.mHandler.sendEmptyMessageDelayed(i, i2 * 1000);
    }

    public static void recordPointDelayed(int i) {
        recordPointDelayed(i, 60);
    }

    public static void recordPointsOnNight() {
        Event event = Event.mediaplayer;
        MediaplayerKey mediaplayerKey = MediaplayerKey.volume_night;
        recordPoint(event, mediaplayerKey, SystemVolume.getInstance().getVolume() + "");
        if (SpeechConfig.getWakeupSound()) {
            recordPoint(Event.micolog_speech, SpeechKey.wakeup_tone_switch, "on");
        } else {
            recordPoint(Event.micolog_speech, SpeechKey.wakeup_tone_switch, "off");
        }
    }

    public static void recordPointsOnNoon() {
        Event event = Event.mediaplayer;
        MediaplayerKey mediaplayerKey = MediaplayerKey.volume_noon;
        recordPoint(event, mediaplayerKey, SystemVolume.getInstance().getVolume() + "");
        recordPoint(Event.micolog_speech_dialog, DialogSpeechKey.switcher_status, FullDuplexSpeechHelper.isEnabled() ? "on" : "off");
    }
}
