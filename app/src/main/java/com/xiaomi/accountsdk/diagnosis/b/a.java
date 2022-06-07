package com.xiaomi.accountsdk.diagnosis.b;

import com.xiaomi.accountsdk.diagnosis.b;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class a {
    private static final ThreadLocal<SimpleDateFormat> a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.accountsdk.diagnosis.b.a.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd", Locale.US);
        }
    };

    private static File a() {
        String format = a.get().format(new Date());
        File a2 = b.a();
        return new File(a2, format + ".log");
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v9 */
    public static void a(String str) {
        Throwable th;
        IOException e;
        BufferedWriter bufferedWriter;
        File a2 = a();
        File parentFile = a2.getParentFile();
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
                bufferedWriter = new BufferedWriter(new FileWriter(a2, true));
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            bufferedWriter.append((CharSequence) str);
            bufferedWriter.newLine();
            a(bufferedWriter);
        } catch (IOException e3) {
            e = e3;
            bufferedWriter2 = bufferedWriter;
            e.printStackTrace();
            a(bufferedWriter2);
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2 = bufferedWriter;
            a((Closeable) bufferedWriter2);
            throw th;
        }
    }
}
