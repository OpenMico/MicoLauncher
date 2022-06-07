package com.xiaomi.miot.host.manager.utils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public abstract class Coder {
    private static final String HMAC_SHA1 = "HmacSHA1";
    public static final String KEY_MAC = "HmacMD5";
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_PBKDF2 = "PBKDF2WithHmacSHA1";
    public static final String KEY_SHA = "SHA";
    private static final String KEY_SHA256 = "SHA-256";

    private static boolean isEmpty(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static byte[] decryptBASE64(String str) {
        return Base64Coder.decode(str);
    }

    public static String encryptBASE64(byte[] bArr) {
        return String.valueOf(Base64Coder.encode(bArr));
    }

    public static byte[] encryptMD5(byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(bArr);
        return instance.digest();
    }

    public static byte[] encryptMD5(byte[]... bArr) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        for (byte[] bArr2 : bArr) {
            if (!isEmpty(bArr2)) {
                instance.update(bArr2);
            }
        }
        return instance.digest();
    }

    public static byte[] encryptSHA(byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance("SHA");
        instance.update(bArr);
        return instance.digest();
    }

    public static String initMacKey() throws Exception {
        return encryptBASE64(KeyGenerator.getInstance("HmacMD5").generateKey().getEncoded());
    }

    public static byte[] encryptHMAC(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "HmacMD5");
        Mac instance = Mac.getInstance(secretKeySpec.getAlgorithm());
        instance.init(secretKeySpec);
        return instance.doFinal(bArr);
    }

    public static byte[] encryptHMAC(byte[] bArr, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(decryptBASE64(str), "HmacMD5");
        Mac instance = Mac.getInstance(secretKeySpec.getAlgorithm());
        instance.init(secretKeySpec);
        return instance.doFinal(bArr);
    }

    public static byte[] encryptHMACSha1(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, HMAC_SHA1);
        Mac instance = Mac.getInstance(HMAC_SHA1);
        instance.init(secretKeySpec);
        instance.update(bArr);
        return instance.doFinal();
    }

    public static byte[] sha256Hash(byte[] bArr) throws NoSuchAlgorithmException, InvalidKeyException {
        MessageDigest instance = MessageDigest.getInstance("SHA-256");
        instance.update(bArr);
        return instance.digest();
    }

    public static byte[] createPBKDF2Key(char[] cArr, byte[] bArr, int i, int i2) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(cArr, bArr, i, i2)).getEncoded();
    }
}
