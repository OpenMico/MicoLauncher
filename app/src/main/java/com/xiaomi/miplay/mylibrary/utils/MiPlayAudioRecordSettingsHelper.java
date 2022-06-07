package com.xiaomi.miplay.mylibrary.utils;

import android.content.Context;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;

/* loaded from: classes4.dex */
public class MiPlayAudioRecordSettingsHelper {
    public static void openMiPlayOptimize(@NonNull Context context) {
        Logger.i("MiPlayAudioRecordSettings", "openMiPlayOptimize", new Object[0]);
        Settings.Global.putInt(context.getContentResolver(), "optimize_for_MiuiAudioplaybackRecorder", 1);
    }

    public static void closeMiPlayOptimize(@NonNull Context context) {
        Logger.i("MiPlayAudioRecordSettings", "closeMiPlayOptimize", new Object[0]);
        Settings.Global.putInt(context.getContentResolver(), "optimize_for_MiuiAudioplaybackRecorder", 0);
    }
}
