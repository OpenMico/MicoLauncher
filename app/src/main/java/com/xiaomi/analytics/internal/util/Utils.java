package com.xiaomi.analytics.internal.util;

import android.text.TextUtils;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/* loaded from: classes3.dex */
public class Utils {
    public static <T> T[] list2Array(List<T> list, Class<T> cls) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        T[] tArr = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, list.size()));
        for (int i = 0; i < list.size(); i++) {
            tArr[i] = list.get(i);
        }
        return tArr;
    }

    public static String getMd5(byte[] bArr) {
        String str = "";
        if (bArr != null) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(bArr);
                str = String.format("%1$032X", new BigInteger(1, instance.digest()));
            } catch (Exception unused) {
            }
        }
        return str.toLowerCase();
    }

    public static String getMd5(String str) {
        return TextUtils.isEmpty(str) ? "" : getMd5(str.getBytes());
    }
}
