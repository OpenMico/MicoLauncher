package com.efs.sdk.base.a.h.c;

import com.efs.sdk.base.a.h.d;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class a {
    private static final IvParameterSpec a = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

    public static byte[] a(byte[] bArr, String str) {
        try {
            SecretKeySpec a2 = a(str);
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, a2, a);
            return instance.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            d.b("efs.base", "aes decrypt error", e);
            return null;
        }
    }

    private static SecretKeySpec a(String str) {
        return new SecretKeySpec(str.getBytes(), "AES");
    }

    public static byte[] b(byte[] bArr, String str) {
        try {
            SecretKeySpec a2 = a(str);
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(1, a2, a);
            return instance.doFinal(bArr);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            d.b("efs.base", "aes encrypt error", e);
            return null;
        }
    }

    public static byte[] a(String str, String str2) {
        try {
            return b(str.getBytes("UTF-8"), str2);
        } catch (UnsupportedEncodingException e) {
            d.b("efs.base", "getBytes error", e);
            return null;
        }
    }
}
