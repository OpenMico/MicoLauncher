package com.xiaomi.onetrack.b;

import android.content.Context;
import com.xiaomi.onetrack.api.d;
import com.xiaomi.onetrack.c.c;
import com.xiaomi.onetrack.e.a;
import com.xiaomi.onetrack.util.i;
import com.xiaomi.onetrack.util.m;
import com.xiaomi.onetrack.util.p;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class g {
    public static final long a = 0;
    public static final long b = 1;
    public static final long c = 2;
    public static final String d = "eventName";
    public static final String e = "data";
    private static final String f = "NetworkAccessManager";
    private static final String g = "networkAccess";
    private static String h = "onetrack_netaccess_%s";
    private static SimpleDateFormat i = new SimpleDateFormat("yyyyMMdd");
    private static boolean j = false;
    private static volatile boolean k = true;
    private static volatile boolean l = false;

    public static boolean a() {
        return j;
    }

    public static void a(boolean z) {
        j = z;
    }

    public static boolean b() {
        return !new File(a.a().getFilesDir(), ".ot_net_disallowed").exists();
    }

    public static void b(boolean z) {
        File file = new File(a.a().getFilesDir(), ".ot_net_allowed");
        File file2 = new File(a.a().getFilesDir(), ".ot_net_disallowed");
        try {
            if (z) {
                file.createNewFile();
                if (file2.exists()) {
                    file2.delete();
                }
            } else {
                file2.createNewFile();
                if (file.exists()) {
                    file.delete();
                }
            }
        } catch (IOException e2) {
            p.b(f, "setNetworkAccessStateEnabled: " + z + "failed ", e2);
        }
    }

    private static String e() {
        Context a2 = a.a();
        return a2.getFilesDir().getAbsolutePath() + File.separator + g;
    }

    public static void a(String str, String str2) {
        i.a(new h(str, str2));
    }

    public static synchronized void c(String str, String str2) {
        FileWriter fileWriter;
        Throwable th;
        Exception e2;
        byte[] a2;
        BufferedWriter bufferedWriter;
        synchronized (g.class) {
            File file = new File(e(), String.format(h, i.format(new Date())));
            BufferedWriter bufferedWriter2 = null;
            try {
                if (!file.exists()) {
                    if (file.getParentFile().exists()) {
                        file.createNewFile();
                    } else {
                        new File(file.getParentFile().getAbsolutePath()).mkdirs();
                        file.createNewFile();
                    }
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(d, str);
                jSONObject.put("data", str2);
                a2 = b.a(jSONObject.toString());
                fileWriter = new FileWriter(file, true);
                try {
                    try {
                        bufferedWriter = new BufferedWriter(fileWriter);
                    } catch (Exception e3) {
                        e2 = e3;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e4) {
                e2 = e4;
                fileWriter = null;
            } catch (Throwable th3) {
                th = th3;
                fileWriter = null;
            }
            try {
                bufferedWriter.write(c.a(a2));
                bufferedWriter.newLine();
                k = true;
                m.a(bufferedWriter);
            } catch (Exception e5) {
                e2 = e5;
                bufferedWriter2 = bufferedWriter;
                p.b(f, "cta doSaveData error: " + e2.toString());
                e2.printStackTrace();
                m.a(bufferedWriter2);
                m.a(fileWriter);
            } catch (Throwable th4) {
                th = th4;
                bufferedWriter2 = bufferedWriter;
                m.a(bufferedWriter2);
                m.a(fileWriter);
                throw th;
            }
            m.a(fileWriter);
        }
    }

    public static synchronized void c(boolean z) {
        File file;
        synchronized (g.class) {
            try {
                file = new File(e());
            } catch (Exception e2) {
                p.b(f, "cta removeObsoleteEvent error: " + e2.toString());
                e2.printStackTrace();
            }
            if (file.exists() && file.isDirectory()) {
                String format = String.format(h, i.format(new Date()));
                File[] listFiles = file.listFiles();
                for (int i2 = 0; i2 < listFiles.length; i2++) {
                    if (listFiles[i2].isFile() && (z || !listFiles[i2].getName().equalsIgnoreCase(format))) {
                        listFiles[i2].delete();
                    }
                }
                if (file.listFiles().length == 0) {
                    k = false;
                }
                return;
            }
            k = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0094 A[Catch: all -> 0x00b3, TryCatch #2 {, blocks: (B:4:0x0003, B:9:0x002c, B:15:0x0052, B:16:0x0055, B:27:0x0088, B:28:0x008c, B:30:0x0094, B:31:0x00a1, B:33:0x00a7, B:37:0x00ac, B:38:0x00b2), top: B:41:0x0003 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a7 A[Catch: all -> 0x00b3, TRY_LEAVE, TryCatch #2 {, blocks: (B:4:0x0003, B:9:0x002c, B:15:0x0052, B:16:0x0055, B:27:0x0088, B:28:0x008c, B:30:0x0094, B:31:0x00a1, B:33:0x00a7, B:37:0x00ac, B:38:0x00b2), top: B:41:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static synchronized java.util.List<org.json.JSONObject> c() {
        /*
            java.lang.Class<com.xiaomi.onetrack.b.g> r0 = com.xiaomi.onetrack.b.g.class
            monitor-enter(r0)
            java.text.SimpleDateFormat r1 = com.xiaomi.onetrack.b.g.i     // Catch: all -> 0x00b3
            java.util.Date r2 = new java.util.Date     // Catch: all -> 0x00b3
            r2.<init>()     // Catch: all -> 0x00b3
            java.lang.String r1 = r1.format(r2)     // Catch: all -> 0x00b3
            java.lang.String r2 = com.xiaomi.onetrack.b.g.h     // Catch: all -> 0x00b3
            r3 = 1
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: all -> 0x00b3
            r5 = 0
            r4[r5] = r1     // Catch: all -> 0x00b3
            java.lang.String r1 = java.lang.String.format(r2, r4)     // Catch: all -> 0x00b3
            java.io.File r2 = new java.io.File     // Catch: all -> 0x00b3
            java.lang.String r4 = e()     // Catch: all -> 0x00b3
            r2.<init>(r4, r1)     // Catch: all -> 0x00b3
            boolean r1 = r2.exists()     // Catch: all -> 0x00b3
            r4 = 0
            if (r1 != 0) goto L_0x002c
            monitor-exit(r0)
            return r4
        L_0x002c:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: all -> 0x00b3
            r1.<init>()     // Catch: all -> 0x00b3
            java.io.FileReader r5 = new java.io.FileReader     // Catch: Exception -> 0x0067, all -> 0x0063
            r5.<init>(r2)     // Catch: Exception -> 0x0067, all -> 0x0063
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch: Exception -> 0x005e, all -> 0x005b
            r2.<init>(r5)     // Catch: Exception -> 0x005e, all -> 0x005b
        L_0x003b:
            java.lang.String r4 = r2.readLine()     // Catch: Exception -> 0x0059, all -> 0x00ab
            if (r4 == 0) goto L_0x0052
            byte[] r4 = com.xiaomi.onetrack.c.c.a(r4)     // Catch: Exception -> 0x0059, all -> 0x00ab
            java.lang.String r4 = com.xiaomi.onetrack.b.b.a(r4)     // Catch: Exception -> 0x0059, all -> 0x00ab
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch: Exception -> 0x0059, all -> 0x00ab
            r6.<init>(r4)     // Catch: Exception -> 0x0059, all -> 0x00ab
            r1.add(r6)     // Catch: Exception -> 0x0059, all -> 0x00ab
            goto L_0x003b
        L_0x0052:
            com.xiaomi.onetrack.util.m.a(r2)     // Catch: all -> 0x00b3
        L_0x0055:
            com.xiaomi.onetrack.util.m.a(r5)     // Catch: all -> 0x00b3
            goto L_0x008c
        L_0x0059:
            r4 = move-exception
            goto L_0x006b
        L_0x005b:
            r1 = move-exception
            r2 = r4
            goto L_0x00ac
        L_0x005e:
            r2 = move-exception
            r9 = r4
            r4 = r2
            r2 = r9
            goto L_0x006b
        L_0x0063:
            r1 = move-exception
            r2 = r4
            r5 = r2
            goto L_0x00ac
        L_0x0067:
            r2 = move-exception
            r5 = r4
            r4 = r2
            r2 = r5
        L_0x006b:
            java.lang.String r6 = "NetworkAccessManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: all -> 0x00ab
            r7.<init>()     // Catch: all -> 0x00ab
            java.lang.String r8 = "cta getCacheData error: "
            r7.append(r8)     // Catch: all -> 0x00ab
            java.lang.String r8 = r4.toString()     // Catch: all -> 0x00ab
            r7.append(r8)     // Catch: all -> 0x00ab
            java.lang.String r7 = r7.toString()     // Catch: all -> 0x00ab
            com.xiaomi.onetrack.util.p.b(r6, r7)     // Catch: all -> 0x00ab
            r4.printStackTrace()     // Catch: all -> 0x00ab
            com.xiaomi.onetrack.util.m.a(r2)     // Catch: all -> 0x00b3
            goto L_0x0055
        L_0x008c:
            int r2 = r1.size()     // Catch: all -> 0x00b3
            r4 = 100
            if (r2 <= r4) goto L_0x00a1
            int r2 = r1.size()     // Catch: all -> 0x00b3
            int r2 = r2 - r4
            int r4 = r1.size()     // Catch: all -> 0x00b3
            java.util.List r1 = r1.subList(r2, r4)     // Catch: all -> 0x00b3
        L_0x00a1:
            int r2 = r1.size()     // Catch: all -> 0x00b3
            if (r2 <= 0) goto L_0x00a9
            com.xiaomi.onetrack.b.g.k = r3     // Catch: all -> 0x00b3
        L_0x00a9:
            monitor-exit(r0)
            return r1
        L_0x00ab:
            r1 = move-exception
        L_0x00ac:
            com.xiaomi.onetrack.util.m.a(r2)     // Catch: all -> 0x00b3
            com.xiaomi.onetrack.util.m.a(r5)     // Catch: all -> 0x00b3
            throw r1     // Catch: all -> 0x00b3
        L_0x00b3:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.onetrack.b.g.c():java.util.List");
    }

    public static boolean d() {
        return !l && k;
    }

    public static synchronized void a(d dVar) {
        synchronized (g.class) {
            if (d()) {
                if (dVar != null && b()) {
                    l = true;
                    i.a(new i(dVar));
                }
            }
        }
    }
}
