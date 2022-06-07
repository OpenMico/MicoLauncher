package com.uc.crashsdk.a;

import androidx.renderscript.ScriptIntrinsicBLAS;
import com.alibaba.fastjson.asm.Opcodes;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public final class b {
    private static final int[] a = {126, 147, 115, 241, 101, Opcodes.IFNULL, 215, 134};
    private static final int[] b = {125, Opcodes.INVOKEINTERFACE, 233, 226, 129, ScriptIntrinsicBLAS.RIGHT, Opcodes.DCMPL, Opcodes.ARETURN};
    private static final int[] c = {238, Opcodes.INVOKEINTERFACE, 233, 179, 129, ScriptIntrinsicBLAS.RIGHT, Opcodes.DCMPL, Opcodes.GOTO};

    public static String a(String str) {
        Exception e;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        File file = new File(str);
        FileInputStream fileInputStream3 = null;
        if (!file.exists()) {
            return null;
        }
        try {
            fileInputStream2 = new FileInputStream(file);
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th) {
            th = th;
        }
        try {
            byte[] bArr = new byte[(int) file.length()];
            fileInputStream2.read(bArr);
            g.a(fileInputStream2);
            byte[] a2 = a(bArr, a);
            if (a2 == null || a2.length <= 0) {
                g.a((Closeable) null);
                return null;
            }
            int length = a2.length - 1;
            String str2 = a2[length] == 10 ? new String(a2, 0, length) : new String(a2);
            g.a((Closeable) null);
            return str2;
        } catch (Exception e3) {
            e = e3;
            fileInputStream = fileInputStream2;
            try {
                g.a(e);
                g.a(fileInputStream);
                return null;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream3 = fileInputStream;
                g.a(fileInputStream3);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream3 = fileInputStream2;
            g.a(fileInputStream3);
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0022, code lost:
        r1 = com.uc.crashsdk.a.g.e(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r7, java.lang.String r8, boolean r9) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.b.a(java.lang.String, java.lang.String, boolean):java.lang.String");
    }

    private static byte[] a(byte[] bArr, int[] iArr) {
        if (bArr.length - 0 < 2 || iArr == null || iArr.length != 8) {
            return null;
        }
        int length = (bArr.length - 2) - 0;
        try {
            byte[] bArr2 = new byte[length];
            byte b2 = 0;
            for (int i = 0; i < length; i++) {
                byte b3 = (byte) (bArr[i + 0] ^ iArr[i % 8]);
                bArr2[i] = b3;
                b2 = (byte) (b2 ^ b3);
            }
            if (bArr[length + 0] == ((byte) ((iArr[0] ^ b2) & 255)) && bArr[length + 1 + 0] == ((byte) ((iArr[1] ^ b2) & 255))) {
                return bArr2;
            }
            return null;
        } catch (Exception e) {
            g.a(e);
            return null;
        }
    }

    private static byte[] b(byte[] bArr, int[] iArr) {
        if (bArr == null || iArr == null || iArr.length != 8) {
            return null;
        }
        int length = bArr.length;
        try {
            byte[] bArr2 = new byte[length + 2];
            byte b2 = 0;
            for (int i = 0; i < length; i++) {
                byte b3 = bArr[i];
                bArr2[i] = (byte) (iArr[i % 8] ^ b3);
                b2 = (byte) (b2 ^ b3);
            }
            bArr2[length] = (byte) (iArr[0] ^ b2);
            bArr2[length + 1] = (byte) (iArr[1] ^ b2);
            return bArr2;
        } catch (Exception e) {
            g.a(e);
            return null;
        }
    }

    public static boolean a(String str, String str2) {
        FileOutputStream fileOutputStream;
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            fileOutputStream = new FileOutputStream(file);
        } catch (Throwable th) {
            g.a(th);
            fileOutputStream = null;
        }
        if (fileOutputStream == null) {
            return false;
        }
        byte[] b2 = b(str2.getBytes(), a);
        if (b2 == null) {
            return false;
        }
        try {
            fileOutputStream.write(b2);
            return true;
        } finally {
            g.a(fileOutputStream);
        }
    }
}
