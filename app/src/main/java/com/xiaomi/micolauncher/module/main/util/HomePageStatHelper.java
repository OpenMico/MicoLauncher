package com.xiaomi.micolauncher.module.main.util;

import com.xiaomi.micolauncher.Launcher;
import com.xiaomi.micolauncher.common.stat.StatUtils;

/* loaded from: classes3.dex */
public class HomePageStatHelper {

    /* loaded from: classes3.dex */
    private enum a {
        RADIO_RECOMMEND_SHOW
    }

    private static String a(a aVar) {
        return aVar.name().toLowerCase();
    }

    public static void recordAudiobookPageShow() {
        StatUtils.recordCountEvent(Launcher.KEY_ENTER_HOMEPAGE, a(a.RADIO_RECOMMEND_SHOW));
    }
}
