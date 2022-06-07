package com.xiaomi.micolauncher.module.homepage;

import android.content.Context;
import com.xiaomi.mico.base.utils.PreferenceUtils;
import com.xiaomi.micolauncher.module.main.manager.RecommendDataManager;

/* loaded from: classes3.dex */
public class HomePageUtils {
    public static final String KEY_SOURCE = "source";
    public static final String MUSIC_SOURCE = "music_source";
    public static final String MUSIC_SOURCE_MI = "MI";
    public static final String MUSIC_SOURCE_NONE = "NONE";
    public static final String MUSIC_SOURCE_QQ = "QQ";
    public static final String QQ_MUSIC_BIND = "qq_music_bind";

    public static boolean isSourceQQ(Context context) {
        if (context == null) {
            return false;
        }
        return "QQ".equals(PreferenceUtils.getSettingString(context, MUSIC_SOURCE, MUSIC_SOURCE_MI));
    }

    public static boolean isSourceQQ() {
        return "QQ".equals(RecommendDataManager.getManager().getMusicResource());
    }

    public static boolean isBindQQ(Context context) {
        return context != null && isSourceQQ(context) && PreferenceUtils.getSettingBoolean(context, QQ_MUSIC_BIND, true);
    }

    public static void setMusicSource(Context context, String str) {
        if (context != null) {
            PreferenceUtils.setSettingString(context, MUSIC_SOURCE, str);
        }
    }
}
