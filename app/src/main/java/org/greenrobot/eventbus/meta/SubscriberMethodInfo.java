package org.greenrobot.eventbus.meta;

import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes5.dex */
public class SubscriberMethodInfo {
    final String a;
    final ThreadMode b;
    final Class<?> c;
    final int d;
    final boolean e;

    public SubscriberMethodInfo(String str, Class<?> cls, ThreadMode threadMode, int i, boolean z) {
        this.a = str;
        this.b = threadMode;
        this.c = cls;
        this.d = i;
        this.e = z;
    }

    public SubscriberMethodInfo(String str, Class<?> cls) {
        this(str, cls, ThreadMode.POSTING, 0, false);
    }

    public SubscriberMethodInfo(String str, Class<?> cls, ThreadMode threadMode) {
        this(str, cls, threadMode, 0, false);
    }
}
