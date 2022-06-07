package com.xiaomi.mico.settingslib.core;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public class AESUtils {
    public static String encrypt(String str, String str2) throws Exception {
        if (str2 == null || str2.length() != 16) {
            System.out.print("Key不合法");
            return null;
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
        instance.init(1, secretKeySpec);
        return Base64.encodeToString(instance.doFinal(str.getBytes(StandardCharsets.UTF_8)), 2);
    }

    public static String decrypt(String str, String str2) throws Exception {
        if (str2 == null || str2.length() != 16) {
            Log.e("Settings.encryption", "Key不合法");
            return null;
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher instance = Cipher.getInstance("AES/ECB/PKCS5Padding");
        instance.init(2, secretKeySpec);
        return new String(instance.doFinal(Base64.decode(str, 2)), StandardCharsets.UTF_8);
    }

    public static String encryptRandom(String str, String str2) throws Exception {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom instance2 = SecureRandom.getInstance("SHA1PRNG");
        instance2.setSeed(str2.getBytes());
        instance.init(128, instance2);
        SecretKeySpec secretKeySpec = new SecretKeySpec(instance.generateKey().getEncoded(), "AES");
        Cipher instance3 = Cipher.getInstance("AES/ECB/PKCS5Padding");
        instance3.init(1, secretKeySpec);
        return Base64.encodeToString(instance3.doFinal(str.getBytes(StandardCharsets.UTF_8)), 2);
    }

    public static String decryptRandom(String str, String str2) throws Exception {
        KeyGenerator instance = KeyGenerator.getInstance("AES");
        SecureRandom instance2 = SecureRandom.getInstance("SHA1PRNG");
        instance2.setSeed(str2.getBytes());
        instance.init(128, instance2);
        SecretKeySpec secretKeySpec = new SecretKeySpec(instance.generateKey().getEncoded(), "AES");
        Cipher instance3 = Cipher.getInstance("AES/ECB/PKCS5Padding");
        instance3.init(2, secretKeySpec);
        return new String(instance3.doFinal(Base64.decode(str, 2)), StandardCharsets.UTF_8);
    }
}
