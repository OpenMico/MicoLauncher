package com.uc.crashsdk.a;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import com.uc.crashsdk.JNIBridge;
import com.uc.crashsdk.b;
import com.umeng.analytics.pro.ai;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;

/* compiled from: ProGuard */
/* loaded from: classes2.dex */
public class g {
    private static Context b;
    static final /* synthetic */ boolean a = !g.class.desiredAssertionStatus();
    private static String c = null;
    private static String d = null;
    private static String e = null;
    private static String f = null;
    private static String g = null;
    private static boolean h = false;
    private static final Object i = new Object();
    private static final char[] j = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void a(File file, File file2) {
        Throwable th;
        FileInputStream fileInputStream;
        byte[] bArr = new byte[524288];
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file2.isDirectory()) {
            file2 = new File(file2, file.getName());
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                while (true) {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            fileOutputStream2.write(bArr, 0, read);
                        } else {
                            a(fileInputStream);
                            a(fileOutputStream2);
                            return;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = fileOutputStream2;
                        a(fileInputStream);
                        a(fileOutputStream);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
        }
    }

    public static boolean a(File file) {
        String[] list;
        if (file.isDirectory() && (list = file.list()) != null) {
            for (String str : list) {
                if (!a(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static void b(File file) {
        a(file, "");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.Closeable, java.io.FileInputStream] */
    public static String c(File file) {
        Throwable th;
        Closeable closeable;
        Throwable th2;
        String str = "";
        boolean exists = file.exists();
        if (!exists) {
            return str;
        }
        try {
            closeable = null;
            try {
                exists = new FileInputStream(file);
            } catch (Throwable th3) {
                th2 = th3;
            }
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            byte[] bArr = new byte[256];
            StringBuilder sb = new StringBuilder();
            while (true) {
                int read = exists.read(bArr);
                if (read <= 0) {
                    break;
                }
                sb.append(new String(bArr, 0, read));
            }
            str = sb.toString();
            a((Closeable) exists);
            exists = sb;
        } catch (Throwable th5) {
            th2 = th5;
            closeable = exists;
            a(th2, false);
            a(closeable);
            exists = closeable;
            return str;
        }
        return str;
    }

    public static String d(File file) {
        return a(file, 64, true);
    }

    public static String a(File file, int i2, boolean z) {
        FileInputStream fileInputStream;
        Throwable th;
        Throwable th2;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                try {
                    byte[] bArr = new byte[i2];
                    int read = fileInputStream.read(bArr);
                    if (read > 0) {
                        String str = new String(bArr, 0, read);
                        a(fileInputStream);
                        return str;
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    if (z) {
                        a(th2, false);
                    }
                    a(fileInputStream);
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                a(fileInputStream);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            fileInputStream = null;
            a(fileInputStream);
            throw th;
        }
        a(fileInputStream);
        return null;
    }

    public static byte[] e(File file) {
        Throwable th;
        Throwable th2;
        FileInputStream fileInputStream = null;
        if (!file.exists()) {
            return null;
        }
        try {
            byte[] bArr = new byte[(int) file.length()];
            fileInputStream = new FileInputStream(file);
            try {
                try {
                    fileInputStream.read(bArr);
                    a(fileInputStream);
                    return bArr;
                } catch (Throwable th3) {
                    th2 = th3;
                    a(th2, false);
                    a(fileInputStream);
                    return null;
                }
            } catch (Throwable th4) {
                th = th4;
                a(fileInputStream);
                throw th;
            }
        } catch (Throwable th5) {
            th = th5;
            a(fileInputStream);
            throw th;
        }
    }

    public static ArrayList<String> a(File file, int i2) {
        Throwable th;
        Reader reader;
        ArrayList<String> arrayList = new ArrayList<>();
        Reader reader2 = null;
        try {
            reader = new FileReader(file);
        } catch (Throwable th2) {
            th = th2;
            reader2 = null;
        }
        try {
            reader2 = new BufferedReader(reader, 512);
            int i3 = 0;
            while (true) {
                try {
                    String readLine = reader2.readLine();
                    if (readLine == null) {
                        break;
                    }
                    arrayList.add(readLine);
                    i3++;
                    if (i2 > 0 && i3 >= i2) {
                        break;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    reader2 = reader;
                    try {
                        a(th, false);
                        a(reader2);
                        a(reader2);
                        return arrayList;
                    } catch (Throwable th4) {
                        th = th4;
                        reader = reader2;
                        a(reader);
                        a(reader2);
                        throw th;
                    }
                }
            }
            a(reader);
        } catch (Throwable th5) {
            th = th5;
            a(reader);
            a(reader2);
            throw th;
        }
        a(reader2);
        return arrayList;
    }

    public static boolean a(File file, byte[] bArr) {
        Throwable th;
        Throwable th2;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (Throwable th3) {
                th2 = th3;
            }
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            a(fileOutputStream);
            return true;
        } catch (Throwable th5) {
            th2 = th5;
            fileOutputStream2 = fileOutputStream;
            a(th2, false);
            a(fileOutputStream2);
            return false;
        }
    }

    public static boolean a(File file, String str) {
        Throwable th;
        Throwable th2;
        FileWriter fileWriter;
        FileWriter fileWriter2 = null;
        try {
            try {
                fileWriter = new FileWriter(file);
            } catch (Throwable th3) {
                th2 = th3;
            }
        } catch (Throwable th4) {
            th = th4;
        }
        try {
            fileWriter.write(str, 0, str.length());
            a(fileWriter);
            return true;
        } catch (Throwable th5) {
            th2 = th5;
            fileWriter2 = fileWriter;
            a(th2, false);
            a(fileWriter2);
            return false;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                a(th, true);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0027, code lost:
        if (r2.toLowerCase().startsWith("http") != false) goto L_0x0029;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String a(java.lang.String r2, java.lang.String r3, boolean r4) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r2)
            boolean r0 = r0.exists()
            r1 = 0
            if (r0 == 0) goto L_0x002a
            java.lang.String r2 = com.uc.crashsdk.a.b.a(r2)
            boolean r0 = a(r2)
            if (r0 == 0) goto L_0x0017
            goto L_0x002a
        L_0x0017:
            if (r4 == 0) goto L_0x0029
            java.lang.String r2 = r2.trim()
            java.lang.String r4 = r2.toLowerCase()
            java.lang.String r0 = "http"
            boolean r4 = r4.startsWith(r0)
            if (r4 == 0) goto L_0x002a
        L_0x0029:
            r1 = r2
        L_0x002a:
            if (r1 != 0) goto L_0x002d
            goto L_0x002e
        L_0x002d:
            r3 = r1
        L_0x002e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.g.a(java.lang.String, java.lang.String, boolean):java.lang.String");
    }

    public static boolean a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean a(StringBuffer stringBuffer) {
        return stringBuffer == null || stringBuffer.length() == 0;
    }

    public static boolean b(String str) {
        return !a(str);
    }

    public static long c(String str) {
        if (!a(str)) {
            try {
                long parseLong = Long.parseLong(str.trim());
                if (parseLong < 0) {
                    return 0L;
                }
                return parseLong;
            } catch (NumberFormatException unused) {
            }
        }
        return 0L;
    }

    public static void a(Throwable th) {
        a(th, false);
    }

    public static void b(Throwable th) {
        a(th, true);
    }

    private static void a(Throwable th, boolean z) {
        if (!z) {
            try {
                if (!com.uc.crashsdk.g.M()) {
                    return;
                }
            } catch (Throwable unused) {
                return;
            }
        }
        th.printStackTrace();
    }

    public static void a(Context context) {
        if (b != null) {
            a.b("mContext is existed");
        }
        b = context;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        c = applicationInfo.dataDir;
        d = applicationInfo.sourceDir;
        try {
            Field declaredField = ApplicationInfo.class.getDeclaredField("primaryCpuAbi");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(applicationInfo);
            if (obj != null && (obj instanceof String)) {
                e = (String) obj;
            }
        } catch (Throwable th) {
            a(th, false);
        }
    }

    public static Context a() {
        return b;
    }

    public static String b() {
        return c;
    }

    public static String c() {
        return d;
    }

    public static String d() {
        String str = e;
        return str != null ? str : "";
    }

    public static boolean e() {
        if (g()) {
            return true;
        }
        return f();
    }

    public static boolean f() {
        return Build.TAGS != null && Build.TAGS.contains("test-keys");
    }

    public static boolean g() {
        int indexOf;
        String i2 = i();
        if (!a(i2) && (indexOf = i2.indexOf(" root ")) > 0) {
            String substring = i2.substring(0, indexOf);
            if (substring.contains("x") || substring.contains(ai.az)) {
                return true;
            }
        }
        return false;
    }

    public static String h() {
        l();
        return a(f) ? "" : f;
    }

    public static String i() {
        l();
        return a(g) ? "" : g;
    }

    public static void j() {
        f.a(0, new e(800), 15000L);
    }

    public static void a(int i2) {
        if (i2 == 800) {
            l();
        } else if (!a) {
            throw new AssertionError();
        }
    }

    private static void l() {
        String trim;
        int indexOf;
        int indexOf2;
        if (!h) {
            synchronized (i) {
                if (!h) {
                    String a2 = a(new String[]{"sh", "-c", "type su"});
                    if (!a(a2) && (indexOf = (trim = a2.trim()).indexOf(32)) > 0 && trim.contains("/su") && (indexOf2 = trim.indexOf(47, indexOf + 1)) > 0) {
                        f = trim.substring(indexOf2);
                        String a3 = a(new String[]{"ls", "-l", f});
                        if (!a(a3)) {
                            g = a3.trim();
                        }
                    }
                    h = true;
                    k();
                }
            }
        }
    }

    public static void k() {
        if (b.d && h) {
            JNIBridge.set(123, f);
            JNIBridge.set(124, g);
        }
    }

    private static String a(String[] strArr) {
        Throwable th;
        InputStreamReader inputStreamReader;
        Throwable th2;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(Runtime.getRuntime().exec(strArr).getInputStream());
            try {
                bufferedReader = new BufferedReader(inputStreamReader, 512);
                try {
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine != null) {
                                sb.append(readLine);
                            } else {
                                String trim = sb.toString().trim();
                                a(bufferedReader);
                                a(inputStreamReader);
                                return trim;
                            }
                        }
                    } catch (Throwable th3) {
                        th2 = th3;
                        a(th2, false);
                        a(bufferedReader);
                        a(inputStreamReader);
                        return null;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    a(bufferedReader);
                    a(inputStreamReader);
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                a(bufferedReader);
                a(inputStreamReader);
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            inputStreamReader = null;
        }
    }

    public static String d(String str) {
        try {
            byte[] bytes = str.getBytes("utf-8");
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            byte[] digest = instance.digest();
            int length = digest.length;
            StringBuilder sb = new StringBuilder(length * 2);
            int i2 = length + 0;
            for (int i3 = 0; i3 < i2; i3++) {
                byte b2 = digest[i3];
                char c2 = j[(b2 & 240) >> 4];
                char c3 = j[b2 & 15];
                sb.append(c2);
                sb.append(c3);
            }
            return sb.toString();
        } catch (Exception e2) {
            a.a("crashsdk", "getMD5: ", e2);
            return null;
        }
    }
}
