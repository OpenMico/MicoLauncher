package com.xiaomi.analytics;

/* loaded from: classes3.dex */
public class TrackAction extends Action {
    public TrackAction setCategory(String str) {
        a("_category_", (Object) str);
        return this;
    }

    public TrackAction setAction(String str) {
        a("_action_", (Object) str);
        return this;
    }

    public TrackAction setLabel(String str) {
        a("_label_", (Object) str);
        return this;
    }

    public TrackAction setValue(long j) {
        a("_value_", (Object) (j + ""));
        return this;
    }
}
