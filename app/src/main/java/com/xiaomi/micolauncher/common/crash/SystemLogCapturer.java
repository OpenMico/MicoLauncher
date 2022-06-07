package com.xiaomi.micolauncher.common.crash;

import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.common.L;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class SystemLogCapturer {
    private static final int BUFF_SIZE = 4096;
    private static final String COMMAND = "logcat -v time";
    private static final String EXT_TXT = "txt";
    private static final String LOG_NAME = "syslog";
    private static final int MAX_FILES_COUNT = 10;
    private static final int MAX_SINGLE_FILE_SIZE = 4194304;
    private static final int REASON_CUR_FILE_FULL = 0;
    private static final int REASON_IO_EXCEPTION = 3;
    private static final int REASON_READ_STREAM_ERROR = 2;
    private static final int REASON_STOP_CAPTURE = 1;
    private static final String SYSLOG_TXT = "syslog.txt";
    private long backoffTime;
    private volatile boolean isRunningCapture;
    private volatile boolean isStopCommand;
    private static final long MAX_WAIT_READ_LOG_MILLIS = TimeUnit.SECONDS.toMillis(10);
    private static final long MAX_BACKOFF = TimeUnit.HOURS.toMillis(1);
    private static final long MIN_BACKOFF = TimeUnit.SECONDS.toMillis(1);
    private static final long WAIT_STOP_CAPUTURE_LOG_MILLIS = TimeUnit.SECONDS.toMillis(1);

    public static SystemLogCapturer getInstance() {
        return Wrapper.INSTANCE;
    }

    private SystemLogCapturer() {
        this.backoffTime = MIN_BACKOFF;
        this.isRunningCapture = false;
        this.isStopCommand = false;
    }

    public void startCaptureEndlessAsync() {
        if (this.isRunningCapture) {
            L.base.e("SystemLogCapture : already running log capture.");
        } else {
            Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.crash.-$$Lambda$SystemLogCapturer$jjCRO8KsnWXcjV3mNlb51QQn-u0
                @Override // java.lang.Runnable
                public final void run() {
                    SystemLogCapturer.this.startCaptureEndless();
                }
            });
        }
    }

    public void stopCaptureLog() {
        if (this.isRunningCapture) {
            this.isStopCommand = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startCaptureEndless() {
        File logPathIndexed;
        clearOldLog();
        int i = 0;
        while (!this.isStopCommand) {
            long nextBackoffTime = getNextBackoffTime();
            try {
                logPathIndexed = getLogPathIndexed(i);
            } catch (Throwable th) {
                th.printStackTrace();
                L.debug.i("set backoff time is %s ms", Long.valueOf(nextBackoffTime));
            }
            if (logPathIndexed == null) {
                L.debug.e("failed to create file %s", Integer.valueOf(i));
                return;
            }
            deleteFile(logPathIndexed);
            if (readCommandResult(logPathIndexed, true, Long.MAX_VALUE) == 0) {
                i++;
                if (i >= 10) {
                    i = 0;
                }
            } else {
                SystemClock.sleep(nextBackoffTime);
            }
        }
    }

    public void clearOldLog() {
        Threads.getIoThreadPool().submit(new Runnable() { // from class: com.xiaomi.micolauncher.common.crash.-$$Lambda$SystemLogCapturer$2ZxDDhlNGopHe0gwH0j3YDHsACc
            @Override // java.lang.Runnable
            public final void run() {
                SystemLogCapturer.lambda$clearOldLog$0(SystemLogCapturer.this);
            }
        });
    }

    public static /* synthetic */ void lambda$clearOldLog$0(SystemLogCapturer systemLogCapturer) {
        systemLogCapturer.isStopCommand = true;
        SystemClock.sleep(WAIT_STOP_CAPUTURE_LOG_MILLIS);
        systemLogCapturer.deleteFile(systemLogCapturer.getLogPath());
        systemLogCapturer.clearCircularLogDir();
        systemLogCapturer.isStopCommand = false;
    }

    private void deleteFile(File file) {
        if (!file.delete()) {
            L.debug.w("SystemLogCapture : failed to delete file %s", file);
        }
    }

    private long getNextBackoffTime() {
        long j = this.backoffTime;
        if (j < MAX_BACKOFF) {
            this.backoffTime = 2 * j;
        } else {
            this.backoffTime = MIN_BACKOFF;
        }
        return j;
    }

    File captureSystemLog() {
        if (!TextUtils.equals(Environment.getExternalStorageState(), "mounted")) {
            return null;
        }
        File logPath = getLogPath();
        readCommandResult(logPath, false, MAX_WAIT_READ_LOG_MILLIS);
        return logPath;
    }

    @Nullable
    private File getLogPathIndexed(int i) {
        File circularLogDir = getCircularLogDir();
        if (!circularLogDir.isDirectory() && !circularLogDir.mkdir()) {
            return null;
        }
        return new File(circularLogDir, i + "." + EXT_TXT);
    }

    private void clearCircularLogDir() {
        File circularLogDir = getCircularLogDir();
        if (circularLogDir.isDirectory()) {
            File[] listFiles = circularLogDir.listFiles();
            if (!ContainerUtil.isEmpty(listFiles)) {
                for (File file : listFiles) {
                    deleteFile(file);
                }
            }
        }
    }

    @NotNull
    private File getCircularLogDir() {
        return new File(FileDirectoryManager.createLauncherLogDir(), LOG_NAME);
    }

    @NonNull
    private File getLogPath() {
        return new File(FileDirectoryManager.createLauncherLogDir(), SYSLOG_TXT);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r18v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.elvishew.xlog.Logger] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v0, types: [int] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int readCommandResult(java.io.File r17, boolean r18, long r19) {
        /*
            Method dump skipped, instructions count: 303
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.common.crash.SystemLogCapturer.readCommandResult(java.io.File, boolean, long):int");
    }

    /* loaded from: classes3.dex */
    static class Wrapper {
        static final SystemLogCapturer INSTANCE = new SystemLogCapturer();

        Wrapper() {
        }
    }
}
