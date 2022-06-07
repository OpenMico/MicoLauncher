package com.xiaomi.mico.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/* loaded from: classes3.dex */
public class ObjectStoreHelper {
    public static <T> String encode(T t) {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream2 = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException unused) {
            objectOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            objectOutputStream.writeObject(t);
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a(byteArrayOutputStream.toByteArray());
        } catch (IOException unused2) {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            objectOutputStream2 = objectOutputStream;
            if (objectOutputStream2 != null) {
                try {
                    objectOutputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002d A[Catch: IOException -> 0x0017, TRY_ENTER, TRY_LEAVE, TryCatch #2 {IOException -> 0x0017, blocks: (B:5:0x0013, B:17:0x002d), top: B:25:0x0000 }] */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v3, types: [T] */
    /* JADX WARN: Type inference failed for: r2v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> T decode(java.lang.String r2) {
        /*
            byte[] r2 = a(r2)
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r2)
            r2 = 0
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch: IOException -> 0x0026, ClassNotFoundException -> 0x0024, all -> 0x0020
            r1.<init>(r0)     // Catch: IOException -> 0x0026, ClassNotFoundException -> 0x0024, all -> 0x0020
            java.lang.Object r2 = r1.readObject()     // Catch: IOException -> 0x001e, ClassNotFoundException -> 0x001c, all -> 0x0031
            r1.close()     // Catch: IOException -> 0x0017
            goto L_0x0030
        L_0x0017:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0030
        L_0x001c:
            r0 = move-exception
            goto L_0x0028
        L_0x001e:
            r0 = move-exception
            goto L_0x0028
        L_0x0020:
            r0 = move-exception
            r1 = r2
            r2 = r0
            goto L_0x0032
        L_0x0024:
            r0 = move-exception
            goto L_0x0027
        L_0x0026:
            r0 = move-exception
        L_0x0027:
            r1 = r2
        L_0x0028:
            r0.printStackTrace()     // Catch: all -> 0x0031
            if (r1 == 0) goto L_0x0030
            r1.close()     // Catch: IOException -> 0x0017
        L_0x0030:
            return r2
        L_0x0031:
            r2 = move-exception
        L_0x0032:
            if (r1 == 0) goto L_0x003c
            r1.close()     // Catch: IOException -> 0x0038
            goto L_0x003c
        L_0x0038:
            r0 = move-exception
            r0.printStackTrace()
        L_0x003c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.mico.base.utils.ObjectStoreHelper.decode(java.lang.String):java.lang.Object");
    }

    private static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }

    private static byte[] a(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
