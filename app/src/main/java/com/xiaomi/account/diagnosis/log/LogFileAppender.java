package com.xiaomi.account.diagnosis.log;

import com.xiaomi.account.diagnosis.DiagnosisConstants;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class LogFileAppender {
    private static final ThreadLocal<SimpleDateFormat> sFormat = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.account.diagnosis.log.LogFileAppender.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", Locale.US);
        }
    };

    private LogFileAppender() {
    }

    private static File getTodayLogFile() {
        String format = sFormat.get().format(new Date());
        File passportCacheDir = DiagnosisConstants.getPassportCacheDir();
        return new File(passportCacheDir, format + ".log");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v9 */
    public static void appendLine(String str) {
        Throwable th;
        IOException e;
        BufferedWriter bufferedWriter;
        File todayLogFile = getTodayLogFile();
        File parentFile = todayLogFile.getParentFile();
        boolean exists = parentFile.exists();
        BufferedWriter bufferedWriter2 = parentFile;
        if (!exists) {
            boolean mkdirs = parentFile.mkdirs();
            bufferedWriter2 = mkdirs;
            if (!mkdirs) {
                return;
            }
        }
        try {
            bufferedWriter2 = 0;
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(todayLogFile, true));
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedWriter.append((CharSequence) str);
            bufferedWriter.newLine();
            closeQuietly(bufferedWriter);
        } catch (IOException e3) {
            e = e3;
            bufferedWriter2 = bufferedWriter;
            e.printStackTrace();
            closeQuietly(bufferedWriter2);
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            closeQuietly(bufferedWriter2);
            throw th;
        }
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }
}
