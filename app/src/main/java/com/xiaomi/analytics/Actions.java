package com.xiaomi.analytics;

/* loaded from: classes3.dex */
public class Actions {
    public static CustomAction newCustomAction() {
        return new CustomAction();
    }

    public static EventAction newEventAction(String str) {
        return new EventAction(str);
    }

    public static EventAction newEventAction(String str, String str2) {
        return new EventAction(str, str2);
    }

    public static AdAction newAdAction(String str) {
        return new AdAction(str);
    }

    public static AdAction newAdAction(String str, String str2) {
        return new AdAction(str, str2);
    }
}
