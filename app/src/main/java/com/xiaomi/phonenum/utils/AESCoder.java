package com.xiaomi.phonenum.utils;

import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class AESCoder {
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String TAG = "AESCoder";
    private static Logger a = LoggerManager.getLogger();
    private SecretKeySpec b;

    public AESCoder(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length != 16) {
                a.e("AESCoder", "aesKey is invalid");
            }
            this.b = new SecretKeySpec(bArr, "AES");
            return;
        }
        throw new SecurityException("aes key is null");
    }

    public String decrypt(String str) throws CipherException {
        if (str == null) {
            a.e("AESCoder", "decrypt failed for empty data");
            return null;
        }
        try {
            return new String(a(Base64.decode(str, 2)), "UTF-8");
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aescoder", e);
        }
    }

    private byte[] a(byte[] bArr) throws CipherException {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, this.b, new IvParameterSpec(a()));
            if (bArr != null) {
                return instance.doFinal(bArr);
            }
            throw new IllegalBlockSizeException("no block data");
        } catch (Exception e) {
            throw new CipherException("fail to decrypt by aescoder", e);
        }
    }

    private byte[] a() {
        return "0102030405060708".getBytes();
    }

    public String encrypt(String str) throws CipherException {
        try {
            return Base64.encodeToString(b(str.getBytes("UTF-8")), 10);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aescoder", e);
        }
    }

    private byte[] b(byte[] bArr) throws CipherException {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, this.b, new IvParameterSpec(a()));
            return instance.doFinal(bArr);
        } catch (Exception e) {
            throw new CipherException("fail to encrypt by aescoder", e);
        }
    }

    /* loaded from: classes4.dex */
    public class CipherException extends Exception {
        private static final long serialVersionUID = -1479750857131098427L;

        public CipherException(String str) {
            super(str);
        }

        public CipherException(String str, Throwable th) {
            super(str, th);
        }

        public CipherException(Throwable th) {
            super(th);
        }
    }
}
