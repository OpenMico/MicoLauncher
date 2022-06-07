package com.xiaomi.micolauncher.common.crash;

import android.os.SystemClock;
import java.io.File;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class DmesgReader {
    private static final int BUFF = 8192;
    private static final String COMMAND = "dmesg";
    private static final String FILENAME = "dmesg.txt";
    private static final String LINE_OVER = "Over";
    private static final String LINE_TIMEOUT = "Timeout";
    private static final long READ_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(5);

    public static File fetchResultFile() {
        File file = new File(FileDirectoryManager.createLauncherLogDir(), FILENAME);
        if (fetch(file)) {
            return file;
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00b7 A[Catch: Throwable -> 0x00bd, all -> 0x00bb, TryCatch #3 {IOException -> 0x00d1, blocks: (B:5:0x0015, B:17:0x008f, B:7:0x001a, B:15:0x008a, B:28:0x00ae, B:30:0x00b3, B:31:0x00b7, B:32:0x00ba, B:35:0x00bf), top: B:42:0x0015 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ae A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean fetch(java.io.File r14) {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.crash.DmesgReader.fetch(java.io.File):boolean");
    }

    private static long time() {
        return SystemClock.uptimeMillis();
    }
}
