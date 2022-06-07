package com.xiaomi.micolauncher.module.homepage.record;

import com.xiaomi.micolauncher.Launcher;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.smarthome.ui.model.SmartHomeStatHelper;

/* loaded from: classes3.dex */
public class AppRecordHelper {
    public static void recordAppShowByMi(String str) {
        StatUtils.recordCountEvent(Launcher.KEY_ENTER_HOMEPAGE, "app_page_show", SmartHomeStatHelper.PARAM_VALUE_TAB, str);
    }

    public static void recordAppClickByMi(String str, String str2) {
        StatUtils.recordCountEvent(Launcher.KEY_ENTER_HOMEPAGE, "app_page_click", SmartHomeStatHelper.PARAM_VALUE_TAB, str, "app", str2);
    }

    public static void recordAppClickTrackKey(String str) {
        StatUtils.recordCountEvent(Launcher.KEY_ENTER_HOMEPAGE, "app_page_click", "trackKey", str);
    }
}
