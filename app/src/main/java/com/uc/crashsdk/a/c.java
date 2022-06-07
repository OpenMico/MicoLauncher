package com.uc.crashsdk.a;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class c {
    static final /* synthetic */ boolean a = !c.class.desiredAssertionStatus();
    private static String b = "";

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, true, false);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z) {
        return a(bArr, bArr2, z, true);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2, true, true);
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, boolean z, boolean z2) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(z ? 1 : 2, secretKeySpec, ivParameterSpec);
        if (!z) {
            return instance.doFinal(bArr);
        }
        if (!z2) {
            bArr = a(bArr);
        }
        return instance.doFinal(bArr);
    }

    private static byte[] a(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 16];
        int length = bArr.length;
        bArr2[0] = (byte) ((length >> 0) & 255);
        bArr2[1] = (byte) ((length >> 8) & 255);
        bArr2[2] = (byte) ((length >> 16) & 255);
        bArr2[3] = (byte) ((length >> 24) & 255);
        for (int i = 4; i < 16; i++) {
            bArr2[i] = 0;
        }
        System.arraycopy(bArr, 0, bArr2, 16, bArr.length);
        return bArr2;
    }

    public static byte[] a() {
        return new byte[]{48, 25, 6, 55};
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00c4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.io.ByteArrayOutputStream, java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] a(java.lang.String r8, byte[] r9) {
        /*
            Method dump skipped, instructions count: 200
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.c.a(java.lang.String, byte[]):byte[]");
    }

    public static void a(byte[] bArr, int i, byte[] bArr2) {
        if (a || bArr2.length == 4) {
            for (int i2 = 0; i2 < 4; i2++) {
                bArr[i2 + i] = bArr2[i2];
            }
            return;
        }
        throw new AssertionError();
    }

    private static byte[] a(File file) {
        FileInputStream fileInputStream;
        Throwable th;
        Exception e;
        int length;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        byte[] bArr = null;
        bufferedInputStream2 = null;
        bufferedInputStream2 = null;
        bufferedInputStream2 = null;
        if (!file.isFile()) {
            return null;
        }
        try {
            length = (int) file.length();
            fileInputStream = new FileInputStream(file);
            try {
                try {
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                } catch (Exception e2) {
                    e = e2;
                    bArr = null;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
            bArr = null;
            fileInputStream = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
        }
        try {
            bArr = new byte[length];
            int i = 0;
            while (i < length) {
                int read = bufferedInputStream.read(bArr, i, length - i);
                if (-1 == read) {
                    break;
                }
                i += read;
            }
            g.a(bufferedInputStream);
            g.a(fileInputStream);
            return bArr;
        } catch (Exception e4) {
            e = e4;
            bufferedInputStream2 = bufferedInputStream;
            g.b(e);
            g.a(bufferedInputStream2);
            g.a(fileInputStream);
            return bArr;
        } catch (Throwable th4) {
            th = th4;
            bufferedInputStream2 = bufferedInputStream;
            g.a(bufferedInputStream2);
            g.a(fileInputStream);
            throw th;
        }
    }

    public static boolean a(File file, String str, String str2) {
        for (int i = 0; i < 2; i++) {
            if (b(file, str, str2)) {
                return true;
            }
            a.a("crashsdk", "upload try again: " + str);
        }
        return false;
    }

    private static boolean b(File file, String str, String str2) {
        try {
            byte[] a2 = a(file);
            if (!(a2 == null || a2.length == 0)) {
                return a(a2, str, str2);
            }
            return false;
        } catch (Exception e) {
            g.a(e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0171 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean a(byte[] r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 373
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.c.a(byte[], java.lang.String, java.lang.String):boolean");
    }
}
