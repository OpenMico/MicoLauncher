package com.xiaomi.micolauncher.module.setting.utils;

import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.speech.SpeechConfig;
import com.xiaomi.micolauncher.common.stat.StatPoints;

/* loaded from: classes3.dex */
public final class FullDuplexSpeechHelper {
    public static final int FROM_APP = 2;
    public static final int FROM_SETTINGS = 1;
    public static final int FROM_SPEECH = 3;
    public static final int FROM_UNKNOWN = 0;
    private static volatile int a = -1;

    public static void enable(int i) {
        if (onEnableChange(true, i)) {
            SystemSetting.setMultiDialog(true);
        }
    }

    public static void disable(int i) {
        if (onEnableChange(false, i)) {
            SystemSetting.setMultiDialog(false);
        }
    }

    public static boolean onEnableChange(boolean z, int i) {
        if (i <= 0 || i > 3) {
            L.base.i("disable: not support from=%s", Integer.valueOf(i));
            return false;
        }
        if (z) {
            if (a == 1) {
                L.base.i("Full Duplex has enabled!");
                return false;
            }
            a = 1;
        } else if (a == 0) {
            L.base.i("Full Duplex has disabled!");
            return false;
        } else {
            a = 0;
        }
        if (z) {
            StatPoints.Event event = StatPoints.Event.micolog_speech_dialog;
            StatPoints.DialogSpeechKey dialogSpeechKey = StatPoints.DialogSpeechKey.switcher_open;
            StatPoints.recordPoint(event, dialogSpeechKey, i + "");
            SystemSetting.setDuplexSpeechOpenTimeStamp();
        } else {
            StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.use_duration, String.valueOf((System.currentTimeMillis() - SystemSetting.getDuplexSpeechOpenTimeStamp()) / 1000));
            StatPoints.recordPoint(StatPoints.Event.micolog_speech_dialog, StatPoints.DialogSpeechKey.switcher_close);
        }
        return true;
    }

    public static boolean isEnabled() {
        if (!SpeechConfig.supportDialogSpeech()) {
            return false;
        }
        if (a == -1) {
            if (SystemSetting.getMultiDialog()) {
                a = 1;
            } else {
                a = 0;
            }
        }
        return a == 1;
    }
}
