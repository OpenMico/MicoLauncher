package com.xiaomi.micolauncher.common.crash;

import android.os.SystemClock;
import com.xiaomi.micolauncher.common.L;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class DumpCmd {
    private static final int BUFF = 8192;
    public static final String CMD_TOP = "top -b -n1 -H -s6";
    public static final String FILE_TOP = "topOutput.log";
    private static final String LINE_OVER = "Over";
    private static final String LINE_TIMEOUT = "Timeout";
    private static final long READ_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(15);

    public static File fetchResultFile(String str, String str2) {
        File file = new File(FileDirectoryManager.createLauncherLogDir(), str2);
        if (fetch(file, str)) {
            return file;
        }
        return null;
    }

    public static File fetchResultFile(String str, File file) {
        if (fetch(file, str)) {
            return file;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0093 A[Catch: Throwable -> 0x0099, all -> 0x0097, TryCatch #6 {IOException -> 0x00ad, blocks: (B:5:0x0013, B:17:0x0075, B:7:0x0018, B:15:0x0070, B:28:0x008a, B:30:0x008f, B:31:0x0093, B:32:0x0096), top: B:46:0x0013 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean fetch(java.io.File r14, java.lang.String r15) {
        /*
            r0 = 2
            r1 = 1
            r2 = 0
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch: IOException -> 0x00bc
            java.lang.Process r3 = r3.exec(r15)     // Catch: IOException -> 0x00bc
            r4 = 8192(0x2000, float:1.14794E-41)
            byte[] r4 = new byte[r4]
            long r5 = time()
            java.io.InputStream r3 = r3.getInputStream()     // Catch: IOException -> 0x00ad
            r7 = 0
            java.io.BufferedOutputStream r8 = new java.io.BufferedOutputStream     // Catch: Throwable -> 0x0099, all -> 0x0097
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch: Throwable -> 0x0099, all -> 0x0097
            r9.<init>(r14)     // Catch: Throwable -> 0x0099, all -> 0x0097
            r8.<init>(r9)     // Catch: Throwable -> 0x0099, all -> 0x0097
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: Throwable -> 0x0082, all -> 0x007f
            r14.<init>()     // Catch: Throwable -> 0x0082, all -> 0x007f
            java.lang.String r9 = "---> Time : "
            r14.append(r9)     // Catch: Throwable -> 0x0082, all -> 0x007f
            android.icu.util.Calendar r9 = android.icu.util.Calendar.getInstance()     // Catch: Throwable -> 0x0082, all -> 0x007f
            java.util.Date r9 = r9.getTime()     // Catch: Throwable -> 0x0082, all -> 0x007f
            java.lang.String r9 = r9.toLocaleString()     // Catch: Throwable -> 0x0082, all -> 0x007f
            r14.append(r9)     // Catch: Throwable -> 0x0082, all -> 0x007f
            java.lang.String r9 = "\n"
            r14.append(r9)     // Catch: Throwable -> 0x0082, all -> 0x007f
            java.lang.String r14 = r14.toString()     // Catch: Throwable -> 0x0082, all -> 0x007f
            byte[] r14 = r14.getBytes()     // Catch: Throwable -> 0x0082, all -> 0x007f
            r8.write(r14)     // Catch: Throwable -> 0x0082, all -> 0x007f
        L_0x004b:
            long r9 = time()     // Catch: Throwable -> 0x0082, all -> 0x007f
            long r9 = r9 - r5
            long r11 = com.xiaomi.micolauncher.common.crash.DumpCmd.READ_TIMEOUT_MILLIS     // Catch: Throwable -> 0x0082, all -> 0x007f
            int r14 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r14 <= 0) goto L_0x0060
            java.lang.String r14 = "Timeout"
            byte[] r14 = r14.getBytes()     // Catch: Throwable -> 0x0082, all -> 0x007f
            r8.write(r14)     // Catch: Throwable -> 0x0082, all -> 0x007f
            goto L_0x0070
        L_0x0060:
            int r14 = r3.read(r4)     // Catch: Throwable -> 0x0082, all -> 0x007f
            r9 = -1
            if (r14 != r9) goto L_0x0079
            java.lang.String r14 = "Over"
            byte[] r14 = r14.getBytes()     // Catch: Throwable -> 0x0082, all -> 0x007f
            r8.write(r14)     // Catch: Throwable -> 0x0082, all -> 0x007f
        L_0x0070:
            r8.close()     // Catch: Throwable -> 0x0099, all -> 0x0097
            if (r3 == 0) goto L_0x0078
            r3.close()     // Catch: IOException -> 0x00ad
        L_0x0078:
            return r1
        L_0x0079:
            if (r14 == 0) goto L_0x004b
            r8.write(r4, r2, r14)     // Catch: Throwable -> 0x0082, all -> 0x007f
            goto L_0x004b
        L_0x007f:
            r14 = move-exception
            r4 = r7
            goto L_0x0088
        L_0x0082:
            r14 = move-exception
            throw r14     // Catch: all -> 0x0084
        L_0x0084:
            r4 = move-exception
            r13 = r4
            r4 = r14
            r14 = r13
        L_0x0088:
            if (r4 == 0) goto L_0x0093
            r8.close()     // Catch: Throwable -> 0x008e, all -> 0x0097
            goto L_0x0096
        L_0x008e:
            r5 = move-exception
            r4.addSuppressed(r5)     // Catch: Throwable -> 0x0099, all -> 0x0097
            goto L_0x0096
        L_0x0093:
            r8.close()     // Catch: Throwable -> 0x0099, all -> 0x0097
        L_0x0096:
            throw r14     // Catch: Throwable -> 0x0099, all -> 0x0097
        L_0x0097:
            r14 = move-exception
            goto L_0x009c
        L_0x0099:
            r14 = move-exception
            r7 = r14
            throw r7     // Catch: all -> 0x0097
        L_0x009c:
            if (r3 == 0) goto L_0x00ac
            if (r7 == 0) goto L_0x00a9
            r3.close()     // Catch: Throwable -> 0x00a4, IOException -> 0x00ad
            goto L_0x00ac
        L_0x00a4:
            r3 = move-exception
            r7.addSuppressed(r3)     // Catch: IOException -> 0x00ad
            goto L_0x00ac
        L_0x00a9:
            r3.close()     // Catch: IOException -> 0x00ad
        L_0x00ac:
            throw r14     // Catch: IOException -> 0x00ad
        L_0x00ad:
            r14 = move-exception
            com.elvishew.xlog.Logger r3 = com.xiaomi.micolauncher.common.L.base
            java.lang.String r4 = "IO run command %s %s"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r2] = r15
            r0[r1] = r14
            r3.e(r4, r0)
            return r2
        L_0x00bc:
            r14 = move-exception
            com.elvishew.xlog.Logger r3 = com.xiaomi.micolauncher.common.L.base
            java.lang.String r4 = "run command %s %s"
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r2] = r15
            r0[r1] = r14
            r3.e(r4, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.crash.DumpCmd.fetch(java.io.File, java.lang.String):boolean");
    }

    public static void dump(String str) {
        try {
            Process exec = Runtime.getRuntime().exec(str);
            byte[] bArr = new byte[8192];
            long time = time();
            try {
                InputStream inputStream = exec.getInputStream();
                while (time() - time <= READ_TIMEOUT_MILLIS && inputStream.read(bArr) != -1) {
                }
            } catch (IOException e) {
                L.latency.e("dum %s error=%s", str, e);
            }
        } catch (IOException e2) {
            L.latency.e("run command %s %s", str, e2);
        }
    }

    private static long time() {
        return SystemClock.uptimeMillis();
    }
}
