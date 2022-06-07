package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes4.dex */
public final class NativeLibraryLoader {
    private static final InternalLogger a = InternalLoggerFactory.getInstance(NativeLibraryLoader.class);
    private static final String b = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    private static final File c;

    static {
        String str = SystemPropertyUtil.get("io.netty.native.workdir");
        if (str != null) {
            File file = new File(str);
            file.mkdirs();
            try {
                file = file.getAbsoluteFile();
            } catch (Exception unused) {
            }
            c = file;
            a.debug("-Dio.netty.native.workdir: " + c);
            return;
        }
        c = a();
        a.debug("-Dio.netty.native.workdir: " + c + " (io.netty.tmpdir)");
    }

    private static File a() {
        File file;
        File a2;
        try {
            a2 = a(SystemPropertyUtil.get("io.netty.tmpdir"));
        } catch (Exception unused) {
        }
        if (a2 != null) {
            InternalLogger internalLogger = a;
            internalLogger.debug("-Dio.netty.tmpdir: " + a2);
            return a2;
        }
        File a3 = a(SystemPropertyUtil.get("java.io.tmpdir"));
        if (a3 != null) {
            InternalLogger internalLogger2 = a;
            internalLogger2.debug("-Dio.netty.tmpdir: " + a3 + " (java.io.tmpdir)");
            return a3;
        }
        if (b()) {
            File a4 = a(System.getenv("TEMP"));
            if (a4 != null) {
                InternalLogger internalLogger3 = a;
                internalLogger3.debug("-Dio.netty.tmpdir: " + a4 + " (%TEMP%)");
                return a4;
            }
            String str = System.getenv("USERPROFILE");
            if (str != null) {
                File a5 = a(str + "\\AppData\\Local\\Temp");
                if (a5 != null) {
                    InternalLogger internalLogger4 = a;
                    internalLogger4.debug("-Dio.netty.tmpdir: " + a5 + " (%USERPROFILE%\\AppData\\Local\\Temp)");
                    return a5;
                }
                File a6 = a(str + "\\Local Settings\\Temp");
                if (a6 != null) {
                    InternalLogger internalLogger5 = a;
                    internalLogger5.debug("-Dio.netty.tmpdir: " + a6 + " (%USERPROFILE%\\Local Settings\\Temp)");
                    return a6;
                }
            }
        } else {
            File a7 = a(System.getenv("TMPDIR"));
            if (a7 != null) {
                InternalLogger internalLogger6 = a;
                internalLogger6.debug("-Dio.netty.tmpdir: " + a7 + " ($TMPDIR)");
                return a7;
            }
        }
        if (b()) {
            file = new File("C:\\Windows\\Temp");
        } else {
            file = new File("/tmp");
        }
        InternalLogger internalLogger7 = a;
        internalLogger7.warn("Failed to get the temporary directory; falling back to: " + file);
        return file;
    }

    private static File a(String str) {
        if (str == null) {
            return null;
        }
        File file = new File(str);
        file.mkdirs();
        if (!file.isDirectory()) {
            return null;
        }
        try {
            return file.getAbsoluteFile();
        } catch (Exception unused) {
            return file;
        }
    }

    private static boolean b() {
        return b.startsWith("windows");
    }

    private static boolean c() {
        return b.startsWith("macosx") || b.startsWith("osx");
    }

    public static void loadFirstAvailable(ClassLoader classLoader, String... strArr) {
        for (String str : strArr) {
            try {
                load(str, classLoader);
                return;
            } catch (Throwable th) {
                a.debug("Unable to load the library: " + str + '.', th);
            }
        }
        throw new IllegalArgumentException("Failed to load any of the given libraries: " + Arrays.toString(strArr));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ec A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void load(java.lang.String r6, java.lang.ClassLoader r7) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.NativeLibraryLoader.load(java.lang.String, java.lang.ClassLoader):void");
    }

    private NativeLibraryLoader() {
    }
}
