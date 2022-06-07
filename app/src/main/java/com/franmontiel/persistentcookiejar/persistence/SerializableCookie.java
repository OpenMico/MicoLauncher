package com.franmontiel.persistentcookiejar.persistence;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import okhttp3.Cookie;

/* loaded from: classes.dex */
public class SerializableCookie implements Serializable {
    private static final String a = "SerializableCookie";
    private static long c = -1;
    private static final long serialVersionUID = -8594045714036645534L;
    private transient Cookie b;

    public String encode(Cookie cookie) {
        Throwable th;
        ByteArrayOutputStream byteArrayOutputStream;
        ObjectOutputStream objectOutputStream;
        IOException e;
        ObjectOutputStream objectOutputStream2;
        try {
            this.b = cookie;
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            objectOutputStream2 = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream2.writeObject(this);
                try {
                    objectOutputStream2.close();
                } catch (IOException e2) {
                    Log.d(a, "Stream not closed in encodeCookie", e2);
                }
                return a(byteArrayOutputStream.toByteArray());
            } catch (IOException e3) {
                e = e3;
                Log.d(a, "IOException in encodeCookie", e);
                if (objectOutputStream2 != null) {
                    try {
                        objectOutputStream2.close();
                    } catch (IOException e4) {
                        Log.d(a, "Stream not closed in encodeCookie", e4);
                    }
                }
                return null;
            }
        } catch (IOException e5) {
            e = e5;
            objectOutputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0) {
                try {
                    objectOutputStream.close();
                } catch (IOException e6) {
                    Log.d(a, "Stream not closed in encodeCookie", e6);
                }
            }
            throw th;
        }
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.ObjectInputStream] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v16 */
    /* JADX WARN: Type inference failed for: r5v17 */
    /* JADX WARN: Type inference failed for: r5v3, types: [okhttp3.Cookie] */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public okhttp3.Cookie decode(java.lang.String r5) {
        /*
            r4 = this;
            byte[] r5 = a(r5)
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream
            r0.<init>(r5)
            r5 = 0
            java.io.ObjectInputStream r1 = new java.io.ObjectInputStream     // Catch: IOException -> 0x003b, ClassNotFoundException -> 0x002c, all -> 0x0028
            r1.<init>(r0)     // Catch: IOException -> 0x003b, ClassNotFoundException -> 0x002c, all -> 0x0028
            java.lang.Object r0 = r1.readObject()     // Catch: IOException -> 0x0026, ClassNotFoundException -> 0x0024, all -> 0x004a
            com.franmontiel.persistentcookiejar.persistence.SerializableCookie r0 = (com.franmontiel.persistentcookiejar.persistence.SerializableCookie) r0     // Catch: IOException -> 0x0026, ClassNotFoundException -> 0x0024, all -> 0x004a
            okhttp3.Cookie r5 = r0.b     // Catch: IOException -> 0x0026, ClassNotFoundException -> 0x0024, all -> 0x004a
            r1.close()     // Catch: IOException -> 0x001b
            goto L_0x0049
        L_0x001b:
            r0 = move-exception
            java.lang.String r1 = com.franmontiel.persistentcookiejar.persistence.SerializableCookie.a
            java.lang.String r2 = "Stream not closed in decodeCookie"
            android.util.Log.d(r1, r2, r0)
            goto L_0x0049
        L_0x0024:
            r0 = move-exception
            goto L_0x002e
        L_0x0026:
            r0 = move-exception
            goto L_0x003d
        L_0x0028:
            r0 = move-exception
            r1 = r5
            r5 = r0
            goto L_0x004b
        L_0x002c:
            r0 = move-exception
            r1 = r5
        L_0x002e:
            java.lang.String r2 = com.franmontiel.persistentcookiejar.persistence.SerializableCookie.a     // Catch: all -> 0x004a
            java.lang.String r3 = "ClassNotFoundException in decodeCookie"
            android.util.Log.d(r2, r3, r0)     // Catch: all -> 0x004a
            if (r1 == 0) goto L_0x0049
            r1.close()     // Catch: IOException -> 0x001b
            goto L_0x0049
        L_0x003b:
            r0 = move-exception
            r1 = r5
        L_0x003d:
            java.lang.String r2 = com.franmontiel.persistentcookiejar.persistence.SerializableCookie.a     // Catch: all -> 0x004a
            java.lang.String r3 = "IOException in decodeCookie"
            android.util.Log.d(r2, r3, r0)     // Catch: all -> 0x004a
            if (r1 == 0) goto L_0x0049
            r1.close()     // Catch: IOException -> 0x001b
        L_0x0049:
            return r5
        L_0x004a:
            r5 = move-exception
        L_0x004b:
            if (r1 == 0) goto L_0x0059
            r1.close()     // Catch: IOException -> 0x0051
            goto L_0x0059
        L_0x0051:
            r0 = move-exception
            java.lang.String r1 = com.franmontiel.persistentcookiejar.persistence.SerializableCookie.a
            java.lang.String r2 = "Stream not closed in decodeCookie"
            android.util.Log.d(r1, r2, r0)
        L_0x0059:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.franmontiel.persistentcookiejar.persistence.SerializableCookie.decode(java.lang.String):okhttp3.Cookie");
    }

    private static byte[] a(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.b.name());
        objectOutputStream.writeObject(this.b.value());
        objectOutputStream.writeLong(this.b.persistent() ? this.b.expiresAt() : c);
        objectOutputStream.writeObject(this.b.domain());
        objectOutputStream.writeObject(this.b.path());
        objectOutputStream.writeBoolean(this.b.secure());
        objectOutputStream.writeBoolean(this.b.httpOnly());
        objectOutputStream.writeBoolean(this.b.hostOnly());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Cookie.Builder builder = new Cookie.Builder();
        builder.name((String) objectInputStream.readObject());
        builder.value((String) objectInputStream.readObject());
        long readLong = objectInputStream.readLong();
        if (readLong != c) {
            builder.expiresAt(readLong);
        }
        String str = (String) objectInputStream.readObject();
        builder.domain(str);
        builder.path((String) objectInputStream.readObject());
        if (objectInputStream.readBoolean()) {
            builder.secure();
        }
        if (objectInputStream.readBoolean()) {
            builder.httpOnly();
        }
        if (objectInputStream.readBoolean()) {
            builder.hostOnlyDomain(str);
        }
        this.b = builder.build();
    }
}
