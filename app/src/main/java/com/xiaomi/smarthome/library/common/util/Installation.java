package com.xiaomi.smarthome.library.common.util;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/* loaded from: classes4.dex */
public class Installation {
    private static String a;

    public static synchronized String id(Context context) {
        String str;
        synchronized (Installation.class) {
            if (a == null) {
                File file = new File(context.getFilesDir(), "INSTALLATION");
                try {
                    if (!file.exists()) {
                        b(file);
                    }
                    a = a(file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            str = a;
        }
        return str;
    }

    private static String a(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] bArr = new byte[(int) randomAccessFile.length()];
        randomAccessFile.readFully(bArr);
        randomAccessFile.close();
        return new String(bArr);
    }

    private static void b(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(UUID.randomUUID().toString().getBytes());
        fileOutputStream.close();
    }
}
