package com.xiaomi.miplay.mylibrary.statistic;

import android.content.Context;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import com.xiaomi.onetrack.Configuration;
import com.xiaomi.onetrack.OneTrack;
import java.util.Map;

/* loaded from: classes4.dex */
public class OneTrackStatistics {
    public static final String AppID = "31000000505";
    private static OneTrack a = null;
    private static boolean b = false;
    private static final String c = "OneTrackStatistics";
    public static final String channel = "miui_full_scene_music";

    public static void init(Context context) {
        Logger.i(c, "init OneTrack start", new Object[0]);
        if (!b) {
            a = OneTrack.createInstance(context, new Configuration.Builder().setAppId(AppID).setChannel(channel).setMode(OneTrack.Mode.SDK).setExceptionCatcherEnable(true).build());
            OneTrack.setDebugMode(true);
            b = true;
            Logger.i(c, "init OneTrack end", new Object[0]);
        }
    }

    public static void track(String str, Map<String, Object> map) {
        if (b) {
            String str2 = c;
            Logger.i(str2, "track: " + str + " params=" + map, new Object[0]);
            a.track(str, map);
        }
    }
}
