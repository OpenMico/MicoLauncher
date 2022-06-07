package com.xiaomi.micolauncher.module.main.util;

import com.xiaomi.micolauncher.common.stat.StatUtils;

/* loaded from: classes3.dex */
public class MainStatHelper {

    /* loaded from: classes3.dex */
    public enum RecommendVal {
        LAUNCHER_MUSIC_RECOMMEND,
        LAUNCHER_RADIO_RECOMMEND,
        LAUNCHER_VIDEO_RECOMMEND,
        LAUNCHER_NEWS_RECOMMEND,
        LAUNCHER_SHORT_VIDEO_RECOMMEND
    }

    /* loaded from: classes3.dex */
    private enum a {
        LAUNCHER_CLICK,
        LAUNCHER_SHOW
    }

    private static String a(a aVar) {
        return aVar.name().toLowerCase();
    }

    public static void recordAppClick(String str) {
        StatUtils.recordCountEvent("main_page", a(a.LAUNCHER_CLICK), "category", str);
    }

    public static void recordCardClick(RecommendVal recommendVal) {
        StatUtils.recordCountEvent("main_page", a(a.LAUNCHER_CLICK), "category", recommendVal.name().toLowerCase());
    }

    public static void recordPageShow() {
        StatUtils.recordCountEvent("main_page", a(a.LAUNCHER_SHOW));
    }
}
