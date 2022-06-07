package com.xiaomi.analytics;

import android.text.TextUtils;
import com.umeng.analytics.pro.ai;
import java.util.List;

/* loaded from: classes3.dex */
public class AdAction extends TrackAction {
    protected static final String AD_MONITOR = "_ad_monitor_";

    public AdAction(String str) {
        setCategory(ai.au);
        setAction(str);
    }

    public AdAction(String str, String str2) {
        setCategory(str);
        setAction(str2);
    }

    public AdAction addAdMonitor(List<String> list) {
        if (list != null) {
            StringBuilder sb = new StringBuilder();
            for (String str : list) {
                if (!TextUtils.isEmpty(str)) {
                    if (sb.length() > 0) {
                        sb.append("|");
                    }
                    sb.append(str);
                }
            }
            if (sb.length() > 0) {
                a(AD_MONITOR, sb.toString());
            }
        }
        return this;
    }
}
