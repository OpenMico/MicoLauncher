package com.xiaomi.analytics;

import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.analytics.internal.util.ALog;

/* loaded from: classes3.dex */
public class EventAction extends Action {
    protected static final String EVENT_DEFAULT_PARAM = "_event_default_param_";
    private String a;

    public EventAction(String str) {
        this(str, null);
    }

    public EventAction(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            Log.w(ALog.addPrefix("EventAction"), "eventName is null when constructing EventAction!");
        }
        this.a = str;
        addEventId(this.a);
        if (str2 != null && !TextUtils.isEmpty(str2)) {
            addParam(EVENT_DEFAULT_PARAM, str2);
        }
    }
}
