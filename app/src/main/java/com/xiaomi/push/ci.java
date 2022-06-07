package com.xiaomi.push;

import android.util.Base64;
import com.xiaomi.micolauncher.module.music.MusicGroupListActivity;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import org.apache.http.NameValuePair;

/* loaded from: classes4.dex */
class ci {
    public static String a(String str) {
        if (str == null) {
            return "";
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(c(str));
            return String.format("%1$032X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String a(List<NameValuePair> list, String str) {
        Collections.sort(list, new cj());
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (NameValuePair nameValuePair : list) {
            if (!z) {
                sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
            }
            sb.append(nameValuePair.getName());
            sb.append("=");
            sb.append(nameValuePair.getValue());
            z = false;
        }
        sb.append(MusicGroupListActivity.SPECIAL_SYMBOL);
        sb.append(str);
        return a(new String(Base64.encode(c(sb.toString()), 2)));
    }

    public static void b(String str) {
    }

    private static byte[] c(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }
}
