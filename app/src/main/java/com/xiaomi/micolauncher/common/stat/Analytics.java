package com.xiaomi.micolauncher.common.stat;

import android.content.Context;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import java.util.Map;

/* loaded from: classes3.dex */
public class Analytics {
    private static OneTrack oneTrack;

    public static void initialize(Context context, String str) {
        oneTrack = OneTrack.createInstance(context, new Configuration.Builder().setAppId(str).setChannel(Hardware.current(context).statChannel()).setMode(OneTrack.Mode.APP).setExceptionCatcherEnable(true).build());
        OneTrack.setDebugMode(false);
    }

    public static void track(String str, Map<String, Object> map) {
        oneTrack.track(str, map);
    }

    public static void track(String str) {
        oneTrack.track(str, null);
    }
}
