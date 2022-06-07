package com.blankj.utilcode.util;

import android.os.Build;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/* loaded from: classes.dex */
public final class EncryptUtils {
    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String encryptMD2ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptMD2ToString(str.getBytes());
    }

    public static String encryptMD2ToString(byte[] bArr) {
        return b.a(encryptMD2(bArr));
    }

    public static byte[] encryptMD2(byte[] bArr) {
        return a(bArr, MessageDigestAlgorithms.MD2);
    }

    public static String encryptMD5ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptMD5ToString(str.getBytes());
    }

    public static String encryptMD5ToString(String str, String str2) {
        if (str == null && str2 == null) {
            return "";
        }
        if (str2 == null) {
            return b.a(encryptMD5(str.getBytes()));
        }
        if (str == null) {
            return b.a(encryptMD5(str2.getBytes()));
        }
        return b.a(encryptMD5((str + str2).getBytes()));
    }

    public static String encryptMD5ToString(byte[] bArr) {
        return b.a(encryptMD5(bArr));
    }

    public static String encryptMD5ToString(byte[] bArr, byte[] bArr2) {
        if (bArr == null && bArr2 == null) {
            return "";
        }
        if (bArr2 == null) {
            return b.a(encryptMD5(bArr));
        }
        if (bArr == null) {
            return b.a(encryptMD5(bArr2));
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return b.a(encryptMD5(bArr3));
    }

    public static byte[] encryptMD5(byte[] bArr) {
        return a(bArr, "MD5");
    }

    public static String encryptMD5File2String(String str) {
        return encryptMD5File2String(b.o(str) ? null : new File(str));
    }

    public static byte[] encryptMD5File(String str) {
        return encryptMD5File(b.o(str) ? null : new File(str));
    }

    public static String encryptMD5File2String(File file) {
        return b.a(encryptMD5File(file));
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x003f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] encryptMD5File(java.io.File r4) {
        /*
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: NoSuchAlgorithmException -> 0x0038, IOException -> 0x0036, all -> 0x0033
            r1.<init>(r4)     // Catch: NoSuchAlgorithmException -> 0x0038, IOException -> 0x0036, all -> 0x0033
            java.lang.String r4 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r4)     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
            java.security.DigestInputStream r2 = new java.security.DigestInputStream     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
            r2.<init>(r1, r4)     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
            r4 = 262144(0x40000, float:3.67342E-40)
            byte[] r4 = new byte[r4]     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
        L_0x0018:
            int r3 = r2.read(r4)     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
            if (r3 > 0) goto L_0x0018
            java.security.MessageDigest r4 = r2.getMessageDigest()     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
            byte[] r4 = r4.digest()     // Catch: NoSuchAlgorithmException -> 0x0031, IOException -> 0x002f, all -> 0x0048
            r1.close()     // Catch: IOException -> 0x002a
            goto L_0x002e
        L_0x002a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x002e:
            return r4
        L_0x002f:
            r4 = move-exception
            goto L_0x003a
        L_0x0031:
            r4 = move-exception
            goto L_0x003a
        L_0x0033:
            r4 = move-exception
            r1 = r0
            goto L_0x0049
        L_0x0036:
            r4 = move-exception
            goto L_0x0039
        L_0x0038:
            r4 = move-exception
        L_0x0039:
            r1 = r0
        L_0x003a:
            r4.printStackTrace()     // Catch: all -> 0x0048
            if (r1 == 0) goto L_0x0047
            r1.close()     // Catch: IOException -> 0x0043
            goto L_0x0047
        L_0x0043:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0047:
            return r0
        L_0x0048:
            r4 = move-exception
        L_0x0049:
            if (r1 == 0) goto L_0x0053
            r1.close()     // Catch: IOException -> 0x004f
            goto L_0x0053
        L_0x004f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0053:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.EncryptUtils.encryptMD5File(java.io.File):byte[]");
    }

    public static String encryptSHA1ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA1ToString(str.getBytes());
    }

    public static String encryptSHA1ToString(byte[] bArr) {
        return b.a(encryptSHA1(bArr));
    }

    public static byte[] encryptSHA1(byte[] bArr) {
        return a(bArr, MessageDigestAlgorithms.SHA_1);
    }

    public static String encryptSHA224ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA224ToString(str.getBytes());
    }

    public static String encryptSHA224ToString(byte[] bArr) {
        return b.a(encryptSHA224(bArr));
    }

    public static byte[] encryptSHA224(byte[] bArr) {
        return a(bArr, "SHA224");
    }

    public static String encryptSHA256ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA256ToString(str.getBytes());
    }

    public static String encryptSHA256ToString(byte[] bArr) {
        return b.a(encryptSHA256(bArr));
    }

    public static byte[] encryptSHA256(byte[] bArr) {
        return a(bArr, MessageDigestAlgorithms.SHA_256);
    }

    public static String encryptSHA384ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA384ToString(str.getBytes());
    }

    public static String encryptSHA384ToString(byte[] bArr) {
        return b.a(encryptSHA384(bArr));
    }

    public static byte[] encryptSHA384(byte[] bArr) {
        return a(bArr, MessageDigestAlgorithms.SHA_384);
    }

    public static String encryptSHA512ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA512ToString(str.getBytes());
    }

    public static String encryptSHA512ToString(byte[] bArr) {
        return b.a(encryptSHA512(bArr));
    }

    public static byte[] encryptSHA512(byte[] bArr) {
        return a(bArr, MessageDigestAlgorithms.SHA_512);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static byte[] a(byte[] bArr, String str) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptHmacMD5ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacMD5ToString(str.getBytes(), str2.getBytes());
    }

    public static String encryptHmacMD5ToString(byte[] bArr, byte[] bArr2) {
        return b.a(encryptHmacMD5(bArr, bArr2));
    }

    public static byte[] encryptHmacMD5(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, "HmacMD5");
    }

    public static String encryptHmacSHA1ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA1ToString(str.getBytes(), str2.getBytes());
    }

    public static String encryptHmacSHA1ToString(byte[] bArr, byte[] bArr2) {
        return b.a(encryptHmacSHA1(bArr, bArr2));
    }

    public static byte[] encryptHmacSHA1(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, "HmacSHA1");
    }

    public static String encryptHmacSHA224ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA224ToString(str.getBytes(), str2.getBytes());
    }

    public static String encryptHmacSHA224ToString(byte[] bArr, byte[] bArr2) {
        return b.a(encryptHmacSHA224(bArr, bArr2));
    }

    public static byte[] encryptHmacSHA224(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, "HmacSHA224");
    }

    public static String encryptHmacSHA256ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA256ToString(str.getBytes(), str2.getBytes());
    }

    public static String encryptHmacSHA256ToString(byte[] bArr, byte[] bArr2) {
        return b.a(encryptHmacSHA256(bArr, bArr2));
    }

    public static byte[] encryptHmacSHA256(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, "HmacSHA256");
    }

    public static String encryptHmacSHA384ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA384ToString(str.getBytes(), str2.getBytes());
    }

    public static String encryptHmacSHA384ToString(byte[] bArr, byte[] bArr2) {
        return b.a(encryptHmacSHA384(bArr, bArr2));
    }

    public static byte[] encryptHmacSHA384(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, "HmacSHA384");
    }

    public static String encryptHmacSHA512ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA512ToString(str.getBytes(), str2.getBytes());
    }

    public static String encryptHmacSHA512ToString(byte[] bArr, byte[] bArr2) {
        return b.a(encryptHmacSHA512(bArr, bArr2));
    }

    public static byte[] encryptHmacSHA512(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, "HmacSHA512");
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, String str) {
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            return null;
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, str);
            Mac instance = Mac.getInstance(str);
            instance.init(secretKeySpec);
            return instance.doFinal(bArr);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptDES2Base64(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return b.f(encryptDES(bArr, bArr2, str, bArr3));
    }

    public static String encryptDES2HexString(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return b.a(encryptDES(bArr, bArr2, str, bArr3));
    }

    public static byte[] encryptDES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return a(bArr, bArr2, "DES", str, bArr3, true);
    }

    public static byte[] decryptBase64DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return decryptDES(b.g(bArr), bArr2, str, bArr3);
    }

    public static byte[] decryptHexStringDES(String str, byte[] bArr, String str2, byte[] bArr2) {
        return decryptDES(b.d(str), bArr, str2, bArr2);
    }

    public static byte[] decryptDES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return a(bArr, bArr2, "DES", str, bArr3, false);
    }

    public static byte[] encrypt3DES2Base64(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return b.f(encrypt3DES(bArr, bArr2, str, bArr3));
    }

    public static String encrypt3DES2HexString(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return b.a(encrypt3DES(bArr, bArr2, str, bArr3));
    }

    public static byte[] encrypt3DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return a(bArr, bArr2, "DESede", str, bArr3, true);
    }

    public static byte[] decryptBase64_3DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return decrypt3DES(b.g(bArr), bArr2, str, bArr3);
    }

    public static byte[] decryptHexString3DES(String str, byte[] bArr, String str2, byte[] bArr2) {
        return decrypt3DES(b.d(str), bArr, str2, bArr2);
    }

    public static byte[] decrypt3DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return a(bArr, bArr2, "DESede", str, bArr3, false);
    }

    public static byte[] encryptAES2Base64(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return b.f(encryptAES(bArr, bArr2, str, bArr3));
    }

    public static String encryptAES2HexString(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return b.a(encryptAES(bArr, bArr2, str, bArr3));
    }

    public static byte[] encryptAES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return a(bArr, bArr2, "AES", str, bArr3, true);
    }

    public static byte[] decryptBase64AES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return decryptAES(b.g(bArr), bArr2, str, bArr3);
    }

    public static byte[] decryptHexStringAES(String str, byte[] bArr, String str2, byte[] bArr2) {
        return decryptAES(b.d(str), bArr, str2, bArr2);
    }

    public static byte[] decryptAES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return a(bArr, bArr2, "AES", str, bArr3, false);
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, String str, String str2, byte[] bArr3, boolean z) {
        SecretKey secretKey;
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            return null;
        }
        try {
            if ("DES".equals(str)) {
                secretKey = SecretKeyFactory.getInstance(str).generateSecret(new DESKeySpec(bArr2));
            } else {
                secretKey = new SecretKeySpec(bArr2, str);
            }
            Cipher instance = Cipher.getInstance(str2);
            int i = 1;
            if (!(bArr3 == null || bArr3.length == 0)) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
                if (!z) {
                    i = 2;
                }
                instance.init(i, secretKey, ivParameterSpec);
                return instance.doFinal(bArr);
            }
            i = 2;
            instance.init(i, secretKey);
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptRSA2Base64(byte[] bArr, byte[] bArr2, int i, String str) {
        return b.f(encryptRSA(bArr, bArr2, i, str));
    }

    public static String encryptRSA2HexString(byte[] bArr, byte[] bArr2, int i, String str) {
        return b.a(encryptRSA(bArr, bArr2, i, str));
    }

    public static byte[] encryptRSA(byte[] bArr, byte[] bArr2, int i, String str) {
        return a(bArr, bArr2, i, str, true);
    }

    public static byte[] decryptBase64RSA(byte[] bArr, byte[] bArr2, int i, String str) {
        return decryptRSA(b.g(bArr), bArr2, i, str);
    }

    public static byte[] decryptHexStringRSA(String str, byte[] bArr, int i, String str2) {
        return decryptRSA(b.d(str), bArr, i, str2);
    }

    public static byte[] decryptRSA(byte[] bArr, byte[] bArr2, int i, String str) {
        return a(bArr, bArr2, i, str, false);
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, int i, String str, boolean z) {
        KeyFactory keyFactory;
        Key key;
        if (bArr == null || bArr.length == 0 || bArr2 == null || bArr2.length == 0) {
            return null;
        }
        try {
            if (Build.VERSION.SDK_INT < 28) {
                keyFactory = KeyFactory.getInstance("RSA", "BC");
            } else {
                keyFactory = KeyFactory.getInstance("RSA");
            }
            if (z) {
                key = keyFactory.generatePublic(new X509EncodedKeySpec(bArr2));
            } else {
                key = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bArr2));
            }
            if (key == null) {
                return null;
            }
            Cipher instance = Cipher.getInstance(str);
            instance.init(z ? 1 : 2, key);
            int length = bArr.length;
            int i2 = i / 8;
            if (z && str.toLowerCase().endsWith("pkcs1padding")) {
                i2 -= 11;
            }
            int i3 = length / i2;
            if (i3 <= 0) {
                return instance.doFinal(bArr);
            }
            byte[] bArr3 = new byte[i2];
            int i4 = 0;
            byte[] bArr4 = new byte[0];
            for (int i5 = 0; i5 < i3; i5++) {
                System.arraycopy(bArr, i4, bArr3, 0, i2);
                bArr4 = a(bArr4, instance.doFinal(bArr3));
                i4 += i2;
            }
            if (i4 == length) {
                return bArr4;
            }
            int i6 = length - i4;
            byte[] bArr5 = new byte[i6];
            System.arraycopy(bArr, i4, bArr5, 0, i6);
            return a(bArr4, instance.doFinal(bArr5));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] rc4(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2 == null) {
            return null;
        }
        if (bArr2.length < 1 || bArr2.length > 256) {
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        }
        byte[] bArr3 = new byte[256];
        byte[] bArr4 = new byte[256];
        int length = bArr2.length;
        for (int i = 0; i < 256; i++) {
            bArr3[i] = (byte) i;
            bArr4[i] = bArr2[i % length];
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            i2 = (i2 + bArr3[i3] + bArr4[i3]) & 255;
            byte b = bArr3[i2];
            bArr3[i2] = bArr3[i3];
            bArr3[i3] = b;
        }
        byte[] bArr5 = new byte[bArr.length];
        int i4 = 0;
        for (int i5 = 0; i5 < bArr.length; i5++) {
            i4 = (i4 + 1) & 255;
            i2 = (i2 + bArr3[i4]) & 255;
            byte b2 = bArr3[i2];
            bArr3[i2] = bArr3[i4];
            bArr3[i4] = b2;
            bArr5[i5] = (byte) (bArr3[(bArr3[i4] + bArr3[i2]) & 255] ^ bArr[i5]);
        }
        return bArr5;
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }
}
