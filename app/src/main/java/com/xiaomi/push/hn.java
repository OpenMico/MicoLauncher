package com.xiaomi.push;

/* loaded from: classes4.dex */
public enum hn {
    MISC_CONFIG(1),
    PLUGIN_CONFIG(2);
    

    /* renamed from: a  reason: collision with other field name */
    private final int f59a;

    hn(int i) {
        this.f59a = i;
    }

    public static hn a(int i) {
        switch (i) {
            case 1:
                return MISC_CONFIG;
            case 2:
                return PLUGIN_CONFIG;
            default:
                return null;
        }
    }

    public int a() {
        return this.f59a;
    }
}
