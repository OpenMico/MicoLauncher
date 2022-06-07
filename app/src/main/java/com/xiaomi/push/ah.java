package com.xiaomi.push;

import android.content.Context;
import android.os.Environment;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/* loaded from: classes4.dex */
public class ah {
    private static final String a = Environment.getExternalStorageDirectory().getPath() + "/mipush/";
    private static final String b = a + "lcfp";
    private static final String c = a + "lcfp.lock";

    public static boolean a(Context context, String str, long j) {
        Throwable th;
        FileLock fileLock;
        IOException e;
        RandomAccessFile randomAccessFile;
        try {
            fileLock = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            File file = new File(c);
            z.m1176a(file);
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                fileLock = randomAccessFile.getChannel().lock();
                boolean b2 = b(context, str, j);
                if (fileLock != null && fileLock.isValid()) {
                    try {
                        fileLock.release();
                    } catch (IOException unused) {
                    }
                }
                z.a(randomAccessFile);
                return b2;
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
                if (fileLock != null && fileLock.isValid()) {
                    try {
                        fileLock.release();
                    } catch (IOException unused2) {
                    }
                }
                z.a(randomAccessFile);
                return true;
            }
        } catch (IOException e3) {
            e = e3;
            randomAccessFile = null;
        } catch (Throwable th3) {
            th = th3;
            if (0 != 0 && fileLock.isValid()) {
                try {
                    fileLock.release();
                } catch (IOException unused3) {
                }
            }
            z.a((Closeable) null);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00dd A[Catch: IOException -> 0x00f3, all -> 0x00f1, LOOP:0: B:43:0x00d7->B:45:0x00dd, LOOP_END, TRY_LEAVE, TryCatch #8 {IOException -> 0x00f3, all -> 0x00f1, blocks: (B:42:0x00d3, B:43:0x00d7, B:45:0x00dd), top: B:65:0x00d3 }] */
    /* JADX WARN: Type inference failed for: r8v3, types: [boolean] */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean b(android.content.Context r16, java.lang.String r17, long r18) {
        /*
            Method dump skipped, instructions count: 265
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.ah.b(android.content.Context, java.lang.String, long):boolean");
    }
}
