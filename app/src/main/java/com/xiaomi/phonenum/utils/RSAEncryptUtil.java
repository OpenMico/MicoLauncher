package com.xiaomi.phonenum.utils;

import android.text.TextUtils;
import android.util.Base64;
import com.xiaomi.phonenum.utils.AESCoder;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* loaded from: classes4.dex */
public class RSAEncryptUtil {
    private static String a = "-----BEGIN CERTIFICATE-----\nMIICDzCCAXigAwIBAgIEWMX4OjANBgkqhkiG9w0BAQUFADBMMQswCQYDVQQGEwJD\nTjEPMA0GA1UEChMGeGlhb21pMQ8wDQYDVQQLEwZ4aWFvbWkxGzAZBgNVBAMTEmFj\nY291bnQueGlhb21pLmNvbTAeFw0xNzAzMTMwMTM5MDZaFw0xODAzMTMwMTM5MDZa\nMEwxCzAJBgNVBAYTAkNOMQ8wDQYDVQQKEwZ4aWFvbWkxDzANBgNVBAsTBnhpYW9t\naTEbMBkGA1UEAxMSYWNjb3VudC54aWFvbWkuY29tMIGfMA0GCSqGSIb3DQEBAQUA\nA4GNADCBiQKBgQCRDQSxAwWUmA57Isfphgl7H+QHgw9qObsvZM0Xx1YeDzKYVB62\nypGPcPfxnvD0+EfpdhbsMQYeO495BPPzFk+TsFJl4aR47k9sstxrIu7AFeFbdvGg\nubcEu3y/cAk7CcFE7aqKaW7+WFJzLaPVTj6tn0IUe7lFpHXnBFkpzZMVxwIDAQAB\nMA0GCSqGSIb3DQEBBQUAA4GBAICkoEOZ9OtLeZDSQpTqzq8GfU19C/aJCD6Ex7sl\nYqqXVn/p6AozxihEyvIilM56hyaKlLzNJdxPVRYUim6nv6r+kOwE8i7yDRAfcaZD\nnbBeTATPI7E3iKXLF64gjm3Syq8Pw30Yi2azEdB9U+57GBRa0cxAU6wfhn5GSXM6\nW+j0\n-----END CERTIFICATE-----\n";
    private static volatile PublicKey b;
    private final SecretKey c = b();
    private final String d = a(Base64.encodeToString(this.c.getEncoded(), 10), a());

    /* loaded from: classes4.dex */
    public static class EncryptResult {
        public String content;
        public String encryptedKey;
    }

    public EncryptResult encrypt(String str) throws EncryptException {
        EncryptResult encryptResult = new EncryptResult();
        encryptResult.content = a(str, this.c);
        encryptResult.encryptedKey = this.d;
        return encryptResult;
    }

    public String aesDecrypt(String str) throws EncryptException {
        return b(str, this.c);
    }

    private static PublicKey a() throws EncryptException {
        if (b != null) {
            return b;
        }
        try {
            b = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(a.getBytes("UTF-8")))).getPublicKey();
            return b;
        } catch (UnsupportedEncodingException e) {
            throw new EncryptException(e);
        } catch (CertificateException e2) {
            throw new EncryptException(e2);
        }
    }

    private static SecretKey b() throws EncryptException {
        try {
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            new SecureRandom();
            instance.init(128);
            return instance.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e);
        }
    }

    private static String a(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).encrypt(str);
        } catch (AESCoder.CipherException e) {
            throw new EncryptException(e);
        }
    }

    private static String b(String str, SecretKey secretKey) throws EncryptException {
        try {
            return new AESCoder(secretKey.getEncoded()).decrypt(str);
        } catch (AESCoder.CipherException e) {
            throw new EncryptException(e);
        }
    }

    private static String a(String str, PublicKey publicKey) throws EncryptException {
        return a(str, publicKey, "RSA/ECB/PKCS1Padding");
    }

    private static String a(String str, Key key, String str2) throws EncryptException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return new String(Base64.encode(a(str.getBytes("UTF-8"), key, str2), 10), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EncryptException(e);
        }
    }

    private static byte[] a(byte[] bArr, Key key, String str) throws EncryptException {
        try {
            Cipher instance = Cipher.getInstance(str);
            instance.init(1, key);
            return instance.doFinal(bArr);
        } catch (InvalidKeyException e) {
            throw new EncryptException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new EncryptException(e2);
        } catch (BadPaddingException e3) {
            throw new EncryptException(e3);
        } catch (IllegalBlockSizeException e4) {
            throw new EncryptException(e4);
        } catch (NoSuchPaddingException e5) {
            throw new EncryptException(e5);
        }
    }

    /* loaded from: classes4.dex */
    public static class EncryptException extends Exception {
        public EncryptException(Throwable th) {
            super(th);
        }
    }

    public static void setRsaPublicKeyForTest(String str) {
        a = str;
    }
}
