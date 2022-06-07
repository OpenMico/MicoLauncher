package com.bumptech.glide.load.engine.executor;

import android.os.Build;
import android.os.StrictMode;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

/* compiled from: RuntimeCompat.java */
/* loaded from: classes.dex */
final class a {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        return Build.VERSION.SDK_INT < 17 ? Math.max(b(), availableProcessors) : availableProcessors;
    }

    private static int b() {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            File file = new File("/sys/devices/system/cpu/");
            final Pattern compile = Pattern.compile("cpu[0-9]+");
            File[] listFiles = file.listFiles(new FilenameFilter() { // from class: com.bumptech.glide.load.engine.executor.a.1
                @Override // java.io.FilenameFilter
                public boolean accept(File file2, String str) {
                    return compile.matcher(str).matches();
                }
            });
            return Math.max(1, listFiles != null ? listFiles.length : 0);
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
