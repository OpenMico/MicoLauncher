package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
public class ag {
    static final char[] a = "0123456789ABCDEF".toCharArray();

    public static String a(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder(i2 * 2);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = bArr[i + i3] & 255;
            sb.append(a[i4 >> 4]);
            sb.append(a[i4 & 15]);
        }
        return sb.toString();
    }

    public static boolean a(Context context) {
        return af.f7a;
    }
}
