package com.xiaomi.ai.b;

import com.xiaomi.ai.log.Logger;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public final class b {
    public static String a(String str) {
        String str2;
        String str3;
        if (f.a(str)) {
            return null;
        }
        try {
            return a(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException unused) {
            str2 = "DigestUtils";
            str3 = "UnsupportedEncodingException";
            Logger.d(str2, str3);
            return null;
        } catch (NoSuchAlgorithmException unused2) {
            str2 = "DigestUtils";
            str3 = "NoSuchAlgorithmException";
            Logger.d(str2, str3);
            return null;
        }
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }
}
