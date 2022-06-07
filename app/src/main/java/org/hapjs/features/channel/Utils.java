package org.hapjs.features.channel;

import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes5.dex */
public class Utils {
    private static final char[] a = "0123456789abcdef".toCharArray();

    public static String getSha256(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            instance.update(bArr);
            return byte2HexString(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e("MessageChannel-Utils", "Md5 algorithm NOT found.", e);
            return "".toLowerCase();
        }
    }

    public static String byte2HexString(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            int i3 = i * 2;
            char[] cArr2 = a;
            cArr[i3] = cArr2[i2 >>> 4];
            cArr[i3 + 1] = cArr2[i2 & 15];
        }
        return new String(cArr);
    }
}
