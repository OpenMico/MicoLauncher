package com.xiaomi.push;

/* loaded from: classes4.dex */
public class dq {
    private static void a(byte[] bArr) {
        if (bArr.length >= 2) {
            bArr[0] = 99;
            bArr[1] = 100;
        }
    }

    public static byte[] a(String str, byte[] bArr) {
        byte[] a = aw.a(str);
        try {
            a(a);
            return h.a(a, bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] b(String str, byte[] bArr) {
        byte[] a = aw.a(str);
        try {
            a(a);
            return h.b(a, bArr);
        } catch (Exception unused) {
            return null;
        }
    }
}
