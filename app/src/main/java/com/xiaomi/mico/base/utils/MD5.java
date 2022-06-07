package com.xiaomi.mico.base.utils;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes3.dex */
public class MD5 {
    public static String MD5_32(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();
            instance.update(str.getBytes());
            for (byte b : instance.digest()) {
                sb.append(a(b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static String MD5_16(String str) {
        String MD5_32 = MD5_32(str);
        return MD5_32 != null ? MD5_32.subSequence(8, 24).toString() : "";
    }

    private static String a(byte b) {
        int i = (b & Byte.MAX_VALUE) + (b < 0 ? 128 : 0);
        StringBuilder sb = new StringBuilder();
        sb.append(i < 16 ? "0" : "");
        sb.append(Integer.toHexString(i).toLowerCase());
        return sb.toString();
    }

    public static String file(File file) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[8192];
                while (true) {
                    try {
                        try {
                            int read = fileInputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            instance.update(bArr, 0, read);
                        } catch (Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e) {
                                Log.e("MD5", "Exception on closing MD5 input stream", e);
                            }
                            throw th;
                        }
                    } catch (IOException e2) {
                        throw new RuntimeException("Unable to process file for MD5", e2);
                    }
                }
                String replace = String.format("%32s", new BigInteger(1, instance.digest()).toString(16)).replace(' ', '0');
                try {
                    fileInputStream.close();
                } catch (IOException e3) {
                    Log.e("MD5", "Exception on closing MD5 input stream", e3);
                }
                return replace;
            } catch (FileNotFoundException e4) {
                Log.e("MD5", "Exception while getting FileInputStream", e4);
                return null;
            }
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }
}
