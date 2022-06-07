package com.xiaomi.ai.android.utils;

import com.xiaomi.ai.log.Logger;

/* loaded from: classes2.dex */
public final class SecurityUtil {
    public static byte[] a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            Logger.d("SecurityUtil", "aesEncrypt: msg is empty");
            return null;
        }
        int length = (((bArr.length - 1) / 16) + 1) * 16;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        for (int length2 = bArr.length; length2 < length; length2++) {
            bArr2[length2] = 32;
        }
        return native_aes(bArr2);
    }

    private static native byte[] native_aes(byte[] bArr);
}
