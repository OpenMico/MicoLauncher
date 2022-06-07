package com.xiaomi.push.service;

import com.xiaomi.push.hn;
import com.xiaomi.push.ho;

/* loaded from: classes4.dex */
/* synthetic */ class j {
    static final /* synthetic */ int[] a;
    static final /* synthetic */ int[] b = new int[ho.values().length];

    static {
        try {
            b[ho.INT.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            b[ho.LONG.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            b[ho.STRING.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            b[ho.BOOLEAN.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        a = new int[hn.values().length];
        try {
            a[hn.MISC_CONFIG.ordinal()] = 1;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            a[hn.PLUGIN_CONFIG.ordinal()] = 2;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
