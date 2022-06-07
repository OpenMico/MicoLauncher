package com.xiaomi.push;

import com.xiaomi.mipush.sdk.Constants;
import java.net.InetSocketAddress;

/* loaded from: classes4.dex */
public final class ct {
    private String a;
    private int b;

    public ct(String str, int i) {
        this.a = str;
        this.b = i;
    }

    public static ct a(String str, int i) {
        String str2;
        int lastIndexOf = str.lastIndexOf(Constants.COLON_SEPARATOR);
        if (lastIndexOf != -1) {
            str2 = str.substring(0, lastIndexOf);
            try {
                int parseInt = Integer.parseInt(str.substring(lastIndexOf + 1));
                if (parseInt > 0) {
                    i = parseInt;
                }
            } catch (NumberFormatException unused) {
            }
        } else {
            str2 = str;
        }
        return new ct(str2, i);
    }

    /* renamed from: a  reason: collision with other method in class */
    public static InetSocketAddress m813a(String str, int i) {
        ct a = a(str, i);
        return new InetSocketAddress(a.m814a(), a.a());
    }

    public int a() {
        return this.b;
    }

    /* renamed from: a  reason: collision with other method in class */
    public String m814a() {
        return this.a;
    }

    public String toString() {
        if (this.b <= 0) {
            return this.a;
        }
        return this.a + Constants.COLON_SEPARATOR + this.b;
    }
}
