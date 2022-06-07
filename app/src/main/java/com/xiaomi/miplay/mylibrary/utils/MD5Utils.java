package com.xiaomi.miplay.mylibrary.utils;

import android.text.TextUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public class MD5Utils {
    public static String md5EncryptTo32(String str) {
        return TextUtils.isEmpty(str) ? "" : a(str, 32).toUpperCase();
    }

    private static String a(String str, int i) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer("");
            for (byte b : digest) {
                int i2 = b;
                if (b < 0) {
                    i2 = b + 256;
                }
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2 == 1 ? 1 : 0));
            }
            if (i == 32) {
                return stringBuffer.toString();
            }
            if (i == 16) {
                return stringBuffer.toString().substring(8, 24);
            }
            return "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
