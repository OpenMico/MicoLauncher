package com.xiaomi.ai.android.utils;

import android.util.Log;
import com.xiaomi.ai.log.Logger;
import com.xiaomi.mipush.sdk.Constants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public final class b {
    public static int a(String str) {
        int i = 1;
        int i2 = 0;
        for (byte b : str.getBytes()) {
            i = (i + b) % 65521;
            i2 = (i2 + i) % 65521;
        }
        return (i2 << 16) | i;
    }

    public static String a(int i) {
        byte[] bArr = new byte[(i + 1) / 2];
        new SecureRandom().nextBytes(bArr);
        return a(bArr).replace(Constants.COLON_SEPARATOR, "").substring(0, i);
    }

    public static String a(String str, byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.reset();
            instance.update(bArr);
            return a(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            Logger.d("DigestUtil", Log.getStackTraceString(e));
            return null;
        }
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String hexString = Integer.toHexString(bArr[i]);
            int length = hexString.length();
            if (length == 1) {
                hexString = "0" + hexString;
            }
            if (length > 2) {
                hexString = hexString.substring(length - 2, length);
            }
            sb.append(hexString);
            if (i < bArr.length - 1) {
                sb.append(':');
            }
        }
        return sb.toString();
    }
}
