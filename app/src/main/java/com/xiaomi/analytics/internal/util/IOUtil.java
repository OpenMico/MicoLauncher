package com.xiaomi.analytics.internal.util;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class IOUtil {
    public static byte[] inputStream2ByteArray(InputStream inputStream) throws IOException {
        return inputStream2ByteArray(inputStream, 1024);
    }

    public static byte[] inputStream2ByteArray(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            return null;
        }
        if (i < 1) {
            i = 1;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[i];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byteArrayOutputStream.close();
                inputStream.close();
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static void closeSafely(Closeable closeable) {
        if (closeable != null && (closeable instanceof Closeable)) {
            try {
                closeable.close();
            } catch (Exception e) {
                Log.e(ALog.addPrefix("IOUtil"), "closeSafely e", e);
            }
        }
    }
}
