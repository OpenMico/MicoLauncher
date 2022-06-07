package com.xiaomi.smarthome.library.crypto;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes4.dex */
public class SHA1Util {
    public static String getSHA1Digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA1");
            instance.update(getBytes(str));
            return String.format("%1$040X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSHA2Digest(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            instance.update(getBytes(str));
            return String.format("%1$040X", new BigInteger(1, instance.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }
}
