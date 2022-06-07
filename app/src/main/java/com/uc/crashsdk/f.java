package com.uc.crashsdk;

import android.util.SparseArray;
import android.util.SparseIntArray;
import com.uc.crashsdk.a.a;
import com.uc.crashsdk.a.e;
import com.uc.crashsdk.a.g;
import com.uc.crashsdk.a.h;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class f {
    static final /* synthetic */ boolean a = !f.class.desiredAssertionStatus();
    private static final Map<String, SparseIntArray> b = new HashMap();
    private static final Object c = new Object();
    private static final SparseArray<String> d = new SparseArray<>();
    private static final Object e = new Object();
    private static boolean f = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int i) {
        a(i, 1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(int i, int i2) {
        if (i2 == 0) {
            a.b("Add stat for type " + i + " with count 0!");
            return;
        }
        a(b.c(), new e(751, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    private static boolean b(int i, int i2) {
        try {
            b.u();
        } catch (Throwable th) {
            g.a(th);
        }
        try {
            String c2 = c(i);
            if (c2 == null) {
                a.a("crashsdk", "Stat type not exists: " + i, null);
                return false;
            }
            File file = new File(b.c());
            if (!file.exists()) {
                file.createNewFile();
            }
            StringBuffer a2 = a(file);
            if (g.a(a2)) {
                if (a2 == null) {
                    a2 = new StringBuffer();
                }
                a2.append("[");
                a2.append(e.h());
                a2.append("]\n");
            }
            a(a2, c2, a(a2, c2) + i2);
            return a(file, a2);
        } catch (Throwable th2) {
            g.a(th2);
            return false;
        }
    }

    private static StringBuffer a(File file) {
        Throwable th;
        StringBuffer stringBuffer;
        Exception e2;
        FileReader fileReader;
        FileReader fileReader2 = null;
        if (!file.exists()) {
            return null;
        }
        char[] d2 = d();
        if (d2 == null) {
            a.a("crashsdk", "readCrashStatData alloc buffer failed!", null);
            return null;
        }
        try {
            stringBuffer = new StringBuffer();
            try {
                fileReader = new FileReader(file);
            } catch (Exception e3) {
                e2 = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            int read = fileReader.read(d2);
            if (read > 0) {
                fileReader2 = null;
                stringBuffer.append(d2, 0, read);
            }
            g.a(fileReader);
        } catch (Exception e4) {
            e2 = e4;
            fileReader2 = fileReader;
            g.a(e2);
            g.a(fileReader2);
            return stringBuffer;
        } catch (Throwable th3) {
            th = th3;
            fileReader2 = fileReader;
            g.a(fileReader2);
            throw th;
        }
        return stringBuffer;
    }

    private static int a(StringBuffer stringBuffer, String str) {
        int indexOf = stringBuffer.indexOf(str);
        if (indexOf < 0) {
            return 0;
        }
        int indexOf2 = stringBuffer.indexOf("=", indexOf + str.length());
        if (indexOf2 < 0) {
            a.b(str + " line not contain '='!");
            return 0;
        }
        int i = indexOf2 + 1;
        int indexOf3 = stringBuffer.indexOf("\n", i);
        if (indexOf3 < 0) {
            indexOf3 = stringBuffer.length();
        }
        try {
            int parseInt = Integer.parseInt(stringBuffer.substring(i, indexOf3));
            if (parseInt < 0) {
                return 0;
            }
            return parseInt;
        } catch (NumberFormatException e2) {
            g.a(e2);
            return 0;
        }
    }

    private static void a(StringBuffer stringBuffer, String str, int i) {
        int indexOf = stringBuffer.indexOf(str);
        if (indexOf >= 0) {
            int indexOf2 = stringBuffer.indexOf("\n", indexOf);
            if (indexOf2 < 0) {
                indexOf2 = stringBuffer.length();
            }
            stringBuffer.replace(indexOf, indexOf2, str + "=" + String.valueOf(i));
        } else if (i > 0) {
            stringBuffer.append(str);
            stringBuffer.append("=");
            stringBuffer.append(i);
            stringBuffer.append("\n");
        }
    }

    private static boolean a(File file, StringBuffer stringBuffer) {
        Throwable th;
        Exception e2;
        FileWriter fileWriter;
        FileWriter fileWriter2 = null;
        try {
            try {
                fileWriter = new FileWriter(file);
            } catch (Exception e3) {
                e2 = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            String stringBuffer2 = stringBuffer.toString();
            fileWriter.write(stringBuffer2, 0, stringBuffer2.length());
            g.a(fileWriter);
            return true;
        } catch (Exception e4) {
            e2 = e4;
            fileWriter2 = fileWriter;
            g.a(e2);
            g.a(fileWriter2);
            return false;
        } catch (Throwable th3) {
            th = th3;
            fileWriter2 = fileWriter;
            g.a(fileWriter2);
            throw th;
        }
    }

    private static boolean b(String str) {
        e();
        synchronized (d) {
            File file = new File(str);
            StringBuffer a2 = a(file);
            if (g.a(a2)) {
                return false;
            }
            int indexOf = a2.indexOf("[");
            if (indexOf < 0) {
                a.a("crashsdk", "Can not found process name start!", null);
                return false;
            }
            int i = indexOf + 1;
            int indexOf2 = a2.indexOf("]", i);
            if (indexOf2 < 0) {
                a.a("crashsdk", "Can not found process name end!", null);
                return false;
            }
            String substring = a2.substring(i, indexOf2);
            boolean z = false;
            for (int i2 = 0; i2 < d.size(); i2++) {
                int keyAt = d.keyAt(i2);
                String str2 = d.get(keyAt);
                int a3 = a(a2, str2);
                if (a3 > 0) {
                    h.a(substring, keyAt, a3);
                    synchronized (b) {
                        SparseIntArray sparseIntArray = b.get(substring);
                        if (sparseIntArray == null) {
                            sparseIntArray = new SparseIntArray();
                            b.put(substring, sparseIntArray);
                        }
                        sparseIntArray.put(keyAt, sparseIntArray.get(keyAt, 0) + a3);
                    }
                    a(a2, str2, 0);
                    z = true;
                }
            }
            if (z) {
                a(file, a2);
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    public static int a(boolean z) {
        int i;
        synchronized (b) {
            if (z) {
                String h = e.h();
                boolean c2 = c(h);
                b.remove(h);
                i = c2;
            } else {
                int i2 = 0;
                for (String str : b.keySet()) {
                    if (c(str)) {
                        i2 = (i2 == 1 ? 1 : 0) + 1;
                    }
                }
                b.clear();
                i = i2;
            }
        }
        return i == 1 ? 1 : 0;
    }

    private static boolean c(String str) {
        SparseIntArray sparseIntArray = b.get(str);
        if (sparseIntArray == null) {
            return false;
        }
        for (int i = 0; i < sparseIntArray.size(); i++) {
            int keyAt = sparseIntArray.keyAt(i);
            d.a(str, keyAt, sparseIntArray.get(keyAt));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b(boolean z) {
        int i;
        synchronized (b) {
            if (z) {
                String h = e.h();
                if (b.containsKey(h)) {
                    b.remove(h);
                    i = 1;
                } else {
                    i = 0;
                }
            } else {
                i = b.size();
                b.clear();
            }
        }
        return i;
    }

    private static char[] d() {
        char[] cArr = null;
        int i = 1024;
        while (cArr == null && i > 0) {
            try {
                cArr = new char[i];
            } catch (Throwable unused) {
                i /= 2;
                if (i < 512) {
                    break;
                }
            }
        }
        return cArr;
    }

    private static boolean a(String str, e eVar) {
        return b.a(c, str, eVar);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str, boolean z) {
        if (h.a(z, "crash detail report")) {
            return false;
        }
        return a(str, new e(752, new Object[]{str}));
    }

    private static String c(int i) {
        String str;
        e();
        synchronized (d) {
            str = d.get(i);
        }
        return str;
    }

    private static void e() {
        synchronized (d) {
            if (d.size() == 0) {
                d.put(100, "start_pv");
                d.put(102, "start_hpv");
                d.put(1, "all_all");
                d.put(2, "all_fg");
                d.put(101, "all_bg");
                d.put(3, "java_fg");
                d.put(4, "java_bg");
                d.put(7, "native_fg");
                d.put(8, "native_bg");
                d.put(27, "native_anr_fg");
                d.put(28, "native_anr_bg");
                d.put(9, "native_ok");
                d.put(10, "unexp_anr");
                d.put(29, "unexp_lowmem");
                d.put(30, "unexp_killed");
                d.put(31, "unexp_exit");
                d.put(32, "unexp_restart");
                d.put(11, "unexp_fg");
                d.put(12, "unexp_bg");
                d.put(40, "anr_fg");
                d.put(41, "anr_bg");
                d.put(42, "anr_cr_fg");
                d.put(43, "anr_cr_bg");
                d.put(13, "log_up_succ");
                d.put(14, "log_up_fail");
                d.put(15, "log_empty");
                d.put(200, "log_tmp");
                d.put(16, "log_abd_all");
                d.put(22, "log_abd_builtin");
                d.put(23, "log_abd_custom");
                d.put(17, "log_large");
                d.put(18, "log_up_all");
                d.put(19, "log_up_bytes");
                d.put(20, "log_up_crash");
                d.put(21, "log_up_custom");
                d.put(24, "log_zipped");
                d.put(201, "log_enced");
                d.put(25, "log_renamed");
                d.put(26, "log_safe_skip");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void c(boolean z) {
        if (g.O() && !b.I()) {
            e.j();
            if (!h.e()) {
                h.a(z);
            }
            if (b.C()) {
                d(z);
                a(b.c(), z);
                h.b(z);
            }
        }
    }

    private static File[] f() {
        File[] listFiles = new File(g.U()).listFiles();
        if (listFiles == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (File file : listFiles) {
            if (file.getPath().endsWith(".st")) {
                arrayList.add(file);
            }
        }
        return (File[]) arrayList.toArray(new File[arrayList.size()]);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int a() {
        File[] f2 = f();
        if (f2 == null) {
            return 0;
        }
        int i = 0;
        for (File file : f2) {
            if (a(file.getAbsolutePath(), false)) {
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean a(String str) {
        return a(str, new e(753, new Object[]{str}));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int b() {
        File[] f2 = f();
        if (f2 == null) {
            return 0;
        }
        int i = 0;
        for (File file : f2) {
            if (a(file.getAbsolutePath())) {
                i++;
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004a A[Catch: all -> 0x0056, TryCatch #0 {, blocks: (B:7:0x0008, B:9:0x000c, B:11:0x000e, B:13:0x0016, B:15:0x0018, B:17:0x0020, B:19:0x002a, B:21:0x0031, B:23:0x0037, B:25:0x0042, B:27:0x004a, B:28:0x004d, B:29:0x0054), top: B:34:0x0008 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void d(boolean r3) {
        /*
            boolean r0 = com.uc.crashsdk.f.f
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            java.lang.Object r0 = com.uc.crashsdk.f.e
            monitor-enter(r0)
            boolean r1 = com.uc.crashsdk.f.f     // Catch: all -> 0x0056
            if (r1 == 0) goto L_0x000e
            monitor-exit(r0)     // Catch: all -> 0x0056
            return
        L_0x000e:
            java.lang.String r1 = "crash detail"
            boolean r3 = com.uc.crashsdk.a.h.a(r3, r1)     // Catch: all -> 0x0056
            if (r3 == 0) goto L_0x0018
            monitor-exit(r0)     // Catch: all -> 0x0056
            return
        L_0x0018:
            r3 = 0
            boolean r1 = com.uc.crashsdk.b.p()     // Catch: all -> 0x0056
            r2 = 1
            if (r1 == 0) goto L_0x0031
            r3 = 2
            a(r3, r2)     // Catch: all -> 0x0056
            boolean r3 = com.uc.crashsdk.b.o()     // Catch: all -> 0x0056
            if (r3 == 0) goto L_0x002f
            r3 = 42
            a(r3, r2)     // Catch: all -> 0x0056
        L_0x002f:
            r3 = r2
            goto L_0x0048
        L_0x0031:
            boolean r1 = com.uc.crashsdk.b.q()     // Catch: all -> 0x0056
            if (r1 == 0) goto L_0x0048
            r3 = 101(0x65, float:1.42E-43)
            a(r3, r2)     // Catch: all -> 0x0056
            boolean r3 = com.uc.crashsdk.b.o()     // Catch: all -> 0x0056
            if (r3 == 0) goto L_0x002f
            r3 = 43
            a(r3, r2)     // Catch: all -> 0x0056
            goto L_0x002f
        L_0x0048:
            if (r3 == 0) goto L_0x004d
            a(r2, r2)     // Catch: all -> 0x0056
        L_0x004d:
            r3 = 100
            a(r3, r2)     // Catch: all -> 0x0056
            com.uc.crashsdk.f.f = r2     // Catch: all -> 0x0056
            monitor-exit(r0)     // Catch: all -> 0x0056
            return
        L_0x0056:
            r3 = move-exception
            monitor-exit(r0)     // Catch: all -> 0x0056
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.f.d(boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void c() {
        com.uc.crashsdk.a.f.a(1, new e(700), 3000L);
    }

    public static void b(int i) {
        if (i == 700) {
            d(false);
        }
    }

    public static boolean a(int i, Object[] objArr) {
        switch (i) {
            case 751:
                if (a || objArr != null) {
                    return b(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue());
                }
                throw new AssertionError();
            case 752:
                if (a || objArr != null) {
                    return b((String) objArr[0]);
                }
                throw new AssertionError();
            case 753:
                if (a || objArr != null) {
                    File file = new File((String) objArr[0]);
                    if (!file.exists()) {
                        return false;
                    }
                    file.delete();
                    return true;
                }
                throw new AssertionError();
            default:
                return false;
        }
    }
}
