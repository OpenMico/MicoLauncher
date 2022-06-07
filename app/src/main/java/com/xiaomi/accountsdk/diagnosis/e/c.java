package com.xiaomi.accountsdk.diagnosis.e;

import com.xiaomi.accountsdk.diagnosis.b;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* loaded from: classes2.dex */
public class c {
    private static final ThreadLocal<SimpleDateFormat> a = new ThreadLocal<SimpleDateFormat>() { // from class: com.xiaomi.accountsdk.diagnosis.e.c.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
        }
    };

    public static File a() {
        File b = b();
        File parentFile = b.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            return null;
        }
        try {
            Runtime.getRuntime().exec("logcat -d -f " + b.getAbsolutePath()).waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return b;
    }

    private static File b() {
        String format = a.get().format(new Date());
        File b = b.b();
        return new File(b, format + ".logcat");
    }
}
