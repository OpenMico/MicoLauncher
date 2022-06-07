package com.xiaomi.onetrack;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micolauncher.module.skill.manager.SkillStatHelper;
import com.xiaomi.onetrack.api.f;
import com.xiaomi.onetrack.c.d;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.aa;
import com.xiaomi.onetrack.util.ac;
import com.xiaomi.onetrack.util.b;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.k;
import com.xiaomi.onetrack.util.p;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class CrashAnalysis {
    public static final String ANR_CRASH = "anr";
    public static final String JAVA_CRASH = "java";
    public static final String NATIVE_CRASH = "native";
    private static final String a = "CrashAnalysis";
    private static final String b = "com.xiaomi.digest.DigestUtil";
    private static final String c = "calcuateJavaDigest";
    private static final String d = "/sdcard/tombstone";
    private static final String e = "backtrace feature id:\n\t";
    private static final String f = "error reason:\n\t";
    private static final String g = "Crash time: '";
    private static final String h = ".xcrash";
    private static final int i = 604800000;
    private static final int j = 102400;
    private static final int k = 10;
    private static final int l = 20;
    private static final String m = "@[0-9a-fA-F]{1,10}";
    private static final String n = "\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}";
    private static final String o = "0x[0-9a-fA-F]{1,10}";
    private static final String p = "\\d+[B,KB,MB]*";
    private static final String q = "((java:)|(length=)|(index=)|(Index:)|(Size:))\\d+";
    private static final int r = 20;
    private static final boolean s = false;
    private static final AtomicBoolean t = new AtomicBoolean(false);
    private final FileProcessor[] u;
    private final f v;

    private CrashAnalysis(Context context, f fVar) {
        try {
            Object newInstance = Class.forName("xcrash.XCrash$InitParameters").getConstructor(new Class[0]).newInstance(new Object[0]);
            a(newInstance, "setNativeDumpAllThreads", false);
            a(newInstance, "setLogDir", a());
            a(newInstance, "setNativeDumpMap", false);
            a(newInstance, "setNativeDumpFds", false);
            a(newInstance, "setJavaDumpAllThreads", false);
            a(newInstance, "setAnrRethrow", false);
            Class.forName("xcrash.XCrash").getDeclaredMethod("init", Context.class, newInstance.getClass()).invoke(null, context.getApplicationContext(), newInstance);
            p.a(a, "XCrash init success");
        } catch (Throwable th) {
            p.a(a, "XCrash init failed: " + th.toString());
        }
        this.v = fVar;
        this.u = new FileProcessor[]{new FileProcessor("java"), new FileProcessor("anr"), new FileProcessor(NATIVE_CRASH)};
    }

    public static boolean isSupport() {
        try {
            Class.forName("xcrash.XCrash");
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void a(Context context) {
        try {
            a.a(context);
            Class.forName("xcrash.XCrash").getDeclaredMethod("initHooker", Context.class, String.class).invoke(null, context.getApplicationContext(), a());
            Log.d(a, "registerHook succeeded");
        } catch (Throwable th) {
            Log.d(a, "registerHook failed: " + th.toString());
        }
    }

    public static void start(final Context context, final f fVar) {
        if (t.compareAndSet(false, true)) {
            i.a(new Runnable() { // from class: com.xiaomi.onetrack.CrashAnalysis.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        CrashAnalysis crashAnalysis = new CrashAnalysis(context, fVar);
                        if (crashAnalysis.d()) {
                            crashAnalysis.e();
                        } else {
                            p.a(CrashAnalysis.a, "no crash file found");
                        }
                    } catch (Throwable th) {
                        p.b(CrashAnalysis.a, "processCrash error: " + th.toString());
                    }
                }
            });
        } else {
            p.b(a, "run method has been invoked more than once");
        }
    }

    public static String c(String str, String str2) {
        int i2;
        int indexOf;
        int indexOf2;
        int indexOf3;
        String str3 = "uncategoried";
        if (!TextUtils.isEmpty(str)) {
            try {
                if (str2.equals("anr")) {
                    int indexOf4 = str.indexOf(" tid=1 ");
                    if (!(indexOf4 == -1 || (indexOf2 = str.indexOf("\n  at ", indexOf4)) == -1 || (indexOf3 = str.indexOf(10, indexOf2 + 6)) == -1)) {
                        str3 = str.substring(indexOf2 + 2, indexOf3);
                    }
                } else {
                    int indexOf5 = str.indexOf(f);
                    if (!(indexOf5 == -1 || (indexOf = str.indexOf("\n\n", (i2 = indexOf5 + 15))) == -1)) {
                        str3 = str.substring(i2, indexOf);
                    }
                }
            } catch (Exception e2) {
                p.b(a, "getErrorReasonString error: " + e2.toString());
            }
        }
        return str3;
    }

    public static String d(String str, String str2) {
        int i2;
        int indexOf;
        int indexOf2;
        String str3 = "";
        if (!TextUtils.isEmpty(str)) {
            try {
                if (str2.equals("anr")) {
                    int indexOf3 = str.indexOf(" tid=1 ");
                    if (!(indexOf3 == -1 || (indexOf2 = str.indexOf("\n\n", indexOf3)) == -1)) {
                        str3 = calculateJavaDigest(str.substring(indexOf3, indexOf2));
                    }
                } else {
                    int indexOf4 = str.indexOf(e);
                    if (!(indexOf4 == -1 || (indexOf = str.indexOf("\n\n", (i2 = indexOf4 + 23))) == -1)) {
                        str3 = str.substring(i2, indexOf);
                    }
                }
            } catch (Exception e2) {
                p.b(a, "calculateFeatureId error: " + e2.toString());
            }
        }
        return str3;
    }

    public static long b(String str) {
        int i2;
        int indexOf;
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            int indexOf2 = str.indexOf(g);
            if (indexOf2 == -1 || (indexOf = str.indexOf("'\n", (i2 = indexOf2 + 13))) == -1) {
                return 0L;
            }
            return b.a(str.substring(i2, indexOf));
        } catch (Exception e2) {
            p.b(a, "getCrashTimeStamp error: " + e2.toString());
            return 0L;
        }
    }

    private void a(Object obj, String str, Object obj2) throws Exception {
        obj.getClass().getDeclaredMethod(str, obj2.getClass() == Boolean.class ? Boolean.TYPE : obj2.getClass()).invoke(obj, obj2);
    }

    private static String a() {
        return k.a();
    }

    private long b() {
        long c2 = aa.c();
        if (c2 == 0) {
            p.a(a, "no ticket data found, return max count");
            return 10L;
        }
        long b2 = ac.b();
        if (c2 / 100 != b2) {
            p.a(a, "no today's ticket, return max count");
            return 10L;
        }
        long j2 = c2 - (b2 * 100);
        p.a(a, "today's remain ticket is " + j2);
        return j2;
    }

    private void a(long j2) {
        aa.d((ac.b() * 100) + j2);
    }

    private List<File> c() {
        File[] listFiles = new File(a()).listFiles();
        if (listFiles == null) {
            p.a(a, "this path does not denote a directory, or if an I/O error occurs.");
            return null;
        }
        List<File> asList = Arrays.asList(listFiles);
        Collections.sort(asList, new Comparator<File>() { // from class: com.xiaomi.onetrack.CrashAnalysis.2
            public int compare(File file, File file2) {
                return (int) (file.lastModified() - file2.lastModified());
            }
        });
        int size = asList.size();
        if (size <= 20) {
            return asList;
        }
        int i2 = size - 20;
        for (int i3 = 0; i3 < i2; i3++) {
            k.a(asList.get(i3));
        }
        return asList.subList(i2, size);
    }

    public boolean d() {
        boolean z;
        List<File> c2 = c();
        long b2 = b();
        if (c2 == null || c2.size() <= 0) {
            z = false;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long b3 = aa.b();
            long j2 = 604800000;
            if (b3 > currentTimeMillis) {
                b3 = currentTimeMillis - 604800000;
            }
            long j3 = 0;
            long j4 = b2;
            long j5 = 0;
            boolean z2 = false;
            for (File file : c2) {
                long lastModified = file.lastModified();
                if (lastModified < currentTimeMillis - j2 || lastModified > currentTimeMillis) {
                    p.a(a, "remove obsolete crash files: " + file.getName());
                    k.a(file);
                } else if (lastModified <= b3) {
                    p.a(a, "found already reported crash file, ignore");
                } else {
                    if (j4 > j3) {
                        long j6 = j5;
                        for (FileProcessor fileProcessor : this.u) {
                            if (fileProcessor.a(file)) {
                                p.a(a, "find crash file:" + file.getName());
                                j4--;
                                if (j6 < lastModified) {
                                    z2 = true;
                                    j6 = lastModified;
                                } else {
                                    z2 = true;
                                }
                            }
                        }
                        j5 = j6;
                    }
                    j2 = 604800000;
                    j3 = 0;
                }
                j2 = 604800000;
                j3 = 0;
            }
            if (j5 > j3) {
                aa.c(j5);
            }
            z = z2;
            b2 = j4;
        }
        if (z) {
            a(b2);
        }
        return z;
    }

    public void e() {
        for (FileProcessor fileProcessor : this.u) {
            fileProcessor.a();
        }
    }

    /* loaded from: classes4.dex */
    public class FileProcessor {
        final List<File> a = new ArrayList();
        final String b;
        final String c;

        FileProcessor(String str) {
            CrashAnalysis.this = r1;
            this.c = str;
            this.b = str + CrashAnalysis.h;
        }

        boolean a(File file) {
            if (!file.getName().contains(this.b)) {
                return false;
            }
            this.a.add(file);
            return true;
        }

        private String a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            String[] split = str.split("__");
            if (split.length != 2) {
                return null;
            }
            String[] split2 = split[0].split("_");
            if (split2.length == 3) {
                return split2[2];
            }
            return null;
        }

        void a() {
            for (int i = 0; i < this.a.size(); i++) {
                String absolutePath = this.a.get(i).getAbsoluteFile().getAbsolutePath();
                String a = a(absolutePath);
                String a2 = k.a(absolutePath, (int) CrashAnalysis.j);
                if (!TextUtils.isEmpty(a2) && CrashAnalysis.this.v != null) {
                    String d = CrashAnalysis.d(a2, this.c);
                    String c = CrashAnalysis.c(a2, this.c);
                    long b = CrashAnalysis.b(a2);
                    p.a(CrashAnalysis.a, "fileName: " + absolutePath);
                    p.a(CrashAnalysis.a, "feature id: " + d);
                    p.a(CrashAnalysis.a, "error: " + c);
                    p.a(CrashAnalysis.a, "crashTimeStamp: " + b);
                    CrashAnalysis.this.v.a(a2, c, this.c, a, d, b);
                    k.a(new File(absolutePath));
                    p.a(CrashAnalysis.a, "remove reported crash file");
                }
            }
        }
    }

    public static String calculateJavaDigest(String str) {
        String[] split = str.replaceAll("\\t", "").split("\\n");
        StringBuilder sb = new StringBuilder();
        int min = Math.min(split.length, 20);
        for (int i2 = 0; i2 < min; i2++) {
            split[i2] = split[i2].replaceAll(q, "$1XX").replaceAll("\\$[0-9a-fA-F]{1,10}@[0-9a-fA-F]{1,10}|@[0-9a-fA-F]{1,10}|0x[0-9a-fA-F]{1,10}", "XX").replaceAll(p, "");
        }
        for (int i3 = 0; i3 < min && (!split[i3].contains("...") || !split[i3].contains(SkillStatHelper.SKILL_STAT_MORE)); i3++) {
            sb.append(split[i3]);
            sb.append('\n');
        }
        return d.h(sb.toString());
    }
}
