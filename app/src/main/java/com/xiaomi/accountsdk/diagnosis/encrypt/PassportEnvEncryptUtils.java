package com.xiaomi.accountsdk.diagnosis.encrypt;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.accountsdk.utils.RSACoder;
import com.xiaomi.mipush.sdk.Constants;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/* loaded from: classes2.dex */
public class PassportEnvEncryptUtils {

    /* loaded from: classes2.dex */
    public static class EncryptException extends Exception {
        public EncryptException(Throwable th) {
            super(th);
        }
    }

    /* loaded from: classes2.dex */
    public static class EncryptResult {
        public String content;
        public String encryptedKey;
    }

    private static String a(String str, SecretKey secretKey) {
        try {
            return new a(secretKey.getEncoded()).a(str);
        } catch (b e) {
            throw new EncryptException(e);
        }
    }

    private static SecretKey a() {
        try {
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            instance.init(new SecureRandom());
            return instance.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
    }

    public static EncryptResult encrypt(String str) {
        EncryptResult encryptResult = new EncryptResult();
        SecretKey a = a();
        try {
            String encodeToString = Base64.encodeToString(f.a(Base64.encode(a.getEncoded(), 10), f.a(RSACoder.SPECIFIED_RSA_PUBLIC_KEY)), 10);
            encryptResult.content = a(str, a);
            encryptResult.encryptedKey = encodeToString;
            return encryptResult;
        } catch (c e) {
            throw new EncryptException(e);
        }
    }

    public static EncryptResult encrypt(String[] strArr) {
        return encrypt(TextUtils.join(Constants.COLON_SEPARATOR, strArr));
    }
}
