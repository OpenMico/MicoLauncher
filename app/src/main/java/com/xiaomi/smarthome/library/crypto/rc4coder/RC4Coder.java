package com.xiaomi.smarthome.library.crypto.rc4coder;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class RC4Coder {
    public static final String RC4_ALGORITHM = "RC4";
    private SecretKeySpec a;

    private static boolean a(byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public RC4Coder(byte[] bArr) throws SecurityException {
        if (a(bArr)) {
            throw new SecurityException("rc4 key is null");
        } else if (bArr.length == 16) {
            this.a = new SecretKeySpec(bArr, RC4_ALGORITHM);
        } else {
            throw new IllegalArgumentException("rc4Key is invalid");
        }
    }

    public byte[] decrypt(byte[] bArr) throws SecurityException {
        try {
            Cipher instance = Cipher.getInstance(RC4_ALGORITHM);
            instance.init(2, this.a);
            if (bArr != null) {
                return instance.doFinal(bArr);
            }
            throw new IllegalBlockSizeException("no block data");
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new SecurityException(e2);
        } catch (BadPaddingException e3) {
            throw new SecurityException(e3);
        } catch (IllegalBlockSizeException e4) {
            throw new SecurityException(e4);
        } catch (NoSuchPaddingException e5) {
            throw new SecurityException(e5);
        }
    }

    public byte[] encrypt(byte[] bArr) throws SecurityException {
        try {
            Cipher instance = Cipher.getInstance(RC4_ALGORITHM);
            instance.init(1, this.a);
            return instance.doFinal(bArr);
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new SecurityException(e2);
        } catch (BadPaddingException e3) {
            throw new SecurityException(e3);
        } catch (IllegalBlockSizeException e4) {
            throw new SecurityException(e4);
        } catch (NoSuchPaddingException e5) {
            throw new SecurityException(e5);
        }
    }
}
